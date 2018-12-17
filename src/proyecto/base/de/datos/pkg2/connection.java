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
import java.sql.Statement;
import javax.swing.JOptionPane;
import static proyecto.base.de.datos.pkg2.conexion_sqlserver.getconexion_sqlserver;

/**
 *
 * @author Gabriel
 */
public class connection {

    Connection con = null;

    public boolean prueba(String instancia, String bd, String puerto, String usuario, String password) {
        try {
            //cargar nuestro driver
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+instancia+"/"+bd, usuario, password);
            System.out.println("conexion establecida");
            JOptionPane.showMessageDialog(null, "conexion establecida");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("FUCK");
            JOptionPane.showMessageDialog(null, "error de conexion " + e);
            System.out.println(e);
            return false;
        }
    }
    
    public Connection conexion() {
        try {
            //cargar nuestro driver
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/northwind", "root", "");
            System.out.println("conexion establecida");
            JOptionPane.showMessageDialog(null, "conexion establecida");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("FUCK");
            JOptionPane.showMessageDialog(null, "error de conexion " + e);
            System.out.println(e);
        }
        return con;
    }

    public void accion(String tabla, String operacion, int id) throws SQLException {
        //todas las lecturas que vas a hacer solo es de la tabla bitacora
        /*aqui empeiza la lectura de bitacora*/
        String ret = "";
        int i = 1;
        String campo1 = "", campo2 = "", campo3 = "";//haces un select que lea los campos id_afectado, campo_1, campo_2, campo_3 y tabla_afectada
        String query = "";
        Statement stmt = null;
        ResultSet resultSet = null;
        Connection con = conexion();
        int id_bitacora, id_afectado;
        stmt = con.createStatement();
        String operacion_bitacora, tabla_afectada, campo_1, campo_2, campo_3;
        resultSet = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM bitacora");
        resultSet.next();
        int rows = resultSet.getInt("rowcount");
        for (int j = 0; j < rows; j++) {
            query = "SELECT id, operacion, tabla_afectada, id_afectado, campo_1,campo_2, campo_3 FROM `bitacora` WHERE id ='" + j + "'";
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                id_bitacora = Integer.parseInt(resultSet.getString(1));
                operacion = resultSet.getString(2) + "";
                tabla_afectada = resultSet.getString(3);
                id_afectado = Integer.parseInt(resultSet.getString(4));
                campo_1 = resultSet.getString(5);
                campo_2 = resultSet.getString(6);
                campo_3 = resultSet.getString(7);
                System.out.println(campo_3);
            }
        }
        resultSet.close();
        stmt.close();
        // termina la lectura de bitacora y empeiza las querys de las otras tablas 
        if (operacion.equals("insert")) {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);
            //insert es general para las tres
            query = "INSERT INTO " + tabla + " VALUES(" + id + ", '" + campo1 + "', '" + campo2 + "', " + campo3 + "');";
            //aqui solo corre el query

        } else if (operacion.equals("update")) {
            if (tabla.equals("customers") || tabla.equals("employees")) {
                query = "UPDATE " + tabla + " SET company = '" + campo1 + "', last_name ='" + campo2 + "', first_name='" + campo3 + "' WHERE id ='" + id + "'";
                stmt = con.createStatement();
                resultSet = stmt.executeQuery(query);
                //aqui solo corre el query

            } else if (tabla.equals("products")) {
                query = "UPDATE " + tabla + " SET product_code='" + campo1 + "', product_name='" + campo2 + "', description='" + campo3 + "' WHERE id ='" + id + "'";
                stmt = con.createStatement();
                resultSet = stmt.executeQuery(query);
                //aqui solo corre el query

            }

        } else if (operacion.equals("delete")) {
            query = "DELETE FROM " + tabla + " WHERE id ='" + id + "'";
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(query);
            //aqui solo corre el query

        }

    }
}
