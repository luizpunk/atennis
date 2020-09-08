/**
 * Este clase permite hacer un analisis de tendencia en las paginas web
 */
package edu.cecar.logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import edu.cecar.persistencia.Conexion;

public class AnalisisTendencia {
	private static Connection con = new Conexion().getConnection();

	/**
	 * Este metodp permite crear una lista de las palabras a buscar en tendencia
	 * @param palabras - cadena que contiene todas las palabras a buscar separadas por ,
	 */
	public void capturarDatos(String palabras) {
		String[] palabra = palabras.split(",");
		int tamano = palabra.length;
		String datos[][] = new String[tamano][3];
		for (int i = 0; i < tamano; i++) {
			datos[i][0] = palabra[i];
			System.out.print("Valor de \""+palabra[i]+"\" si se encuentra en el titulo: ");
			datos[i][1] = new Scanner(System.in).nextLine();
			System.out.print("Valor de \""+palabra[i]+"\" si se encuentra en el descripcion: ");
			datos[i][2] = new Scanner(System.in).nextLine();
		}

		analisis(datos);
	}

	/**
	 * Este metodo busca el numero de ocurrencias de una palabra dentro de un titulo
	 * @param palabra - palabra a buscar los titulos
	 * @return total de palabrasa encontrada en los titulos
	 */
	public int totalEnTitulos(String palabra) {
		int totalTitulos = 0;
		ResultSet resultado;
		try {
			PreparedStatement consulta;
			consulta = con.prepareStatement("SELECT titulo FROM Articulos");
			resultado = consulta.executeQuery();
			while(resultado.next()) {
				String titulo = resultado.getString("titulo");
				while (titulo.indexOf (palabra) > -1) {
					titulo = titulo.substring(titulo.indexOf(
							palabra)+palabra.length(),titulo.length());
					totalTitulos++; 
				}
			}			
		} catch (Exception e) {
			System.out.println("Error al analisar los titulos! ");
		}
		return totalTitulos;

	}

	/**
	 * Este metodo busca el numero de ocurrencias de una palabra dentro de una descripcion
	 * @param palabra - palabra a buscar la descripcion
	 * @return total de palabrasa encontrada en la descripcion
	 */
	public int totalEnDescripcion(String palabra) {
		int totalDescripcion = 0;
		ResultSet resultado;
		try {
			PreparedStatement consulta;
			consulta = con.prepareStatement("SELECT descripcion FROM Articulos");
			resultado = consulta.executeQuery();
			while(resultado.next()) {
				String titulo = resultado.getString("descripcion");
				while (titulo.indexOf(palabra) > -1) {
					titulo = titulo.substring(titulo.indexOf(
							palabra)+palabra.length(),titulo.length());
					totalDescripcion++; 
				}
			}			
		} catch (Exception e) {
			System.out.println("Error al analisar las descrpciones! ");
		}
		return totalDescripcion;

	}

	/**
	 * Este metodo realiza un analisis de tendencia
	 * @param datos matriz con los datos a analisaar
	 */
	public void analisis(String[][] datos) {
		
		try {
			for (int i = 0; i < datos.length; i++) {
				String palabraClave = datos[i][0].toString();
				float puntosxtitulo = 0;
				float puntosxdescripcion = 0;
				int totalEnTitulo = new AnalisisTendencia().totalEnTitulos(palabraClave);
				int totalEnDescripcion = new AnalisisTendencia().totalEnDescripcion(palabraClave);

				puntosxtitulo = (Float.parseFloat(datos[i][1])*totalEnTitulo);
				puntosxdescripcion = (Float.parseFloat(datos[i][2])*totalEnDescripcion);
				System.out.println("--------------------------------");
				System.out.println("Puntaje para: "+palabraClave);
				System.out.print("Por titulo: "+puntosxtitulo);
				System.out.println("   | Por Descripcion: "+puntosxdescripcion);

			}
		} catch (NumberFormatException e) {
			System.out.println("\n\nERROR: Verifique que los datos ingresados sean correctos!");
		}
		System.out.println("--------------------------------");
	}


}
