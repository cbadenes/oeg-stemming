package es.upm.oeg.stemming.lib.algorithm;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by cbadenes on 09/07/15.
 */
public class PorterStemmer extends LuceneStemmer {

    @Override
    public String id() {
        return "porter";
    }

    @Override
    public String name() {
        return "Porter Stem Filter from Lucene";
    }

    @Override
    public String description() {
        return "The Porter stemming algorithm is a process for removing the commoner morphological and inflexional endings from words in English. Its main use is as part of a term normalisation process that is usually done when setting up Information Retrieval systems";
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

        // porter stemmizer
        tokenStream = new PorterStemFilter(tokenStream);

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
