package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine().length() > 100 ? "HARD" : "EASY");
    }
}
