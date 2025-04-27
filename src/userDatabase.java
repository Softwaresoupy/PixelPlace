import java.io.*;
import java.util.ArrayList;

public class userDatabase extends Database{
    File userFile;
    ArrayList<User> userArray;

    public userDatabase(File f){
        userFile = f;
        userArray = new ArrayList<User>();
        populate();
    }
    public void populate(){
        //read through file
        try{
            FileReader fr = new FileReader(userFile);
            BufferedReader br = new BufferedReader(fr);
            String userObjectString = br.readLine();
            int id = 0;
                while (userObjectString != null) {

                    String[] splitArtDesc = userObjectString.split("%*'");
                    String username = splitArtDesc[0];
                    String password = splitArtDesc[1];
                    String email = splitArtDesc[2];
                    User newUser = new User(username, password, email);
                    userObjectString = br.readLine();
                    newUser.setUploadedArtworksString(userObjectString.split(","));
                    userObjectString = br.readLine();
                    newUser.setFollowingString(userObjectString.split(","));
                    userArray.add(newUser);
                }
                stringToObjectMatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stringToObjectMatch(){

    }
    public void add(Object a) {
        userArray.add((User) a);
        saveFile();
    }

    public void subtract(Object a) {
        boolean removed = userArray.remove((User) a);
        if(!removed){
            System.out.println("Something went wrong. User " + a.toString().split("'*%")[0] + " not found");
        }
    }

    public void saveFile() {
        // open and get ready to print to file
        try{
            FileWriter fw = new FileWriter(userFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for (User u : userArray){
                bw.write(u.toString());
                bw.write(u.getUploadedArtworksString().toString()); //CANT HAVE ,
                //bw.write(u.getCreatedGalleries().toString());
                bw.write(u.getFollowingString().toString()); //CANT HAVE ,
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
