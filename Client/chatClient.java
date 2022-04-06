import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class chatClient 
{
    public static void main(String[] args) throws Exception
    {
        Socket s = new Socket("localhost", 2222); 
        Scanner clientInput = new Scanner(s.getInputStream());
        String question = clientInput.nextLine();
        System.out.println(question);
        Scanner localInput = new Scanner(System.in);
        PrintStream clientOutput = new PrintStream(s.getOutputStream());
        Thread lt = new Thread() {
            public void run()
            {
                String line;
                String quit = "/quit";
                int n = 0;
    
                while(true)
                {
                    line = clientInput.nextLine();
                    if (line.equals(quit))
                    {
                        try
                        {
                            clientQuit.setProgramQuit(1);
                            return;
                        }
                        catch (Exception e)
                        {
                            System.err.println("Error Quitting");
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        System.out.println(line);
                    }
                    
                }
                
            }
        };

        lt.start();

        while(clientQuit.checkForQuit() == false)
        {
            clientOutput.println(localInput.nextLine());
        }
        
    }
}

