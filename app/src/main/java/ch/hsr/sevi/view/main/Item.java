package ch.hsr.sevi.view.main;

/**
 * Created by SKU on 25.11.2014.
 */
public class Item {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public Item(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Item(String id, String name, String date) {
        this.name = name;
        this.date = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

}
