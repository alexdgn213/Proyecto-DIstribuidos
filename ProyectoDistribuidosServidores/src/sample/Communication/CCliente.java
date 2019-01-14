package sample.Communication;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class CCliente {
    MulticastSocket multicastSocket;
    DatagramPacket datagramPacket;
    ByteArrayInputStream b_in;
    byte[] bo;


    public CCliente() {
        try {
            multicastSocket = new MulticastSocket(CServidor.puerto);
            multicastSocket.joinGroup(InetAddress.getByName(CServidor.ipaddress));
            bo = new byte[65535];
            b_in = new ByteArrayInputStream(bo);
            datagramPacket = new DatagramPacket(bo, bo.length);

        } catch (IOException e) {
            e.printStackTrace();
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
                    System.out.println(cSolicitud.toString());
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
