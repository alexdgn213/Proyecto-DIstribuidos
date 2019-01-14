package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {
    private static SQL instance=null;
    private Connection connection;
    private static String BD_USER = "CoreMensajeria";
    private static String BD_PASSWORD = "coremensajeria";
    private static String BD_URL = "jdbc:postgresql://localhost:5432/ProyectoDistribuidos";

    private SQL() {
        try
        {
            this.connection = DriverManager.getConnection(BD_URL,BD_USER, BD_PASSWORD);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static SQL getInstance(){
        if(instance==null){
            instance = new SQL();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
