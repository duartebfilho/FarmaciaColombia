
package models;

// modelo Categories
public class Categories {
    // atributos de Categories
    private int id;
    private String name;
    private String created;
    private String updated;

    // constructor sin parametros
    public Categories() {
    }
    
    // constructor con parametros

    public Categories(int id, String name, String created, String updated) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.updated = updated;
    }
    
    // getter y setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
      
    // é só isso.......:-)
}
