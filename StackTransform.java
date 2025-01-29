public class StackTransform
{
    private String[] stack;
    private int top = -1;

    public StackTransform(int size)
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
        String pop = stack[top];

        stack[top] = null;
        top--;

        return pop;
    }

    public boolean isEmpty()
    {
        return top < 0;
    }

    public String onTop()
    {
        if(top >= 0)
        {
            return stack[top];
        }
        else
        {
            return null;
        }
    }
}