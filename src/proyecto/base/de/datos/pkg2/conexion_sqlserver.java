/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.base.de.datos.pkg2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;

/**
 *
 * @author Gabriel
 */
public class conexion_sqlserver {
     static Connection contacto = null;
    public static String usuario;
    public static String password;
    public static boolean status = false;
    
    public boolean prueba(String instancia, String bd, String puerto, String usuario, String password){
        status = false;
        String url = "jdbc:sqlserver://"+instancia+":"+puerto+";databaseName="+bd;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
        }catch (ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se pudo establece la conexion... revisar Driver" + e.getMessage(),
            "Error de conexion_sqlserver",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try{
            contacto = DriverManager.getConnection(url, usuario, password);
            status = true;
            return true;
        }catch (SQLException e){
             JOptionPane.showMessageDialog(null, "Error" + e.getMessage(),
            "Error de conexion_sqlserver",JOptionPane.ERROR_MESSAGE);
             return false;
        }
    }
    
    public static Connection getconexion_sqlserver(){
        status = false;
        String url = "jdbc:sqlserver://CkriZz666:1433;databaseName=curso";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
        }catch (ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se pudo establece la conexion... revisar Driver" + e.getMessage(),
            "Error de conexion_sqlserver",JOptionPane.ERROR_MESSAGE);
        }
        try{
            contacto = DriverManager.getConnection(url, conexion_sqlserver.usuario, conexion_sqlserver.password);
            status = true;
        }catch (SQLException e){
             JOptionPane.showMessageDialog(null, "Error" + e.getMessage(),
            "Error de conexion_sqlserver",JOptionPane.ERROR_MESSAGE);
        }
        return contacto;
    }
    
    
    public static void setcuenta(String usuario, String password){
        conexion_sqlserver.usuario = usuario;
        conexion_sqlserver.password = password;
    }
    
    public static boolean getstatus(){
        return  status;
    }
    
    public static ResultSet Consulta(String consulta){
        Connection con = getconexion_sqlserver();
        Statement declara;
        try{
            declara=con.createStatement();
            ResultSet respuesta = declara.executeQuery(consulta);
            return respuesta;
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage(),
            "Error de conexion_sqlserver",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }   
}
