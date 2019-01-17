package sample;

import java.util.Scanner;

public class Main {

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
                try {
                    System.out.println("\n\n\nIngrese la funcion que desea realizar: ");
                    System.out.println("    1.- Commit ");
                    System.out.println("    2.- Update ");
                    System.out.println("    3.- Ver informacion del servidor");
                    System.out.println("    4.- Ver todos los servidores");
                    System.out.println("    5.- Ver servidores disponibles");
                    System.out.println("    0.- Salir");
                    System.out.println("\n");
                    System.out.println("Ingrese su eleccion: ");
                    int op = input.nextInt();
                    String archivo;
                    switch (op) {
                        case 0:
                            continuar = false;
                            break;
                        case 1:
                            System.out.println("\n");
                            System.out.println("Ingrese la ruta del archivo a subir: ");
                            archivo = input.next();
                            c.iniciarCommint(archivo);
                            break;
                        case 2:
                            System.out.println("\n");
                            System.out.println("Ingrese el nombre del archivo a buscar: ");
                            archivo = input.next();
                            System.out.println("\n");
                            System.out.println("Ingrese de la ruta donde descargar: ");
                            String ruta = input.next();
                            c.iniciarUpdate(archivo, ruta);
                            break;
                        case 3:
                            System.out.println("\n");
                            c.actualizarServidor();
                            System.out.println(c.s.toString());
                            break;
                        case 4:
                            System.out.println("\n");
                            c.verTodosLosServidores();
                            break;
                        case 5:
                            c.getServidoresDisponibles();
                            break;
                    }
                } catch (Exception e) {
                }
            }
            c.closeServidor();
        }
        else{
            System.out.println("\nSeleccione un servidor de la lista de servidores disponible para iniciar: ");
            int tipo = input.nextInt();
            c.setServidorDesconocido(tipo);
            c.enviarConexionNueva();
            c.pedirTodosLosServidore();
            while(continuar){
                try {
                    System.out.println("\n\n\nIngrese la funcion que desea realizar: ");
                    System.out.println("    1.- Ver informacion del servidor");
                    System.out.println("    2.- Ver todos los servidores");
                    System.out.println("    0.- Salir");
                    System.out.println("\n");
                    System.out.println("Ingrese su eleccion: ");
                    int op = input.nextInt();
                    switch (op) {
                        case 0:
                            continuar = false;
                            break;
                        case 1:
                            System.out.println("\n");
                            c.actualizarServidor();
                            System.out.println(c.s.toString());
                            break;
                        case 2:
                            System.out.println("\n");
                            c.verTodosLosServidores();
                            break;
                    }
                } catch (Exception e){

                }
            }
            c.closeServidor();
        }
        System.exit(0);

    }
}
