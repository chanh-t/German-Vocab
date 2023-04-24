package com.company;

final public class noun {
    public String NOUN_GER;
    public String NOUN_ENG;
    public String NOUN_GENDER;
    public verb NOUN_TO_VERB;

    public noun(String german, String english, String gender) {
        this.NOUN_GER = german;
        this.NOUN_ENG = english;
        this.NOUN_GENDER = gender;
    }

    public noun() {

    }

    @Override
    public String toString() {
        return NOUN_GER + " " + NOUN_ENG + " " + NOUN_GENDER;
    }
}
