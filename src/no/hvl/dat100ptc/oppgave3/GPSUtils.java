package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min = da[0];

		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}

		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double [] latitudeArray = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {
			latitudeArray[i] = gpspoints[i].getLatitude();
		}
		return latitudeArray;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		
		double [] longitudeArray = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {
			longitudeArray[i] = gpspoints[i].getLongitude();
		}
		return longitudeArray; 

	}

	private static final int R = 6371000; 

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		// double d;
		double latitude1, longitude1, latitude2, longitude2;

		latitude1 = toRadians(gpspoint1.getLatitude());
		latitude2 = toRadians(gpspoint2.getLatitude());
		longitude1 = toRadians(gpspoint1.getLongitude());
		longitude2 = toRadians(gpspoint2.getLongitude());

		double deltaLat = latitude2 - latitude1;
		double deltaLong = longitude2 - longitude1;

		double a = compute_a(latitude1, latitude2, deltaLat, deltaLong);
		double c = compute_c(a);

		return R * c;
	}
	
	// dårlige varaibelnavn for å forstå hva koden ber om, oppdatert til bedre
	private static double compute_a(double latitude1, double latitude2, double deltaLatetude, double deltaLongitude) {
		
		return pow(sin(deltaLatetude / 2), 2) + cos(latitude1) * cos(latitude2) * pow(sin(deltaLongitude / 2), 2);
 
	}

	private static double compute_c(double a) {

		return 2 * atan2(sqrt(a), sqrt(1 - a));

	}

	
	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		double distanse = distance(gpspoint1, gpspoint2);
		secs = (gpspoint2.getTime() - gpspoint1.getTime());

		speed = distanse / secs;
		return speed;
	}

	public static String formatTime(int secs) {

		String timestr = "  ";
		String TIMESEP = ":";

		int timer = secs/3600;
		secs %= 3600;
		int minutter = secs/60;
		secs %= 60;

		timestr += String.format("%02d",timer) + TIMESEP + String.format("%02d", minutter) + TIMESEP + String.format("%02d", secs);

		return timestr;
		
	}
	
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {
		d = Math.round(d * 100.0) / 100.0; 
		String str = String.format("%.2f", d).replace(",", "."); 
		String result = "";
	
		
		if (str.length() < TEXTWIDTH) { 
			for (int i = 0; i < (TEXTWIDTH - str.length()); i++) {
				result += " "; 
			}
		}
	
		result += str; 
		
		return result; 
	}
	
}
