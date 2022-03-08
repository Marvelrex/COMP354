package eternity;

import exceptions.InvalidArgumentException;
import exceptions.InvalidDomainException;
import exceptions.ParenthesisMismatchException;
import exceptions.UnknownFunctionException;
import formatter.OutputFormatter;
import parser.ReversePolishNotationEvaluator;
import parser.ShuntingYard;
import utils.IOFunctions;
import utils.Menu;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Eternity {

    private final static Menu menu = new Menu();
    private final static IOFunctions ioFunctions = new IOFunctions();
    private final static Scanner keyboard = new Scanner(System.in);
    private final static ArrayList<String> totalExpressions = new ArrayList<>();
    private final static ArrayList<Double> totalResults = new ArrayList<>();

    public static void main(String... args) {

        if (args.length == 0) {
            menu.displayLogo();
            menu.startInfo();
            noArgumentCli();
        } else {
            mathExpression(String.join(" ", args));
        }

        keyboard.close();
    }

    public static void noArgumentCli() {
        boolean done = false;

        while (!done) {
            System.out.print("\n> ");
            String expression = keyboard.nextLine();

            switch (expression.toLowerCase()) {
                case "exit":
                    menu.exitInfo();
                    done = true;
                    break;
                case "help":
                    menu.help();
                    break;
                case "history":
                    menu.displayResults(totalExpressions, totalResults);
                    break;
                case "clear":
                    totalExpressions.clear();
                    totalResults.clear();
                    System.out.println("History has been cleared.");
                    break;
                default:
                    if (expression.contains("export")) {
                        if (totalExpressions.isEmpty() || totalResults.isEmpty()) {
                            System.out.println("History is empty.");
                            break;
                        }

                        var outFileName = expression.substring(expression.indexOf("export") + 7);
                        ioFunctions.createOutputFile(totalExpressions, totalResults, outFileName);
                    } else {
                        mathExpression(expression);
                    }
            }
        }
    }

    // Provided command was a math command with possible modifiers
    private static void mathExpression(String expression) {
        boolean isFromFile = false;
        boolean isToFile = false;
        boolean isFormat = false;
        int accuracy = 0;
        var expressions = new ArrayList<String>();

        // Replace whitespaces by standard space character to simplify string manipulation
        expression = expression.replaceAll("\\s+", " ");

        // Check for file input
        if(expression.startsWith("file")) {
            isFromFile = true;
            // Read from input file
            var fileExpressions = ioFunctions.fileInput(getInputFileName(expression));

            expressions.addAll(fileExpressions);

            if (expression.contains("format")) {
                isFormat = true;

                try {
                    accuracy = Integer.parseInt(expression.substring(expression.indexOf("format") + 6).trim());
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                    isFormat = false;
                }
            }
        }

        String outFileName = "";
        var results = new ArrayList<Double>();

        // Simple expression with no modifiers
        if (!expression.startsWith("file") && !expression.contains(">") && !expression.contains("format")) {
            expressions.add(expression);
        } else if (expression.contains(">")) {
            isToFile = true;
            try {
                outFileName = expression.substring(expression.indexOf(">") + 2,
                        (expression.contains("format")) ?
                                expression.indexOf("format") :
                                expression.length());
            } catch (StringIndexOutOfBoundsException e) {
                return;
            }

            if(!expression.startsWith("file")) {
                var mathExpression = expression.substring(0, expression.indexOf(">"));
                expressions.add(mathExpression);
            }

            if(expression.contains("format")) {
                isFormat = true;
                try {
                    accuracy = Integer.parseInt(expression.substring(expression.indexOf("format") + 6).trim());
                } catch(NumberFormatException e) {
                    System.out.println(e.getMessage());
                    isFormat = false;
                }
            }
        } else if (expression.contains("format") && !expression.contains(">") && !expression.contains("file")) {
            isFormat = true;

            var mathExpression = expression.substring(0, expression.indexOf("format"));

            try {
                accuracy = Integer.parseInt(expression.substring(expression.indexOf("format") + 6).trim());
                expressions.add(mathExpression);
            } catch (NumberFormatException e) {
                System.out.println("We couldn't quite get the format. Please use the [help] command for more information on how to use ETERNITY.");
                isFormat = false;
            }
        }

        // Calculate results of the given expressions
        for(String s : expressions) {
            double result = evaluateExpression(s.toLowerCase());

            // No results if the expression was invalid
            if (!Double.isNaN(result)) {
                totalExpressions.add(s);

                if (isFormat) {
                    try {
                        result = OutputFormatter.formatOutput(result, accuracy);
                    } catch (InvalidDomainException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                }

                totalResults.add(result);
                results.add(result);
            } else {
                if (isFromFile) {
                    System.out.println("The expression [" + s + "] in file " + outFileName + " contains an error.");
                }
                return;
            }
        }

        if(isToFile) {
            ioFunctions.createOutputFile(expressions, results, outFileName);
        } else {
            menu.displayResults(expressions, results);
        }
    }

    // Gets input file name from given expression
    private static String getInputFileName(String expression) {
        // Get file name
        var fileName = expression.substring(expression.indexOf(" ") + 1);

        if (expression.contains(">")) {
            fileName = expression.substring(expression.indexOf(" ") + 1, expression.indexOf(">"));
        }

        if (expression.contains("format") && !expression.contains(">")) {
            fileName = expression.substring(expression.indexOf(" ") + 1, expression.indexOf("format"));
        }

        return fileName;
    }

    // Evaluate the given math expression
    private static double evaluateExpression(String expression) {
        double result = Double.NaN;

        try {
            return new ReversePolishNotationEvaluator(new ShuntingYard(expression).parse()).evaluate();
        } catch(ParenthesisMismatchException | UnknownFunctionException e) {
            System.out.println(e.getMessage() + ". Please use the [help] command for more information on how to use ETERNITY.");
        } catch(InvalidDomainException e) {
            System.out.println("The argument is not within the domain of this function!\n" + e.getMessage());
        } catch(InvalidArgumentException e) {
            System.out.println("The number of arguments is incorrect. " + e.getMessage() + " For more information on how to use the functions, please use the [help] command.");
        } catch(NoSuchElementException e) {
            System.out.println("Syntax error. For more information on how to use the functions, please use the [help] command.");
        } catch(Exception e) {
            System.out.println("We didn't quite understand your expression. Please use the [help] command for more information on how to use ETERNITY.");
        }

        return result;
    }
}
