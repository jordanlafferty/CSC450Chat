import java.io.PrintStream;
import java.util.ArrayList;

public class CORE 
{
    private static ArrayList<PrintStream> theClientStreams = new ArrayList<PrintStream>();
    
    public static synchronized void changeTheClientStreams(PrintStream ps, String doWhatWIthIt)
    {
        if(doWhatWIthIt.equals("add"))
        {
            System.out.println("adding client thread");
            CORE.theClientStreams.add(ps);
        }
        else if (doWhatWIthIt.equals("remove"))
        {
            CORE.theClientStreams.remove(ps);
        }
    }

    public static void broadcastMessage(String message)
    {
        System.out.println("About to broadcast....");
        for (PrintStream ps : CORE.theClientStreams)
        {    
            ps.println(message);
        }
    }

    public static void broadcastFile()
    {
        System.out.println("File has been transferred!");
    }
}
