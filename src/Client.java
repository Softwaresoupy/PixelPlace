import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Client {
    int port;
    String ipAddress;
    User currentUser;
    OutputStream outStream;
    PrintWriter pw;
    Socket s;
    Locale en, zh;
    ResourceBundle bundle;

    // All the GUIs associated with it
    ArtGallery loginGraphicUI;

    Scanner in;
    public Client(String ip, int port){
        ipAddress = ip;
        this.port = port;
        en = new Locale("en", "US");
        zh = new Locale("zh", "CN");
        bundle = ResourceBundle.getBundle("PixelPlace", Locale.getDefault());
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

    public void useMessage(String message) {
        if (message.equalsIgnoreCase("loginI"))
            loginGraphicUI.incorrectLogin();
        else if (message.equalsIgnoreCase("username already taken!")) {
            loginGraphicUI.incorrectRegister();
        } else if (message.equalsIgnoreCase("register success!")){
            loginGraphicUI.correctRegister();
        }else {
            loginGraphicUI.correctLogin(message);
        }
    }

    public void login(){
        loginGraphicUI = new ArtGallery(this, bundle);
        loginGraphicUI.showLoginGUI(this, bundle);
    }

    public void changeLang(String language){
        if (language.equalsIgnoreCase("English")){
            bundle = ResourceBundle.getBundle("PixelPlace", en);
            loginGraphicUI.changeLangUI(bundle);
        } else if (language.equalsIgnoreCase("中文")){
            try {
                bundle = new PropertyResourceBundle(new InputStreamReader(new FileInputStream("src/PixelPlace_zh.properties"), StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            loginGraphicUI.changeLangUI(bundle);
            //System.out.println(bundle);
        }
    }

    public ResourceBundle getLangBundle(){
        return bundle;
    }

    public static void main(String args[]){
        Client client = new Client("127.0.0.1", 8190);
        client.startClient();
        client.login();

    }
}
