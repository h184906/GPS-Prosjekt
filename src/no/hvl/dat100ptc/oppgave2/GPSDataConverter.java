package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	
	private static int TIME_STARTINDEX = 11; 

	public static int toSeconds(String timestr) {
		
		int secs;
		int hr, min, sec;

		String tiden = timestr.substring(TIME_STARTINDEX, 19);

		String[] splittet = tiden.split(":");
		
		hr = Integer.parseInt(splittet[0]);
		min = Integer.parseInt(splittet[1]);
		sec = Integer.parseInt(splittet[2]);

		secs = sec + min*60 + hr*3600;

		return secs;
		
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {
		double latituden = Double.parseDouble(latitudeStr);
		double lonituden = Double.parseDouble(longitudeStr);
		double elevationen = Double.parseDouble(elevationStr);

		GPSPoint gpspoint = new GPSPoint(toSeconds(timeStr), latituden, lonituden, elevationen);
		return gpspoint;
	}
	
}
