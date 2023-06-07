package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Categories;
import models.CategoriesDao;
import static models.EmployeesDao.rol_user;
import views.SystemView;

public class CategoriesController implements ActionListener, MouseListener, KeyListener {

    //03-encapsular las variables y definir rol
    private Categories category;
    private CategoriesDao categoryDao;
    private SystemView views;
    String rol = rol_user;  // eso debe importar (models.EmployeesDao.rol_user)
    // importar de los modelos y de la vista (SystemView)

    //04-creamos el constructor, botón derecho, insert code, constructor, seleccionamos todo y generate
    // 05-ahora, debemos implementar ActionListener, en public class CategoriesController
    // queda así - public class CategoriesController implements ActionListener
    // importar y implementar sus metodos
    // 06-debemos colocar en escucha el botón de registrar categories (btn_register_category)
    // en constructor insertar la linea
    DefaultTableModel model = new DefaultTableModel();

    public CategoriesController(Categories category, CategoriesDao categoryDao, SystemView views) {
        this.category = category;
        this.categoryDao = categoryDao;
        this.views = views;
        // colocar en escucha el boton btn_register_category
        this.views.btn_register_category.addActionListener(this);
        this.views.categories_table.addMouseListener(this);
        this.views.txt_search_category.addKeyListener(this);
        // boton de modificar
        this.views.btn_update_category.addActionListener(this);
        // escucha en el boton btn_cancel_category
        this.views.btn_cancel_category.addActionListener(this);
        // escucha en jLabelCategories
        this.views.jLabelCategories.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_category) {
            if (views.txt_category_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                // aqui ok, vamos a insertar
                category.setName(views.txt_category_name.getText().trim());
                // ahora vamos a llamar el metodo de registrar
                if (categoryDao.registerCategoryQuery(category)) {
                    cleanTable();
                    cleanFields();
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Categoria registrada con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar la categoria");
                }
            }
        } else if (e.getSource() == views.btn_update_category) {
            if (views.txt_category_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txt_category_id.getText().equals("")
                        || views.txt_category_name.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    category.setId(Integer.parseInt(views.txt_category_id.getText()));
                    category.setName(views.txt_category_name.getText().trim());

                    if (categoryDao.updateCategoryQuery(category)) {
                        cleanTable();
                        cleanFields();
                        listAllCategories();
                        JOptionPane.showMessageDialog(null, "Categoria modificada con éxito");
                        views.btn_register_category.setEnabled(true);
                    }
                }
            }
        }else if(e.getSource() == views.btn_cancel_category) {
		cleanFields();
		views.btn_register_category.setEnabled(true);
	}
    }

    public void listAllCategories() {
        if (rol.equals("Administrador")) {
            List<Categories> list = categoryDao.listCategoryQuery(views.txt_search_category.getText());
            model = (DefaultTableModel) views.categories_table.getModel();
            Object[] row = new Object[2];
            for (int i = 0; i < list.size(); i++) {
                // moviendo los datos de la tabla DB para el arreglo (matriz)
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                // movidos los datos de uno registro, insertamos en el arreglo
                model.addRow(row);
            }
            views.categories_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == views.categories_table) {
            // para saber en que linea el operador ha pulsado
            int row = views.categories_table.rowAtPoint(e.getPoint());
            // el numero de la linea pulsada está en row, nuestra referencia 
            views.txt_category_id.setText(views.categories_table.getValueAt(row, 0).toString());
            views.txt_category_name.setText(views.categories_table.getValueAt(row, 1).toString());
            // en la insercion deshabilitamos los botones
            views.btn_register_category.setEnabled(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_category) {
            // limpiar la tabla
            cleanTable();
            // listar categorias
            listAllCategories();
        }

    }

    //limpiar tabla
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;

        }
    }

    // limpiar campos
    public void cleanFields() {
        views.txt_category_id.setText("");
        views.txt_category_name.setText("");
    }

}
