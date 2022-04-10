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
                int n = 0;
                byte[]bufArr = new byte[4092];
                String fileAddress = clientInput.nextLine();
                if(fileAddress.equals("/quit"))
                {
                        CORE.broadcastMessage("Someone left the server");
                        CORE.changeTheClientStreams(this.clientOutput, "remove");
                        break;
                }
                
                CORE.broadcastMessage(fileAddress + " is the requested file");
                FileOutputStream fileOutput = new FileOutputStream(fileAddress);
                while((n = this.inData.read(bufArr)) != -1){
                    fileOutput.write(bufArr,0,n);
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
