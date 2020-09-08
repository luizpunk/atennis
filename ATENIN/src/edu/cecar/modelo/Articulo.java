/**Clase principal para generar objetos de tipo Articulos screpedos de las
 * las paginas web selecionadas
 * @author Luis Perez
 * @version 1.0
 */
package edu.cecar.modelo;

public class Articulo {
	private String titulo;
	private String descripcion;
	private String fecha;
	
	public Articulo() {
		this.titulo = null;
		this.descripcion = null;
		this.fecha = null;
	}
	
	public Articulo(String titulo, String descripcion, String fecha) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
