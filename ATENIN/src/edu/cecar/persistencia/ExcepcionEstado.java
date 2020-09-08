/**
 * Esta clase permite implementar excepciones para
 * comprobar el estado de las URLs
 * @author luis
 */
package edu.cecar.persistencia;

public class ExcepcionEstado extends Exception{
	private int codigo;
	
	/**
	 * Constructor de la clase que recibe el codigo de Error
	 * que puede lanzar la URL
	 * @param codigo
	 */
	public ExcepcionEstado(int codigo) {
		super();
		this.codigo = codigo;
	}
	
	
	/**
	 * Este metodo retorna el mensaje correspondiente
	 * al codigo de error
	 */
	@Override
	public String getMessage() {
		String mensaje = "";
		switch (codigo) {
		case 202:
			mensaje = "URL sin contenido"; break;
		case 301:
			mensaje = "Movido temporalmente"; break;
		case 400:
			mensaje = "Peticion mala";	break;
		case 403:
			mensaje = "No se puede acceder a la URL";	break;
		case 404:
			mensaje = "Pagina no encontrada"; break;
		case 500:
			mensaje = "Se desconoce esa la url"; break;
		case 502:
			mensaje = "error puerta de enlace"; break;
		case 503:
			mensaje = "Siervicio no disponible"; break;
		default:
			if (codigo !=200) {
				mensaje = "Error en la peticion HTTP";
			}
			break;
		}
		return mensaje;
	}
}
