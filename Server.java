import java.io.File;
public class Server {
    /* This class takes the input from the clients and translates it into data that
    can be put into the databases and their according files.
     */
    int port;
    String ipAddress;
    userDatabase users;
    artDatabase arts;

    public Server(int port, String ipAddress){
        // Start server
        this.port = port;
        this.ipAddress = ipAddress;
        File f = new File("sadfdasd");
        users = new userDatabase(f);
        users.populate();
        f = new File("sadfdasd");
        arts = new artDatabase(f);
        arts.populate();
    }

    public void startServer(){
        //starts server

    }

    public static void main(String[] args){
        Server s = new Server(1111, "wdfsfa");
        s.startServer();

    }
}
