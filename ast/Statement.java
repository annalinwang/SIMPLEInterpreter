package ast;
import environment.*;

/**
 * Statement is a parent of all statements. Statements performs
 * actions such as printing and storing assigned values in the environment.
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public abstract class Statement
{
    /**
     * Executes a statement. 
     * @param env is the environment the AST Tree is in.
     */
    public abstract void exec(environment.Environment env);
    
}