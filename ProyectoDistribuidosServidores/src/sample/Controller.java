package sample;

import sample.Communication.CServidor;
import sample.Communication.CSolicitud;
import sample.Domain.Archivo;
import sample.Domain.Servidor;
import sample.Domain.Version;
import sample.Persistence.DAOArchivo;
import sample.Persistence.DAOServidor;
import sample.Persistence.DAOVersion;
import sample.Util.FileManager;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

public class Controller {
    Servidor s;
    CServidor cs;
    ArrayList<Servidor> servidoresConectados = new ArrayList<Servidor>();
    DAOServidor daoServidor = new DAOServidor();
    int fallas = 2;

    public Controller() {

    }

    public void setServidor(int servidor){
        DAOServidor daoServidor = new DAOServidor();
        try {
            this.s = daoServidor.getById(servidor);
            daoServidor.setDisponible(false,s.get_id());
            cs = new CServidor(s, this);
            System.out.println(s);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeServidor(){
        DAOServidor daoServidor = new DAOServidor();
        try {
            daoServidor.setDisponible(true,s.get_id());
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

    public void iniciarCommint(String archivo){
        String[] partes = archivo.split("/");
        String nombre = partes[partes.length-1];
        partes = nombre.split("\\.");
        String ext = partes[1];
        Archivo archivo1 = new Archivo(nombre);
        DAOArchivo daoArchivo = new DAOArchivo();
        try {
            Archivo a = daoArchivo.findByName(nombre);
            if (a==null) daoArchivo.createArchivo(archivo1);
            else archivo1 = a;
            Collections.sort(servidoresConectados, new Comparator<Servidor>() {
                @Override
                public int compare(Servidor servidor, Servidor t1) {
                    return new Integer(servidor.get_cantidadDeArchivos()).compareTo(new Integer(t1.get_cantidadDeArchivos()));
                }
            });
            int i = 1;
            ArrayList<Servidor> servidoresAEnviar = new ArrayList<Servidor>();
            boolean flag = true;
            for (Servidor s: servidoresConectados){
                if(flag == true)
                servidoresAEnviar.add(s);
                i = i+1;

                if(i>fallas+1){
                    flag = false;
                    break;
                }
            }
            DAOVersion daoVersion = new DAOVersion();
            Version v = daoVersion.createVersion(archivo1,servidoresAEnviar);
            for(Servidor s: servidoresAEnviar){
                enviarSolicitudDeCopia(archivo,String.valueOf(v.get_id()),s.get_id());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void enviarSolicitudDeCopia(String archivo,String version, int servidor){
        CSolicitud cSolicitud = new CSolicitud();
        cSolicitud.setTipo(1);
        cSolicitud.setServidor(servidor);
        cSolicitud.setNombre(version);
        cSolicitud.setArchivo(new File(archivo));
        try {
            cs.Enviar(cSolicitud);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarConexionNueva(){
        CSolicitud cSolicitud = new CSolicitud();
        cSolicitud.setTipo(0);
        cSolicitud.setServidor(1);
        cSolicitud.setNombre(String.valueOf(s.get_id()));
        try {
            cs.Enviar(cSolicitud);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCopy(File archivo,String version){
        try {
            String[] partes = archivo.getPath().split("/");
            String nombre = partes[partes.length-1];
            partes = nombre.split("\\.");
            String ext = partes[1];
            Archivo archivo1 = new Archivo(nombre);

            //ArrayList<Servidor> servidors = new ArrayList<Servidor>();
            //servidors.add(s);
            //servidors.add(daoServidor.getById(25));
            //servidors.add(daoServidor.getById(30));
            //servidors.add(daoServidor.getById(32));
            //servidors.add(daoServidor.getById(28));

            //DAOArchivo daoArchivo = new DAOArchivo();
            //daoArchivo.createArchivo(archivo1);
            //System.out.println(archivo1.toString());

            //DAOVersion daoVersion = new DAOVersion();
            //Version v = daoVersion.createVersion(archivo1,servidors);
            //System.out.println(v.toString());
            FileManager.createCopy(archivo,s.getPath()+version+"."+ext);
        } catch (IOException e) {
            e.printStackTrace();}
    }

    public void agregarServidor(int servidor){
        try {
            servidoresConectados.add(daoServidor.getById(servidor));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
