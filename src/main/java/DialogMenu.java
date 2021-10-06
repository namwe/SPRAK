import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class DialogMenu {
    private Scanner scanner;
    private static Integer[] ALLOWED_INPUT = {1, 2, 3, 0};

    public DialogMenu() {
        scanner = new Scanner(System.in);
    }

    private void menuLoop() {
        boolean exit = false;
        while (!exit) {
            System.out.println("* * * H Ä N G A G U B B E * * *" + "\n" +
                    "1. Spela" + "\n" +
                    "2. Hur spelar man?" + "\n" +
                    "3. Lägg till ord" + "\n" +
                    "0. Avsluta");
            switch (input()){
                case 0:
                    //Avsluta
                    exit = true;
                    break;
                case 1:
                    //Spela
                    break;
                case 2:
                    //Hur spelar man?
                    break;
                case 3:
                    //Lägg till ord
                    break;
            }
        }
    }

    /**
     * Asks user for input and validates it.
     *
     * @return -1 for invalid choice, 0-4 for valid choices
     */
    private int input() {
        System.out.print("> ");
        String rawInput = scanner.nextLine();
        if (rawInput.isBlank()) {
            System.out.println("Välj ett menyval.");
            return -1;
        }
        int menuChoice;
        try {
            menuChoice = Integer.parseInt(rawInput.substring(0, 1));
            if (!Arrays.asList(ALLOWED_INPUT).contains(menuChoice)) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Ogiltigt alternativ, försök igen.");
            return -1;
        }

        return menuChoice;
    }

    public static void main(String[] args) {
        DialogMenu dialogMenu = new DialogMenu();
        dialogMenu.menuLoop();
    }

}
