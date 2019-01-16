package sample.Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Servidor implements Serializable {
    private int _id;
    private boolean _principal;
    private int _tipo;
    private ArrayList<Version> _listaVersiones = new ArrayList<Version>();
    private int _cantidadDeArchivos;

    public Servidor(int _id, boolean _principal, int _tipo, ArrayList<Version> _listaVersiones) {
        this._id = _id;
        this._principal = _principal;
        this._tipo = _tipo;
        this._listaVersiones = _listaVersiones;
    }

    public Servidor() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public boolean is_principal() {
        return _principal;
    }

    public void set_principal(boolean _principal) {
        this._principal = _principal;
    }

    public int get_tipo() {
        return _tipo;
    }

    public void set_tipo(int _tipo) {
        this._tipo = _tipo;
    }

    public ArrayList<Version> get_listaVersiones() {
        return _listaVersiones;
    }

    public void set_listaVersiones(ArrayList<Version> _listaVersiones) {
        this._listaVersiones = _listaVersiones;
    }

    public String getPath(){
        return "C:\\Users\\Public\\FSServidores\\Servidor"+_id+"\\";
    }

    public int get_cantidadDeArchivos() {
        return _cantidadDeArchivos;
    }

    public void set_cantidadDeArchivos(int _cantidadDeArchivos) {
        this._cantidadDeArchivos = _cantidadDeArchivos;
    }

    @Override
    public String toString() {
        String ret = "Servidor :"  + _id +
                "\n    Tipo:" + getTipoAsString() +
                "\n    Archivos:" + _cantidadDeArchivos +
                "\n    Lista de versiones en el servidor:";
        for(Version v : _listaVersiones)
        ret = ret + "\n        " + v;
        ret = ret+ "\n\n";
        return ret;
    }

    public  String getTipoAsString(){
        if(_principal) return "Principal";
        if(_tipo==0) return "Pasivo";
        else if(_tipo==1) return "Activo";
        return "NO existe";

    }
}
