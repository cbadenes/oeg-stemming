package es.upm.oeg.stemming.lib.algorithm;

import com.neovisionaries.i18n.LanguageCode;
import org.apache.commons.lang.LocaleUtils;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.EnglishStemmer;
import org.tartarus.snowball.ext.FrenchStemmer;
import org.tartarus.snowball.ext.SpanishStemmer;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by cbadenes on 09/07/15.
 */
public class SnowballStemmer extends LuceneStemmer {

    @Override
    public String id() {
        return "snowball";
    }

    @Override
    public String name() {
        return "Snowball Stemmers from Lucene";
    }

    @Override
    public String description() {
        return "Snowball stemmers for English, for Russian, for the Romance languages French, Spanish, Portuguese and Italian, for German and Dutch, for Swedish, Norwegian and Danish, and for Finnish";
    }

    /**
     * Stemmize the given term.
     *
     * @param term The term to analyze.
     * @return The analyze of the given term.
     * @throws IOException If an I/O error occured.
     */
    protected String stemmize(String term, Locale language) throws IOException {

        // tokenize term
        TokenStream tokenStream = new ClassicTokenizer(LUCENE_VERSION, new StringReader(term));

        LanguageCode code = LanguageCode.getByLocale(language);

        // snowball stemmizer
        SnowballProgram snowballProgram = null;
        try {
            snowballProgram = (SnowballProgram) Class.forName("org.tartarus.snowball.ext."+code.getName()+"Stemmer").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new IOException("Error getting snowball stemmer from language: " + e);
        }
        tokenStream = new SnowballFilter(tokenStream, snowballProgram);


        Set<String> stems = new HashSet<String>();
        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
        // for each token
        while (tokenStream.incrementToken()) {
            // add it in the dedicated set (to keep unicity)
            stems.add(token.toString());
        }

        // if no analyze or 2+ stems have been found, return null
        if (stems.size() != 1) {
            return null;
        }

        String stem = stems.iterator().next();

        // if the analyze has non-alphanumerical chars, return null
        if (!stem.matches("[\\w-]+")) {
            return null;
        }

        return stem;
    }


}
