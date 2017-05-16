/**
 * Created by calamarte on 16/05/2017.
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
