package es.upm.oeg.stemming.lib.algorithm;

import es.upm.oeg.stemming.lib.domain.Analysis;
import es.upm.oeg.stemming.lib.domain.Document;
import es.upm.oeg.stemming.lib.domain.Keyword;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by cbadenes on 09/07/15.
 */
public class PorterStemmerTest {

    IStemmer stemmer;

    @Before
    public void setup(){
        stemmer = new PorterStemmer();
    }


    @Test
    public void singleWord() throws IOException {

        String word = "caresses";
        String stem = "caress";

        Document doc = new Document();
        doc.setText(word);

        Analysis analysis = stemmer.analyze(doc);

        List<Keyword> stems = analysis.getStems();

        Assert.assertEquals("Number of stems",1,stems.size());
        Assert.assertEquals("Stem",stem,stems.get(0).getStem());
        Assert.assertEquals("Highest Frequency",new Integer(1),stems.get(0).getFrequency());

    }

    @Test
    public void singleSentence() throws IOException {

        String word = "connection connections connective connected connecting";
        String stem = "connect";

        Document doc = new Document();
        doc.setLanguage("en_US");
        doc.setText(word);

        Analysis analysis = stemmer.analyze(doc);

        List<Keyword> stems = analysis.getStems();

        Assert.assertEquals("Number of stems",1,stems.size());
        Assert.assertEquals("Stem",stem,stems.get(0).getStem());
        Assert.assertEquals("Highest Frequency",new Integer(5),stems.get(0).getFrequency());

    }

}
