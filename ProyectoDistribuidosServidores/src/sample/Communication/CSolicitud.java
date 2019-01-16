package sample.Communication;

import sample.Domain.Servidor;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class CSolicitud implements Serializable {

    private int tipo;
    private int servidor;
    private int origen;
    private String nombre;
    private File archivo;
    private ArrayList<Servidor> servidores;

    public CSolicitud(int tipo, int servidor, String nombre, File archivo) {
        this.tipo = tipo;
        this.servidor = servidor;
        this.nombre = nombre;
        this.archivo = archivo;

    }

    public CSolicitud() {
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getServidor() {
        return servidor;
    }

    public void setServidor(int servidor) {
        this.servidor = servidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public File getArchivo() {
        return archivo;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public ArrayList<Servidor> getServidores() {
        return servidores;
    }

    public void setServidores(ArrayList<Servidor> servidores) {
        this.servidores = servidores;
    }

    @Override
    public String toString() {
        return "CSolicitud{" +
                "tipo=" + tipo +
                ", servidor=" + servidor +
                ", origen=" + origen +
                ", nombre='" + nombre + '\'' +
                ", archivo=" + archivo +
                '}';
    }
}
