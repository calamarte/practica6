import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 * Ejecuta las ordenes según se dictan por el ususario
 */
public class main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Tar t = null;
        boolean load = false;

        //Bucle que acaba cuando el ususario decida salir del programa
        while (true){
            try {
                //Se lee lo que escribe el ususario y se guarda en un array de Strings
                String aux = s.nextLine();
                String [] comando = aux.split(" ");

                //Opción load creo un objeto tar con la ruta facilitada por el ususario y se cargan los archivos
                //del .tar
                if (comando[0].equals("load")){
                    t = new Tar(comando[1]);
                    t.expand();
                    load = true;
                    continue;
                }

                //Imprime una lista de los archivos mediante el uso de un bucle y mediante el uso del metodo .list del
                //objeto Tar solo se puede ejecutar si el archivo esta cargado
                if (comando[0].equals("list") && load){
                    String[] list = t.list();
                    for (int i = 0; i < list.length; i++) {
                        System.out.println(list[i]);
                    }
                    continue;
                }

                //extrae uno o todos los archivos dentro del .tar cargado gracias a la función extract
                if (comando[0].equals("extract") && load){
                    extract(comando,t);
                    continue;
                }

                //Imprime un texto de ayuda que muestran el funcionamiento de los comandos
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
                //Finaliza el programa
                if (comando[0].equals("quit"))break;
                //Imprime mensages de para informar al ususario
                if (!load)System.out.println("Primero cargue el archivo");
                else System.out.println("Comando no encontrado");
            //Gestiona las excepciones
            }catch (NoTarFoundException e){
                System.out.println("No existe ningun archivo .tar en esta ruta");
            }catch (Exception e){
                System.out.println("Archivo no encontrado");
            }
        }
    }
    //Se encarga de extraer el archivo o los archivos definidos por el usuario
    private static void extract(String[] comando,Tar t)throws Exception{
        //Al ser la longitud mayor de 3 significa que el nombre del documento contiene 1 o más espacios
        //en este caso se reconstruye para que el comando quede como es devido
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

        //Se crean dos objetos File que uno será el directorio y el otro el archivo
        File directorio = new File(comando[2]);
        File arch;
        //Si no existe se crean los directorios necesarios
        if (!directorio.exists()) directorio.mkdirs();
        FileOutputStream salida;

        //Si solo es un archivo se define la ruta del archivo sumando la la dirección del directorio y
        //el nombre del archivo, se genera el archivo y luego se le escriben los datos, sinó se hace un
        //procedimiento similiar pero con la información de los objetos ArchivoInterno y dentro de un bucle
        if (!comando[1].equals("all")) {
            arch = new File(directorio.getAbsolutePath(),comando[1]);
            arch.createNewFile();
            salida = new FileOutputStream(arch);
            salida.write(t.getBytes(comando[1]));
            salida.close();
            System.out.println("Extarct : "+arch.getAbsolutePath());

        }else {
            ArchivoInterno[] lista = t.getArchivos();
            for (int i = 0; i < lista.length; i++) {
                arch = new File(directorio.getAbsolutePath(),lista[i].toString());
                arch.createNewFile();
                salida = new FileOutputStream(arch);
                salida.write(lista[i].getInfo());
                salida.close();
                System.out.println("Extarct : "+arch.getAbsolutePath());
            }

        }
    }
}
