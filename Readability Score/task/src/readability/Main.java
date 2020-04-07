package readability;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        Analis analis = new Analis(args[0]);
        analis.calc();
        analis.show();
    }
}
