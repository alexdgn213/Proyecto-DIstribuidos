package sample;

import sample.Domain.Servidor;
import sample.Util.FileManager;

import java.io.IOException;

public class Controller {
    Servidor s;

    public Controller(int servidor) {
        this.s = new Servidor(servidor,true,1,null);
    }

    public void createCopy(String archivo){
        try {
            String[] partes = archivo.split("/");
            String nombre = partes[partes.length-1];
            FileManager.createCopy(archivo,s.getPath()+nombre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
