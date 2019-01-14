package sample;

import java.sql.*;
import java.util.logging.Logger;

public abstract class DAO {

    private static Connection conInstance;
    private Statement _st;
    private ResultSet _rs;
    private static String BD_USER = "CoreMensajeria";
    private static String BD_PASSWORD = "coremensajeria";
    private static String BD_URL = "jdbc:postgresql://localhost:5432/ProyectoDistribuidos";
    private static String BD_CLASS_FOR_NAME = "org.postgresql.Driver";
    private Connection _conn = bdConnect();



    /**
     * Metodo que realiza la conexion con la base de datos
     * @return Conexion hecha a la base de datos
     * @throws ClassNotFoundException Si la clase no es encontrada
     * @throws SQLException Problemas con sql
     * @throws Exception
     * @see Connection
     * @see Statement
     */
    public static Connection bdConnect()
    {
        return SQL.getInstance().getConnection();
    }

}
