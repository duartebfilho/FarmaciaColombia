
package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static models.EmployeesDao.address_user;
import static models.EmployeesDao.email_user;
import static models.EmployeesDao.full_name_user;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.telephone_user;
import views.SystemView;

public class SettingsController implements MouseListener {
    
    // instanciar la vista
    private SystemView views;
    
    //criar el metodo constructor
    public SettingsController(SystemView views) {
        
        this.views = views;
        // para colocar las etiquetas y los paneles en escucha....
        this.views.jLabelProducts.addMouseListener(this);
        this.views.jLabelCategories.addMouseListener(this);
        this.views.jLabelCustomers.addMouseListener(this);
        this.views.jLabelSales.addMouseListener(this);
        this.views.jLabelEmployees.addMouseListener(this);
        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        this.views.jLabelSettings.addMouseListener(this);
        this.views.jLabelSuppliers.addMouseListener(this);
        Profile();
    }
    
    // Asignar el perfil del usuario
    public void Profile(){
        this.views.txt_id_profile.setText(""+id_user);
        this.views.txt_name_profile.setText(full_name_user);
        this.views.txt_address_profile.setText(address_user);
        this.views.txt_phone_profile.setText(telephone_user);
        this.views.txt_email_profile.setText(email_user);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
        // aqui pergunta, onde está o mouse num dado instante
        if (e.getSource() == views.jLabelProducts) {
            views.jPanelProducts.setBackground(new Color(152,202,63));
       } else if (e.getSource() == views.jLabelPurchases) {
           views.jPanelPurchases.setBackground(new Color(152,202,63));
       } else if (e.getSource() == views.jLabelCustomers) {
           views.jPanelCustomers.setBackground(new Color(152,202,63));
       } else if (e.getSource() == views.jLabelEmployees) {
           views.jPanelEmployees.setBackground(new Color (152,202,63));
       } else if (e.getSource() == views.jLabelSuppliers) {
           views.jPanelSuppliers.setBackground(new Color(152,202,63));
       } else if (e.getSource() == views.jLabelCategories) {
           views.jPanelCategories.setBackground(new Color(152,202,63));
       } else if (e.getSource() == views.jLabelReports) {
           views.jPanelReports.setBackground(new Color(152,202,63));
       } else if (e.getSource() == views.jLabelSettings) {
           views.jPanelSettings.setBackground(new Color(152,202,63));
       }else if (e.getSource() == views.jLabelSales){
           views.jPanelSales.setBackground(new Color(152,202,63));
       }
        
    }

    @Override
    //
    // criar condicional para verificar si la persona dejo de situar el mouse en alguna de las 
    // opciones del menu y entonces tratar de estas situaciones aqui en mouseExited
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == views.jLabelProducts) {
            views.jPanelProducts.setBackground(new Color(18,45,61));
       } else if (e.getSource() == views.jLabelPurchases) {
           views.jPanelPurchases.setBackground(new Color(18,45,61));
       } else if (e.getSource() == views.jLabelCustomers) {
           views.jPanelCustomers.setBackground(new Color(18,45,61));
       } else if (e.getSource() == views.jLabelEmployees) {
           views.jPanelEmployees.setBackground(new Color(18,45,61));
       } else if (e.getSource() == views.jLabelSuppliers) {
           views.jPanelSuppliers.setBackground(new Color(18,45,61));
       } else if (e.getSource() == views.jLabelCategories) {
           views.jPanelCategories.setBackground(new Color(18,45,61));
       } else if (e.getSource() == views.jLabelReports) {
           views.jPanelReports.setBackground(new Color(18,45,61));
       } else if (e.getSource() == views.jLabelSettings) {
           views.jPanelSettings.setBackground(new Color(18,45,61));
       } else if (e.getSource() == views.jLabelSales){
            views.jPanelSales.setBackground(new Color(18,45,61));
       }
    }
    
}
