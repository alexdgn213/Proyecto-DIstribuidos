package sample;

import sample.Domain.Archivo;
import sample.Domain.Servidor;
import sample.Domain.Version;
import sample.Persistence.DAOArchivo;
import sample.Persistence.DAOServidor;
import sample.Persistence.DAOVersion;
import sample.Util.FileManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Controller {
    Servidor s;

    public Controller(int servidor) {
        DAOServidor daoServidor = new DAOServidor();
        try {
            this.s = daoServidor.getById(servidor);
            daoServidor.setDisponible(false,s.get_id());
            System.out.println(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCopy(String archivo){
        try {
            DAOServidor daoServidor = new DAOServidor();
            String[] partes = archivo.split("/");
            String nombre = partes[partes.length-1];
            partes = nombre.split("\\.");
            String ext = partes[1];
            Archivo archivo1 = new Archivo(nombre);

            ArrayList<Servidor> servidors = new ArrayList<Servidor>();
            servidors.add(s);
            servidors.add(daoServidor.getById(25));
            servidors.add(daoServidor.getById(30));
            servidors.add(daoServidor.getById(32));
            servidors.add(daoServidor.getById(28));

            DAOArchivo daoArchivo = new DAOArchivo();
            daoArchivo.createArchivo(archivo1);
            System.out.println(archivo1.toString());

            DAOVersion daoVersion = new DAOVersion();
            Version v = daoVersion.createVersion(archivo1,servidors);
            System.out.println(v.toString());

            for(Servidor s2: servidors)
                FileManager.createCopy(archivo,s2.getPath()+v.get_id()+"."+ext);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Servidor> getServidoresDisponibles(){
        ArrayList<Servidor> servidores = new ArrayList<Servidor>();
        DAOServidor daoServidor = new DAOServidor();
        try {
            servidores = daoServidor.getAllDisponibles();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servidores;

    }
}
