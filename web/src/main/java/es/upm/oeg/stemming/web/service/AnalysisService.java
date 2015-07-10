package es.upm.oeg.stemming.web.service;

import es.upm.oeg.stemming.lib.domain.Analysis;
import es.upm.oeg.stemming.lib.domain.Document;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AnalysisService {

    @Autowired
    StemmerService stemmerService;


    public Analysis stem(Exchange exchange) throws IOException {

        Document doc    = (Document) exchange.getIn().getBody();
        String id       = exchange.getIn().getHeader("id",String.class);
        return stemmerService.getAlgorithm(id).analyze(doc);
    }

}
