package com.company;

public class verb {
    public String VERB_GER;
    public String VERB_ENG;
    public String PERFEKT_TENSE;

    public verb(String VERB_GER, String VERB_ENG, String PERFEKT_TENSE) {
        this.VERB_GER = VERB_GER;
        this.VERB_ENG = VERB_ENG;
        this.PERFEKT_TENSE = PERFEKT_TENSE;
    }

    public verb() {
    }

    @Override
    public String toString() {
        return "VERB_GER: " + VERB_GER + " VERB_ENG: " + VERB_ENG + " PERFEKT_TENSE: " + PERFEKT_TENSE;
    }
}
