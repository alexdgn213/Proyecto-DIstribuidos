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
import java.util.logging.Logger;

public class Controller {
    Servidor s;
    CServidor cs;

    public Controller() {

    }

    public void setServidor(int servidor){
        DAOServidor daoServidor = new DAOServidor();
        try {
            this.s = daoServidor.getById(servidor);
            daoServidor.setDisponible(false,s.get_id());
            cs = new CServidor(s);
            System.out.println(s);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    public void enviarSolicitudDeCopia(String archivo){
        CSolicitud cSolicitud = new CSolicitud();
        cSolicitud.setTipo(1);
        cSolicitud.setServidor(0);
        cSolicitud.setArchivo(new File(archivo));
        try {
            cs.Enviar(cSolicitud);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
