package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Communication.CCliente;
import sample.Communication.CServidor;
import sample.Communication.CSolicitud;
import sample.Domain.Servidor;
import sample.Persistence.DAOServidor;
import sample.Util.FileManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
//www.javaspecialist.eu/archive/Issue028.html

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Bienvenido a los servidores de FSGit");
        System.out.println("Desea utilizar este servidor como servidor primario? (s/n)\n");
        String primario = input.next().toLowerCase();
        Controller c = new Controller();
        boolean continuar = true;
        if(primario.equals("s")){
            c.setServidor(1);
            System.out.println("\nIngrese el numero de fallas a tolerar: ");
            int fallas = input.nextInt();
            c.fallas=fallas;
            while(continuar){
                System.out.println("\n\n\nIngrese la funcion que desea realizar: ");
                System.out.println("    1.- Commit ");
                System.out.println("    2.- Update ");
                System.out.println("    3.- Ver archivos en el servidor");
                System.out.println("    4.- Ver informacion del el servidor");
                System.out.println("    0.- Salir");
                int op = input.nextInt();
                switch (op){
                    case 0 :
                        continuar = false;
                        break;
                    case 1 :
                        System.out.println("\n\nIngrese la ruta del archivo a subir");
                        String archivo = input.next();
                        c.iniciarCommint(archivo);
                        break;
                    case 4 :
                        for(Servidor s : c.servidoresConectados)
                            System.out.println(s.toString());
                        break;

                }
            }
            c.closeServidor();
        }
        else{
            System.out.println("Seleccione un servidor de la lista de servidores disponible para iniciar:\n");
            for(Servidor s : c.getServidoresDisponibles()){
                System.out.println(s.get_id()+".- Servidor "+s.get_id()+" Tipo: "+s.getTipoAsString());
            }
            int tipo = input.nextInt();
            c.setServidor(tipo);
            c.enviarConexionNueva();
            while(continuar){
                System.out.println("\n\n\nIngrese la funcion que desea realizar: ");
                System.out.println("    1.- Ver archivos en el servidor");
                System.out.println("    2.- Ver informacion del el servidor");
                System.out.println("    0.- Salir");
                int op = input.nextInt();
                switch (op){
                    case 0 :
                        continuar = false;
                        break;
                }
            }
            c.closeServidor();
        }
        System.exit(0);

    }
}
