import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class userDatabase extends Database{
    File userFile;
    ArrayList<Art> userArray;

    public userDatabase(File f){
        userFile = f;
        userArray = new ArrayList<Art>();
        populate();
    }
    public void populate(){
        //read through file
        while(true){
            //add(a);
        }

    }
    public void add(Object a) {
        userArray.add((User) a);

    }

    public void subtract(Object a) {

    }

    public void saveFile() {

    }


}
