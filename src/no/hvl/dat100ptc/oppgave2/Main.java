package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class Main {

	
	public static void main(String[] args) {

		GPSPoint punkt1 = new GPSPoint(5, 2.0, 66.0, 9.0);
		GPSPoint punkt2 = new GPSPoint(1, 23.0, 26.0, 91.0);
		GPSData daten = new GPSData(2);
		daten.insertGPS(punkt1);
		daten.insertGPS(punkt2);
		
		daten.print();
	}
}
