import java.io.*;
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
    PrintWriter pw;
    Scanner in;


    public Server(int port){
        // Start server
        this.port = port;
        this.ipAddress = ipAddress;
        File f = new File("artFiles/userDatabaseFile.txt");
        users = new userDatabase(f);
        f = new File("artFiles/artDatabaseFile.txt");
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
                    new Thread(() -> {
                        try {
                            InputStream inStream = incoming.getInputStream();
                            OutputStream outStream = incoming.getOutputStream();
                            in = new Scanner(inStream);
                            pw = new PrintWriter(outStream, true);
                            String clientMessage;
                            while (in.hasNextLine()) {
                                clientMessage = in.nextLine();
                                System.out.println("Received: " + clientMessage);
                                useMessage(clientMessage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
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

    public void useMessage(String message) {
        String clientMessage = message;
        String [] splitString;
        if (clientMessage.substring(0, 5).equalsIgnoreCase("LOGIN")) {
            clientMessage = clientMessage.substring(5);
            splitString = clientMessage.split(",");
            clientMessage = users.loginSearch(splitString[0], splitString[1]);
            if (clientMessage.equals(","))
                sendMessage("LoginI");
            else
                sendMessage(clientMessage);
        } else if(clientMessage.substring(0, 6).equalsIgnoreCase("ADDART")){
            clientMessage = clientMessage.substring(6);
            splitString = clientMessage.split("%");
            Art added = new Art();
            added.setName(splitString[0]);
            added.setArtTime(splitString[1]);
            added.setArtUser(splitString[2]);
            added.setArtLocation(splitString[3]);
            added.setArtFile(splitString[4]);
            added.setArtDescription(splitString[5]);
            arts.addy(added);
        } else if (clientMessage.equalsIgnoreCase("EXIT")){
            arts.saveFile();
            users.saveFile();
        }
    }

    public void sendMessage(String message){
            try {
                pw.println(message);
            }catch(Exception exc){
            exc.printStackTrace();
        }
    }


    public static void main(String[] args){
        Server s = new Server(8190);
        s.startServer();
        artDatabase mainAD = new artDatabase(new File("artFiles/artDatabaseFile.txt"));
        mainAD.saveFile();
        userDatabase mainUD = new userDatabase(new File("artFiles/userDatabaseFile.txt"));
        mainUD.saveFile();
    }
}
