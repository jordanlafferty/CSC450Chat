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
            File theFile = new File(line);
            clientOutput.println(localInput.nextLine());
            int n = 0;
            byte[]buf = new byte[4092];
            FileInputStream incomingFile = new FileInputStream(theFile);
            while((n =incomingFile.read(buf)) != -1)
            {
                outData.write(buf,0,n);
                outData.flush();

            }
            incomingFile.close();

            
            
        }
        System.out.println("goodbye");
        lt.wait();
        System.exit(0);

        
    }
}

