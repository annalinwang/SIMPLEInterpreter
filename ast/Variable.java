package ast;
import environment.*;

/**
 * Variable class evaluates a string containing the string n.
 *
 * @author Anna Wang
 * @version May 31 2018
 */
public class Variable extends Expression
{
    private String name;
    /**
     * Constructor for objects of class Variable, taking in a string
     * @param n the string
     */
    public Variable(String n)
    {
        name = n;
    }

    /**
     * Evaluates the value.
     * @param env is the environment the AST is in.
     * @return the value
     */
    public int eval(environment.Environment env)
    {
        return env.getVariable(name);
    }
}