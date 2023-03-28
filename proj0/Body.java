public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	String imgFileName;

	public static double G = 6.67e-11;
	
	public Body(double xP, double yP, double xV,
              double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
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
	public double calcDistance(Body b) {
		Double xDistance = b.xxPos - xxPos;
		Double yDistance = b.yyPos - yyPos;

		return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
	}

	public double calcForceExertedBy(Body b) {
		double r = calcDistance(b);
		double force = G * mass * b.mass / Math.pow(r, 2);

		return force;  
	}

	/*	Calculates the Force exerted by b on the x component
		Fx = Fcosθ -> Fx = F(dx/r)
	*/
	public double calcForceExertedByX(Body b) {
		double force = calcForceExertedBy(b);
		double xDistance = b.xxPos - xxPos;
		double distance = calcDistance(b);

		// Returns Force in the x direction
		return force * xDistance / distance;
	}

	/*	Calculates the Force exerted by b on the y component
		Fy = Fsinθ -> Fy = F(dy/r)
	*/
	public double calcForceExertedByY(Body b) {
		double force = calcForceExertedBy(b);
		double yDistance = b.yyPos - yyPos;
		double distance = calcDistance(b);

		// Returns Force in the x direction
		return force * yDistance / distance;
	}
}