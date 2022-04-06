import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class chatServer
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket s = new ServerSocket(2222);
        ArrayList<ChatWorkerThread> theThreads = new ArrayList<ChatWorkerThread>();
        while(true)
        {
            System.out.println("Listenning for Connection...");
            Socket client = s.accept(); //blocks
            ChatWorkerThread t = new ChatWorkerThread(client);
            theThreads.add(t);
            t.start();
        }
        
    }
}