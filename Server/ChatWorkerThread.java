import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatWorkerThread extends Thread
{
    private Socket theClientSocket;
    private PrintStream clientOutput;
    private Scanner clientInput;
    private DataInputStream inData;
    private DataOutputStream outData;


    public ChatWorkerThread(Socket theClientSocket)
    {
        try 
        {
            System.out.println("Connection Established...");
            this.theClientSocket = theClientSocket;
            this.clientOutput = new PrintStream(this.theClientSocket.getOutputStream());    
            //System.out.println("About to add a printstream");
            CORE.changeTheClientStreams(this.clientOutput, "add");
            this.inData = new DataInputStream(new BufferedInputStream(theClientSocket.getInputStream()));
            this.outData = new DataOutputStream(new BufferedOutputStream(theClientSocket.getOutputStream()));
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
        while(true)
        {
            //this is what the thread does
            
            try
            {
                this.clientOutput.println("Type file location:");
                int x = 0;
                byte[]bufArr = new byte[4092]; // buffer array to hold the bytes of a file
                String fileAddress = clientInput.nextLine();
                if(fileAddress.equals("/quit"))
                {
                        CORE.broadcastMessage("Someone left the server");
                        CORE.changeTheClientStreams(this.clientOutput, "remove");
                        break;
                }
                //lets the client know what they are recieving/sending
                CORE.broadcastMessage(fileAddress + " is the requested file");
                // sets the file output to the file address
                FileOutputStream fileOutput = new FileOutputStream(fileAddress);

                // gets the bytes until there are none left in the file
                while((x = this.inData.read(bufArr)) != -1)
                {
                    // writes to the file output stream
                    fileOutput.write(bufArr,0,x);
                    //clears the output
                    fileOutput.flush();
                }
                fileOutput.close();
            }
            catch (Exception e)
            {
                System.err.println("File could not transfer!!");
                e.printStackTrace();
            }
        }
        
       

    }

}
