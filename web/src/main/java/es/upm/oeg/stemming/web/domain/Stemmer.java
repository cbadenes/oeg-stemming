package es.upm.oeg.stemming.web.domain;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Stemmer {

    private String id;

    private String name;

    private String description;

}
