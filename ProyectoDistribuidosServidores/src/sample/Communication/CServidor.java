package sample.Communication;

import sample.Controller;
import sample.Domain.Archivo;
import sample.Domain.Servidor;
import sample.Persistence.DAOServidor;
import sample.Persistence.DAOVersion;
import sample.Util.FileManager;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CServidor {
    MulticastSocket multicastSocket;
    DatagramPacket datagramPacket;
    ByteArrayInputStream b_in;
    byte[] bo;
    DatagramSocket _datagramSocket;
    public final static int puerto = 8893;
    public final static String ipaddress = "235.1.1.1";
    private Servidor servidor;
    private Controller controller;

    public CServidor(Servidor servidor, Controller controller) throws IOException {
        this.servidor = servidor;
        this.controller = controller;
        //Para enviar
        _datagramSocket = new DatagramSocket();
        //Para recibir
        multicastSocket = new MulticastSocket(CServidor.puerto);
        multicastSocket.joinGroup(InetAddress.getByName(CServidor.ipaddress));
        bo = new byte[65535];
        b_in = new ByteArrayInputStream(bo);
        datagramPacket = new DatagramPacket(bo, bo.length);
        new Thread(new Listener(this)).start();
        controller.agregarServidor(servidor.get_id());
    }

    public void Enviar(CSolicitud cSolicitud) throws IOException {
        ByteArrayOutputStream b_out = new ByteArrayOutputStream();
        ObjectOutputStream o_out = new ObjectOutputStream(b_out);
        o_out.writeObject(cSolicitud);
        byte[] b = b_out.toByteArray();
        String mensaje = "c";
        DatagramPacket datagramPacket = new DatagramPacket(b,b.length,InetAddress.getByName(ipaddress),puerto);
        _datagramSocket.send(datagramPacket);
    }

    public void procesarSolicitud(CSolicitud cSolicitud){
        if(cSolicitud.getServidor()==0||cSolicitud.getServidor()==servidor.get_id()){
            if(cSolicitud.getTipo()==0){ //Alguien nuevo se conecto
                controller.agregarServidor(Integer.parseInt(cSolicitud.getNombre()));
            } else if(cSolicitud.getTipo()==1){ //Alguien solicito que almacenara un archivo
                controller.createCopy(cSolicitud.getArchivo(),cSolicitud.getNombre());
            } else if(cSolicitud.getTipo()==2){ //Alguien solicito un archivo
                controller.enviarArchivoSolicitado(cSolicitud.getNombre(),cSolicitud.getOrigen());
            } else if(cSolicitud.getTipo()==3){ //Alguien devolvio su version del archivo
                controller.procesarArchivoEnviado(cSolicitud.getArchivo());
            } else if(cSolicitud.getTipo()==4){ //El servidor principal asigno como responsable del commit el origen es el numero de fallas
                controller.procesarInicioDeCommit(cSolicitud.getArchivo(),cSolicitud.getNombre(),cSolicitud.getOrigen());
            } else if(cSolicitud.getTipo()==5){ //El servidor principal asigno como responsable del update
                //controller.procesarInicioDeCommit(cSolicitud.getArchivo(),cSolicitud.getNombre());
            } else if(cSolicitud.getTipo()==6){ //Solicitaron todos los servidores
                controller.enviarTodosLosServidore();
            } else if(cSolicitud.getTipo()==7){ //Solicitaron un servidor
                controller.enviarServidorSolicitado(cSolicitud.getOrigen());
            } else if(cSolicitud.getTipo()==8){ //Enviaron todos los servidores
                controller.agregarTodosLosServidores(cSolicitud.getServidores());
            } else if(cSolicitud.getTipo()==9){ //Enviaron mi servidor
                controller.agregarServidor(cSolicitud.getServidores());
            }
        }


    }

    public class  Listener implements Runnable{
        CServidor cServidor;


        @Override
        public void run() {
            try{
                while(true){
                    multicastSocket.receive(datagramPacket);
                    ObjectInputStream o_in = new ObjectInputStream(b_in);
                    CSolicitud cSolicitud = (CSolicitud)o_in.readObject();
                    datagramPacket.setLength(bo.length);
                    b_in.reset();
                    procesarSolicitud(cSolicitud);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        public Listener(CServidor cServidor) {
            this.cServidor = cServidor;

        }
    }

}
