package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.Employees;
import models.EmployeesDao;
import views.LoginView;
import views.SystemView;

public class LoginController implements ActionListener {
    //
    // ao completar com implements ActionListener (1) importar (add import for java.awt.event.ActionListener)
    // e depois implementar seu metodo (implement all abstract metholds) na "bolinha" que aparece à esq.da linha de codigo
    // vamos encapsular as variaveis

    // importar, abajo viene de los models
    private Employees employee;
    // importar, abajo viene de los models
    private EmployeesDao employee_dao;
    // ese viene de la view
    private LoginView login_view;

    // ahora usamos el constructor con parametros
    public LoginController(Employees employee, EmployeesDao employee_dao, LoginView login_view) {
        this.employee = employee;
        this.employee_dao = employee_dao;
        this.login_view = login_view;
        this.login_view.btn_enter.addActionListener(this);
    }

    // ahora implementar el ActionListener, na chamada public class LoginController (acrescenta implements ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        // eliminar a linha abaixo....why? i dont know yet....
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        // obtener os datos de la vista
        String user = "";
        user = login_view.txt_username.getText().trim();
        String pass = "";
        pass = String.valueOf(login_view.txt_password.getPassword());
        if (e.getSource() == login_view.btn_enter) {
            // validar se campos vazios
            if (!user.equals("") || !pass.equals("")) {
                employee = employee_dao.LoginQuery(user, pass);
                // verificar existencia de usuario
                if (employee.getUsername() != null) {
                    // if abajo, se admin mostra ventana do admin, si no...
                    if (employee.getRol().equals("Administrador")) {
                        SystemView admin = new SystemView();
                        admin.setVisible(true);
                    } else {  // se nao for admin entao é o aux ou outro tipo de usuario
                        // aqui, nao eh admin, é outro user e mostra ventana deste tipo de user (aqui chama rol)
                        SystemView aux = new SystemView();
                        aux.setVisible(true);
                    }
                    // cerrar la ventana de login y dejar solamente la ventana de SystemView
                    this.login_view.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
        // if usuario/contraseña incorrecto sale aquí
        // benfeitoria made in dbf !@#$
        login_view.txt_username.setText("");
        login_view.txt_password.setText("");
    }
}
