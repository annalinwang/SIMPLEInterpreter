package ast;
import java.util.*;

/**
 * While does an while loop block
 *
 * @author Anna Wang
 * @version May 31 2018
 */
public class While extends Statement
{
    private Expression exp;
    private Program p;

    /**
     * Constructor for objects of a while statement
     * @param exp is the condition
     * @param p is the program to execute while exp is true
     */
    public While(Expression exp, Program p)
    {  
        this.exp = exp;
        this.p = p;
    }

    /**
     * Executes the program while exp is true, or equal to 1
     * @param env is the environment the AST Tree is in.
     */
    public void exec(environment.Environment env)
    {
        while (exp.eval(env) == 1)
        {
            List<Statement> stmts = p.getStatements();
            for (int i = 0; i < stmts.size(); i++)
            {
                stmts.get(i).exec(env);
            }
        }
    }
}
