package lk.parinda.safemellow.db_controll.module;

public class DownloadFileModule {

    int id;
    String path;

    public DownloadFileModule(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public DownloadFileModule(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
