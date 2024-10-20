package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;


public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		
		xstep = scale(MAPXSIZE, minlon, maxlon);
		ystep = scale(MAPYSIZE, minlat, maxlat);
		
		showRouteMap(MARGIN + MAPYSIZE);

		replayRoute(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	public double scale(int maxsize, double minval, double maxval) {

		double step = maxsize / (Math.abs(maxval - minval));

		return step;
	}

	public void showRouteMap(int ybase) {

		int radius = 5;
		double[] latitudeArray = GPSUtils.getLatitudes(gpspoints);
		double[] longitudeArray = GPSUtils.getLongitudes(gpspoints);
	
		for (int i = 0; i < gpspoints.length; i++) {

			int x = MARGIN + (int) ((longitudeArray[i] - minlon) * xstep);
			int y = ybase - (int) ((latitudeArray[i] - minlat) * ystep);
	
			setColor(137, 137, 137);
			fillCircle(x, y, radius);
	
			if (i > 0) {

				int prevX = MARGIN + (int) ((longitudeArray[i - 1] - minlon) * xstep);
				int prevY = ybase - (int) ((latitudeArray[i - 1] - minlat) * ystep);
	
				double elevationEndring = gpspoints[i].getElevation() - gpspoints[i - 1].getElevation();
	
				if (elevationEndring > 0) {
					setColor(0, 255, 0); 
				} else {
					setColor(255, 0, 0); 
				}
	
				drawLine(prevX, prevY, x, y);
				fillCircle(x, y, radius);
			}
		}
	}
	
	
	

	public void showStatistics() {

		String[] strengArray = gpscomputer.statestikkArray();
		int TEXTDISTANCE = 20;
		int gapNed = 20;


		setColor(0,0,0);
		setFont("Courier",12);
		drawString("==============================================",TEXTDISTANCE, gapNed);

		for (int i = 0; i < gpscomputer.statestikkArray().length; i++) {
			gapNed += 15;
			drawString(strengArray[i], TEXTDISTANCE, gapNed);
		}
		gapNed += 15;

		drawString("==============================================", TEXTDISTANCE, gapNed);
	}

	public void replayRoute(int ybase) {

		setSpeed(10);

		double[] longitudeArray = GPSUtils.getLongitudes(gpspoints);
		double[] latitudeArray = GPSUtils.getLatitudes(gpspoints);
		
		int radius = 7;
		int x = MARGIN + (int) ((longitudeArray[0] - minlon) * xstep);
		int y = ybase - (int) ((latitudeArray[0] - minlat) * ystep);

		setColor(60, 60, 250);
		int sirkel = fillCircle(x, y, radius);
	
	
		for (int i = 1; i < gpspoints.length; i++) {
			int nextX = MARGIN + (int) ((longitudeArray[i] - minlon) * xstep);
			int nextY = ybase - (int) ((latitudeArray[i] - minlat) * ystep);

			moveCircle(sirkel, nextX, nextY);
			pause(100); 
		}
	}
	
	

}
