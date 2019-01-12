package sample;

import java.sql.*;
import java.util.logging.Logger;

public abstract class DAO {

    private static Connection conInstance;
    private Statement _st;
    private ResultSet _rs;
    private static String BD_USER = "CoreMensajeria";
    private static String BD_PASSWORD = "coremensajeria";
    private static String BD_URL = "jdbc:postgresql://localhost:5432/CoreMensajeria";
    private static String BD_CLASS_FOR_NAME = "org.postgresql.Driver";

    /**
     * Metodo que realiza la conexion con la base de datos
     * @return Conexion hecha a la base de datos
     * @throws ClassNotFoundException Si la clase no es encontrada
     * @throws SQLException Problemas con sql
     * @throws Exception
     * @see Connection
     * @see Statement
     */
    public static Connection getBdConnect()
    {

        try
        {

            Class.forName(BD_CLASS_FOR_NAME);
            return DriverManager.getConnection(BD_URL,BD_USER, BD_PASSWORD);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    protected static void closeConnection()
    {
        try
        {
            conn.close();
        }
        catch ( SQLException e )
        {
//            logger.error( "Metodo: {} {}", "getBdConnect", e.toString() );
        }
    }
}
