
package models;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeesDao {
    
    // instanciar la conexion (classe ConnectionMySQL)
    
    ConnectionMySQL cn = new ConnectionMySQL();
    //
    // las variables abajo sirven para conectar con la base de datos
    Connection conn; // permite la conección con la base de datos - necesita import java.sql.Connection;
    PreparedStatement pst; // habilita las consultas - necesita import java.sql.PreparedStatement;
    ResultSet rs; // conten los datos de la consulta - necesita import java.sql.ResultSet;
    
    // agora crear variables para enviar/recibir datos entre interfaces
    // neste caso para capturar el id, full_name, address, telephone......
    // el unico dato que no vamos almacenar es password
    
    public static int id_user = 0;
    public static String full_name_user = "";
    public static String username_user = "";
    public static String address_user = "";
    public static String rol_user = "";
    public static String email_user = "";
    public static String telephone_user = "";
    //
    // Método de login
    //
    public Employees LoginQuery(String user, String password){
        // crear a string query con select necesario
        // a interrogacao abaixo ainda nao conhecia....ver mais
        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        // ahora instanciar la clase Employees
        Employees employee = new Employees();
        // ahora definir try/catch para tratar a obtencion de los datos de la base de datos
        // pode satisfazer a consulta (ok) pode não encontrar (erro "não encontrado") 
        //
        try{
            // ahora ejecuta los comandos para conectar e obtener los datos de la base de datos
            // cn variable de la conexion e conn conten lo resultado de la conexion
            
            conn = cn.getConnection();
            
            // pst conterá string con los datos para busca/lectura
            
            pst = conn.prepareStatement(query);
            
            // enviar parametros para consulta no caso para 2 parametros user e password, passando cada um deles
            
            pst.setString(1,user);
            pst.setString(2,password);
            //
            
            // ahora ejecutar la consulta , resultado en variable rs (ResultSet)
            // obs.o metodo executeQuery é dado pelo java
            rs = pst.executeQuery();           
            //
            // la consulta ejecutada, tratar el resultado y coletar los campos
            //
            // ahora vamos verificar si los datos
            // user , password coinciden 

            if (rs.next()){
                //
                // el "if" verifica si coincide user, password entonces
                // para cada campo hacer conforme abajo
                
                // para cada campo de la tabla - escrivir las 2 lineas conforme abajo
                //
                // (1) acceder los metodos setter y getter del employees para cada campo
                // 
                // employee.setId(rs.getInt("id"));  // setter and getter para cada campo
                //
                // (2) ahora mover lo dato para la variable public static definida antes (arriba)
                // para cada una dellas
                //
                // id_user = employee.getId();
                
                // vamos
                // OBS. tem nome campo da tabela, nome da variabel do programa
                // no set e get vai nome do campo da tabela
                // na linha id_user = employee.getId(); id_user é a variavel do progr.
                
                // campo id (tabla) y id_user (variable progr.)
                employee.setId(rs.getInt("id"));
                id_user = employee.getId();
                
                // campo full_name (tabla) y full_name_user (variable progr.
                employee.setFull_name(rs.getString("full_name"));
                full_name_user = employee.getFull_name();
                
                // campo username (tabla) y username_user (progr.)
                employee.setUsername(rs.getString("username"));
                username_user = employee.getUsername();
                
                // campo address (tabla) y address_user
                employee.setAddress(rs.getString("address"));
                address_user = employee.getAddress();

               // telephone (tabla) y telephone_user (progr.) 
               employee.setTelephone(rs.getString("telephone"));
               telephone_user = employee.getTelephone();

                // email (tabla) y email_user (progr.)
                
                employee.setEmail(rs.getString("email"));
                email_user = employee.getEmail(); 
                
               // rol (tabla) y rol_user (progr.)
                employee.setRol(rs.getString("rol"));
                rol_user = employee.getRol();

            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al obtener al empleado" + e);
        }
        return employee;
    
            
    }
    
    // Metodo Registrar empleado
    
    // crear metodo boolean
    public boolean registerEmployeeQuery(Employees employee) {
        //
        // crear variable string para conter query
        String query = "INSERT INTO employees(id, full_name, username, address, telephone, "
                + "email, password, rol, created, updated) VALUES (?,?,?,?,?,?,?,?,?,?)";
        // Ahora vamos a crear otras variables para created y updated de la tabla employees
        // para eso vamos a usar Timestamp para obtener fecha, hora, etc
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        // porque vamos acceder la base datos para eso try / catch
        
        try{
            
            // conn conterá a conexion a traves de objeto cn 
            conn = cn.getConnection();
            // en pst cargará la query en BD
            pst = conn.prepareStatement(query);
            //
            // ahora vamos acceder los metodos setter para cada campo de empleados y enviar datos
            //
            pst.setInt(1,employee.getId());
            pst.setString(2,employee.getFull_name());
            pst.setString(3, employee.getUsername());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getTelephone());
            pst.setString(6, employee.getEmail());
            pst.setString(7, employee.getPassword());
            pst.setString(8, employee.getRol());
            pst.setTimestamp(9,datetime);
            pst.setTimestamp(10, datetime);
            pst.execute();
            return true;
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al registrar al empleado " + e);
            return false;
        }
    }
    //
    // crear metodo lista empleados
    //
    public List listEmployeesQuery(String value){
        List<Employees> list_employees = new ArrayList();
        // debemos importar library java para List = add import for java.util.ArrayList
        // y para List importar = add import for java.util.List
        //
        // carregar as strings com as queries necesarias, uma para listar toda tabela employees e
        // outra query para listar apenas um determinado empleado
        //
        // String query = "SELECT * FROM employees ORDER BY rol ASC";
        String query = "SELECT * FROM employees";
        String query_search_employee = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'";
        //
        // ahora try catch para aceso BD
        //
        try {
            // instanciar la conexion
            conn = cn.getConnection();
            //
            // if abajo - (1) caso nao digite nada (nao procura employee determinado) -> vazio ""
            
            if (value.equalsIgnoreCase("")){
                // nao procura registro especifico - carrega pst com a query sem WHERE
                pst = conn.prepareStatement(query);
                // e carrega rs (ResultSet) com resultado da consulta (query)
                rs = pst.executeQuery();                
            } else{
                // caso contrario, digitou um rol ou id desejado -> SELECT com WHERE = segunda consulta
                // query_search_employee
                pst = conn.prepareStatement(query_search_employee);
                rs = pst.executeQuery();                
            }
        while(rs.next()){
            Employees employee = new Employees();
            // con los metodos setter vamos almacenar cada uno de los resultados
            employee.setId(rs.getInt("id"));
            employee.setFull_name(rs.getString("full_name"));
            employee.setUsername(rs.getString("username"));
            employee.setAddress(rs.getString("address"));
            employee.setTelephone(rs.getString("telephone"));
            employee.setEmail(rs.getString("email"));
            // o que esta linha fazia aqui?? será??? employee.setPassword(rs.getString("password"));
            employee.setRol(rs.getString("rol"));
            // e ahora cargar el array con los datos
            list_employees.add(employee);            
        }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_employees;
    }
    
    // crear metodo para modificar / editar empleados
    // copia metodo de registrar empleados (acima) e faz as alteracoes necesarias
    public boolean updateEmployeeQuery(Employees employee) {
        //
        // crear variable string para conter query
        // a query foi modificada pois agora servirá para modificar/atualizar os registros
        // e a montagem é como abaixo
        String query = "UPDATE employees SET full_name=?, username=?, address=?, telephone=?, "
                + "email=?, rol=?, updated=? WHERE id=?";
        // Ahora vamos a crear otras variables para created y updated de la tabla employees
        // para eso vamos a usar Timestamp para obtener fecha, hora, etc
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        // porque vamos acceder la base datos para eso try / catch
        
        try{
            
            // conn conterá a conexion a traves de objeto cn 
            conn = cn.getConnection();
            // en pst cargará la query en BD
            pst = conn.prepareStatement(query);
            //
            // ahora vamos acceder los metodos setter para cada campo de empleados y enviar datos
            //
            // alguns campos serao desabilitados pois agora é para modificar/atualizar tabela
            // pst.setInt(1,employees.getId());
            // os indices dos parametros de pst serao corrigidos porque alguns campos foram retirados
            // como id, password, created
            pst.setString(1,employee.getFull_name());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getRol());
            pst.setTimestamp(7, datetime);
            pst.setInt(8,employee.getId());
            pst.execute();
            return true;
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del empleado " + e);
            return false;
        }
    }
    
    // metodo para eliminar empleados
    public boolean deleteEmployeeQuery(int id){
        // monta query para delete
        String query = "DELETE FROM employees WHERE id = " + id;
        // vai acesar BD entao try catch
        //
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No se puede eliminar que tenha relación con otra tabla");
            return false;
        }
    }
    
    // crear metodo para modificar la contraseña
    public boolean updateEmployeePassword(Employees employee){
        String query = "UPDATE employees SET password = ? WHERE username = '"+ username_user + "'";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1,employee.getPassword());
            pst.executeUpdate();
            return true;
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Ha ocurrido un error al modificar la contraseña" + e);
        }   return false;
    }
}
