package handlers;

import com.badlogic.gdx.math.Vector2;

public abstract class MathFormulas {

	public static Vector2 pointAlongCircumference(float xOrigin, float yOrigin, float radius, float angle) {
	    double x = xOrigin + radius * Math.cos(angle);
	    double y = yOrigin + radius * Math.sin(angle);
		return new Vector2((float) x, (float) y);
	}
	
	public static float radiansToDegrees(float radians) {
		float converter = 180 / (float) Math.PI;
		float degrees = radians * converter;
		return degrees;
	}
	
	public static boolean circleCollidingWithCircle(Vector2 position1, float r1, Vector2 position2, float r2) {
		r1 += 1;
		r2 += 2;
		float xDistance = (float) Math.pow(position2.x-position1.x, 2);
		float yDistance = (float) Math.pow(position1.y - position2.y, 2);
		float rDistance = (float) Math.pow(r1 + r2, 2);
		return xDistance + yDistance <= rDistance;
	}
}
