package edu.cecar.test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;

import edu.cecar.logica.AnalisisTendencia;

public class AnalisisTendenciaTest {

	@Test
	public void testTotalEnTitulos() {
		int resultado = new AnalisisTendencia().totalEnTitulos("Messi"); //solo hay un 'Messi'
		int esperado = 1;
		assertEquals(esperado, resultado);
	}
	@Test
	public void testTotalEnDescripcion() {
		int resultado = new AnalisisTendencia().totalEnDescripcion("Messi"); //solo hay 12 'Messi'
		int esperado = 12;
		assertEquals(esperado, resultado);
	}

	@Test
	public void testAnalisis() {
		String datos[][] = {{"Messi","10","5"},{"Barcelona","10","5"}};
		try {
			new AnalisisTendencia().analisis(datos);
		} catch (Exception e) {
			fail("Se lanca una Excepcion");
			// TODO: handle exception
		}		
	}

}
