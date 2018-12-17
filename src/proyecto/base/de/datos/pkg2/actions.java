/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.base.de.datos.pkg2;

/**
 *
 * @author Machado
 */
public class actions {

    public void accion() {
        //todas las lecturas que vas a hacer solo es de la tabla bitacora
        String operacion = "";// lees operacion
        int id = 0;
        String tabla = "", campo1 = "", campo2 = "", campo3 = "";//haces un select que lea los campos id_afectado, campo_1, campo_2, campo_3 y tabla_afectada
        String query = "";
        if (operacion.equals("insert")) {
            //insert es general para las tres
            query = "Insert into " + tabla + " values(" + id + ", '" + campo1 + "', '" + campo2 + "', " + campo3 + "');";
            //aqui solo corre el query
            
        } else if (operacion.equals("update")) {
            if (tabla.equals("customers") || tabla.equals("employees")) {
                query = "update " + tabla + " set company = '" + campo1 + "', last_name ='" + campo2 + "', first_name='" + campo3 + "' where id = " + id;
                //aqui solo corre el query

            } else if (tabla.equals("products")) {
                query = "update " + tabla + " set product_code='" + campo1 + "', product_name='" + campo2 + "', description='" + campo3 + "' where id = " + id;
                //aqui solo corre el query

            }

        } else if (operacion.equals("delete")) {
            query = "delete from " + tabla + " where id = " + id;
            //aqui solo corre el query

        }
    }
}
