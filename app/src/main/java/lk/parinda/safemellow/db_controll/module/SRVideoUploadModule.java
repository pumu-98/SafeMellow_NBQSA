package lk.parinda.safemellow.db_controll.module;

public class SRVideoUploadModule {

    int id;
    int isUploaded;
    String path;
    int index;

    public SRVideoUploadModule(int id, int isUploaded, String path, int index) {
        this.id = id;
        this.isUploaded = isUploaded;
        this.path = path;
        this.index = index;
    }

    public SRVideoUploadModule(int isUploaded, String path, int index) {
        this.isUploaded = isUploaded;
        this.path = path;
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(int isUploaded) {
        this.isUploaded = isUploaded;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
