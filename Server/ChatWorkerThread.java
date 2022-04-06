import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatWorkerThread extends Thread
{
    private Socket theClientSocket;
    private PrintStream clientOutput;
    private Scanner clientInput;

    public ChatWorkerThread(Socket theClientSocket)
    {
        try 
        {
            System.out.println("Connection Established...");
            this.theClientSocket = theClientSocket;
            this.clientOutput = new PrintStream(this.theClientSocket.getOutputStream());    
            //System.out.println("About to add a printstream");
            CORE.addClientThreadPrintStream(this.clientOutput);

            this.clientInput = new Scanner(this.theClientSocket.getInputStream());
        } 
        catch (Exception e) 
        {
            System.err.println("Bad things happened in thread!!!!!");
            e.printStackTrace();
        }
        
    }

    public void run()
    {
        //this is what the thread does
        this.clientOutput.println("What is your name?");
        String name = clientInput.nextLine();
        CORE.broadcastMessage(name + " has joined!");
        
        String message;
        String quit = "a";

        while(true)
        {
            message = clientInput.nextLine();
            if (message.equals(quit))
            {
                try{
                    
                    break;
        
                }
                catch (Exception e)
                {
                    System.err.println("Error Quitting");
                    e.printStackTrace();
                }
            }
            else
            {
                CORE.broadcastMessage(message);  
            }
            
        }

    }

}
