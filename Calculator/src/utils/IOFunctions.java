package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class IOFunctions {
    /**
     * Prompt user for filename
     * @return filename
     */
    static ArrayList<String>expressions =  new ArrayList<>();

    public String getFileName() {
        Scanner kb = new Scanner(System.in);
        System.out.print("Please enter the name of the file you wish to use (ex: ex1.txt): ");
        String filename = kb.nextLine();
        return filename;
    }

    /**
     * parse file for expression
     * @return math expression
     */
    public ArrayList<String> fileInput() {
        String expression = "";
        File file = new File(getFileName());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((expression = reader.readLine()) != null) {

                expressions.add(expression);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return expressions;
    }

    /**
     * Parse file for expressions given a file name
     * @param fileName Name of file to be parsed
     * @return Expressions parsed from file
     */
    public ArrayList<String> fileInput(String fileName) {
        expressions.clear();
        String expression = "";
        File file = new File(fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((expression = reader.readLine()) != null) {
                expressions.add(expression);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return expressions;
    }

    /**
     * Creates output file based on the given expression and result
     * @param expressions expressions
     * @param results results
     */
    public void createOutputFile(ArrayList<String> expressions, ArrayList<String> results) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream("output.txt"));
            int size = expressions.size();

            for (int i = 0; i<size; i++){
                writer.println("Expression: " + expressions.get(i));
                writer.println("Result: " + results.get(i));
            }
            System.out.println("Results has been saved to output.txt");
        }
        catch(IOException e) {
            System.out.println("Sorry, the document could not be created.");
        }
        finally {
            if(writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Create an output file from results with a provided file name
     * @param expressions expressions
     * @param results results
     * @param fileName output file name
     */
    public void createOutputFile(ArrayList<String> expressions, ArrayList<Double> results, String fileName) {
        PrintWriter writer = null;

        if (!results.isEmpty()) {
            try {
                writer = new PrintWriter(new FileOutputStream(fileName));
                int size = expressions.size();

                for (int i = 0; i < size; i++){
                    writer.println("Expression: " + expressions.get(i));
                    writer.println("Result: " + results.get(i));
                }

                System.out.println("Results have been saved to " + fileName);
            }
            catch(IOException e) {
                System.out.println("Sorry, the document could not be created.");
            }
            finally {
                if(writer != null) {
                    writer.close();
                }
            }
        }
    }
}
