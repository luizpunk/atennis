/**
 * Esta clase permite gestionar los articulos en la base datos
 * @author luis
 * @since septiembre 2020
 * @copyright CECAR
 */
package edu.cecar.persistencia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import edu.cecar.modelo.Articulo;

public class GestionarArticulos {
	private static Connection con = new Conexion().getConnection();
	
	/**
	 * Este metodo permite almacenar objetos de tipo Articulo en la base de datos
	 * @param dominio - nombre de la pagina web EJ: DiarioADN, Emeridiano, etc.
	 * @param articulo - Objeto de tipo Articulo
	 */
	public static void guadarArticulo(Articulo articulo, String dominio) {
		
		try {
			PreparedStatement consulta;
			consulta = con.prepareStatement("INSERT INTO Articulos(titulo,descripcion,fecha,idUrl) VALUES (?,?,?,?)");			
			consulta.setString(1, articulo.getTitulo());
			consulta.setString(2, articulo.getDescripcion());
			consulta.setString(3, articulo.getFecha());
			consulta.setInt(4, GestionarURLs.getIdUrl(dominio));
			consulta.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error al guardar un articulo" +e);
		}
	}
	
}
