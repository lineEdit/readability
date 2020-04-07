package readability;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        AnaliseText analiseText = new AnaliseText(args[0]);
        analiseText.show();
    }
}
