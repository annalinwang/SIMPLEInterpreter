package ast;
import environment.*;

/**
 * BinOp evaluates a binary operator, taking in a string and two expressions
 *
 * @author Anna Wang
 * @version May 31 2018
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;
    
    /**
     * Constructor for objects of class BinOp
     * @param s the string
     * @param expression1   the first expression
     * @param expression2   the second expression
     */
    public BinOp(String s, Expression expression1, Expression expression2)
    {
        op = s;
        exp1 = expression1;
        exp2 = expression2;
    }
    
    /**
     * Evaluates the value of the binary operator
     * @param env is the environment the AST is in.
     * @return the value
     */
    public int eval(environment.Environment env)
    {
        if (op.equals("*"))
        {
            return exp1.eval(env) * exp2.eval(env);
        }
        else if (op.equals("+"))
        {
            return exp1.eval(env) + exp2.eval(env);
        }
        else if (op.equals("-"))
        {
            return exp1.eval(env) - exp2.eval(env);
        }
        else //if (op.equals("/"))
        {
            return exp1.eval(env) / exp2.eval(env);
        }
    }
}
