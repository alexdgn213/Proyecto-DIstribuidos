package sample;

import sample.Domain.Archivo;
import sample.Domain.Servidor;
import sample.Domain.Version;
import sample.Persistence.DAOArchivo;
import sample.Persistence.DAOVersion;
import sample.Util.FileManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Controller {
    Servidor s;

    public Controller(int servidor) {
        this.s = new Servidor(servidor,true,1,null);
    }

    public void createCopy(String archivo){
        try {
            String[] partes = archivo.split("/");
            String nombre = partes[partes.length-1];
            Archivo archivo1 = new Archivo(nombre);

            ArrayList<Servidor> servidors = new ArrayList<Servidor>();
            servidors.add(s);

            DAOArchivo daoArchivo = new DAOArchivo();
            daoArchivo.createArchivo(archivo1);
            System.out.println(archivo1.toString());

            DAOVersion daoVersion = new DAOVersion();
            Version v = daoVersion.createVersion(archivo1,servidors);
            System.out.println(v.toString());

            FileManager.createCopy(archivo,s.getPath()+nombre);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
