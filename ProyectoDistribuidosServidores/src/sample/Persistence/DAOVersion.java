package sample.Persistence;

import sample.DAO;
import sample.Domain.Archivo;
import sample.Domain.Servidor;
import sample.Domain.Version;

import java.sql.*;
import java.util.ArrayList;

public class DAOVersion extends DAO {

    public Version createVersion(Archivo archivo, ArrayList<Servidor> servidorArrayList) throws SQLException {
        String query = "Insert into Version(ver_fecha,ver_archivo) values(CURRENT_TIMESTAMP ,?)";
        Version v = new Version();
        v.set_archivo(archivo);
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1,archivo.get_id());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next())
            v.set_id(rs.getInt(1));
        for (Servidor s : servidorArrayList){
            String query2 = "INSERT into ser_ver(ser_ver_version,ser_ver_servidor) values(?,?)";
            PreparedStatement preparedStatement = c.prepareStatement(query2);
            preparedStatement.setInt(1,v.get_id());
            preparedStatement.setInt(2,s.get_id());
            preparedStatement.executeUpdate();
        }
        c.close();
        return v;
    }

    //TRAE LA VERSION MAS RECIENTE
    public Version findByArchivoReciente(Archivo archivo) throws SQLException {
        String query = "select * from Version where ver_archivo = ? order by ver_fecha desc limit 1";
        Version version = null;
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,archivo.get_id());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("ver_archivo");
            Timestamp fecha = rs.getTimestamp("ver_fecha");
            version = new Version(archivo,fecha);
        }
        c.close();
        return version;

    }

    //TRAE TODAS LAS VERSIONES
    public ArrayList<Version> findByArchivo(Archivo archivo) throws SQLException {
        String query = "select * from Version where ver_archivo = ? order by ver_fecha desc ";
        ArrayList<Version> versionArrayList = null;
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,archivo.get_id());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("ver_id");
            Timestamp fecha = rs.getTimestamp("ver_fecha");
            versionArrayList.add(new Version(id,archivo,fecha));

        }
        c.close();
        return versionArrayList;

    }

    //TRAE TODAS LAS VERSIONES
    public ArrayList<Version> findByServidor(Servidor servidor) throws SQLException {
        String query = "select * from Version,Ser_Ver where ver_id = ser_ver_version and ser_ver_servidor = ? order by ver_archivo desc ";
        ArrayList<Version> versionArrayList = new ArrayList<Version>();
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,servidor.get_id());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("ver_id");
            Timestamp fecha = rs.getTimestamp("ver_fecha");
            int idArchivo = rs.getInt("ver_archivo");
            DAOArchivo daoArchivo = new DAOArchivo();
            Archivo archivo = daoArchivo.findById(idArchivo);
            versionArrayList.add(new Version(id,archivo,fecha));

        }
        c.close();
        return versionArrayList;

    }

    public void deleteArchivo(Archivo archivo) throws SQLException{
        String query = "Delete from Archivo where arc_id = ?";
        Connection c = DAO.bdConnect();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1,archivo.get_id());
        ps.execute();
        c.close();
    }


}
