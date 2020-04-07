package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        AnaliseText analiseText = new AnaliseText(args[0]);
        analiseText.showText();
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        final Scanner scanner = new Scanner(System.in);
        switch (scanner.next()) {
            case "ARI": analiseText.showARI(); break;
            case "FK": analiseText.showFK(); break;
            case "SMOG": analiseText.showSMOG(); break;
            case "CL": analiseText.showCLI(); break;
            case "all": analiseText.showAll(); break;
        }
    }
}
