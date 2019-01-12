package sample.Domain;

import java.sql.Date;

public class Version {
    private int _id;
    private Archivo _archivo;
    private Date _fecha;

    public Version() {
    }

    public Version(Archivo _archivo, Date _fecha) {
        this._archivo = _archivo;
        this._fecha = _fecha;
    }

    public Version(int _id, Archivo _archivo, Date _fecha) {
        this._id = _id;
        this._archivo = _archivo;
        this._fecha = _fecha;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Archivo get_archivo() {
        return _archivo;
    }

    public void set_archivo(Archivo _archivo) {
        this._archivo = _archivo;
    }

    public Date get_fecha() {
        return _fecha;
    }

    public void set_fecha(Date _fecha) {
        this._fecha = _fecha;
    }

    @Override
    public String toString() {
        return "Version{" +
                "_id=" + _id +
                ", _archivo=" + _archivo +
                ", _fecha=" + _fecha +
                '}';
    }
}
