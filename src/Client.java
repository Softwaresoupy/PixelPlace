import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    int port;
    String ipAddress;
    User currentUser;
    OutputStream outStream;
    PrintWriter pw;
    Socket s;

    // All the GUIs associated with it
    ArtGallery loginGraphicUI;

    Scanner in;
    public Client(String ip, int port){
        ipAddress = ip;
        this.port = port;
    }

    public void startClient(){
        try {
            s = new Socket(ipAddress, port);
            pw = new PrintWriter(s.getOutputStream(), true);
            outStream = s.getOutputStream();
            in = new Scanner(s.getInputStream());
            new Thread(() -> {
                receiveMessage();
            }).start();
        }
        catch(Exception ioexc){
            ioexc.printStackTrace();
        }
    }

    public void sendMessage(String message){
        System.out.println("Sending message '" + message + "'");
        pw.println(message);
        if (message.equalsIgnoreCase("EXIT")){
            try {
                if (pw != null) pw.close();
                if (in != null) in.close();
                if (s != null && !s.isClosed()) s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void receiveMessage(){
        String serverMessage;
        try{
            while(in.hasNextLine()){
                serverMessage = in.nextLine();
                System.out.println("using message '" + serverMessage + "'");
                useMessage(serverMessage);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void useMessage(String message){
        if (message.equalsIgnoreCase("loginI"))
            loginGraphicUI.incorrectLogin();
        else {
            loginGraphicUI.correctLogin(message);
        }
    }

    public void login(){
        loginGraphicUI = new ArtGallery(this);
        loginGraphicUI.showLoginGUI(this);
    }

    public static void main(String args[]){
        Client client = new Client("127.0.0.1", 8190);
        client.startClient();
        client.login();

    }
}
