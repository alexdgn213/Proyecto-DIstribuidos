package sample.Domain;

public class Archivo {

    private int _id;
    private String _nombre;

    public Archivo(String _nombre) {
        this._nombre = _nombre;
    }

    public Archivo(int _id, String _nombre) {
        this._id = _id;
        this._nombre = _nombre;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    @Override
    public String toString() {
        return "Archivo{" +
                "_id=" + _id +
                ", _nombre='" + _nombre + '\'' +
                '}';
    }
}
