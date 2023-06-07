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
import static models.EmployeesDao.rol_user;
import models.Suppliers;
import models.SuppliersDao;
import views.SystemView;

public class SuppliersController implements ActionListener, MouseListener, KeyListener {

    // encapsular las variables
    private Suppliers supplier;
    private SuppliersDao supplierDao;
    private SystemView views;
    String rol = rol_user;
    // definir DefaultTableModel
    DefaultTableModel model = new DefaultTableModel();

    public SuppliersController(Suppliers supplier, SuppliersDao supplierDao, SystemView views) {
        this.supplier = supplier;
        this.supplierDao = supplierDao;
        this.views = views;
        // boton registrar proveedor en escucha
        this.views.btn_register_supplier.addActionListener(this);
        // tabla suppliers_table en escucha
        this.views.suppliers_table.addMouseListener(this);
        // campo pesquisar proveedor en escucha
        this.views.txt_search_supplier.addKeyListener(this);
        // boton modificar proveedores
        this.views.btn_update_supplier.addActionListener(this);
        // escucha en jLabelSuppliers
        this.views.jLabelSuppliers.addMouseListener(this);
        // boton de eliminar proveedor
        this.views.btn_delete_supplier.addActionListener(this);
        // boton de cancelar
        this.views.btn_cancel_supplier.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_supplier) {
            if (views.txt_supplier_name.getText().equals("")
                    || views.txt_supplier_description.getText().equals("")
                    || views.txt_supplier_address.getText().equals("")
                    || views.txt_supplier_telephone.getText().equals("")
                    || views.txt_supplier_email.getText().equals("")
                    || views.cmb_supplier_city.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatórios");
            } else {
                // realizar la inserción
                supplier.setName(views.txt_supplier_name.getText().trim());
                supplier.setDescription(views.txt_supplier_description.getText().trim());
                supplier.setAddress(views.txt_supplier_address.getText().trim());
                supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                supplier.setEmail(views.txt_supplier_email.getText().trim());
                supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());

                // vamos hacerlo a traves de el if para tener la accion y la respuesta junto
                if (supplierDao.registerSupplierQuery(supplier)) {
                    cleanTable();
                    cleanFields();
                    listAllSuppliers();

                    JOptionPane.showMessageDialog(null, "Proveedor registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al proveedor");
                }
            }
        } else if (e.getSource() == views.btn_update_supplier) {
            if (views.txt_supplier_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                // aproveitando copiar codigo de registrar supplier   
                if (views.txt_supplier_name.getText().equals("")
                        || views.txt_supplier_address.getText().equals("")
                        || views.txt_supplier_telephone.getText().equals("")
                        || views.txt_supplier_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatórios");
                } else {
                    // copiando codigo de registrar supplier
                    supplier.setName(views.txt_supplier_name.getText().trim());
                    supplier.setDescription(views.txt_supplier_description.getText().trim());
                    supplier.setAddress(views.txt_supplier_address.getText().trim());
                    supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                    supplier.setEmail(views.txt_supplier_email.getText().trim());
                    supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());
                    supplier.setId(Integer.parseInt(views.txt_supplier_id.getText()));

                    if (supplierDao.updateSupplierQuery(supplier)) {
                        // limpiar tabla
                        cleanTable();
                        // limpar campos
                        cleanFields();
                        // list proveedores
                        listAllSuppliers();
                        JOptionPane.showMessageDialog(null, "Datos del proveedor modificados ccon éxito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha occurido un error al modificar los datos del proveedor");
                    }
                } // vacio
            } // if selecciona una fila
        } else if (e.getSource() == views.btn_delete_supplier) {
            int row = views.suppliers_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un proveedor para eliminar");
            } else {
                int id = Integer.parseInt(views.suppliers_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "En realidad quieres eliminar este proveedor?");
                if (question == 0 && supplierDao.deleteSupplierQuery(id) != false) {
                    // limpiar tabla
                    cleanTable();
                    // limpiar campos
                    cleanFields();
                    // listar proveedores
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proveedor eliminado con éxito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_supplier) {
            cleanFields();
            views.btn_register_supplier.setEnabled(true);
    }
}  // fim do actionPerformed     

// list proveedores
public void listAllSuppliers() {
        if (rol.equals("Administrador")) {
            List<Suppliers> list = supplierDao.listSuppliersQuery(views.txt_search_supplier.getText());
            model = (DefaultTableModel) views.suppliers_table.getModel();
            // el Object row neste caso debe ser 7 campos
            Object[] row = new Object[7];
            // ciclo for para preencher row com dados do DB
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getDescription();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getCity();
                model.addRow(row);
            } // salida ciclo for
            views.suppliers_table.setModel(model);
        }

    }

    @Override
public void mouseClicked(MouseEvent e) {
        // aqui vamos verificar si operador hizo clic en la tabla e saberemos en que linea
        // hizo clic
        // verificar si operador hizo clic en la tabla
        if (e.getSource() == views.suppliers_table) {
            // si hizo clic, recoger qué linea fue
            int row = views.suppliers_table.rowAtPoint(e.getPoint());
            // ahora sabemos qué linea hizo clic, entonces vamos recoger los datos
            // como es una tabla, hay lineas y columnas, la linea es row y la columna (de 0 a 6, tot.7 campos)
            // assim, preencher cada caixa de texto da interface com os dados da table correspondentes
            views.txt_supplier_id.setText(views.suppliers_table.getValueAt(row, 0).toString());
            views.txt_supplier_name.setText(views.suppliers_table.getValueAt(row, 1).toString());
            views.txt_supplier_description.setText(views.suppliers_table.getValueAt(row, 2).toString());
            views.txt_supplier_address.setText(views.suppliers_table.getValueAt(row, 3).toString());
            views.txt_supplier_telephone.setText(views.suppliers_table.getValueAt(row, 4).toString());
            views.txt_supplier_email.setText(views.suppliers_table.getValueAt(row, 5).toString());
            views.cmb_supplier_city.setSelectedItem(views.suppliers_table.getValueAt(row, 6).toString());

            // deshabilitar botones
            views.btn_register_supplier.setEnabled(false);
            views.txt_supplier_id.setEditable(false);
        } else if (e.getSource() == views.jLabelSuppliers) {
            // somente adm poderão acceder
            if (rol.equals("Administrador")) {
                // habilita aba de proveedores/suppliers
                views.jTabbedPane2.setSelectedIndex(5);
                // limpiar tabla
                cleanTable();
                // limpiar campos
                cleanFields();
                // lista proveedores
                listAllSuppliers();
            } else {
                views.jTabbedPane2.setEnabledAt(5, false);
                views.jLabelSuppliers.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tiene privilegios de administrador para acceder a esta vista");
            }
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
        // aqui el operador hizo clic en alguna linea, entonces vamos a 
        // limpiar la tabla y listar proveedores
        if (e.getSource() == views.txt_search_supplier) {
            // vamos a limpiar la tabla
            cleanTable();
            // listar proveedor
            listAllSuppliers();
        }
    }

    // limpiar campos
    public void cleanFields() {
        views.txt_supplier_id.setText("");
        views.txt_supplier_id.setEditable(true);
        views.txt_supplier_name.setText("");
        views.txt_supplier_description.setText("");
        views.txt_supplier_address.setText("");
        views.txt_supplier_telephone.setText("");
        views.txt_supplier_email.setText("");
        views.cmb_supplier_city.setSelectedIndex(0);
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

}
