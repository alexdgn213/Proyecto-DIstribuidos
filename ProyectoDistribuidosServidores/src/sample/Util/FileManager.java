package sample.Util;

import org.apache.commons.io.FileUtils;
import sample.Domain.Servidor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private static Logger l = Logger.getLogger("FileManager");

    public static void createCopy(File origen, String destino) throws IOException {
        File original = origen;
        File copia = new File(destino);
        FileUtils.copyFile(original,copia);
    };

    public static String getFileName(String path){
        String[] partes = path.split("/");
        String nombre = partes[partes.length-1];
        return nombre;
    }

    public static String getFileExt(String fileName){
        String[] partes = fileName.split("\\.");
        String ext = partes[1];
        return ext;
    }

    public static File getCorrect(ArrayList<File> files) throws IOException {
        ArrayList<PosibleFile> posibleFiles = new ArrayList<PosibleFile>();
        for(File f : files){
            PosibleFile pf = new PosibleFile(f);
            for(File f2: files){
                if(FileUtils.contentEquals(f,f2))
                    pf.ocurrencias = pf.ocurrencias+1;
            };
            posibleFiles.add(pf);
        };
        Collections.sort(posibleFiles, new Comparator<PosibleFile>() {
            @Override
            public int compare(PosibleFile pf, PosibleFile t1) {
                return new Integer(t1.ocurrencias).compareTo(new Integer(pf.ocurrencias));
            }
        });
        return  posibleFiles.get(0).archivo;
    }

    static class PosibleFile{
        int ocurrencias;
        File archivo;
        public PosibleFile(File f){
            this.archivo = f;
        }
    }
}
