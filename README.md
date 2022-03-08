# ETERNITY - COMP354 Project
## Team N
Jiangrui Wen, Jimmy Wong, Weiliang Xie, Jialin Yang, Yunwei Yang, Yiyi Yao, Aurélie Ye, and Fan Zhang

## Getting Started
- To run the application, make sure that you at least have `Java 10` installed on your computer.
- Run the provided `jar` file called `Eternity.jar` on a terminal or console using the command `java -jar Eternity.jar`
- You can also open the provided source code in an IDE and run the class `Eternity.java`

## How To Use Eternity
- Arguments can be provided when running the `jar` for a one-time computation.
    - Ex: `java -jar Eternity.jar sinh 2`
- If not arguments are provided, the application will launch and you will be in the Eternity "environment" where you will be able to freely compute mathematical expressions.

## CLI Command and Math Functions
- Helpful information and information on the specific commands can also be found in the application itself on startup or by using the `help` command.

- Basic commands in ETERNITY
    - `help`: Get a comprehensive list of all ETERNITY commands and all supported math functions and how to use them
    - `exit`: Exit ETERNITY
    - `history`: Get a list of all valid math expressions that were entered as well as their results.`
    - `export`: Export history to a file.
        - Usage: `export` [`file-name`]
        - Example: export history.txt
    - `clear`: Clear the history.
    - `file`: Read math expressions from a file.
        - Usage: `file` [`file-name`]
        - Example: file example.txt
    - `>`: Save math expression and result to an output file.
        - Usage: [`expression`] `>` [`file-name`]
        - Example: 2 + 2 > output.txt
    - `format`: Format a math expression's result with a given accuracy as a positive integer.
        - Usage: [`expression`] `format` [`accuracy`]
        - Example: 2 + 2 format 2
    - Specific commands can be combined:
        - `file` [`file-name`] [`expression`] `>` [`output-file-name`] `format` [`accuracy`]
        - Example: file example.txt > output.txt format 2

- The supported math functions and their usages are:
    - "All basic arithmetic operations (+ - * /)
        - Example: 2+3
        - Example: 2*3
        - Example: 2-3
        - Example: 2/3
    - Unary minus or negative number (~)
        - Example: ~1
        - Example: ~0.5
    - `log b x`
        - Usage: `log` [base] [argument]
        - Example: `log 10 100`
    - `sd` (Standard Deviation)
        - Usage: `sd` [argument] [argument2] [argument3] ... [argument n]
        - Example: `sd 1 2 3 4`
    - `sinh x`
        - Usage: `sinh` [argument]
        - Example: `sinh 2`
    - `arccos x`
        - Usage: `arccos` [argument]
        - Example: `arccos 0.5`
    - `x^y`
        - Usage: [base]^[exponent]
        - Example: `2^4`
    - `a*b^x`
        - Usage: [a]*[b]^[x]
        - Example: `2*10^3`
    - `mad` (Mean Average Deviation)
        - Usage: `mad` [argument] [argument2] [argument3] ... [argument n]
        - Example: `mad 1 2 3 4`
    - `gamma`
        - Usage: `gamma` [argument]
        - Example: `gamma 5`