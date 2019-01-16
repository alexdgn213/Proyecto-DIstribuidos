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
        System.out.println("---------------------Bienvenido a los servidores de FSGit-------------------------");
        System.out.println("Desea utilizar este servidor como servidor primario? (s/n):");
        String primario = input.nextLine().toLowerCase();
        Controller c = new Controller();
        boolean continuar = true;
        if(primario.equals("s")){
            c.setServidor(1);
            System.out.println("\n");
            System.out.println("Ingrese el numero de fallas a tolerar: ");
            int fallas = input.nextInt();
            c.fallas=fallas;
            while(continuar){
                System.out.println("\n\n\nIngrese la funcion que desea realizar: ");
                System.out.println("    1.- Commit ");
                System.out.println("    2.- Update ");
                System.out.println("    3.- Ver informacion del servidor");
                System.out.println("    0.- Salir");
                System.out.println("\n");
                System.out.println("Ingrese su eleccion: ");
                int op = input.nextInt();
                String archivo;
                switch (op){
                    case 0 :
                        continuar = false;
                        break;
                    case 1 :
                        System.out.println("\n");
                        System.out.println("Ingrese la ruta del archivo a subir: ");
                        archivo = input.next();
                        c.iniciarCommint(archivo);
                        break;
                    case 2 :
                        System.out.println("\n");
                        System.out.println("Ingrese el nombre del archivo a buscar: ");
                        archivo = input.next();
                        System.out.println("\n");
                        System.out.println("Ingrese de la ruta donde descargar: ");
                        String ruta = input.next();
                        c.iniciarUpdate(archivo,ruta);
                        break;
                    case 3 :
                        System.out.println("\n");
                        c.actualizarServidor();
                        System.out.println(c.s.toString());

                }
            }
            c.closeServidor();
        }
        else{
            System.out.println("Seleccione un servidor de la lista de servidores disponible para iniciar:\n");
            c.getServidoresDisponibles();
            System.out.println("\n");
            System.out.println("Ingrese su eleccion: ");
            int tipo = input.nextInt();
            c.setServidor(tipo);
            c.enviarConexionNueva();
            while(continuar){
                System.out.println("\n\n\nIngrese la funcion que desea realizar: ");
                System.out.println("    1.- Ver informacion del servidor");
                System.out.println("    0.- Salir");
                System.out.println("\n");
                System.out.println("Ingrese su eleccion: ");
                int op = input.nextInt();
                switch (op){
                    case 0 :
                        continuar = false;
                        break;
                    case 1 :
                        System.out.println("\n");
                        c.actualizarServidor();
                        System.out.println(c.s.toString());
                }
            }
            c.closeServidor();
        }
        System.exit(0);

    }
}
