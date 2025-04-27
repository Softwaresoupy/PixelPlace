import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    int port;
    String ipAddress;
    User currentUser;
    ArtGallery loginGraphicUI;
    public Client(String ip, int port){
        ipAddress = ip;
        this.port = port;
    }

    public void startClient(){
        try {
            Socket s = new Socket(ipAddress, port);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            try {
                OutputStream outStream = s.getOutputStream();
            }
            finally{
                s.close();
            }
        }
        catch(IOException ioexc){
            ioexc.printStackTrace();
        }
    }

    public void login(){
        ArtGallery ag = new ArtGallery();
        ag.showLoginGUI();
    }

    public static void main(String args[]){
        Client client = new Client("127.0.0.1", 8189);
        client.startClient();
        client.login();

    }
}
