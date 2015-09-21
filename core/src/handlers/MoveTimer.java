package handlers;

public class MoveTimer {

	public boolean acting;
	public long startTime;
	public int duration;
	public float distance;
	
	public MoveTimer(boolean acting, long startTime, int duration, float distance) {
		this.acting = acting;
		this.startTime = startTime;
		this.duration = duration;
		this.distance = distance;
	}

	public void setActing(boolean acting) {
		this.acting = acting;
	}
}
