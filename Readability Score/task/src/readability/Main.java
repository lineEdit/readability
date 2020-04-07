package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        String[] string = scanner.nextLine().split("[!.?]");
        int mid = 0;
        for (String item : string) {
            mid += item.split("\\s+").length;
        }
        System.out.println(mid/string.length > 10 ? "HARD" : "EASY");
    }
}
