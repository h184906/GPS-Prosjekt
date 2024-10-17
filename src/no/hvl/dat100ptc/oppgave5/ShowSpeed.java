package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowSpeed extends EasyGraphics {
			
	private static int MARGIN = 50;
	private static int BARHEIGHT = 100; 

	private GPSComputer gpscomputer;
	
	public ShowSpeed() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Speed profile", 
				2 * MARGIN + 
				2 * gpscomputer.speeds().length, 2 * MARGIN + BARHEIGHT);
		
		showSpeedProfile(MARGIN + BARHEIGHT);
	}
	
	public void showSpeedProfile(int ybase) {
		
		int x = MARGIN;

		setColor(0, 150, 50);

		double [] fartArray = gpscomputer.speeds();

		for (int i = 0; i < fartArray.length; i++) {
			double punktHøyde = fartArray[i];

			drawLine(x, ybase, x, (ybase - (int) punktHøyde));
			x += 2;
		}
		
		setColor(255, 0, 0);  
		drawLine(MARGIN, ybase - (int) gpscomputer.averageSpeed(), MARGIN + 2 * fartArray.length, ybase - (int) gpscomputer.averageSpeed());
	}
}
