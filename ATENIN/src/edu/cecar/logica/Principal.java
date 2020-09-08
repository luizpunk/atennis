/**
 * Clase principal para ejecutar
 */
package edu.cecar.logica;

import java.util.Scanner;

import edu.cecar.persistencia.GestionarArticulos;
import edu.cecar.persistencia.GestionarURLs;

public class Principal {
	
	/**
	 * Este metodo muestra el menu para comprobar si hay datos descargados o no
	 */
	public void menuDescargar() {
		if (GestionarURLs.hayQueDescargar() == 0) {
			Scanner tc = new Scanner(System.in);
			System.out.println("-----------------------------------------------");
			System.out.print("Ingrese las URL de las paginas separadas por , :");
			String urls = tc.nextLine();
			System.out.println("Descargando URLs...");
			new GestionarURLs().guardarURL(urls);
			System.out.println("URLs descargadas!");
			new GestionarURLs().consultarURLs();
			System.out.println("Scrapiando las URLs");
			new Scraping().scrapearDiarioADN();
			new Scraping().scrapearPeriodicoDeHoy();
			System.out.println("Todas las URLs finalizado!");
		}else{
			menuCRUDUrls();
		}
	}
	
	
	public void menuCRUDUrls() {
		Scanner tc = new Scanner(System.in);
		System.out.println("-----------------------------");
		System.out.println("QUE DESEA HACER?\n0: Ver URLs guardadas\n1: Agregar nueva URL\n2: Acualizar URL"
				+ "\n3: Eliminar URL\n4: Analisis de tendencia\n5: Salir");
		System.out.print("Ingrese una opcion:");
		int opcion = tc.nextInt();
		switch (opcion) {
		
		case 0:
			new GestionarURLs().consultarURLs();
			menuCRUDUrls();
			break;
		case 1:
			System.out.print("Ingrese la(s) nuevas URLs a guardar: ");
			String urls = new Scanner(System.in).nextLine();
			new GestionarURLs().guardarURL(urls);
			menuCRUDUrls();
			break;
		case 2:
			System.out.print("Ingrese ID de URL a Actualizar: ");
			int id = new Scanner(System.in).nextInt();
			System.out.print("Nuevo valor para la URL: ");
			String url = new Scanner(System.in).nextLine();
			new GestionarURLs().modificarURL(id, url);
			System.out.println("Valor Actualizado");
			menuCRUDUrls();
			break;
		case 3:
			System.out.print("Ingrese ID de URL a Eliminar: ");
			int _id = new Scanner(System.in).nextInt();
			new GestionarURLs().eliminarURL(_id);
			menuCRUDUrls();
		case 4:
			AnalisisTendencia analisisTendencia = new AnalisisTendencia();
			System.out.println("---------------------------");
			System.out.println("ANALISIS DE TENDENCIA");
			System.out.println("---------------------------");
			System.out.print("Ingrese palabras separadas por ,: ");
			String palabras = new Scanner(System.in).nextLine();
			analisisTendencia.capturarDatos(palabras);
			menuCRUDUrls();
			
		case 5:
			System.out.println("Programa Finalizado!");
			System.exit(0);
			break;
		default:
			System.out.println("Opcion invalida!");
			System.exit(0);
			break;
		}
		
	}
	
	public static void main(String[] args) {
		Principal principal = new Principal();
		System.out.println("-----------------------------------------------");
		System.out.println("|  ATENIN (ANALISIS DE TENDENCIA INFORMATIVA)  |");
		System.out.println("-----------------------------------------------");
		principal.menuDescargar();
		
		
	}

}
