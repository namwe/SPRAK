import java.util.Arrays;
import java.util.Scanner;

public class MenuDialogue {
    UserDialogue userDialogue;

    public MenuDialogue() throws Exception {
        userDialogue = new UserDialogue();
    }

    private void menuLoop() {
        boolean exit = false;
        Integer[] allowedInput = {1, 2, 0};
        while (!exit) {
            System.out.println("* * * H Ä N G A G U B B E * * *" + "\n" +
                    "1. Spela" + "\n" +
                    "2. Hur spelar man?" + "\n" +
                    "0. Avsluta");
            switch (numericInput(allowedInput)) {
                case -1:
                    System.out.println("Ogiltigt alternativ, försök igen.");
                    break;
                case 1:
                    //Spela
                    userDialogue.start();
                    break;
                case 2:
                    //Hur spelar man?
                    printHowTo();
                    break;
                case 0:
                    //Avsluta
                    exit = true;
                    break;
            }
        }
    }

    private void printHowTo() {
        System.out.println("Välkommen till hängagubbe - grammatik" + "\n" +
                "Istället för att fråga om bokstäver så frågar du om ordegenskaper. " + "\n" +
                "Botten väljer ett ord ur en mening och ditt uppdrag är att gissa vilket ord det är." + "\n" +
                "Du får rätt om du lyckas gissa ordet eller dess lemma." + "\n" +
                "Här är de nyckelord du kan fråga om: ");
        for (String keyword : userDialogue.getWordsToTags().keySet()) {
            System.out.println("\t" + keyword);
        }
        System.out.println();
    }

    /**
     * Asks user for input and validates it.
     *
     * @param allowedInput Specifies valid input.
     * @return -1 for invalid choice. Returns inputted number if it is in allowedInput.
     */
    private int numericInput(Integer[] allowedInput) {
        Scanner scanner = new Scanner(System.in);
        int menuChoice;
        try {
            System.out.print("> ");
            menuChoice = scanner.nextInt();
            if (!Arrays.asList(allowedInput).contains(menuChoice)) {
                throw new Exception("Input is not in allowedInput");
            }
        } catch (Exception e) {
            return -1;
        }
        return menuChoice;
    }

    public static void main(String[] args) {
        try {
            MenuDialogue menuDialogue = new MenuDialogue();
            menuDialogue.menuLoop();
        } catch (Exception e) {
            System.out.println("Kritiskt fel med inläsning av filer: en eller flera filer saknas");
        }
    }

}
