import java.util.Scanner;

/**
 * Created by calamarte on 16/05/2017.
 */
public class main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String archivo = null;
        Tar t = null;


        while (true){
            try {
                if (archivo == null) {
                    System.out.println("Escribe la dirrección del archivo que quieres expandir");
                    archivo = s.nextLine();
                    t = new Tar(archivo);
                    t.expand();
                }
                String aux = s.next();

                if (aux.equals("h")){
                    System.out.print(
                            "list : muestra una lista de los nombres de todos los archivos dentro del .tar \n" +
                                    "expand file : aisla un archivo definido a continuación \n" +
                                    "path : permite cambiar la dirección del archivo\n" +
                                    "h : muestra una lista de funciones \n" +
                                    "quit : finaliza el programa \n"
                    );
                    continue;
                }

                if (aux.equals("list")){
                    String[] list = t.list();
                    for (int i = 0; i < list.length; i++) {
                        System.out.println(list[i]);
                    }
                    continue;
                }

                if (aux.equals("expand file")){
                    System.out.println("Escribe el nombre del archivo");
                    aux = s.nextLine();
                    //t.getBytes()
                }

                if (aux.equals("quit"))break;



            }catch (Exception e){
                archivo = null;
                System.out.println("Archivo no encontrado");
            }
        }
    }
}
