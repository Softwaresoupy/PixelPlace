import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    /* This class takes the input from the clients and translates it into data that
    can be put into the databases and their according files.
     */
    int port;
    String ipAddress;
    userDatabase users;
    artDatabase arts;

    public Server(int port){
        // Start server
        this.port = port;
        this.ipAddress = ipAddress;
        File f = new File("userDatabaseFile.txt");
        users = new userDatabase(f);
        f = new File("artDatabaseFile.txt");
        arts = new artDatabase(f);
    }

    public void startServer(){
        //starts server
        try
        {
            ServerSocket s = new ServerSocket(port); //the server socket
            boolean over = false;
            while(!over)  //put in a loop that keeps running
            {
                Socket incoming = s.accept(); //accept a connection from a client
                try
                {
                    InputStream inStream = incoming.getInputStream();  // the Input stream handler
                    OutputStream outStream = incoming.getOutputStream();  // the output stream handler

                    Scanner in = new Scanner(inStream);   //setup of input
                    PrintWriter out = new PrintWriter(outStream,true);  //sends output
                }
                catch (Exception exc1){
                    exc1.printStackTrace();
                }
            }
        }
        catch (Exception exc2)
        {
            exc2.printStackTrace();
        }
    }

    public static void main(String[] args){
        Server s = new Server(8189);
        s.startServer();
        artDatabase mainAD = new artDatabase(new File("artDBFile.txt"));
        mainAD.saveFile();
        userDatabase mainUD = new userDatabase(new File("userDBFile.txt"));
        mainUD.saveFile();
    }
}
