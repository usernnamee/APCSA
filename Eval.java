public class Eval
{
    // Table of properties for operands and operators
    static Properties table = new Properties();

    public static void main(String[] args)
    {
        // Infix expression input
        System.out.println("\nPostfix expression: " + args[0]);

        // Tokenize input
        String expression = tokenize(args[0]);

        // Print result
        System.out.println();
        System.out.print("Result: " + eval(expression));
        System.out.println("\n");
    }

    // Adds spaces between tokens in expression
    public static String tokenize(String input)
    {
        // Tokens
        String tokenized = "";
        int start = 0;

        // Loop over entire input to tokenize
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if(tokenEnd(c))
            {
                // Add token before the operators / parens
                if (start < i)
                {
                    tokenized += input.substring(start, i) + " ";
                }

                // Add the operators as tokens, exclude spaces
                if(c != ' ')
                {
                    tokenized += input.substring(i, i + 1) + " ";
                }

                start = i + 1;
            }
        }

        // Add the final token if string does not end with an operator
        if (start < input.length())
        {
            tokenized += input.substring(start);
        }

        return tokenized;

    }

    // When char is operator or space, signals end of token
    public static boolean tokenEnd(char c)
    {
        return "+-*/^() ".indexOf(c) >= 0;
    }

    public static double eval(String expression)
    {

        String[] pf = expression.split("\\s+");

        StackEval stack = new StackEval(pf.length);
        double result;
        double r1;
        double r2;

        for(int i = 0; i < pf.length; i++)
        {
            switch(pf[i])
            {
                case "+":
                    result = Double.parseDouble(stack.pop()) + Double.parseDouble(stack.pop());
                    stack.push(Double.toString(result));
                    break;

                case "-":
                    r2 = Double.parseDouble(stack.pop());
                    r1 = Double.parseDouble(stack.pop());
                    result = r1 - r2;
                    stack.push(Double.toString(result));
                    break;

                case "*":
                    result = Double.parseDouble(stack.pop()) * Double.parseDouble(stack.pop());
                    stack.push(Double.toString(result));
                    break;

                case "/":
                    r2 = Double.parseDouble(stack.pop());
                    r1 = Double.parseDouble(stack.pop());
                    result = r1 / r2;
                    stack.push(Double.toString(result));
                    break;

                case "^":
                    double exp = 1;
                    double pow = Double.parseDouble(stack.pop());
                    double base = Double.parseDouble(stack.pop());

                    for(int j = 0; j < pow; j++)
                    {
                        exp *= base;
                    }

                    stack.push(Double.toString(exp));
                    break;
                
                default: stack.push(pf[i]);
            }
        }
        return Double.parseDouble(stack.pop());
    }
}