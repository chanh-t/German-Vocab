package com.company;

final public class word {
    String german;
    String english;
    String gender;

    public word(String german, String english, String gender) {
        this.german = german;
        this.english = english;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return german;
    }
}
