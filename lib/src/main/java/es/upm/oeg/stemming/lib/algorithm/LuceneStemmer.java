package es.upm.oeg.stemming.lib.algorithm;

import com.google.common.base.Strings;
import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.LanguageCode;
import es.upm.oeg.stemming.lib.domain.Analysis;
import es.upm.oeg.stemming.lib.domain.Document;
import es.upm.oeg.stemming.lib.domain.Keyword;
import org.apache.commons.lang.LocaleUtils;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by cbadenes on 09/07/15.
 */
public abstract class LuceneStemmer implements IStemmer{


    protected static Version LUCENE_VERSION = Version.LUCENE_36;


    protected abstract String stemmize(String term, Locale language) throws IOException;


    protected Map<String,Set<?>> stopwordsMap;

    public LuceneStemmer(){
        this.stopwordsMap = new HashMap<>();
        this.stopwordsMap.put(LanguageCode.en.toLocale().getLanguage(),EnglishAnalyzer.getDefaultStopSet());
        this.stopwordsMap.put(LanguageCode.es.toLocale().getLanguage(),SpanishAnalyzer.getDefaultStopSet());
        this.stopwordsMap.put(LanguageCode.fr.toLocale().getLanguage(),FrenchAnalyzer.getDefaultStopSet());
        this.stopwordsMap.put(LanguageCode.de.toLocale().getLanguage(),GermanAnalyzer.getDefaultStopSet());
    }


    /**
     * Tries to find the given example within the given collection. If it hasn't been found, the
     * example is automatically added in the collection and is then returned.
     *
     * @param collection The collection to search into.
     * @param example The example to search.
     * @return The existing element if it has been found, the given example otherwise.
     */
    private static <T> T find(Collection<T> collection, T example) {
        for (T element : collection) {
            if (element.equals(example)) {
                return element;
            }
        }
        collection.add(example);
        return example;
    }

    /**
     * Guesses keywords from given input document.
     * @param document
     * @return  A set of potential keywords.
     * @throws IOException If an I/O error occured.
     */
    @Override
    public Analysis analyze(Document document) throws IOException {
        String input = document.getText();

        // hack to keep dashed words (e.g. "non-specific" rather than "non" and "specific")
        input = input.replaceAll("-+", "-0");
        // replace any punctuation char but dashes and apostrophes and by a space
        input = input.replaceAll("[\\p{Punct}&&[^'-]]+", " ");
        // replace most common english contractions
        input = input.replaceAll("(?:'(?:[tdsm]|[vr]e|ll))+\\b", "");

        // tokenize input
        TokenStream tokenStream = new ClassicTokenizer(LUCENE_VERSION, new StringReader(input));
        // to lower case
        tokenStream = new LowerCaseFilter(LUCENE_VERSION, tokenStream);
        // remove dots from acronyms (and "'s" but already done manually above)
        tokenStream = new ClassicFilter(tokenStream);
        // convert any char to ASCII
        tokenStream = new ASCIIFoldingFilter(tokenStream);

        // add stop words
        Locale documentLocale = Strings.isNullOrEmpty(document.getLanguage())? LocaleUtils.toLocale("en_US") : LocaleUtils.toLocale(document.getLanguage());
        tokenStream = new StopFilter(LUCENE_VERSION, tokenStream, this.stopwordsMap.get(documentLocale.getLanguage()));

        List<Keyword> keywords = new LinkedList<Keyword>();
        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);

        // for each token
        while (tokenStream.incrementToken()) {
            String term = token.toString();
            // stemmize
            String stem = stemmize(term, documentLocale);
            if (stem != null) {
                // create the keyword or get the existing one if any
                Keyword keyword = find(keywords, new Keyword(stem.replaceAll("-0", "-")));
                // add its corresponding initial token
                keyword.add(term.replaceAll("-0", "-"));
            }
        }

        // reverse sort by frequency
        //Collections.sort(keywords);

        // report the analysis
        Analysis analysis = new Analysis();
        analysis.setId(UUID.randomUUID().toString());
        analysis.setStemmer(id());
        analysis.setStems(keywords);

        return analysis;
    }


}
