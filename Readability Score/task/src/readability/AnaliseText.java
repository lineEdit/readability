package readability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AnaliseText {
    private String input;
    private List<String> listWords;
    private int words;
    private int sentences;
    private int characters;
    private int syllables;
    private int polysyllables;
    private double average;

    public AnaliseText(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        input = stringBuilder.toString();
        listWords = Arrays.asList(input.split("\\s+"));
        calc();
    }

    private void calc() {
        words = getCountWords();
        sentences = getCountSentences();
        characters = getCountCharacters();
        syllables = getCountSyllables();
        polysyllables = getCountPolySyllables();
        average = (getLevel(getARI())
                + getLevel(getFK())
                + getLevel(getSMOG())
                + getLevel(getCLI())) / 4.0;
    }

    public void showText() {
        System.out.println("The text is:\n" + input + "\n");
        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("Syllables: " + syllables);
        System.out.println("Polysyllables: " + polysyllables);
    }

    public void showAll() {
        showARI();
        showFK();
        showSMOG();
        showCLI();
        showAverage();
    }

    public void showARI() {
        double score = getARI();
        System.out.println("Automated Readability Index: "
                + score
                + getLevel(score));
    }

    public void showFK() {
        double score = getFK();
        System.out.println("Flesch–Kincaid readability tests: "
                + score
                + getLevel(score));
    }

    public void showSMOG() {
        double score = getSMOG();
        System.out.println("Simple Measure of Gobbledygook: "
                + score
                + getLevel(score));
    }

    public void showCLI() {
        double score = getCLI();
        System.out.println("Coleman–Liau index: "
                + score
                + showLevel(score));
    }

    public void showAverage() {
        System.out.println("\nThis text should be understood in average by " + average + " year olds.");
    }

    private int getCountWords() {
        return listWords.size();
    }

    private int getCountCharacters() {
        return input.replaceAll("\\s+", "").length();
    }

    private int getCountSentences() {
        return input.split("[!.?]").length;
    }

    public double getARI() {
        return 4.71 * ((float) characters / words) + 0.5 * ((float) words / sentences) - 21.43;
    }

    public double getFK() {
        return 0.39 * ((float) words / sentences) + 11.8 * ((float) syllables / words) - 15.59;
    }

    public double getSMOG() {
        return 1.043 * Math.sqrt((float) polysyllables * 30 / sentences) + 3.1291;
    }

    public double getCLI() {
        double letters = (float) characters / words * 100.0;
        double sentences = (float) this.sentences / words * 100.0;
        return 0.0588 * letters - 0.296 * sentences - 15.8;
    }

    private boolean isVowel(char symbol) {
        return symbol == 'a'
                || symbol == 'e'
                || symbol == 'i'
                || symbol == 'o'
                || symbol == 'u'
                || symbol == 'y';
    }

    private int getCountSyllables() {
        int count = 0;
        for (var item : listWords) {
            count += getCountSyllablesInWord(item);
        }
        return count;
    }

    private int getCountPolySyllables() {
        int count = 0;
        for (var item : listWords) {
            if (getCountSyllablesInWord(item) > 2) {
                ++count;
            }
        }
        return count;
    }

    private int getCountSyllablesInWord(String word) {
        int countVowel = 0;
        char[] symbols = word.toLowerCase().toCharArray();
        for (int i = 0; i < symbols.length; ++i) {
            if (isVowel(symbols[i]) && !isVowel(symbols[i + 1])) {
                ++countVowel;
            }
        }
        if (symbols[symbols.length - 1] == 'e') {
            --countVowel;
        }
        if (countVowel == 0) {
            countVowel = 1;
        }
        return countVowel;
    }

    public int getLevel(double value) {
        int score = (int) Math.ceil(value);
        int end = (score >= 3) ? score + 6 : score + 5;
        if (score >= 13) {
            end = 24;
        }
        return end;
    }

    public String showLevel(double value) {
        return "(about " + getLevel(value) + " year olds).";
    }
}
