package sample.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Comunicator {
    InetAddress group = InetAddress.getByName("228.5.6.7");
    MulticastSocket s = new MulticastSocket(6789);

    public Comunicator() throws IOException {
        s.joinGroup(group);
        String hola = "Hola como estas?";
    }
}
