import java.io.*;
import java.util.ArrayList;

public class userDatabase implements Database{
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
                    String[] splitArtDesc = userObjectString.split("%");
                    String username = splitArtDesc[0];
                    String password = splitArtDesc[1];
                    String email = splitArtDesc[2];
                    User newUser = new User(username, password, email);
                    userArray.add(newUser);
                    System.out.println("User added: " + userObjectString);
                    userObjectString = br.readLine();
                }
                stringToObjectMatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stringToObjectMatch(){

    }
    public void addy(Object a) {
        userArray.add((User) a);
        System.out.println("Added User to array:" + a.toString());
        saveFile();
    }

    public String loginSearch(String userName, String password){
        for (User u : userArray){
            if (u.getUsername().equalsIgnoreCase(userName) && u.getPassword().equalsIgnoreCase(password)){
                return u.toString();
            }
        }
        return ","; // return error
    }
    public String registerSearch(String userName){
        for (User u : userArray){
            if (u.getUsername().equalsIgnoreCase(userName)){
                return "username taken!";
            }
        }
        return ","; // return error
    }

    public void subtract(Object a) {
        boolean removed = userArray.remove((User) a);
        if(!removed){
            System.out.println("Something went wrong. User " + a.toString().split("%")[0] + " not found");
        }
    }

    public void saveFile() {
        // open and get ready to print to file
        try{
            FileWriter fw = new FileWriter(userFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for (User u : userArray){
                bw.write(u.toString());
                bw.newLine();
                System.out.println("saving user to file:" + u.toString());
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
