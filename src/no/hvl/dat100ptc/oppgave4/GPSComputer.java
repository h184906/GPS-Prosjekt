package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;


public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	public double totalDistance() {

		double distance = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			distance += GPSUtils.distance(gpspoints[i-1],gpspoints[i]);
		}

		return distance;
	}

	public double totalElevation() {

		double elevation = 0;
		double forgjeElevation = gpspoints[0].getElevation();

		for (int i = 1; i < gpspoints.length; i++) {
			double nåværendeElevation = gpspoints[i].getElevation();

			if (forgjeElevation < nåværendeElevation) {
				elevation += nåværendeElevation - forgjeElevation;

			}

			forgjeElevation = nåværendeElevation;
			
		}

		return elevation;
		
	}

	public int totalTime() {

		int totalTiden = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			int tidenNå = gpspoints[i].getTime();
        	int tidenForrige = gpspoints[i - 1].getTime();

			totalTiden += tidenNå - tidenForrige;
		}

		return totalTiden;
		
	}
		

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length-1];

		for (int i = 1; i < gpspoints.length; i++) {
			speeds[i-1] = GPSUtils.speed(gpspoints[i-1], gpspoints[i]);
		}


		return speeds;
		
	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		double[] fartArray = speeds();

		for (double c : fartArray) {
			if (maxspeed < c) {
				maxspeed = c;
			}
		}

		return maxspeed;
	
	}

	public double averageSpeed() {

		return totalDistance()/totalTime();

	}


	// conversion factor m/s to miles per hour (mps)
	public static final double MS = 2.23;

	public double kcal(double weight, int secs, double speed) {

		double kcal;

		double met;		
		double speedmph = speed * MS;

		if (speedmph > 20.0) {
			met = 16.0;
		} else if (speedmph >= 16) {
			met = 12.0;
		} else if (speedmph >= 14) {
			met = 10.0;
		} else if (speedmph >= 12) {
			met = 8.0;
		} else if (speedmph >= 10) {
			met = 6.0;
		} else {
			met = 4.0;
		}
		
		kcal = met * weight * ((double) secs / 3600);

		return kcal;
		
	}

	public double totalKcal(double weight) {
		double totalkcal = 0;

		// double speed = averageSpeed();
		// int secs = totalTime();
		// totalkcal = kcal(weight, secs, speed);
	
		for (int i = 1; i < gpspoints.length; i++) {

			int secs = (gpspoints[i].getTime()) - (gpspoints[i - 1].getTime()); 

			double speed = GPSUtils.speed(gpspoints[i-1], gpspoints[i]);
			
			totalkcal += kcal(weight, secs, speed); 
		}
	
		return totalkcal;

	}
	
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {
		String totalTid = GPSUtils.formatTime(totalTime());
		double totaleDistansen = totalDistance();
		double totaleElevation = totalElevation();
		double maxFart = maxSpeed();
		double avgSpeed = averageSpeed();
		double totalKalorier = totalKcal(WEIGHT);

		System.out.println("==============================================");
		System.out.printf("Total Time      : %11s\n", totalTid);
		System.out.printf("Total distance  : %11.2f km\n", totaleDistansen);
		System.out.printf("Total elevation : %11.2f m\n", totaleElevation);
		System.out.printf("Max speed       : %11.2f km/t\n", maxFart);
		System.out.printf("Average speed   : %11.2f km/t\n", avgSpeed);
		System.out.printf("Energy          : %11.2f kcal\n", totalKalorier);
		System.out.println("==============================================");
		
	}

}
