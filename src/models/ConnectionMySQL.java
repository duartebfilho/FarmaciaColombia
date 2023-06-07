package models;

import java.sql.Connection;
import java.sql.DriverManager;
// import java.sql.SQLDataException;
import java.sql.SQLException;

public class ConnectionMySQL {

    // atributos da conexão com db
    private String database_name = "pharmacy_database";
    private String user = "root";
    private String password = "M@nd1oc@";
    private String url = "jdbc:mysql://localhost:3306/" + database_name;
    Connection conn = null;

    // criar metodo para conexao com db
    public Connection getConnection() {
        try {
            // obtener valor de driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // obtener la connección
            conn = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.err.println("Ha occurido un ClassNotFoundException" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ha occurido un SQLException" + e.getMessage());
        }
        return conn;
    }

}
