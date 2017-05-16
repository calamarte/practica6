import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Tar {
    private File tar;
    private ArchivoInterno[] archivos;

    // Constructor
    public Tar(String filename) {this.tar = new File(filename);}

    // Torna un array amb la llista de fitxers que hi ha dins el TAR
    public String[] list()throws Exception {
       return null;
    }
    // Torna un array de bytes amb el contingut del fitxer que té per nom
// igual a l'String «name» que passem per paràmetre
    public byte[] getBytes(String name) {
        return null;
    }
    // Expandeix el fitxer TAR dins la memòria
    public void expand()throws Exception {
        List<ArchivoInterno> lista = new ArrayList<>();
        InputStream inputStream = new FileInputStream(this.tar);

        while (true){
            StringBuilder sb = new StringBuilder();
            byte[] nombre = new byte[100];
            byte[] longitud = new byte[12];
            String name;
            inputStream.read(nombre);

            int contador = 0;
            for (int i = 0; i < nombre.length; i++) if (nombre[i] == 0)contador++;

            if (contador == nombre.length)break;

            for (int i = 0; i < nombre.length; i++)sb.append((char) nombre[i]);

            System.out.println(sb.toString());

            name = sb.toString();
            inputStream.skip(24);


            sb = new StringBuilder();
            for (int i = 0; i < 12; i++) {
                int b = inputStream.read();
                if (b > 0) sb.append((char) b);
            }
            int tam = Integer.parseInt(sb.toString(), 8);
            System.out.println("Tamaño: " + tam);


            inputStream.skip(376);
            byte[] info = new byte[Integer.parseInt(sb.toString(),8)];
            inputStream.read(info);

            System.out.println(Arrays.toString(info));

            lista.add(new ArchivoInterno(name,info));
            inputStream.skip((long) getResto(Integer.parseInt(sb.toString(),8)));

        }
        this.archivos = lista.toArray(new ArchivoInterno[lista.size()]);
    }

    private int getResto(int total){
        while (total > 512) total -= 512;
        total = 512 - total;
        return total;
    }
}