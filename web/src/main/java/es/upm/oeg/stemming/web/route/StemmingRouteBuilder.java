package es.upm.oeg.stemming.web.route;

import es.upm.oeg.stemming.lib.domain.Analysis;
import es.upm.oeg.stemming.lib.domain.Document;
import es.upm.oeg.stemming.web.domain.Stemmer;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class StemmingRouteBuilder extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json_xml)
                .dataFormatProperty("prettyPrint", "true")
                .dataFormatProperty("json.in.disableFeatures", "FAIL_ON_UNKNOWN_PROPERTIES,ADJUST_DATES_TO_CONTEXT_TIME_ZONE")
                .dataFormatProperty("xml.out.mustBeJAXBElement", "false")
                .contextPath("stemming/rest")
                .port(8080);

        rest("/stemmers").description("Stemmer rest service")
                .consumes("application/json").produces("application/json")

                .get("/").description("List all stemmers").outTypeList(Stemmer.class)
                .to("bean:stemmerService?method=listStemmers")

                .get("/{id}").description("Find a stemmer by id").outType(Stemmer.class)
                //.param().name("id").type(path).description("The id of the user to get").dataType("int").endParam()
                .to("bean:stemmerService?method=getStemmer(${header.id})");



        rest("/stemmers/{id}/analysis").description("Stemming analysis rest service")

                .post().type(Document.class).outType(Analysis.class).description("Make a stemming process using the stemmer")
                //.param().name("body").type(body).description("The document to be stemmed").endParam()
                .to("bean:analysisService?method=stem");
    }
}
