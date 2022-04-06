public class clientQuit 
{
    private static int quits = 0;

    public static void setProgramQuit(int x)
    {
        quits = x;
    }

    public static boolean checkForQuit()
    {
        if (quits == 0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
}
