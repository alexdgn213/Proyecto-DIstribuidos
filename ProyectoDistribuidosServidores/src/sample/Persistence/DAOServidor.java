package sample.Persistence;

import sample.DAO;
import sample.Domain.Archivo;
import sample.Domain.Servidor;
import sample.Domain.Version;

import java.sql.*;
import java.util.ArrayList;

public class DAOServidor extends DAO {

    public void createArchivo(Archivo archivo) throws SQLException {
        String query = "Insert into Archivo(arc_nombre) values(?)";
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,archivo.get_nombre());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next())
            archivo.set_id(rs.getInt(1));
        c.close();
    }

    public void deleteArchivo(Archivo archivo) throws SQLException{
        String query = "Delete from Archivo where arc_id = ?";
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,archivo.get_id());
        ps.execute();
        c.close();
    }

    public ArrayList<Servidor> getAllDisponibles() throws SQLException {

        String query = "select * from public.Servidor where ser_disponible = true ";
        Servidor servidor = null;
        ArrayList<Servidor> servidors = new ArrayList<Servidor>();
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("ser_id");
            int tipo = rs.getInt("ser_tipo");
            Boolean principal = rs.getBoolean("ser_principal");
            servidor = new Servidor(id,principal,tipo,null);
            servidors.add(servidor);
        }
        c.close();
        return servidors;
    }

    public Servidor getById(Servidor servidor) throws SQLException {

        String query = "select * from public.Servidor where ser_id = ? ";
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,servidor.get_id());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("ser_id");
            int tipo = rs.getInt("ser_tipo");
            Boolean principal = rs.getBoolean("ser_principal");
            DAOVersion daoVersion = new DAOVersion();
            servidor = new Servidor(id,principal,tipo,daoVersion.findByServidor(servidor));
        }
        c.close();
        return servidor;
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
        c.close();
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
        c.close();
        return archivo;
    }
}