/**
 * Esta clase permite gestionar las URLs en la base datos
 * @author luis
 * @since septiembre 2020
 * @copyright CECAR
 */
package edu.cecar.persistencia;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;



public class GestionarURLs {
	private static Connection con = new Conexion().getConnection();

	/**
	 * Este metodo comprrueba que una URL sea valida
	 * @param url URL a validar
	 * @return True si es valida, False si no
	 */
	public static boolean validarURL(String url) {
		try {
			new URL(url).toURI();
			return true;
		}
		catch (URISyntaxException | MalformedURLException e) {
			System.out.println("la URL ingresada es invalida!");
			System.exit(0);
			return false;
		}
	}
	/**
	 * Metodo que comprueba  el estado de una URL
	 * @param url
	 * @return codigo - estado de la de peticion HTTP
	 */
	public static int getCodigoEstado(String url) {
		Response respuesta = null;
		if (validarURL(url)) {
			try {
				respuesta = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
				throw new ExcepcionEstado(respuesta.statusCode());

			} catch (ExcepcionEstado ex) {
				System.out.println(ex.getMessage());
			} catch (IOException e) {
				System.out.print("URL desconocida ");

			}
		}
		return respuesta.statusCode();
	}

	/**
	 * Este metodo permite almacenar las URL en la base de datos
	 * @param urls cadena que contine las URLs seperadas por ,
	 */
	public void guardarURL(String urls) {
		try {
			String[] url = urls.split(",");
			PreparedStatement consulta;
			for (String link : url) {
				if (GestionarURLs.getCodigoEstado(link) ==200) {
					consulta = con.prepareStatement("INSERT INTO Urls(link) VALUES (?);");
					consulta.setString(1, link);
					consulta.executeUpdate();
				}				
			}
		} catch (Exception e) {
			System.out.println("Error al guardar la URL");;
		}
	}

	/**
	 * Este metodo perdite modificar una URL almanacenada en la base de datos
	 * @param id - ID del la URL a modificar
	 * @param url - Nueva URL
	 */
	public void modificarURL(int id, String url) {
		try {
			PreparedStatement consulta;
			if (GestionarURLs.getCodigoEstado(url) ==200) {
				consulta = con.prepareStatement("UPDATE Urls SET link = ? WHERE idUrl = ?");
				consulta.setString(1, url);
				consulta.setInt(2, id);
				System.out.println("URL ACTUALIZADA!");
				consulta.executeUpdate();
			}				
		} catch (Exception e) {
			System.out.println("Error al modificar la URL, Verifique que sea correcta "+e);
		}
	}

	/**
	 * Metodo para eliminar una URL de la base de datos
	 * @param id - ID de la URL a eliminar
	 */
	public void eliminarURL(int id) {
		try {
			PreparedStatement consulta;
			consulta = con.prepareStatement("DELETE FROM Urls WHERE idUrl = ?");
			consulta.setInt(1, id);
			System.out.println("URL ELIMINADA!");
			consulta.executeUpdate();				
		} catch (Exception e) {
			System.out.println("Error al Eliminar la URL "+e);
		}
	}

	/**
	 * Metodo que perimite consultar todas las URL almacenadas en la base de datos
	 */
	public void consultarURLs() {
		ResultSet resultado;
		try {
			PreparedStatement consulta;
			consulta = con.prepareStatement("SELECT * FROM Urls");
			resultado = consulta.executeQuery();
			while(resultado.next()) {
				System.out.println("ID: "+resultado.getInt("idUrl")+" URL: "+resultado.getString("link"));
				System.out.println("-----------------------------------------");
			}			
		} catch (Exception e) {
			System.out.println("Error al listar las URLs "+e);
		}
	}

	/**
	 * Este metodo devuelvete cualquier id de una pagina web resgistrada en la
	 * base de datos Ej: DiarionADN, ElEspectador, Eltiempo, etc.
	 * @param pagina pagina web perteneciente a la URL en la base de datos
	 * @return id - ID de la pagina encontrada
	 */
	public static int getIdUrl(String pagina) {
		int id = 0;
		ResultSet resultado;
		try {

			PreparedStatement consulta;
			consulta = con.prepareStatement("SELECT idUrl FROM Urls WHERE link LIKE \"%"+pagina+"%\"");
			resultado = consulta.executeQuery();
			while(resultado.next()) {
				id = resultado.getInt("idUrl");
			}			
		} catch (Exception e) {
			System.out.println("Error al obtener el ID del dominio ");
		}
		return id;
	}

	/**
	 * Este metodo permite saber si ya la informacion esta descargada o no
	 * @return descarga True o False si ya hay datos guargados en la base de datos
	 */
	public static int hayQueDescargar() {
		int descargar = 0;
		try {
			PreparedStatement consulta = con.prepareStatement("SELECT COUNT(*) FROM Urls;");
			ResultSet resultado = consulta.executeQuery();
			if(resultado.next()) {
				int n= resultado.getInt(1);
				if (n == 0) {
					System.out.println("NO HAY DATOS DESCARGADOS, SE DECARGARAN...");
					descargar = 0;
				}else
					System.out.println("TODOS LOS DATOS ESTAN DESCARGADOS"); 
				descargar = 1;
			}

		} catch (Exception e) {
			System.out.println("No se pudo obtener informacion de descarga!");
		}
		return descargar;
	}
}
