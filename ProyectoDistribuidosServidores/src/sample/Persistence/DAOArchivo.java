package sample.Persistence;

import sample.DAO;
import sample.Domain.Archivo;

import java.sql.*;
import java.util.ArrayList;

public class DAOArchivo extends DAO {

    public void createArchivo(Archivo archivo) throws SQLException {
        String query = "Insert into Archivo(arc_nombre) values(?)";
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,archivo.get_nombre());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next())
            archivo.set_id(rs.getInt(1));
    }

    public void deleteArchivo(Archivo archivo) throws SQLException{
        String query = "Delete from Archivo where arc_id = ?";
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,archivo.get_id());
        ps.execute();
    }

    public ArrayList<Archivo> getAll() throws SQLException {

        String query = "select * from public.Archivo";
        Archivo archivo = null;
        ArrayList<Archivo> archivos = new ArrayList<Archivo>();
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("arc_id");
            String nombre = rs.getString("arc_nombre");
            archivo = new Archivo(id,nombre);
            archivos.add(archivo);
        }
        return archivos;
    }



    public Archivo findByName(String name) throws SQLException {
        String query = "select * from public.Archivo where arc_nombre = ? ";
        Archivo archivo = null;
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,name);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("arc_id");
            String nombre = rs.getString("arc_nombre");
            archivo = new Archivo(id,nombre);
        }
        return archivo;
    }

    public Archivo findById(int id) throws SQLException {
        String query = "select * from public.Archivo where arc_id = ? ";
        Archivo archivo = null;
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id2 = rs.getInt("arc_id");
            String nombre = rs.getString("arc_nombre");
            archivo = new Archivo(id2,nombre);
        }
        return archivo;
    }

}
