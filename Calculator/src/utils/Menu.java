package utils;

import functions.MathFunctions;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {
    public static MathFunctions func = new MathFunctions();
    public static IOFunctions input = new IOFunctions();
    private final String logo = "  ______ _                  _ _         \n" +
                                  " |  ____| |                (_) |        \n" +
                                  " | |__  | |_ ___ _ __ _ __  _| |_ _   _ \n" +
                                  " |  __| | __/ _ \\ '__| '_ \\| | __| | | |\n" +
                                  " | |____| ||  __/ |  | | | | | |_| |_| |\n" +
                                  " |______|\\__\\___|_|  |_| |_|_|\\__|\\__, |\n" +
                                  "                                   __/ |\n" +
                                  "                                  |___/  Version 0.1\n";
    private final String commandUsageInfo = "Basic commands in ETERNITY:\n" +
                                              "\t[help]: Get a comprehensive list of all ETERNITY commands and all supported math functions and how to use them.\n" +
                                              "\t[exit]: Exit ETERNITY.\n" +
                                              "\t[history]: Get a list of all valid math expressions that were entered as well as their results.\n" +
                                              "\t[export]: Export history to a file.\n" +
                                                    "\t\tUsage: export [file-name]\n" +
                                              "\t[clear]: Clear the history.\n" +
                                              "\t[file]: Read math expressions from a file.\n" +
                                                "\t\tUsage: file [file-name]\n" +
                                              "\t[>]: Save math expression and result to an output file.\n" +
                                              "\t\tUsage: [expression] > [file-name]\n" +
                                              "\t[format]: Format a math expression's result with a given accuracy as a positive integer.\n" +
                                                "\t\tUsage: [expression] format [accuracy]\n" +
                                              "\tSpecific commands can be combined:\n" +
                                              "\tfile [file-name] [expression] > [output-file-name] format [accuracy]\n";
    private final String supportedFunctionsList = "\nThe supported math functions are:\n" +
                                                    "\tAll basic arithmetic operations (+ - * /)\n" +
                                                    "\tUnary minus or negative number (~)\n" +
                                                    "\tlog b x\n" +
                                                    "\tsd (Standard Deviation)\n" +
                                                    "\tsinh x\n" +
                                                    "\tarccos x\n" +
                                                    "\tx^y\n" +
                                                    "\ta*b^x\n" +
                                                    "\tmad (Mean Average Deviation)\n" +
                                                    "\tgamma\n\n";
    private final String functionUsageInfo = "\nThe supported math functions and their usages are:\n" +
                                                     "\tAll basic arithmetic operations (+ - * /)\n" +
                                                        "\t\tExample: 2+3\n" +
                                                        "\t\tExample: 2*3\n" +
                                                        "\t\tExample: 2-3\n" +
                                                        "\t\tExample: 2/3\n" +
                                                     "\tUnary minus or negative number (~)\n" +
                                                        "\t\tExample: ~1\n" +
                                                        "\t\tExample: ~0.5\n" +
                                                     "\tlog b x\n" +
                                                        "\t\tUsage: log [base] [argument]\n" +
                                                        "\t\tExample: log 10 100\n" +
                                                     "\tsd (Standard Deviation)\n" +
                                                        "\t\tUsage: sd [argument] [argument2] [argument3] ... [argument n]\n" +
                                                        "\t\tExample: sd 1 2 3 4\n" +
                                                     "\tsinh x\n" +
                                                        "\t\tUsage: sinh [argument]\n" +
                                                        "\t\tExample: sinh 2\n" +
                                                     "\tarccos x\n" +
                                                        "\t\tUsage: arccos [argument]\n" +
                                                        "\t\tExample: arccos 0.5\n" +
                                                     "\tx^y\n" +
                                                        "\t\tUsage: [base]^[exponent]\n" +
                                                        "\t\tExample: 2^4\n" +
                                                     "\ta*b^x\n" +
                                                        "\t\tUsage: [a]*[b]^[x]\n" +
                                                        "\t\tExample: 2*10^3\n" +
                                                     "\tmad (Mean Average Deviation)\n" +
                                                        "\t\tUsage: mad [argument] [argument2] [argument3] ... [argument n]\n" +
                                                        "\t\tExample: mad 1 2 3 4\n" +
                                                     "\tgamma\n" +
                                                        "\t\tUsage: gamma [argument]\n" +
                                                        "\t\tExample: gamma 5\n";
    private String comboExample = "\nTo combine multiple functions, separate each functions with parentheses:\n" +
                                      "\tUsage: ([expression1]) * ([expression2]) + ([expression3]\n" +
                                      "\tExample: (sinh 2) * (sd 1 2 3) + (arccos 0)\n";
    private String fullComboExample = "\nTo combine commands and functions:\n" +
                                              "\tUsage: [expression]\n" +
                                              "\tExample: 2 + 2\n" +
                                              "\tUsage: [expression] format [accuracy]\n" +
                                              "\tExample: 2 + 2 format 2\n" +
                                              "\tUsage: [expression] > [output-file-name]\n" +
                                              "\tExample: 2 + 2 > output.txt\n" +
                                              "\tUsage: [expression] > [output-file-name] format [accuracy]\n" +
                                              "\tExample: 2 + 2 > output.txt format 2\n" +
                                              "\tUsage: file [file-name]\n" +
                                              "\tExample: file example.txt\n" +
                                              "\tUsage: file [file-name] format [accuracy]\n" +
                                              "\tExample: file example.txt format 2\n" +
                                              "\tUsage: file [file-name] > [output-file-name]\n" +
                                              "\tExample: file example.txt > output.txt\n" +
                                              "\tUsage: file [file-name] > [output-file-name] format [accuracy]\n" +
                                              "\tExample: file example.txt > output.txt format 2";
    private enum MenuOptions {
        MANUAL("Manual"), FILE("File"), MANUAL_OUT("Manual w/ output file"), FILE_OUT("File w/ output file"), EXIT("Exit Eternity");

        private String option;

        MenuOptions(String op) {
            this.option = op;
        }
        public String toString() {
            return option;
        }
    }

    public enum MenuCommands {
        HELP("help"), EXIT("exit"), FILE_IN("file"), FILE_OUT(">");

        private String commandName;

        MenuCommands(String op) {
            this.commandName = op;
        }
        public String toString() {
            return commandName;
        }
    }

    /**
     * Displays the Eternity logo
     */
    public void displayLogo() {
        System.out.println(logo);
    }

    /**
     * Display menu options
     */
    public void displayOptions() {
        int counter = 0;
        for (MenuOptions menuOption : MenuOptions.values()) {
            System.out.println("\t" + (counter+1) + ". " + menuOption.toString());
            counter++;
        }
    }

    // Comprehensive help menu.
    public void help() {
        System.out.println(commandUsageInfo + functionUsageInfo + comboExample + fullComboExample);
    }

    // Starting help information when using the calculator.
    public void startInfo() {
        System.out.println(commandUsageInfo + supportedFunctionsList +
                           "To see this message as well as each math function's specific usage, use the [help] command."
        );
    }

    /**
     * Exit the function with a farewell message
     */
    public void exitInfo() {
        System.out.println("Thank you for using Eternity!");
    }

    /**
     * Display the results on screen
     * @param expressions Expressions to display
     * @param results Results to display
     */
    public void displayResults(ArrayList<String> expressions, ArrayList<Double> results) {
        if (!results.isEmpty()) {
            for(int i = 0; i < expressions.size(); i++) {
                System.out.println("Expression: " + expressions.get(i).toLowerCase());
                System.out.println("Result: " + results.get(i));
            }
        }
    }

    /**
     * Select a menu option
     * @return selected menu option
     */
    public int selectOption() {
        Scanner kb = new Scanner(System.in);
        int selectedOption = 0, max = MenuOptions.values().length;
        boolean valid;

        do {
            valid = true;
            System.out.print("\nPlease enter an option: ");
            try {
                selectedOption = kb.nextInt();
                System.out.print("\n");
                if (selectedOption <= 0 || selectedOption > max) {
                    valid = false;
                    System.out.println("Invalid input. Please enter an integer from 1-" + max);
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Only numeric values allowed.");
                valid = false;
                kb.nextLine();
            }
        } while (!valid);

        return selectedOption;
    }

    /**
     * Execute main menu
     * @return the selected choice
     */
    public int executeMainMenu() {
        int choice;
        System.out.println("\nHow would you like to input your expression?");
        displayOptions();
        choice = selectOption();
        return choice;
    }
}
