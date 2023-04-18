package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // pass the path to the file as a parameter
        File file = new File("/Users/chanhtruong/Personal-Projects/german/src/com/company/test.txt");
        Scanner scanner = new Scanner(file);
        ArrayList<verb> verb_vocabs = new ArrayList<verb>();

        new_parser(file, scanner, verb_vocabs);
        vocab_test(verb_vocabs, true, true);

    }

    private static void new_parser(File file, Scanner scanner, ArrayList<verb> verb_vocabs) throws FileNotFoundException{
        int dash = 0;
        while (dash != 2) {
            String line = scanner.nextLine();
            if (line.equals("-")) {
                dash++;
            }
        }

        verb verb = new verb();
        while (scanner.hasNextLine()){
            String cur_text = scanner.nextLine();
            if (cur_text.contains("VERB_GER")) {
                verb.VERB_GER = cur_text.substring(cur_text.indexOf(' ')).trim();
            } else if (cur_text.contains("VERB_ENG")) {
                verb.VERB_ENG = cur_text.substring(cur_text.indexOf(' ')).trim();
            } else if (cur_text.contains("PERFEKT_TENSE")) {
                verb.PERFEKT_TENSE = cur_text.substring(cur_text.indexOf(' ')).trim();
            } else if (cur_text.contains("-")) {
                verb_vocabs.add(verb);
                verb = new verb();
            }
        }
    }

    // field german means vocab test german -> english
    private static void vocab_test(ArrayList<verb> verb_vocabs, boolean eliminate, boolean german) {
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

            verb_vocabs.remove(prev);
        }
    }

    private static boolean ask_gender(word word, Scanner scanner) {
        if (word.gender.contains("none")) {
            return true;
        }
        System.out.println("Gender?");
        String guess = scanner.nextLine();
        if (word.gender.equals(guess)) {
            System.out.println("correct!");
            return true;
        } else {
            System.out.println("good try! it was " + word.gender);
            return false;
        }
    }





    private static void pure_gender_test(ArrayList<word> vocab) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        while (!vocab.isEmpty()) {
            int index = random.nextInt(vocab.size());
            word word = vocab.get(index);

            if (word.gender.contains("none")) {
                System.out.println("None noun found: " + word.german);
                vocab.remove(index);
                continue;
            }

            System.out.println(word.german);
            System.out.println("Gender?");
            String answer = scanner.nextLine();

            if (answer.equals(word.gender)) {
                vocab.remove(index);
                System.out.println("Good job!");
            } else {
                System.out.println("The correct answer was: " + word.gender);
            }
        }

        System.out.println("Finished!");
    }

    private static void test_vocab(ArrayList<word> vocab, boolean ger_to_engl, boolean eliminate, boolean no_repeats, boolean ask_gender, boolean show_remaining, boolean one_trie) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        int prev = 0;

        if (one_trie) {
            System.out.println("ONE TRY MODE. YOU GOT THIS!");
        }
        while (true) {
            if (vocab.size() == 0) {
                System.out.println("vocab list finished! good job :)");
                return;
            }

            int num = get_num(rand, vocab.size(), prev, no_repeats);
            prev = num;

            word word = vocab.get(num);
            if (show_remaining) {
                System.out.println("Remaining vocab: " + vocab.size());
            }

            if (ger_to_engl) {
                System.out.println(word.german);
                String guess = scanner.nextLine();

                if (guess.equals(word.english)) {
                    System.out.println("correct!");
                    if (eliminate) {
                        vocab.remove(num);
                    }
                } else {
                    System.out.println("false! the correct word was: " + word.english);
                    if (one_trie) {
                        System.out.println("Better luck next time! You had " + vocab.size() + " words left");
                        return;
                    }
                }

            } else {
                System.out.println(word.english);
                String guess = scanner.nextLine();

                if (guess.equals(word.german)) {
                    System.out.println("correct!");
                    if (eliminate) {
                        vocab.remove(num);
                    }
                } else {
                    System.out.println("false! the correct word was: " + word.german);
                    if (one_trie) {
                        System.out.println("Better luck next time! You had " + vocab.size() + " words left");
                        return;
                    }
                }

            }

            if (ask_gender) {
                ask_gender(word, scanner);
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
    private static void parse_file_nouns(Scanner scanner, ArrayList<word> vocab_list) {
        scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            scanner.nextLine(); // get rid of "-"
            String german_word = scanner.nextLine();
            String english_word = scanner.nextLine();
            String gender = scanner.nextLine();

            vocab_list.add(new word(german_word, english_word, gender));
        }
        System.out.println("Vocab total of: " + vocab_list.size());
    }
}

/*

        String[] german_prefix_set1 = {"abholen", "anbietan", "ankommen", "annehmen", "anrufen", "anschauen", "aufgeben", "aufhören",
                                 "aufstehen", "auftreten", "ausgehen", "einkaufen", "vorstellen"};
        String[] english_prefix_set1 = {"to pick up", "to offer", "to arrive", "to accept; to offer; to adopt", "to call up", "to watch",
                "to give up; to hand in", "to stop; be over", "to get up", "to appear; to occur", "to go out", "to shop; shop for",
                "to introduce"};

        String[] german_the_w_set1 = {"wer", "wie", "woher", "wo", "was", "wann", "welcher", "wohnen"};
        String[] english_the_w_set1 = {"who", "how", "where from", "where", "what", "when", "which", "to live"};

        String[] german_words_rand = {"geboren", "Abend", "heute", "fragen", "viele", "frühstücken",
                                        "trinken", "kochen", "vor", "nach", "viertel", "hören"};
        String[] english_words_rand = {"born", "Evening", "today", "questions", "lots", "to eat breakfast",
                                        "to drink", "to cook", "to/before", "after", "quarter(time)", "to listen"};

        String[] german_days_of_week = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonnstag"};
        String[] english_days_of_week = {"Monday", "Tuesday", "Wednesday", "Donnerstag", "Friday", "Saturday", "Sunday"};

        String[] german_kapitel1_1 = {"wandern", "tanzen", "reiten", "spazieren", "segeln", "reisen", "hören",
                                        "Jahreszeiten Monate: im, um, or am?",
                                        "Uhrzeiten: im, um, or am?",
                                        "Wochentage: im, um, or am?", "Wochentage",
                                        "geboren", "zelten"};
        String[] english_kapitel1_1 = {"to hike", "to dance", "to ride", "to walk", "to sail", "to travel", "to listen",
                                        "im", "um", "am", "day of the week", "to be born", "to camp"};

        String[] german_pos = {"ich (pos.)", "du (pos. formal)", "du (pos. informal sing/plur)", "he, it (pos.)",
                                "she (pos.)", "they (pos.)"};
        String[] english_pos = {"mein", "dein", "Ihr", "sein", "ihr", "ihr"};
 */