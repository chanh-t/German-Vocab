package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File file_verb = new File("/Users/chanhtruong/Personal-Projects/german/src/com/company/verbs.txt");
        File file_noun = new File("/Users/chanhtruong/Personal-Projects/german/src/com/company/nouns.txt");

        Scanner scanner_verb = new Scanner(file_verb);
        Scanner scanner_noun = new Scanner(file_noun);

        ArrayList<verb> verb_vocabs = new ArrayList<verb>();
        ArrayList<noun> noun_vocabs = new ArrayList<noun>();

        noun_parser(file_noun, scanner_noun, noun_vocabs);

        vocab_test_nouns(noun_vocabs, true, false);

        verb_parser(file_verb, scanner_verb, verb_vocabs);
        vocab_test_verbs(verb_vocabs, true, true);

    }

    // array list of files containing verbs only
    private static void test_verbs(ArrayList<File> files) {

    }

    private static void test_nouns(ArrayList<File> files) {

    }

    private static String parse_attribute(String text_line) {
        return text_line.substring(text_line.indexOf(' ')).trim();
    }

    private static void noun_parser(File file, Scanner scanner, ArrayList<noun> noun_vocabs) throws FileNotFoundException {
        skip_comments(scanner);
        noun noun = new noun();
        while (scanner.hasNextLine()) {
            String cur_text = scanner.nextLine();
            if (cur_text.contains("NOUN_GER")) {
                noun.NOUN_GER = parse_attribute(cur_text);
            } else if (cur_text.contains("NOUN_ENG")) {
                noun.NOUN_ENG = parse_attribute(cur_text);
            } else if (cur_text.contains("NOUN_GENDER")) {
                noun.NOUN_GENDER = parse_attribute(cur_text);
            } else if (cur_text.equals("-")) {
                noun_vocabs.add(noun);
                noun = new noun();
            }
        }
    }

    private static void verb_parser(File file, Scanner scanner, ArrayList<verb> verb_vocabs) throws FileNotFoundException{
        skip_comments(scanner);
        verb verb = new verb();
        while (scanner.hasNextLine()){
            String cur_text = scanner.nextLine();
            if (cur_text.contains("VERB_GER")) {
                verb.VERB_GER = parse_attribute(cur_text);
            } else if (cur_text.contains("VERB_ENG")) {
                verb.VERB_ENG = parse_attribute(cur_text);
            } else if (cur_text.contains("PERFEKT_TENSE")) {
                verb.PERFEKT_TENSE = parse_attribute(cur_text);
            } else if (cur_text.equals("-")) {
                verb_vocabs.add(verb);
                verb = new verb();
            }
        }
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
            if (verb.VERB_GER != null && !german) {
                System.out.print("german word: ");
                String answer = scanner.nextLine().trim();
                if (answer.equals(verb.VERB_GER)) {
                    System.out.println("correct!");
                } else {
                    System.out.println("wrong, the correct answer was: " + verb.VERB_GER);
                    System.out.println("------------------------------");
                    continue;
                }
            }

            if (verb.VERB_ENG != null && german) {
                System.out.print("english word: ");
                String answer = scanner.nextLine().trim();
                if (answer.equals(verb.VERB_ENG)) {
                    System.out.println("correct!");
                } else {
                    System.out.println("wrong, the correct answer was: " + verb.VERB_ENG);
                    System.out.println("------------------------------");
                    continue;
                }
            }
            // the above groupings go together, below are other attrributes

            if (verb.PERFEKT_TENSE != null) {
                System.out.print("perfect tense: ");
                String answer = scanner.nextLine().trim();
                if (answer.equals(verb.PERFEKT_TENSE)) {
                    System.out.println("correct!");
                } else {
                    System.out.println("wrong, the correct answer was: " + verb.PERFEKT_TENSE);
                    System.out.println("------------------------------");
                    continue;
                }
            }

            if (eliminate) {
                verb_vocabs.remove(prev);
            }
        }
        System.out.println("great job!! you've finished the set :)");
    }

    private static void vocab_test_nouns(ArrayList<noun> noun_vocabs, boolean eliminate, boolean german) {
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);
        int prev = 0;

        while (noun_vocabs.size() != 0) {
            prev = get_num(rand, noun_vocabs.size(), prev, true);
            noun noun = noun_vocabs.get(prev);

            if (german) {
                System.out.println("german: " + noun.NOUN_GER);

                System.out.print("english: ");
                String guess = scanner.nextLine();

                if (guess.equals(noun.NOUN_ENG)) {
                    System.out.println("correct!");
                } else {
                    System.out.println("wrong, the correct answer was: " + noun.NOUN_ENG);
                    System.out.println("------------------------------");
                    continue;
                }
            } else {
                System.out.println("english: " + noun.NOUN_ENG);

                System.out.print("german: ");
                String guess = scanner.nextLine();

                if (guess.equals(noun.NOUN_GER)) {
                    System.out.println("correct!");
                } else {
                    System.out.println("wrong, the correct answer was: " + noun.NOUN_GER);
                    System.out.println("------------------------------");
                    continue;
                }
            }

            System.out.print("gender: ");
            String guess = scanner.nextLine();
            if (guess.equals(noun.NOUN_GENDER)) {
                System.out.println("correct!");
            } else {
                System.out.println("wrong, the correct answer was: " + noun.NOUN_GENDER);
                System.out.println("------------------------------");
                continue;
            }

            if (eliminate) {
                noun_vocabs.remove(prev);
            }
        }
        System.out.println("great job!! you've finished the set :)");
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

