package handlers;

public abstract class Easers {

	public static float easeInSine(float time, float value, float endValue, float duration) {
		double result = -endValue * Math.cos(time/duration * (Math.PI/2)) + endValue + value;
		return (float) result;
	}

	public static float easeInBack(float time, float value, float endValue, float duration, float backAmount) {
		return endValue * (time/=duration)* time *(( backAmount + 1) * time - backAmount) + value;
	}
	
	public static float easeOutSine(float time, float b, float c, float d) {
		double result = c * Math.sin(time/d * (Math.PI/2)) + b;
		return (float) result;
	}
	
}