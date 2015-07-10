package es.upm.oeg.stemming.lib.domain;

import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Keyword holder, composed by a unique analyze, its frequency, and a set of found corresponding
 * terms for this analyze.
 */
@Data
public class Keyword  implements Comparable<Keyword> {

    /**
     * Unique constructor.
     *
     * @param stem The unique analyze this instance must hold.
     */
    public Keyword(String stem) {
        this.stem = stem;
        terms = new HashSet<String>();
        frequency = 0;
    }

    /** The unique analyze. */
    private String stem;

    /** The frequency of the analyze. */
    private Integer frequency;

    /** The found corresponding terms for this analyze. */
    private Set<String> terms;

    /**
     * Used to reverse sort a list of keywords based on their frequency (from the most frequent
     * keyword to the least frequent one).
     */
    @Override
    public int compareTo(Keyword o) {
        return o.frequency.compareTo(frequency);
    }


    /**
     * Add a found corresponding term for this analyze. If this term has been already found, it
     * won't be duplicated but the analyze frequency will still be incremented.
     *
     * @param term The term to add.
     */
    public void add(String term) {
        terms.add(term);
        frequency++;
    }

    /**
     * Used to keep unicity between two keywords: only their respective stems are taken into
     * account.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Keyword && obj.hashCode() == hashCode();
    }

    /**
     * Used to keep unicity between two keywords: only their respective stems are taken into
     * account.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{stem});
    }

}
