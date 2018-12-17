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
    Connection con = null;

    public conexion_sqlserver(String instancia, String database, String puerto, String user, String password) {
        con = getconexion_sqlserver(instancia, database, puerto, user, password);
    }

    public boolean prueba(String instancia, String bd, String puerto, String usuario, String password) {
        status = false;
        String url = "jdbc:sqlserver://" + instancia + ":" + puerto + ";databaseName=" + bd;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se pudo establece la conexion... revisar Driver" + e.getMessage(),
                    "Error de conexion_sqlserver", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            contacto = DriverManager.getConnection(url, usuario, password);
            status = true;
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage(),
                    "Error de conexion_sqlserver", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static Connection getconexion_sqlserver(String instancia, String database, String puerto, String user, String password) {
        status = false;
        String url = "jdbc:sqlserver://" + instancia + ":" + puerto + ";databaseName=" + database + ";user=" + user + ";password=" + password;
        ;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se pudo establece la conexion... revisar Driver" + e.getMessage(),
                    "Error de conexion_sqlserver", JOptionPane.ERROR_MESSAGE);
        }
        try {
            contacto = DriverManager.getConnection(url, conexion_sqlserver.usuario, conexion_sqlserver.password);
            status = true;
            JOptionPane.showMessageDialog(null, "conexion establecida");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage(),
                    "Error de conexion_sqlserver", JOptionPane.ERROR_MESSAGE);
        }
        return contacto;
    }

    public void accion(String instancia, String bd, String puerto, String usuario, String password, String tablamodificar) throws SQLException {
        //todas las lecturas que vas a hacer solo es de la tabla bitacora
        /*aqui empeiza la lectura de bitacora*/
        String ret = "";
        int i = 1;
        String campo1 = "", campo2 = "", campo3 = "";//haces un select que lea los campos id_afectado, campo_1, campo_2, campo_3 y tabla_afectada
        String query = "";
        Statement stmt = null;
        ResultSet resultSet = null;
        connection mysql = new connection(instancia, bd, puerto, usuario, password);
        int id_bitacora, id_afectado;
        stmt = con.createStatement();
        String operacion, tabla_afectada, campo_1, campo_2, campo_3;
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
                if (tablamodificar.equals(tabla_afectada)) {
                    mysql.ejecutar(operacion, tabla_afectada, id_bitacora, campo1, campo2, campo3);
                }
                else{
                    mysql.ejecutar(operacion, tabla_afectada, id_bitacora, campo1, campo2, campo3);
                }

            }
        }
        resultSet.close();
        stmt.close();
        // termina la lectura de bitacora y empeiza las querys de las otras tablas 

    }

    public void ejecutar(String operacion, String tabla_afectada, int id, String campo1, String campo2, String campo3, String tabla) throws SQLException {

        String query = "";
        Statement stmt = null;
        ResultSet resultSet = null;
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
