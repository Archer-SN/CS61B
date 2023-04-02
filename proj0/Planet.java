public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	String imgFileName;

	public static double G = 6.67e-11;
	
	public Planet(double xP, double yP, double xV,
				  double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	/** Calculates distance between two celestial objects
	 * We break the distance into x and y
	 * We then use the formula r = sqrt(x^2 + y^2) to find the distance
	 */
	public double calcDistance(Planet b) {
		Double xDistance = b.xxPos - xxPos;
		Double yDistance = b.yyPos - yyPos;

		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}

	public double calcForceExertedBy(Planet b) {
		double r = calcDistance(b);
		double force = G * mass * b.mass / Math.pow(r, 2);

		return force;  
	}

	/**	Calculates the Force exerted by b on the x component
	 * Fx = Fcosθ -> Fx = F(dx/r)
	 */
	public double calcForceExertedByX(Planet b) {
		double force = calcForceExertedBy(b);
		double xDistance = b.xxPos - xxPos;
		double distance = calcDistance(b);

		// Returns Force in the x direction
		return force * xDistance / distance;
	}

	/**	Calculates the Force exerted by b on the y component
	 * Fy = Fsinθ -> Fy = F(dy/r)
	 */
	public double calcForceExertedByY(Planet b) {
		double force = calcForceExertedBy(b);
		double yDistance = b.yyPos - yyPos;
		double distance = calcDistance(b);

		// Returns Force in the x direction
		return force * yDistance / distance;
	}

	/** Calculates the net force on the x direction acting upon this body */
	public double calcNetForceExertedByX(Planet[] bodies) {
		double netForceX = 0;
		for (Planet b : bodies) {
			if (!this.equals(b)) {
				netForceX = netForceX + calcForceExertedByX(b);
			}
		}
		return netForceX;
	}

	/** Calculates the net force on the y direction acting upon this body */
	public double calcNetForceExertedByY(Planet[] bodies) {
		double netForceY = 0;
		for (Planet b : bodies) {
			if (!this.equals(b)) {
				netForceY = netForceY + calcForceExertedByY(b);
			}
		}
		return netForceY;
	}

	public void update(double dt, double fX, double fY) {
		double aX = fX / mass;
		double aY = fY / mass;

		// Updates the current body's velocity
		xxVel = xxVel + aX * dt;
		yyVel = yyVel + aY * dt;

		// Updates the current body's position
		xxPos = xxPos + xxVel * dt;
		yyPos = yyPos + yyVel * dt;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}