package sample.Communication;

import sample.Domain.Archivo;
import sample.Domain.Servidor;
import sample.Domain.Version;
import sample.Persistence.DAOArchivo;
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

    public CServidor(Servidor servidor) throws IOException {
        this.servidor = servidor;
        //Para enviar
        _datagramSocket = new DatagramSocket();
        //Para recibir
        multicastSocket = new MulticastSocket(CServidor.puerto);
        multicastSocket.joinGroup(InetAddress.getByName(CServidor.ipaddress));
        bo = new byte[65535];
        b_in = new ByteArrayInputStream(bo);
        datagramPacket = new DatagramPacket(bo, bo.length);
        new Thread(new Listener(this)).start();
    }

    public void Enviar(CSolicitud cSolicitud) throws IOException {
        ByteArrayOutputStream b_out = new ByteArrayOutputStream();
        ObjectOutputStream o_out = new ObjectOutputStream(b_out);
        o_out.writeObject(cSolicitud);
        byte[] b = b_out.toByteArray();
        String mensaje = "c";
        DatagramPacket datagramPacket = new DatagramPacket(b,b.length,InetAddress.getByName(ipaddress),puerto);
        _datagramSocket.send(datagramPacket);
        System.out.println(mensaje.getBytes());
    }

    public void procesarSolicitud(CSolicitud cSolicitud){
        if(cSolicitud.getServidor()==0||cSolicitud.getServidor()==servidor.get_id()){
            if(cSolicitud.getTipo()==1){ //Quiero hacer un push
                createCopy(cSolicitud.getArchivo());
            }

        }


    }

    public void createCopy(File archivo){
        try {
            DAOServidor daoServidor = new DAOServidor();
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
            FileManager.createCopy(archivo,servidor.getPath()+"hola"+"."+ext);
        } catch (IOException e) {
            e.printStackTrace();}
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
