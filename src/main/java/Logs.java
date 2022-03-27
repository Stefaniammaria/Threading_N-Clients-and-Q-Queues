import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logs {

    public Logger logger;
    FileHandler fh;

    public Logs(String file_name) throws SecurityException, IOException{

        File f = new File(file_name);
        if(!f.exists()){

            f.createNewFile();
        }
        fh = new FileHandler(file_name,true);
        logger = Logger.getLogger("main");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

    }
}
