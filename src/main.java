import java.io.File;
import java.util.Scanner;

/**
 * Created by calamarte on 16/05/2017.
 */
public class main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Tar t = null;
        boolean load = false;

        while (true){
            try {
                String aux = s.nextLine();
                String [] comando = aux.split(" ");

                if (comando[0].equals("load")){
                    t = new Tar(comando[1]);
                    t.expand();
                    load = true;
                    continue;
                }


                if (comando[0].equals("list") && load){
                    String[] list = t.list();
                    for (int i = 0; i < list.length; i++) {
                        System.out.println(list[i]);
                    }
                    continue;
                }

                if (comando[0].equals("extract") && load){
                    extract(comando,t);

                    //t.getBytes()
                }

                if (comando[0].equals("h")){
                    System.out.print(
                            "load : carga el fichero tar en memoria\n" +
                                    "load [dirección del tar]\n" +
                                    "list : muestra una lista de los nombres de todos los archivos dentro del .tar previamente cargado en memoria\n" +
                                    "extract : extrae en un fichero el archivo en el directorio deseado\n" +
                                    "extract [all | nombre del archivo] [ruta de destino] <create-[nombre de directorio]>\n" +
                                    "h : muestra una lista de funciones \n" +
                                    "quit : finaliza el programa \n"
                    );
                    continue;
                }
                if (aux.equals("quit"))break;
                else System.out.println("Comando no encontrado");

            }catch (Exception e){
                System.out.println("Archivo no encontrado");
            }
        }
    }
    static void extract(String[] comando,Tar t)throws Exception{
        if (comando[1].equals("all")){
            ArchivoInterno [] lista = t.getArchivos();
            for (int i = 0; i < lista.length; i++) {

            }
        }else {

        }
    }
}
