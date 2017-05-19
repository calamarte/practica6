import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
                    continue;
                }

                if (comando[0].equals("help")){
                    System.out.print(
                            "load : carga el fichero tar en memoria\n" +
                                    "load [dirección del tar]\n" +
                                    "list : muestra una lista de los nombres de todos los archivos dentro del .tar previamente cargado en memoria\n" +
                                    "extract : extrae en un fichero el archivo en la ruta deseada\n" +
                                    "extract [all | nombre del archivo] [ruta de destino]\n" +
                                    "help : muestra una lista de funciones \n" +
                                    "quit : finaliza el programa \n"
                    );
                    continue;
                }
                if (comando[0].equals("quit"))break;
                else System.out.println("Comando no encontrado");

            }catch (NoTarFoundException e){
                System.out.println("No existe ningun archivo .tar en esta ruta");
            }catch (Exception e){
                System.out.println("Archivo no encontrado");
            }
        }
    }
    static void extract(String[] comando,Tar t)throws Exception{
            if (comando.length > 3){
               String[] aux = new String[3];
               String s = "";
               aux[0] = comando[0];
                for (int i = 1; i <comando.length-1 ; i++) {
                    if (i != 1) s += " "+comando[i];
                    else s += comando[i];
                }
                aux[1] = s;
                aux[2] = comando[comando.length-1];
                comando = aux;
            }

        File directorio = new File(comando[2]);
        File arch;
        if (!directorio.exists()) directorio.mkdirs();
        FileOutputStream salida;

        if (!comando[1].equals("all")) {
            arch = new File(directorio.getAbsolutePath(),comando[1]);
            arch.createNewFile();
            salida = new FileOutputStream(arch);
            salida.write(t.getBytes(comando[1]));
            salida.close();

        }else {
            ArchivoInterno[] lista = t.getArchivos();
            for (int i = 0; i < lista.length; i++) {
                arch = new File(directorio.getAbsolutePath(),lista[i].toString());
                arch.createNewFile();
                salida = new FileOutputStream(arch);
                salida.write(lista[i].getInfo());
                salida.close();
            }

        }
    }
}
