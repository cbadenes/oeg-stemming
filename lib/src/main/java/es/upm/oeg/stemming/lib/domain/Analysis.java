package es.upm.oeg.stemming.lib.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by cbadenes on 09/07/15.
 */
@Data
public class Analysis {

    private String id;

    private String stemmer;

    private List<Keyword> stems;

}
