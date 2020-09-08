/**
 * Esta clase permite scrapiar las paginas web almacenadas en la base de datos
 */
package edu.cecar.logica;

import java.io.IOException;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import edu.cecar.modelo.Articulo;
import edu.cecar.persistencia.GestionarArticulos;
import edu.cecar.persistencia.GestionarURLs;

public class Scraping {
	Articulo articulo = new Articulo();
	
	/**
	 * Metodo para obtener el codigo HTML de la pagina web a scrapear
	 * @param url - direccion  de la pagina  web
	 * @return el codigo HTML de la pagina web
	 */
	public static  Document getHTMLPagina(String url) {
		Document html = null;
		try {
			html = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		} catch (Exception e) {
			System.out.println("Error al obtener el codigo HTML de la pagina");
		}
		return html;
	}
	
	/**
	 * Este metodo devuelve la fechas en la pagina Periodico de hoy en formato mm/dd/yyyy
	 * @param fecha - String de la fecha a formatear
	 * @return fechaSalida - String de la fecha en formato mm/dd/yyyy
	 */
	public static String fomatoFechaPeriodicoHoy(String fecha) {
		String fechaSalida = "";
		
		String nombreMeses[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		String dia = fecha.substring(0,2);
		int mes = 0;
		String ano  = fecha.substring(Math.max(0, fecha.length() - 4));
		for (int i = 0; i < nombreMeses.length; i++) {
			if (fecha.contains(nombreMeses[i].toString())) {
				mes = (i+1);
			}
		}
		fechaSalida = mes+"-"+dia+"-"+ano;
		return fechaSalida;
	}
	/**
	 * Este metodo Scrapea en especifico la pagina web del DiarioADN
	 * @param url recibe la URL del sitio web
	 */
	public void scrapearDiarioADN() {
		String url = "https://www.diarioadn.co/";
		Calendar c1 = Calendar.getInstance();
		System.out.println("Scrapiando y guardanso los articulos de Diario ADN...");
		
		Elements articulos = Scraping.getHTMLPagina(url).select("article.noticias_home");
		for (Element links : articulos) {
			
			try {
				String link = links.select("a").attr("abs:href");
				Document htmlNoticia = Jsoup.connect(link).userAgent("Mozilla/5.0").timeout(100000).get();
				
				String titulo = htmlNoticia.select("h1").text();
				String descripcion = htmlNoticia.select("p").text();
				String fechaConsulta = (c1.get(Calendar.MONTH)+1)+"-"+c1.get(Calendar.DAY_OF_MONTH)+"-"+c1.get(Calendar.YEAR);
				
				Articulo articulo = new Articulo(titulo,descripcion,fechaConsulta);
				
				//se guarda en la base de datos
				GestionarArticulos.guadarArticulo(articulo, "diarioadn");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Scraping DiarionADN finalizado!");
	}
	/**
	 * Este metodo que Scrapea en especifico la pagina web de Periodico de Hoy
	 * @param url recibe la URL del sitio web
	 */
	public void scrapearPeriodicoDeHoy() {
		String url = "http://www.periodicodehoy.com/";
		System.out.println("Scrapiando y guardanso los articulos de Periodicodehoy...");
		Elements articulos = Scraping.getHTMLPagina(url).select("div.body");
		for (Element links : articulos) {
			try {
				String link = links.select("a").attr("abs:href");
				Document htmlNoticia = Jsoup.connect(link).userAgent("Mozilla/5.0").timeout(100000).get();
				String titulo = htmlNoticia.select("h1").text();
				String fecha = Scraping.fomatoFechaPeriodicoHoy(htmlNoticia.getElementsByClass("date").text());
				String descripcion = htmlNoticia.select("p").not("p.date").text();
				
				Articulo articulo = new Articulo(titulo,descripcion,fecha);
				//se guarda en la base de datos
				GestionarArticulos.guadarArticulo(articulo, "periodicodehoy");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Scraping a  periodicodehoy finalizado!");
	}
	
}
