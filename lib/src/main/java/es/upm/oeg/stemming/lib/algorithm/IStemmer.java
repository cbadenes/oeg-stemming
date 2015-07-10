package es.upm.oeg.stemming.lib.algorithm;

import es.upm.oeg.stemming.lib.domain.Analysis;
import es.upm.oeg.stemming.lib.domain.Document;

import java.io.IOException;

/**
 * Created by cbadenes on 09/07/15.
 */
public interface IStemmer {

    String id();

    String name();

    String description();

    Analysis analyze(Document document) throws IOException;

}
