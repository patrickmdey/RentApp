package ar.edu.itba.paw.models;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBImage dbImage = (DBImage) o;
        return getId() == dbImage.getId() && Arrays.equals(getImg(), dbImage.getImg());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId());
        result = 31 * result + Arrays.hashCode(getImg());
        return result;
    }
}
