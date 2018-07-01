package ast;
import java.util.*;
import environment.*;

/**
 * If does an if block
 *
 * @author Anna Wang
 * @version May 31 2018
 */
public class If extends Statement
{
    private Expression condition;
    private Program statement;
    private Program elseThing;
    /**
     * Constructor for objects of an if statement
     * @param condition is the condition
     * @param statement is the program to execute if the condition is true
     * @param elseThing is the program to execute if the condition is false
     */
    public If(Expression condition, Program statement, Program elseThing)
    {
        this.condition = condition;
        this.statement = statement;
        this.elseThing = elseThing;
    }
    
    /**
     * Executes an If statement. It the condition evaluates to 0, it executes
     * the else statement, otherwise, it will just execute the normal statement
     * @param env is the environment the AST Tree is in.
     */
    public void exec(environment.Environment env)
    {
        if (condition.eval(env) == 0)
        {
            List<Statement> elseStatements = elseThing.getStatements();
            for (int i = 0; i < elseStatements.size(); i++)
            {
                elseStatements.get(i).exec(env);
            }
        }
        else //if (condition.eval(env) == 1)
        {          
            List<Statement> statements = statement.getStatements();
            for (int i = 0; i < statements.size(); i++)
            {
                statements.get(i).exec(env);
            }
        }
        
    }
}
