package es.upm.oeg.stemming.web.domain;


import lombok.Builder;
import lombok.Data;

/**
 * Created by cbadenes on 10/07/15.
 */
@Builder
@Data
public class Stemmer {

    private String id;

    private String name;

    private String description;

}
