package models;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class CustomersDao {

    // (1) instanciar a conexao, para isso vamos a EmployeesDao para copiar codigo (usaremos modelo)
    //
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // (2) crear metodo de registro, poderia copiar de employees mas na aula vai fazer na mao outra vez
    // registrar cliente
    public boolean registerCustomersQuery(Customers customer) {
        String query = "INSERT INTO customers (id, full_name, address, telephone, email, created, updated) "
                + "VALUES (?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        // 
        // ahora vamos acceder la tabla customers - cargar las variables 
        // Connection / PreparedStatement / ResultSet
        // usar try / catch
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            // ahora acceder a los metodos setter del cliente 
            pst.setInt(1, customer.getId());
            pst.setString(2, customer.getFull_name());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getTelephone());
            pst.setString(5, customer.getEmail());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            // executamos pst
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al cliente");
            return false;
        }

    }

    // metodo Listar Clientes
    public List listCustomersQuery(String value) {
        // criar array para listagem clientes
        List<Customers> list_customers = new ArrayList();
        // criar 2 queries uma para listar tudo e outra para lista cliente especifico
        // listar tudo
        String query = "SELECT * FROM customers";
        String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%" + value + "%'";
        //
        // agora acessa BD entao try catch
        try {
            conn = cn.getConnection();
            // agora testa se vai listar 1 ou vários clientes
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_customer);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Customers customer = new Customers();
                customer.setId(rs.getInt("id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                list_customers.add(customer);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_customers;
    }

    // modificar cliente
    public boolean updateCustomersQuery(Customers customer) {
        // pra fazer metodo copiamos de "registerCustomersQuery" mas a query tem que mudar conforma abaixo
        //
        String query = "UPDATE customers SET full_name=?, address=?, telephone=?, email=?, updated=? "
                + "WHERE id=?";
        // Timestamp usada para os campos created e updated, neste caso apenas updated (edicao)
        Timestamp datetime = new Timestamp(new Date().getTime());
        // 
        // ahora vamos acceder la tabla customers - cargar las variables 
        // Connection / PreparedStatement / ResultSet
        // usar try / catch
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            // ahora acceder a los metodos setter del cliente 
            pst.setString(1, customer.getFull_name());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, customer.getId());

            // executamos pst
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del cliente");
            return false;
        }

    }

    //eliminar cliente - pegar modelo de EmployeesDao eliminar empleado
    public boolean deleteCustomerQuery(int id) {
        // monta query para delete
        String query = "DELETE FROM customers WHERE id = " + id;
        // vai acesar BD entao try catch
        //
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            // pst.executeQuery(); ou eu errei ou foi o prof
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puede eliminar cliente que tenga relación con otra tabla");
            return false;
        }
    }

}
