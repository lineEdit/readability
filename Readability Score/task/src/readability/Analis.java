package readability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Analis {
    private String input;
    private int words;
    private int sentences;
    private int characters;
    private double score;

    public Analis(String fileName) {
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
    }

    public void calc() {
        words = getWords();
        sentences = getSentences();
        characters = getCharacters();
        score = getScore();
    }

    public void show() {
        System.out.println("The text is:\n" + input + "\n");
        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("The score is: " + score);
        System.out.println(getLevel());
    }

    private int getWords() {
        return input.split("\\s+").length;
    }

    private int getCharacters() {
        return input.replaceAll("\\s+", "").length();
    }

    private int getSentences() {
        return input.split("[!.?]").length;
    }

    private double getScore() {
        return 4.71 * ((float) characters / words) + 0.5 * ((float) words / sentences) - 21.43;
    }

    private String getLevel() {
        int score = (int) Math.round(this.score);
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
