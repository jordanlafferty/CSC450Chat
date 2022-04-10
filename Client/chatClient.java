import java.io.DataInputStream;
import java.io.*;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class chatClient 
{
    public static void main(String[] args) throws Exception
    {
        Socket s = new Socket("localhost", 2222); 
        Scanner clientInput = new Scanner(s.getInputStream());
        DataInputStream inData = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        DataOutputStream outData = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        String question = clientInput.nextLine();
        System.out.println(question);
        Scanner localInput = new Scanner(System.in);
        PrintStream clientOutput = new PrintStream(s.getOutputStream());
        Thread lt = new Thread() {
            public void run()
            {
                String line;

    
                while(true)
                {
                    line = clientInput.nextLine();
                    System.out.println(line);   
                    
                }
                
            }
        };

        lt.start();
        String line;
        String quit = "/quit";

        while(true)
        {
            
            line = localInput.nextLine();
            if (line.equals(quit))
            {
                break;
            }
            // gets the file address of where it should go in the recieving client, and makes a new file
            File theFile = new File(line);
            clientOutput.println(localInput.nextLine());
            //buffer array that holds the bytes of the file
            int x = 0;
            byte[]buf = new byte[4092];
            // gets the file
            FileInputStream incomingFile = new FileInputStream(theFile);
            //reads in each byte of the file so it can get recieve it
            while((x =incomingFile.read(buf)) != -1)
            {
                outData.write(buf,0,x); //writes each byte into the output stream
                clientOutput.println("Writing the file"); //check to see if it is working
                outData.flush(); // clears the dataStream

            }
            incomingFile.close();

            
            
        }
        System.out.println("goodbye");
        lt.wait();
        System.exit(0);

        
    }
}

