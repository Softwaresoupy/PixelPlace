import java.io.*;
import java.util.ArrayList;

public class artDatabase implements Database{
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
                Art newArt = new Art();
                id++;
                String[] splitArtDesc = artObjectString.split("%");
                newArt.setName(splitArtDesc[0]);
                newArt.setArtTime(splitArtDesc[1]);
                newArt.setArtUser(splitArtDesc[2]);
                newArt.setArtLocation(splitArtDesc[3]);
                newArt.setArtDescription(splitArtDesc[5]);
                newArt.setArtFile(splitArtDesc[4]);
                artArray.add(newArt);
                artObjectString = br.readLine();
                //System.out.println("Art added to array: " + newArt.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addy(Object a) {
        try {
            //System.out.println("adding to array: " + a.toString());
            artArray.add((Art) a);
            for(Art aa : artArray){
                //System.out.println(aa);
            }
            saveFile();
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
            System.out.println("saving to file");
            FileWriter fw = new FileWriter(artFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Art a : artArray){
                bw.write(a.toString());
                bw.newLine();
                System.out.println("saved" + a.toString());
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
