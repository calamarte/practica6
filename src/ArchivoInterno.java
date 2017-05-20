/**
 * Clase que contiene cada la información de cada uno del os archivos del tar
 * guardan el nombre y los bytes que componen el archivo
 */
public class ArchivoInterno {
    private String name;
    private byte[] info;

    public ArchivoInterno(String name,byte[] info){
        this.name = name;
        this.info = info;
    }

    @Override
    public String toString() {
        return name;
    }

    public byte[] getInfo() {
        return info;
    }
}
