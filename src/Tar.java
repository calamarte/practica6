import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Tar {
    private File tar;
    private ArchivoInterno[] archivos;

    // Constructor que lanza una excepción si la ruta no es un archivo .tar
    public Tar(String filename) throws NoTarFoundException {
        if (!filename.endsWith(".tar")) throw new NoTarFoundException();
        this.tar = new File(filename);
    }

    // Torna un array amb la llista de fitxers que hi ha dins el TAR
    public String[] list(){
        //Crea un array de Strings y lo lleno con los nombres de los archivos
        String[] resultado = new String[this.archivos.length];
        for (int i = 0; i < this.archivos.length; i++) {
           resultado[i] = this.archivos[i].toString();
        }
       return resultado;
    }
    // Torna un array de bytes amb el contingut del fitxer que té per nom
    // igual a l'String «name» que passem per paràmetre
    public byte[] getBytes(String name) {
        //Comprueba cual es el nombre del archivo que coincide y cuando lo hace devuelve su array de bytes
        for (int i = 0; i < this.archivos.length; i++) {
            if (name.equals(this.archivos[i].toString()))return this.archivos[i].getInfo();
        }
        return null;
    }

    // Expandeix el fitxer TAR dins la memòria
    public void expand() throws Exception {
        //lista será donde iré guardando los archivos internos encontrados en el tar
        List<ArchivoInterno> lista = new ArrayList<>();
        InputStream inputStream = new FileInputStream(this.tar);

        //Hago un bucle indefinido
        while (true){
            StringBuilder sb = new StringBuilder();
            byte[] nombre = new byte[100];
            String name;
            //Lee el nombre y lo guarda en un array de bytes
            inputStream.read(nombre);

            //Define se debe acabar el bucle cuando los bytes que dictan el nombre son todos 0
            int contador = 0;
            for (int i = 0; i < nombre.length; i++) if (nombre[i] == 0)contador++;

            if (contador == nombre.length)break;

            //Convierte el nombre en un String
            for (int i = 0; i < nombre.length; i++) {
                if (nombre[i] != 0) sb.append((char) nombre[i]);
            }
            name = sb.toString();
            //Salta 24 posiciones para llegar a donde está la longitud
            inputStream.skip(24);

            //Se reinicia el StringBuilder
            sb = new StringBuilder();
            //Se lee byte a byte la longitud
            for (int i = 0; i < 12; i++) {
                int b = inputStream.read();
                if (b > 0) sb.append((char) b);
            }
            //Se convierte los bytes en un integer
            int tam = Integer.parseInt(sb.toString(), 8);

            //Salta 376 bytes para empezar a leer el archivo
            inputStream.skip(376);
            byte[] info = new byte[tam];
            //Se gurada el archivo en un array adecuado
            inputStream.read(info);

            //Finalmente se guarda el nombre y la información como un objeto ArchivoInterno en la lista
            lista.add(new ArchivoInterno(name,info));
            //Salto los bytes que sobran del bloque
            inputStream.skip((long) getResto(tam));

        }
        //Doy valor a la variable de instancia archivos
        this.archivos = lista.toArray(new ArchivoInterno[lista.size()]);
    }

    //Genera el valor sobrante de bytes de un archivo dentro del tar
    private int getResto(int total){
        //Quita los bloques enteros
        while (total > 512) total -= 512;
        //Devuelve el resto
        return 512 - total;
    }

    //Devuelve la variable archivos
    public ArchivoInterno[] getArchivos() {
        return archivos;
    }
}