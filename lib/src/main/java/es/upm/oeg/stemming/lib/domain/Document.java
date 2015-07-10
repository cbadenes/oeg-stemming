package es.upm.oeg.stemming.lib.domain;

import lombok.Data;

/**
 * Created by cbadenes on 09/07/15.
 */
@Data
public class Document {

    // ISO 639
    private String language;

    private String text;
}
