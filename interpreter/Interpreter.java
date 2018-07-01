package interpreter;
import java.util.*;
import ast.*;
import environment.*;
import scanner.*;

/**
 * Interpreter Class creates an interpreter that takes in a stream 
 * of tokens outputted by the Scanner which scans an input file, 
 * and will check whether the input follows the following SIMPLE grammar:
 * 
 * Program -> Statement P
 * P -> Program | e
 * Statement -> display Expression St1
 * | assign id = Expression
 * | while Expression do Program end
 * | if Expression then Program St2
 * St1 -> read id | e
 * St2 -> end | else Program end
 * Expression -> Expression relop AddExpr
 * | AddExpr
 * AddExpr -> AddExpr + MultExpr
 * | AddExpr – MultExpr
 * | MultExpr
 * MultExpr -> MultExpr * NegExpr
 * | MultExpr / NegExpr
 * | NegExpr
 * NegExpr -> -Value
 * | Value
 * Value -> id | number | (Expression)
 * 
 * @author Anna Wang
 * @version May 31 2018
 */
public class Interpreter
{
    private scanner.Scanner scan;
    private String currentToken;
    /**
     * Creates an interpreter object with a given Scanner
     * @param x the scanner that will scan the input
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Interpreter(scanner.Scanner x) throws ScanErrorException
    {
        scan = x;
        currentToken = x.nextToken();
    }

    /**
     * Takes in the expected token and if it matches current token, 
     * replaces current token by asking scanner for next token. 
     * If they don't match, it instead throws an illegal argument expression
     * @param expectedToken the expected token
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    private void eat(String expectedToken) throws ScanErrorException
    {
        if (expectedToken.equals(currentToken))
        {
            currentToken = scan.nextToken();
        }
        else
        {
            throw new IllegalArgumentException("Expected " + expectedToken
                + " but found " + currentToken); //change later
        }
    }

    /**
     * With the precondition that the current token is an integer, this
     * parses that number, effectively eating it.
     * 
     * precondition: current token is an integer
     * postcondition: the number token is eaten
     * and currentToken is the first token after the Integer
     * @return the value of the parsed integer
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    private Expression parseNumber() throws ScanErrorException
    {
        Expression num = new ast.Number(Integer.parseInt(currentToken));
        eat(currentToken);
        return num;
    }

    /**
     * Parses a statement containing numbers or symbols
     * like parenthesis, negatives, or others, effectively 
     * using recursion to return the expression.
     * 
     * precondition: currentToken is -, (, variable/procedure id, or an int
     * postcondition: the factor is eaten and
     * currentToken is the first token after the factor
     * @return the value of the parsed factor.
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Expression parseFactor() throws ScanErrorException
    {
        if (currentToken.equals("-"))
        {
            eat("-");
            return new BinOp("-", new ast.Number(0), parseFactor());
        }
        else if (currentToken.equals("("))
        {
            eat("(");
            Expression num = parseExpression();
            eat(")");
            return num;
        }
        else if (scan.isDigit(currentToken.charAt(0)))
            return parseNumber();
        else
        {
            String var = currentToken;
            eat(currentToken);
            return new Variable(var);
        }

    }

    /**
     * Recursively parses a term that includes division or
     * multiplication, calling parseFactor inside
     * 
     * precondition: the currentToken is the start of the factor
     * postcondition: the whole term is eaten
     * and currentToken is the first token after the term
     * @return  the Expression object
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Expression parseTerm()throws ScanErrorException
    {
        Expression val = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            if (currentToken.equals("*"))
            {
                eat("*");
                val = new BinOp("*", val, parseFactor());
            }
            if (currentToken.equals("/"))
            {
                eat("/");
                val = new BinOp("/", val, parseFactor());
            }
        }
        return val;
    }

    /**
     * Recursively parses an expression that can include 
     * relop characters like <, >, >=, <=, <>, or =.
     * 
     * precondition: currentToken is the start of a term
     * postcondition: the whole expression is eaten
     * and currentToken is the first token after the expression
     * @return the value of the parsed expression
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Expression parseExpression () throws ScanErrorException
    {
        Expression val = parseTerm();
        while (currentToken.equals("<") || currentToken.equals(">") 
            || currentToken.equals("=") || currentToken.equals("+") 
            || currentToken.equals("-"))
        {
            if (currentToken.equals("<") || currentToken.equals(">") 
                || currentToken.equals("="))
            {
                List<Expression> expressions = new ArrayList<Expression>();
                List<String> operators = new ArrayList<String>();
                expressions.add(val);
                while (currentToken.equals("<") || currentToken.equals(">") 
                    || currentToken.equals("="))
                {
                    String operator = currentToken;
                    eat(currentToken);
                    operators.add(operator);
                    expressions.add(parseTerm());
                }
                return new Inequality(expressions, operators);
            }

            else if (currentToken.equals("+"))
            {
                eat("+");
                val = new BinOp("+", val, parseTerm());
            }
            else if (currentToken.equals("-"))
            {
                eat("-");
                val = new BinOp("-", val, parseTerm());
            }
        }
        return val;
    }

    /**
     * Recursively parses a statement according the the SIMPLE grammar
     * 
     * preondition: currentToken is either display, while, if, or assign
     * postcondition: the whole statement is eaten
     * and currentToken is the first token after the statement
     * @return  the statement
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Statement parseStatement() throws ScanErrorException
    {
        if (currentToken.equals("display"))
        {
            eat("display");
            Expression exp = parseExpression();
            if (currentToken.equals("read"))
            {
                eat("read");
                String name = currentToken;
                eat(name);
                System.out.println("Please enter an input for " + name + ": ");
                java.util.Scanner sc = new java.util.Scanner(System.in);
                ast.Number num = new ast.Number(sc.nextInt());
                List<Statement> statements = new ArrayList<Statement>();
                return new Input(name, num, exp);
            }
            else
            {
                return new Display(exp);
            }
        }
        else if (currentToken.equals("while"))
        {
            eat("while");
            Expression exp = parseExpression();
            eat("do");
            Program p = parseProgram();
            eat("end");
            return new While(exp, p);
        }
        else if (currentToken.equals("if"))
        {
            eat("if"); 
            Expression exp = parseExpression();
            eat("then");
            Program st2 = parseProgram();
            List<Statement> stmts = new ArrayList<Statement>();
            Program elsestmts = new Program(stmts);
            if (currentToken.equals("else"))
            {
                eat("else");
                elsestmts = parseProgram();
            }
            eat("end");
            return new If(exp, st2, elsestmts);
        }
        eat("assign");
        String id = currentToken;
        eat(currentToken);
        eat("=");
        Assignment a = new Assignment(id, parseExpression());
        return a;
    }

    /**
     * Parses a list of statements and returns a Program 
     * @return  a Program that contains the procedures and statements
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Program parseProgram() throws ScanErrorException
    {
        List<Statement> statements = new ArrayList<Statement>();
        while (!currentToken.equals("end") && !currentToken.equals("else"))
        {
            statements.add(parseStatement());
        }
        return new Program(statements);
    }
}