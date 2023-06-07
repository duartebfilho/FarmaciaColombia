
package models;

public class Customers {
    //
    // declara os atributos de Customers (campos da tabela customers)
    //
    private int id;
    private String full_name;
    private String address;
    private String telephone;
    private String email;
    private String created;
    private String updated;
    
    // assim como feito na tabela employees, usar constructor sin y con parametros
    // botao derecho, insert code, constructor

    public Customers() {
    }
    //
    // agora con parametros

    public Customers(int id, String full_name, String address, String telephone, String email, String created, String updated) {
        this.id = id;
        this.full_name = full_name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.created = created;
        this.updated = updated;
    }
    // ahora metodos getter and setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    // e ahora crear CustomersDao (asi como fué en employees)
}
