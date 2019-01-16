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
import java.util.Collections;
import java.util.Comparator;

public class Controller {
    Servidor s;
    CServidor cs;
    ArrayList<Servidor> servidoresConectados = new ArrayList<Servidor>();
    DAOServidor daoServidor = new DAOServidor();
    DAOArchivo daoArchivo = new DAOArchivo();
    DAOVersion daoVersion = new DAOVersion();
    int fallas = 0;
    ArrayList<File> copiasArchivo = new ArrayList<File>();
    String rutaUpdate;
    String nombreUpdate;

    public Controller() {

    }

    public void setServidor(int servidor){
        try {
            DAOServidor daoServidor = new DAOServidor();
            this.s = daoServidor.getById(servidor);
            daoServidor.setDisponible(false,s.get_id());
            cs = new CServidor(s, this);
            System.out.println(s);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeServidor(){
        try {
            DAOServidor daoServidor = new DAOServidor();
            daoServidor.setDisponible(true,s.get_id());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getServidoresDisponibles(){
        try {
            ArrayList<Servidor> servidores = new ArrayList<Servidor>();
            DAOServidor daoServidor = new DAOServidor();
            servidores = daoServidor.getAllDisponibles();
            for(Servidor s : servidores){
                System.out.println(s.get_id()+".- Servidor "+s.get_id()+" Tipo: "+s.getTipoAsString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void iniciarCommint(String archivo){
        try {
            String nombre = FileManager.getFileName(archivo);
            Archivo archivo1 = new Archivo(nombre);
            DAOArchivo daoArchivo = new DAOArchivo();
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

                if(i>3*fallas+1){
                    flag = false;
                    break;
                }
            }
            DAOVersion daoVersion = new DAOVersion();
            Version v = daoVersion.createVersion(archivo1,servidoresAEnviar);
            for(Servidor s: servidoresAEnviar){
                enviarSolicitudDeCommit(archivo,String.valueOf(v.get_id()),s.get_id());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void iniciarUpdate(String nombreArchivo,String nuevaRuta){
        try {
            Archivo archivo = daoArchivo.findByName(nombreArchivo);
            System.out.println(archivo.get_id()+archivo.get_nombre());
            Version version = daoVersion.findByArchivoReciente(archivo);
            System.out.println(version.get_id());
            ArrayList<Servidor> servidoresConCopia = daoServidor.getByVersion(version);
            copiasArchivo = new ArrayList<File>();
            rutaUpdate = nuevaRuta;
            nombreUpdate = nombreArchivo;
            for(Servidor s: servidoresConCopia){
                enviarSolicitudDeUpdate(version.get_id()+"."+FileManager.getFileExt(nombreArchivo),s.get_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void enviarConexionNueva(){
        try {
            CSolicitud cSolicitud = new CSolicitud();
            cSolicitud.setTipo(0);
            cSolicitud.setServidor(1);
            cSolicitud.setOrigen(s.get_id());
            cSolicitud.setNombre(String.valueOf(s.get_id()));
            cs.Enviar(cSolicitud);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void enviarSolicitudDeCommit(String archivo, String version, int servidor){
        try {
            CSolicitud cSolicitud = new CSolicitud();
            cSolicitud.setTipo(1);
            cSolicitud.setServidor(servidor);
            cSolicitud.setNombre(version);
            cSolicitud.setOrigen(s.get_id());
            cSolicitud.setArchivo(new File(archivo));
            cs.Enviar(cSolicitud);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void enviarSolicitudDeUpdate(String archivo, int servidor){
        try {
            CSolicitud cSolicitud = new CSolicitud();
            cSolicitud.setTipo(2);
            cSolicitud.setServidor(servidor);
            cSolicitud.setOrigen(s.get_id());
            cSolicitud.setNombre(archivo);
            cs.Enviar(cSolicitud);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void enviarArchivoSolicitado(String nombre, int servidor){
        try {
            CSolicitud cSolicitud = new CSolicitud();
            cSolicitud.setTipo(3);
            cSolicitud.setServidor(servidor);
            cSolicitud.setNombre(nombre);
            cSolicitud.setArchivo(new File(s.getPath()+nombre));
            cs.Enviar(cSolicitud);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    };

    public void createCopy(File archivo,String version){
        try {
            String nombre = FileManager.getFileName(archivo.getPath());
            String ext = FileManager.getFileExt(nombre);
            FileManager.createCopy(archivo,s.getPath()+version+"."+ext);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void procesarArchivoEnviado(File archivo){
        try{
            this.copiasArchivo.add(archivo);
            if(copiasArchivo.size()==3*fallas+1)
                obtenerArchivo();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void obtenerArchivo(){
        try {
            if(copiasArchivo.size()>0) {
                File f = FileManager.getCorrect(copiasArchivo);
                FileManager.createCopy(f, rutaUpdate + "/" + nombreUpdate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch ( Exception e){
            e.printStackTrace();
        }
    }

    public void agregarServidor(int servidor){
        try {
            servidoresConectados.add(daoServidor.getById(servidor));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
