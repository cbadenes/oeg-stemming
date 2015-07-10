package es.upm.oeg.stemming.web.service;

import es.upm.oeg.stemming.lib.algorithm.IStemmer;
import es.upm.oeg.stemming.lib.algorithm.PorterStemmer;
import es.upm.oeg.stemming.lib.algorithm.SnowballStemmer;
import es.upm.oeg.stemming.web.domain.Stemmer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StemmerService {

    private Map<String,IStemmer> stemmers;


    public StemmerService(){
        this.stemmers = new HashMap<>();

        PorterStemmer porterStemmer = new PorterStemmer();
        this.stemmers.put(porterStemmer.id(),porterStemmer);

        SnowballStemmer snowballStemmer = new SnowballStemmer();
        this.stemmers.put(snowballStemmer.id(),snowballStemmer);

    }


    public List<Stemmer> listStemmers(){
        List<Stemmer> stemmerList = new ArrayList<>();
        for (IStemmer stemmer: stemmers.values()){
            stemmerList.add(Stemmer.builder().id(stemmer.id()).name(stemmer.name()).description(stemmer.description()).build());
        }
        return stemmerList;
    }


    public Stemmer getStemmer(String id){
        if (!stemmers.containsKey(id)){
            throw new RuntimeException("Stemmer not found");
        }
        IStemmer stemmer = stemmers.get(id);
        return Stemmer.builder().id(stemmer.id()).name(stemmer.name()).description(stemmer.description()).build();
    }

    public IStemmer getAlgorithm(String id){
        return stemmers.get(id);
    }

}
