package sample.Util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private static Logger l = Logger.getLogger("FileManager");

    public static void createCopy(File origen, String destino) throws IOException {
        File original = origen;
        File copia = new File(destino);
        FileUtils.copyFile(original,copia);
    };
}
