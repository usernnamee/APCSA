public class Main
{
    // Table of properties for operands and operators
    static Properties table = new Properties();

    public static void main(String[] args)
    {
        // Infix expression input
        System.out.println("\nInfix expression: " + args[0]);

        // Tokenize input
        String[] expression = tokenize(args[0]);

        // Convert infix to postfix
        expression = transform(expression);

        // Print postfix expression
        System.out.print("Postfix expression: ");
        for (int i = 0; i < expression.length && expression[i] != null; i++)
        {
            System.out.print(expression[i] + " ");
        }

        // Print result
        System.out.println();
        System.out.print("Result: " + eval(expression));
        System.out.println("\n");
    }

    public static String[] tokenize(String input)
    {
        // Tokens
        String[] tokens = new String[input.length()];

        // Start of token
        int start = 0;

        // Number of tokens
        int j = 0;

        // Loop over entire input to tokenize
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if(tokenEnd(c))
            {
                // Add token before the operators / parens
                if (start < i)
                {
                    tokens[j] = input.substring(start, i);
                    j++;
                }

                // Add the operators as tokens, exclude spaces
                if(c != ' ')
                {
                    tokens[j] = input.substring(i, i + 1);
                    j++;
                }

                start = i + 1;
            }
        }

        // Add the final token if string does not end with an operator
        if (start < input.length())
        {
            tokens[j] = input.substring(start);
            j++;
        }

        // Properly size the tokens array, and copy everything
        String[] exp = new String[j];
        for (int i = 0; i < j; i++)
        {
            exp[i] = tokens[i];
        }

        return exp;
        
    }

    // When char is operator or space, signals end of token
    public static boolean tokenEnd(char c)
    {
        return "+-*/^() ".indexOf(c) >= 0;
    }

    // Transform infix into postfix
    public static String[] transform(String[] input)
    {
        // Stack for operators
        StackTransform stack = new StackTransform(input.length);

        // Output expression
        String[] pfexp = new String[input.length];
        // Position in the postfix expression
        int j = 0;

        // Loop through the input expression
        for (int i = 0; i < input.length; i++)
        {
            // If operand, add directly to postfix expression
            if (table.lookslike(input[i]) == 'd' && !input[i].equals("(")) pfexp[j++] = input[i];

            // If operator, handle precedence
            else if(stack.isEmpty()) stack.push(input[i]);

            else
            {
                // Difference in precedences
                int diff = table.getIPrec(input[i]) - table.getSPrec(stack.onTop());

                // If input precedence is higher than stack precedence, push
                if(diff > 0) stack.push(input[i]);

                // If precedences are equal (case of parens) pop the stack and bail
                else if (diff == 0) stack.pop();
                // If input precedence is lower, pop to postfix until it is higher
                else
                {
                    while(table.getIPrec(input[i]) < table.getSPrec(stack.onTop()))
                    {
                        pfexp[j] = stack.pop();
                        j++;
                    }
                    stack.push(input[i]);
                }
            }
        }

        // Flush the remaining operators in the stack to the postfix expression
        while (!stack.isEmpty())
        {
            if(!table.isOutputable(stack.onTop())) stack.pop();
            else pfexp[j++] = stack.pop();
        }

        // Properly size the expression
        String[] exp = new String[j];
        for (int i = 0; i < j; i++)
        {
            exp[i] = pfexp[i];
        }

        return exp;
    }

    
    public static double eval(String[] pf)
    {
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