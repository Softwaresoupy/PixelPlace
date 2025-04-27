import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class artDatabase extends Database{
    File artFile;
    ArrayList<Art> artArray;

    public artDatabase(File f){
        artFile = f;
        artArray = new ArrayList<Art>();
        populate();
    }

    public void populate(){
        //read through file
        try{
            FileReader fr = new FileReader(artFile);
            BufferedReader br = new BufferedReader(fr);
            String artObjectString = br.readLine();
            int id = 0;
            while (artObjectString != null) {
                Art newArt = new Art(id);
                id++;
                String[] splitArtDesc = artObjectString.split("%*'");
                newArt.setName(splitArtDesc[1]);
                newArt.setArtTime(splitArtDesc[2]);
                newArt.setArtUser(splitArtDesc[3]);
                newArt.setArtLocation(splitArtDesc[4]);
                newArt.setArtDescription(splitArtDesc[5]);
                artArray.add(newArt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void add(Object a) {
        try {
            artArray.add((Art) a);
        } catch (Exception e){
            System.out.println("that's not an art object!");
        }
    }

    public void subtract(Object a) {
        boolean removed = artArray.remove((Art) a);
        if(!removed){
            System.out.println("Something went wrong. Art" + a.toString().split("%*'")[1] + " not found");
        }
    }

    public void saveFile() {
        try{
            FileWriter fw = new FileWriter(artFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Art a : artArray){
                bw.write(a.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
