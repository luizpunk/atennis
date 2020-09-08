/**
 * Esta clase crea la conexion a la base de datos
 * @author luis 
 */
package edu.cecar.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

	private static Connection conn;
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/scraping";
	
	/**
	 * Constructor de la clase de nos conecta a la base de datos
	 * @param url del host
	 * @param driver de conexion
	 * @param password del usuario
	 */
	public Conexion() {
		conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"root","");
			if(conn == null) {
				System.out.println("DESCONECTADO");
			}
		} catch (Exception e) {
			System.out.println("ERROR AL CONECTAR A LA BASE DE DATOS");
			System.out.println("Programa finalizado!");
			System.exit(0);
		}
	}
	/**
	 * Este metodo nos devuleve la conexion entre la base de datos y el programa
	 * @return un objeto de tipo Conexion
	 */
	public  Connection getConnection() {
		return conn;
	}
	
	/**
	 * Este metodo nos desconecta de la base de datos
	 */
	public void desconectar() {
		conn = null;
		if(conn == null) {
			System.out.println("CONEXION A LA BASE DE DATOS FINALIZADA");
		}
	}
}
