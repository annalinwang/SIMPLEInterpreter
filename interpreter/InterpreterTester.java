package interpreter;
import environment.*;
import java.io.*;
import java.util.*;
import ast.*;

/**
 * InterpreterTester tests Interpreter.
 *
 * @author Anna Wang
 * @version May 31 2018
 */
public class InterpreterTester
{
    /**
     * @param args  argument
     */
    public static void main (String[]args) throws 
    FileNotFoundException, scanner.ScanErrorException
    {
        FileInputStream inStream = new FileInputStream(
                new File("/Users/Anna/Documents/Compilers/Lab/parser/simpleTest2.txt"));
        scanner.Scanner lex = new scanner.Scanner(inStream);
        Interpreter p = new Interpreter(lex);
        Environment env = new Environment(null);
        Program prog = p.parseProgram();
        prog.exec(env);
    }
}
