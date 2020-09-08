package edu.cecar.test;

import java.util.Calendar;
import java.util.Date;

public class prueba {
public static void main(String[] args) {
	Date fecha = new Date();
	Calendar c1 = Calendar.getInstance();
	System.out.println((c1.get(Calendar.MONTH)+1)+"-"+c1.get(Calendar.DAY_OF_MONTH)+c1.get(Calendar.YEAR));
}
}
