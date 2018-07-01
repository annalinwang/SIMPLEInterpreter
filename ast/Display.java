package ast;
import environment.*;

/**
 * Writeln writes a line, taking in an expression.
 *
 * @author Anna Wang
 * @version May 31 2018
 */
public class Display extends Statement
{
    private Expression exp;

    /**
     * Constructor for objects of class writeln
     * @param exp   the expression to write
     */
    public Display(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Prints out the expression
     * @param env is the environment the AST Tree is in.
     */
    public void exec(environment.Environment env)
    {
        System.out.println(exp.eval(env));
    }
}
