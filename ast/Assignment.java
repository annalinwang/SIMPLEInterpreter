package ast;
import environment.*;

/**
 * Assignment assigns a variable to a value, 
 * taking in a string and an expression.
 *
 * @author Anna Wang
 * @version May 31 2018
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;
    /**
     * Constructor for objects of class Assignment
     * @param v   the variable
     * @param e   the value
    */
    public Assignment(String v, Expression e)
    {
        var = v;
        exp = e;
    }
    
    /**
     * Executes the assignment, sets a value with a variable.
     * @param env is the environment the AST Tree is in.
     */
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }
}