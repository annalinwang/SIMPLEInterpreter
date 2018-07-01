package ast;
import environment.*;

/**
 * Number class creates a number that contains an int value.
 *
 * @author Anna Wang
 * @version May 31 2018
 */
public class Number extends Expression
{
    private int value;
    /**
     * Constructor for objects of class Number, taking in an integer
     * @param v   the number
    */
    public Number(int v)
    {
        value = v;
    }
    
    /**
     * Returns the value of the number
     * @param env is the environment the AST is in.
     * @return number
     */
    public int eval(environment.Environment env)
    {
        return value;
    }
}
