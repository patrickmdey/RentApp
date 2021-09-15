package ar.edu.itba.paw.models;

public class DBImage {
    private long id;
    private byte[] img;

    public DBImage(long id, byte[] img) {
        this.id = id;
        this.img = img;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public long getId() {
        return id;
    }
}
