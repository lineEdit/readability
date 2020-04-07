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
    }

    public void show() {
        calc();
        System.out.println("The text is:\n" + input + "\n");
        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("The score is: " + getARI());
//        System.out.println(getLevel());
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

    private String getLevel(double value) {
        int score = (int) Math.ceil(value);
        int start = (score >= 4) ? score + 5 : score + 4;
        int end = (score >= 3) ? score + 6 : score + 5;
        if (score == 13) {
            start = 18;
            end = 24;
        }
        if (score == 14) {
            return "This text should be understood by 24+ year olds.";
        }
        return "This text should be understood by "
                + start
                + "-"
                + end
                + " year olds.";
    }
}
