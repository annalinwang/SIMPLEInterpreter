package ast;
import environment.*;

/**
 * Expression is a parent of all expressions. Each expression
 * evaluates and returns an integer including obtaining the value of a
 * variable from an environment
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public abstract class Expression
{    
    /**
     * Evaluates an expression.
     * @param env is the environment the AST is in.
     * @return the value
     */
    public abstract int eval(environment.Environment env);
}
