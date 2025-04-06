import java.io.File;
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
        while(true){
            //add(Art);
        }

    }
    public void add(Object a) {
        artArray.add((Art) a);

    }

    public void subtract(Object a) {

    }

    public void saveFile() {

    }


}
