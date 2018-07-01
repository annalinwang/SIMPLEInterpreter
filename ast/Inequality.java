package ast;
import environment.*;
import java.util.*;

/**
 * The Inequality class evaluates an inequality, returning 0 
 * if it's false and 1 if it's true. It takes in a list of operators
 * and a list of numbers and then iterates through the operators
 * to see if the whole inequality is true or not.
 *
 * @author Anna Wang
 * @version May 31 2018
 */
public class Inequality extends Expression
{
    private List<Expression> expressions;
    private List<String> operators;

    /**
     * Creates an inequality by taking in a list of the numbers and a 
     * list of the operators
     * @param expressions   list of numbers
     * @param operators     list of operators
     */
    public Inequality(List<Expression> expressions, List<String> operators)
    {
        this.expressions = expressions;
        this.operators = operators;
    }

    /**
     * Evaluates the inequality, returning 1 if the inequality
     * is true and 0 if the inequality is false
     * @param env is the environment the AST is in.
     * @return the value
     */
    public int eval(environment.Environment env)
    {
        for (int i = 0; i < operators.size(); i++)
        {
            int exp1 = expressions.get(i).eval(env);
            int exp2 = expressions.get(i + 1).eval(env);
            String operator = operators.get(i);
            if (operators.get(i).equals("<") && (!(exp1 < exp2)))
                return 0;
            else if (operators.get(i).equals(">") && (!(exp1 > exp2)))
                return 0;
            else if (operators.get(i).equals("=") && (!(exp1 == exp2)))
                return 0;
            else if (operators.get(i).equals(">=") && (!(exp1 >= exp2)))
                return 0;
            else if (operators.get(i).equals("<=") && (!(exp1 <= exp2)))
                return 0;
            else if (operators.get(i).equals("<>") && (!(exp1 != exp2)))
                return 0;
        }
        return 1;
    }
}
