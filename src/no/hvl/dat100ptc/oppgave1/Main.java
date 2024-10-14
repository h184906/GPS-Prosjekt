package no.hvl.dat100ptc.oppgave1;

public class Main {

	public static void main(String[] args) {
		
		GPSPoint punkt1 = new GPSPoint(1, 2.0, 3.0, 5.0);
		
		punkt1.getTime();
		punkt1.setTime(2);

		punkt1.toString();
	}

}
