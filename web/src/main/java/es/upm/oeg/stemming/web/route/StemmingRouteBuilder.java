package es.upm.oeg.stemming.web.route;

import es.upm.oeg.stemming.lib.domain.Analysis;
import es.upm.oeg.stemming.lib.domain.Document;
import es.upm.oeg.stemming.web.domain.Stemmer;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * Created by cbadenes on 09/07/15.
 */
public class StemmingRouteBuilder extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        // configure we want to use servlet as the component for the rest DSL
        restConfiguration()
                // The Camel Rest component to use for the REST transport, such as restlet, spark-rest
                .component("servlet")
                // and we enable json/xml binding mode
                .bindingMode(RestBindingMode.json_xml)
                // and output using pretty print
                .dataFormatProperty("prettyPrint", "true")
                .dataFormatProperty("json.in.disableFeatures", "FAIL_ON_UNKNOWN_PROPERTIES,ADJUST_DATES_TO_CONTEXT_TIME_ZONE")
                .dataFormatProperty("xml.out.mustBeJAXBElement", "false")
                        // setup context path and port number that Apache Tomcat will deploy
                        // this application with, as we use the servlet component, then we
                        // need to aid Camel to tell it these details so Camel knows the url
                        // to the REST services.
                        // Notice: This is optional, but needed if the RestRegistry should
                        // enlist accurate information. You can access the RestRegistry
                        // from JMX at runtime
                .contextPath("stemming/rest")
                .port(8080);

        // Stemmer
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
                .to("bean:analysisService?method=stem(${header.id})");
    }
}
