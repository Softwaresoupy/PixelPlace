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
        File f = new File("userDatabaseFile.txt");
        users = new userDatabase(f);
        f = new File("artDatabaseFile.txt");
        arts = new artDatabase(f);
    }

    public void startServer(){
        //starts server

    }

    public static void main(String[] args){
        Server s = new Server(1111, "wdfsfa");
        s.startServer();
        artDatabase mainAD = new artDatabase(new File("artDBFile.txt"));
        mainAD.saveFile();
        userDatabase mainUD = new userDatabase(new File("userDBFile.txt"));
        mainUD.saveFile();
    }
}
