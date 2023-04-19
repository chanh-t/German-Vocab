package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // pass the path to the file as a parameter
        File file_verb = new File("/Users/chanhtruong/Personal-Projects/german/src/com/company/verbs.txt");
        File file_noun = new File("/Users/chanhtruong/Personal-Projects/german/src/com/company/nouns.txt");
        Scanner scanner = new Scanner(file_verb);
        ArrayList<verb> verb_vocabs = new ArrayList<verb>();
        ArrayList<noun> noun_vocabs = new ArrayList<noun>();

        verb_parser(file_verb, scanner, verb_vocabs);

        System.out.println(verb_vocabs);
        vocab_test_verbs(verb_vocabs, true, true);

    }


    private static void skip_comments(Scanner scanner) {
        int dash = 0;
        while (dash != 2) {
            String line = scanner.nextLine();
            if (line.equals("-")) {
                dash++;
            }
        }
    }

    private static void verb_parser(File file, Scanner scanner, ArrayList<verb> verb_vocabs) throws FileNotFoundException{
        skip_comments(scanner);

        verb verb = new verb();
        while (scanner.hasNextLine()){
            String cur_text = scanner.nextLine();
            if (cur_text.contains("VERB_GER")) {
                verb.VERB_GER = cur_text.substring(cur_text.indexOf(' ')).trim();
            } else if (cur_text.contains("VERB_ENG")) {
                verb.VERB_ENG = cur_text.substring(cur_text.indexOf(' ')).trim();
            } else if (cur_text.contains("PERFEKT_TENSE")) {
                verb.PERFEKT_TENSE = cur_text.substring(cur_text.indexOf(' ')).trim();
            } else if (cur_text.equals("-")) {
                verb_vocabs.add(verb);
                verb = new verb();

            }
        }
    }

    // field german means vocab test german -> english
    private static void vocab_test_verbs(ArrayList<verb> verb_vocabs, boolean eliminate, boolean german) {
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);
        int prev = 0;
        verb verb;

        while (verb_vocabs.size() != 0) {
            prev = get_num(rand, verb_vocabs.size(), prev, true);

            verb = verb_vocabs.get(prev);
            if (german) {System.out.print("german: ");} else {System.out.print("english: ");}
            if (german) {System.out.println(verb.VERB_GER);} else {System.out.println(verb.VERB_ENG);}

            // checks if german != null and if we are doing germa -> english or vice versa
            if (!verb.VERB_GER.equals(null) && !german) {
                System.out.print("german word: ");
                String answer = scanner.nextLine().trim();
                if (answer.equals(verb.VERB_GER)) {
                    System.out.println("correct!");
                } else {
                    System.out.println("wrong");
                    continue;
                }
            }

            if (!verb.VERB_ENG.equals(null) && german) {
                System.out.print("english word: ");
                String answer = scanner.nextLine().trim();
                if (answer.equals(verb.VERB_ENG)) {
                    System.out.println("correct!");
                } else {
                    System.out.println("wrong");
                    continue;
                }
            }
            // the above groupings go together, below are other attrributes

            if (!verb.PERFEKT_TENSE.equals(null)) {
                System.out.print("perfect tense: ");
                String answer = scanner.nextLine().trim();
                if (answer.equals(verb.PERFEKT_TENSE)) {
                    System.out.println("correct!");
                } else {
                    System.out.println("wrong");
                    continue;
                }
            }

            if (eliminate) {
                verb_vocabs.remove(prev);
            }
        }
    }

    private static int get_num(Random rand, int vocab_size, int prev, boolean no_repeats) {
        int num = rand.nextInt(vocab_size);
        while (num == prev && vocab_size > 1 && no_repeats) {
            num = rand.nextInt(vocab_size);
            System.out.println("repeated");
        }
        return num;
    }
}

