package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDao {

    // instanciar la conexion con base datos
    // aqui estao os metodos para conexao 
    ConnectionMySQL cn = new ConnectionMySQL();
    // variaveis para conexion / aceso base datos
    // Connection 
    Connection conn;
    // para consultas
    PreparedStatement pst;
    //contem resultado da consulta
    ResultSet rs;

    // agora vem os metodos necesarios para esta tabela, registar, alterar, eliminar, listar
    //
    // metodo registrar venta
    public boolean registerSaleQuery(int customer_id, int employee_id, double total) {

        String query = "INSERT INTO sales (customer_id, employee_id, total, sale_date) "
                + "VALUES (?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, customer_id);
            pst.setInt(2, customer_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, datetime);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar la compra");
            return false;
        }
    }

    // registrar los detalles de la venta
    public boolean registerSaleDetailQuery(int product_id, int sale_id, int sale_quantity, double sale_price,
            double sale_subtotal) {
        String query = "INSERT INTO sale_details (sale_quantity, sale_price, sale_subtotal, product_id, "
                + "sale_id) VALUES (?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, sale_quantity);
            pst.setDouble(2, sale_price);
            pst.setDouble(3, sale_subtotal);
            pst.setInt(4, product_id);
            pst.setInt(5, sale_id);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }

    }

    // obtener el id de la venta
    public int saleId() {
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM sales";

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    // listar ventas realizadas
    public List listAllSalesQuery() {
        List<Sales> list_sale = new ArrayList();
        String query = "SELECT s.id AS invoice, c.full_name AS customer, e.full_name AS employee, s.total, "
                + "s.sale_date FROM sales s INNER JOIN customers c ON s.customer_id=c.id "
                + "INNER JOIN employees e ON s.employee_id=e.id ORDER BY s.id ASC";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Sales sale = new Sales();
                sale.setId(rs.getInt("invoice"));
                sale.setCustomer_name(rs.getString("customer"));
                sale.setEmployee_name(rs.getString("employee"));
                sale.setTotal_to_pay(rs.getDouble("total"));
                sale.setSale_date(rs.getString("sale_date"));
                list_sale.add(sale);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_sale;
    }

}
