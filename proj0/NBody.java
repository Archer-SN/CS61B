public class NBody {
	/** Reads the radius of the universe from a file
	 * @return radius 
	 */
	public static double readRadius(String filename) {
		In in = new In(filename);
		//Skips the first value which is the number of bodies
		in.readInt();

		return in.readDouble();
	}

	/** Extracts data from a file and turns it into bodies 
	 * @return list of bodies
	 */
	public static Body[] readBodies(String filename) {
		In in = new In(filename);
		int numberOfBodies = in.readInt();
		Body[] bodies = new Body[numberOfBodies];

		// Skips the second value which is the radius
		in.readDouble();


		for (int i = 0; i < numberOfBodies; i++) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			bodies[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
		}

		return bodies;
	}

	public static void drawBodies(Body[] bodies) {
		for (Body b : bodies) {
			b.draw();
		}
	}

	// Runs the universe
	public static void main (String[] args) {
		// Period
		Double T = Double.parseDouble(args[0]);
		// Change in time
		Double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Body[] allBodies = readBodies(filename); 

		// Time elapsed in the universe
		double time = 0;

		// Setting the size of canvas
		StdDraw.setScale(-radius , radius);

		StdDraw.enableDoubleBuffering();

		while (time < T) {
			Double[] xForces = new Double[allBodies.length];
			Double[] yForces = new Double[allBodies.length];

			for (int i = 0; i < allBodies.length; i++) {
				Body currentBody = allBodies[i];
				xForces[i] = currentBody.calcNetForceExertedByX(allBodies);
				yForces[i] = currentBody.calcNetForceExertedByY(allBodies);
			}

			for (int i = 0; i < allBodies.length; i++) {
				Body currentBody = allBodies[i];
				currentBody.update(dt, xForces[i], yForces[i]);
			}

			// Adds the background
			StdDraw.picture(0, 0, "images/starfield.jpg");

			// Draws all the bodies
			drawBodies(allBodies);

			// Show the pictures
			StdDraw.show();
			
			// Pauses the animation for 10 miliseconds
			StdDraw.pause(10);
		}

		
	}
}