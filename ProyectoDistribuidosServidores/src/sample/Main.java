package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Domain.Servidor;
import sample.Util.FileManager;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Bienvenido a los servidores de FSGit");
        System.out.println("Desea utilizar este servidor como servidor primario? (s/n)\n");

        String primario = input.next().toLowerCase();
        System.out.println("Este servidor sera de tipo activo (1) o pasivo (2)?\n");
        int tipo = input.nextInt();

        Controller c = new Controller(2);
        if(primario.equals("s")){
            System.out.println("Ingrese la direccion del archivo a copiar: ");
            String archivo = input.next();
            c.createCopy(archivo);
        }

    }
}
