public class StackEval
{
    private String[] stack;
    private int top = -1;

    public StackEval(int size)
    {
        stack = new String[size];
    }

    public void push(String push) 
    {
        top++;
        stack[top] = push;
    }

    public String pop()
    {
        if(!isEmpty())
        {
            String pop = stack[top];

            stack[top] = null;
            top--;

            return pop;
        }
        else return null;
    }

    public boolean isEmpty()
    {
        return top < 0;
    }

    public String onTop()
    {
        if(!isEmpty())
        {
            return stack[top];
        }
        else
        {
            return null;
        }
    }
}