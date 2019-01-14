package sample.Communication;

import java.io.File;
import java.io.Serializable;

public class CSolicitud implements Serializable {

    private int tipo;
    private int servidor;
    private String nombre;
    private File archivo;

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

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return "CSolicitud{" +
                "tipo=" + tipo +
                ", servidor=" + servidor +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
