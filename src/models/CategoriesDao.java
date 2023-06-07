package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriesDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    // registrar categoria
    public boolean registerCategoryQuery(Categories category) {
        String query = "INSERT INTO categories (name, created, updated) VALUES (?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            // nao usar esta linha porque o id é generado automatic [pst.setInt(1,category.getId());]
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setTimestamp(3, datetime);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la Categoria");
            return false;
        }
    }

    // listar categorias
    public List listCategoryQuery(String value) {
        List<Categories> list_categories = new ArrayList();
        String query = "SELECT * FROM categories";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE '%" + value + "%'";
        // 
        // try catch
        try {
            conn = cn.getConnection();
            // agora testa se vai listar 1 ou vários clientes
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }

            while (rs.next()) {
                // abajo, estamos instanciando y creando el objeto category
                Categories category = new Categories();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                list_categories.add(category);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        return list_categories;
    }

    // modificar categoria
    // podemos copiar/colar tudo de registerCategoryQuery acima e alterar o correspondente
    public boolean updateCategoryQuery(Categories category) {
        // crear query de insert
        String query = "UPDATE categories SET name=?, updated=? WHERE id =?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            // nao usar esta linha porque o id é generado automatic [pst.setInt(1,category.getId());]
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setInt(3, category.getId());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos de la Categoria");
            return false;
        }
    }

    // metodo eliminar categoria
    public boolean deleteCategoryQuery(int id) {
        // monta query para delete
        String query = "DELETE FROM categories WHERE id = " + id;
        // vai acesar BD entao try catch
        //
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.executeQuery();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puede eliminar categoria que tenga relación con otra tabla");
            return false;
        }
    }

}
