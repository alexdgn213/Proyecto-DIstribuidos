package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Domain.Servidor;
import sample.Persistence.DAOServidor;
import sample.Util.FileManager;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Bienvenido a los servidores de FSGit");
        System.out.println("Desea utilizar este servidor como servidor primario? (s/n)\n");
        String primario = input.next().toLowerCase();

        Controller c = new Controller(2);
        if(primario.equals("s")){
            System.out.println("Ingrese la direccion del archivo a copiar: ");
            String archivo = input.next();
            c.createCopy(archivo);
        }
        else{
            System.out.println("Seleccione de la lista de servidores disponible para iniciar:\n");
            for(Servidor s : c.getServidoresDisponibles()){
                DAOServidor dao = new DAOServidor();
                try {
                    System.out.println(dao.getById(s.get_id()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            int tipo = input.nextInt();
        }

    }
}
