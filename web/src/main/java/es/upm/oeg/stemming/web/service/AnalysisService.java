package es.upm.oeg.stemming.web.service;

import es.upm.oeg.stemming.lib.domain.Analysis;
import es.upm.oeg.stemming.lib.domain.Document;
import lombok.Setter;

import java.io.IOException;

/**
 * Created by cbadenes on 09/07/15.
 */
public class AnalysisService {

    @Setter
    private StemmerService stemmerService;


    public Analysis stem(String id, Document doc) throws IOException {
        return stemmerService.getAlgorithm(id).analyze(doc);
    }

}
