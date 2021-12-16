import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

final public class Main {
    private int pcMove;
    private SecretKeyGeneration sKeyGeneration;
    public static void main(String[] args) {
        Main main = new Main();
        main.checkingArguments(args);
        main.go(args);

    }

    public void go(String[] args) {
        pcMove = scriptMove(args);
        sKeyGeneration = new SecretKeyGeneration(args[pcMove]);
        sKeyGeneration.showHMACmove();
        menu(args);

    }

    private void menu(String[] movements) {
        System.out.println("Available moves:");
        for (int i = 0; i < movements.length; i++) {
            System.out.printf("%2d - %s\n", i + 1, movements[i]);
        }
        System.out.println(" 0 - exit");
        System.out.println(" ? - help");
        choiceOfMovement(movements);
    }

    private void choiceOfMovement(String[] movements) {
        Scanner sc = new Scanner(System.in);
        String choice1 = sc.nextLine();

        System.out.println("Enter your move: " + choice1);

        if (choice1.equals("0")) {
            System.out.println("End the program");
            System.exit(0);
        } else if (choice1.equals("?")) {
            System.out.println("Table: ");
            Table table = new Table();
            table.createTable(movements, new Rules());
            System.out.println("Return to menu");
            menu(movements);
        } else {
            int choiceNum;
            try {
                choiceNum = Integer.parseInt(choice1);
            } catch (NumberFormatException e) {
                choiceNum = -1;
            }
            if (choiceNum > movements.length || choiceNum < 1) {
                System.out.println("Please, choose a correct move");
                menu(movements);
            } else {
                System.out.println("Your move: " + movements[choiceNum - 1]);
                System.out.println("Computer move: " + movements[pcMove]);
                duel(movements.length, choiceNum, pcMove, new Rules(), movements);
            }
        }
    }


    private int scriptMove(String[] strings) {
        int random = (int) ((Math.random() * (strings.length)) + 1);
        return random - 1;
    }


    private void duel(int allMoves, int userMove, int pcMove, Rules rules, String[] args) {
        int[][] info = rules.positionsForTable(allMoves);
        if (info[userMove - 1][pcMove] == 1) System.out.println("You win!");
        else if (info[userMove - 1][pcMove] == -1) System.out.println("You lose!");
        else System.out.println("Draw.");
        sKeyGeneration.showSecretKey();
        System.out.println("\nNew game - new choice\n");
        go(args);
    }

    private void checkingArguments(final String[] args) {
        try {
            if (args.length < 3 || args.length % 2 == 0) throw new Exception();
        } catch (Exception e) {
            System.out.println("You entered the wrong number of arguments.");
            for (int i = 0; i < args.length; i++) System.out.print(args[i] + "  ");
            System.out.println("""


                    It must be greater than or equal to three and must also be odd.
                    For example: Scissors Paper Lizard Spock\s
                    or: 1 2 3 4 5 6 7 8 9""");
            System.out.println("\nPlease try again!");
            System.exit(0);
        }
        try {
            if (new HashSet<>(Arrays.asList(args)).size() != args.length) throw new Exception();
        } catch (Exception e) {
            System.out.println("Duplicates found among arguments.");
            for (int i = 0; i < args.length; i++) System.out.print(args[i] + "  ");
            System.out.println("""


                    The arguments must be unique.
                    For example: Scissors Paper Lizard Spock\s
                    or: 1 2 3 4 5 6 7 8 9""");
            System.out.println("\nPlease try again!");
            System.exit(0);
        }

    }

}
