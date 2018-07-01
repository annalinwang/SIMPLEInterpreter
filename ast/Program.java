package ast;
import java.util.*;
import environment.*;
import java.io.*;
import ast.*;

/**
 * Program defines a program with a list of statements. It
 * keeps track of the list of procedures and the statement after
 *
 * @author Anna Wang
 * @version May 28 2018
 */
public class Program
{
    private List<Statement> statements;

    /**
     * Creates a Program with a list of statements
     * @param statements     the list of statements for the program
     */
    public Program(List<Statement> statements)
    {
        this.statements = statements;
    }
    
    /**
     * Returns the list of statements
     * @return  the statements
     */
    public List<Statement> getStatements()
    {
        return statements;
    }

    /**
     * Executes the procedures in the correct environment
     * @param env   the environment for storing variables
     */
    public void exec (Environment env)
    {
        for (int i = 0; i < statements.size(); i++)
        {
            statements.get(i).exec(env);
        }
    }
}
