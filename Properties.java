public class Properties
{
    String operators = "+-*/^()";
    int[] iprec = {1, 1, 3, 3, 6, 10, 0};
    int[] sprec = {2, 2, 4, 4, 5, 0, 0};
    char[] looks = {'r', 'r', 'r', 'r', 'r', 'd', 'r'};
    char[] expect = {'d', 'd', 'd', 'd', 'd', 'd', 'r'};
    boolean[] outputable = {true, true, true, true, true, false, false};

    public Properties(){}

    public int getIPrec(String oper)
    {
        char c = oper.charAt(0);
        int index = operators.indexOf(c);
        if (index >= 0) return iprec[index];
        return -1; // Default or error value
    }

    public int getSPrec(String oper)
    {
        char c = oper.charAt(0);
        int index = operators.indexOf(c);
        if (index >= 0) return sprec[index];
        return -1; // Default or error value
    }

    public char lookslike(String oper)
    {
        char c = oper.charAt(0);
        int index = operators.indexOf(c);
        if (index >= 0) return looks[index];
        return 'd'; // Default value
    }

    public char expects(String oper)
    {
        char c = oper.charAt(0);
        int index = operators.indexOf(c);
        if (index >= 0) return expect[index];
        return 'r'; // Default value
    }

    public boolean isOutputable(String oper)
    {
        char c = oper.charAt(0);
        int index = operators.indexOf(c);
        if (index >= 0) return outputable[index];
        return true; // Default value
    }
}