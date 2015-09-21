package entities;

import handlers.Easers;
import handlers.MathFormulas;
import handlers.MoveTimer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class CopyOfGlove extends Actor {

//	public boolean punching;
	public boolean receding;
//	private long punchingStartTime;
//	private int punchingDuration;
	private long recedingStartTime;
	private int recedingDuration;
	private boolean left;
//	private float punchDistance;
	private float recedingDistance;
	private Vector2 currentPunchLocation;
	private MoveTimer punchTimer;

	public CopyOfGlove(boolean left) {
		super();
		position = new Vector2(0f, 0f);
		setRadius(12);
//		punchDistance = 40;
//		punchingDuration = 280;
		recedingDistance = 40;
		recedingDuration = 400;
		setRotationSpeed(0.04f);
//		punching = false;
		this.left = left;
		
		punchTimer = new MoveTimer(false, -1l, 280, 40);
		
		if (left) setSprite(new Sprite(new Texture("leftGlove.png")));
		else setSprite(new Sprite(new Texture("rightGlove.png")));
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setPunching(boolean punching) {
//		this.punching = punching;
		punchTimer.setActing(true);
		updateSpritePosition();
	}

	public void update(Vector2 playerPosition, float playerRadius, float playerAngle, Boxer player) {
		super.update();
		float x = playerPosition.x;
		float y = playerPosition.y;
		float r = playerRadius * 1.55f;
		long timePassed;
		float resultRadius;
		float punchAngle;
		float hookAngle = (float) -(Math.PI / 2);
		
		float angleDifference = (float) Math.PI * 1 / 5;
		float targetAngle = (float) Math.PI * 1/8;
		punchAngle = playerAngle + targetAngle;
		if (!left) {
			angleDifference *= -1;
			punchAngle = playerAngle - targetAngle;
			hookAngle *= -1;
		} 
		
		resultRadius = r;
		Vector2 restLocation = MathFormulas.pointAlongCircumference(x,y, resultRadius, playerAngle - angleDifference);

//		if (punching) {
		if (punchTimer.acting) {
//			timePassed = System.currentTimeMillis() - punchingStartTime;
			timePassed = System.currentTimeMillis() - punchTimer.startTime;
//			resultRadius = Easers.easeInBack(timePassed, 0, punchDistance, punchingDuration, 1.7f);	
			resultRadius = Easers.easeInBack(timePassed, 0, punchTimer.distance, punchTimer.duration, 1.7f);
			
//			float headTurn = Easers.easeInBack(timePassed, 0, (float) Math.PI / 16, punchingDuration, 3.5f);
			float headTurn = Easers.easeInBack(timePassed, 0, (float) Math.PI / 16, punchTimer.duration, 3.5f); 
			
			if (!left) headTurn *= -1;
			player.getSprite().setRotation(MathFormulas.radiansToDegrees(playerAngle + headTurn));
//			if (System.currentTimeMillis() >= punchingStartTime + punchingDuration) {
			if (System.currentTimeMillis() >= punchTimer.startTime + punchTimer.duration) {
//				punching = false;
				punchTimer.setActing(false);
				receding = true;
				recedingStartTime = System.currentTimeMillis();

			} else {
				currentPunchLocation = MathFormulas.pointAlongCircumference(restLocation.x, restLocation.y, resultRadius, punchAngle);
//				if (System.currentTimeMillis() >= punchingStartTime + punchingDuration/2){
				if (System.currentTimeMillis() >= punchTimer.startTime + punchTimer.duration/2) {
					
					float t_float = Easers.easeInSine(timePassed, 0, 7, punchTimer.duration/2);
//					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, Easers.easeInSine(timePassed, 0, 7, punchingDuration/2), punchAngle + hookAngle);
					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, t_float, punchAngle + hookAngle);
					currentPunchLocation.add(hookVector);
				} else{
//					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, Easers.easeOutSine(timePassed, 7, 0, punchingDuration/2), punchAngle + hookAngle);
					float x_float = Easers.easeOutSine(timePassed, 7, 0, punchTimer.duration/2);
					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, x_float, punchAngle + hookAngle);
					currentPunchLocation.add(hookVector);
				}
			}
		}
		
		if (receding) {
			timePassed = System.currentTimeMillis() - recedingStartTime;
			resultRadius = recedingDistance - Easers.easeInSine(timePassed, 0, recedingDistance, recedingDuration);

//			float headTurn = Easers.easeInSine(timePassed, (float) Math.PI / 16, 0, punchingDuration);
			float headTurn = Easers.easeInSine(timePassed, (float) Math.PI / 16, 0, punchTimer.duration);
			if (!left) headTurn *= -1;
			player.getSprite().setRotation(MathFormulas.radiansToDegrees(playerAngle + headTurn));
			
			if (System.currentTimeMillis() >= recedingStartTime + recedingDuration) {
				receding = false;
				resultRadius = r;
				currentPunchLocation = restLocation;
				player.getSprite().setRotation(MathFormulas.radiansToDegrees(playerAngle));
			} else {
				currentPunchLocation = MathFormulas.pointAlongCircumference(restLocation.x, restLocation.y, resultRadius, punchAngle);
				if (System.currentTimeMillis() >= punchTimer.startTime + punchTimer.duration/2) {
//				if (System.currentTimeMillis() >= punchingStartTime + punchingDuration/2){
					float r_float = Easers.easeOutSine(timePassed, 0, 3, punchTimer.duration/2);
					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, r_float, punchAngle + hookAngle);
//					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, Easers.easeOutSine(timePassed, 0, 3, punchingDuration/2), punchAngle + hookAngle);
					currentPunchLocation.add(hookVector);
				} else{
					float i_float = Easers.easeInBack(timePassed, 3, 0, punchTimer.duration/2, 1.7f);
					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, i_float, punchAngle + hookAngle);
//					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, Easers.easeInBack(timePassed, 3, 0, punchingDuration/2, 1.7f), punchAngle + hookAngle);
					currentPunchLocation.add(hookVector);
				}
			}
		}
		
//		if (!punching && !receding) {
		if (!punchTimer.acting && !receding) {
			resultRadius = 0;
			currentPunchLocation = restLocation;
			
		}

		setPosition(currentPunchLocation);
	}

	public void punch() {
//		if (!punching && !receding) {
		if (!punchTimer.acting && !receding) {
			System.out.println("NOT PUNCHING NOT RECEDING");
//			punching = true;
//			punchingStartTime = System.currentTimeMillis();
			punchTimer.setActing(true);
			punchTimer.startTime = System.currentTimeMillis();
		}
		System.out.println("PUNCH");
		

	}

}