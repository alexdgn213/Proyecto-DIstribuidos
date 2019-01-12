package sample.Domain;

import java.util.ArrayList;
import java.util.Date;

public class Servidor {
    private int _id;
    private boolean _principal;
    private int _tipo;
    private ArrayList<Version> _listaVersiones;

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

    @Override
    public String toString() {
        return "Servidor{" +
                "_id=" + _id +
                ", _principal=" + _principal +
                ", _tipo=" + _tipo +
                ", _listaVersiones=" + _listaVersiones +
                '}';
    }
}
