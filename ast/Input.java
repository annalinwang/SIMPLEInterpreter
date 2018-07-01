package ast;
import scanner.*;
import environment.Environment;
import java.util.Scanner;

/**
 * The Input class is an input for SIMPLE, taking in the name and expression 
 * and also the earlier expression to print later
 * if it's false and 1 if it's true
 *
 * @author Anna Wang
 * @version May 31 2018
 */
public class Input extends Statement
{
    private String name;
    private Expression exp;
    private Expression disp;

    /**
     * Creates an Input, taking in the name, expression, and 
     * first expression to display
     * @param name  the name of the input
     * @param exp   the Expression for the input
     * @param disp  the earlier Expression that needed to be displayed
     */
    public Input(String name, Expression exp, Expression disp)
    {
        this.name = name;
        this.exp = exp;
        this.disp = disp;
    }

    /**
     * Executes an input statement, printing display as well
     * @param env is the environment the AST Tree is in.
     */
    public void exec(environment.Environment env)
    {
        System.out.println(disp.eval(env));
        env.setVariable(name, exp.eval(env));        
    }
}
