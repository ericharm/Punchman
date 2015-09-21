package entities;

import handlers.Easers;
import handlers.MathFormulas;
import handlers.MoveTimer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Glove extends Actor {

	private boolean left;
	private Vector2 currentPunchLocation;
	private MoveTimer punchTimer;
	private MoveTimer recedeTimer;
	
	public Glove(boolean left) {
		super();
		position = new Vector2(0f, 0f);
		setRadius(12);
		setRotationSpeed(0.04f);
		this.left = left;
		
		punchTimer = new MoveTimer(false, -1l, 280, 40);
		recedeTimer = new MoveTimer(false, 1l, 400, 40);
		
		if (left) setSprite(new Sprite(new Texture("leftGlove.png")));
		else setSprite(new Sprite(new Texture("rightGlove.png")));
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setPunching(boolean punching) {
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

		if (punchTimer.acting) {
			timePassed = System.currentTimeMillis() - punchTimer.startTime;
			resultRadius = Easers.easeInBack(timePassed, 0, punchTimer.distance, punchTimer.duration, 1.7f);
			
			float headTurn = Easers.easeInBack(timePassed, 0, (float) Math.PI / 16, punchTimer.duration, 3.5f); 
			
			if (!left) headTurn *= -1;
			player.getSprite().setRotation(MathFormulas.radiansToDegrees(playerAngle + headTurn));
			if (System.currentTimeMillis() >= punchTimer.startTime + punchTimer.duration) {
				punchTimer.setActing(false);
				recedeTimer.acting = true;
				recedeTimer.startTime = System.currentTimeMillis();

			} else {
				currentPunchLocation = MathFormulas.pointAlongCircumference(restLocation.x, restLocation.y, resultRadius, punchAngle);
				if (System.currentTimeMillis() >= punchTimer.startTime + punchTimer.duration/2) {
					
					float t_float = Easers.easeInSine(timePassed, 0, 7, punchTimer.duration/2);
					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, t_float, punchAngle + hookAngle);
					currentPunchLocation.add(hookVector);
				} else{
					float x_float = Easers.easeOutSine(timePassed, 7, 0, punchTimer.duration/2);
					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, x_float, punchAngle + hookAngle);
					currentPunchLocation.add(hookVector);
				}
			}
		}
		
		if (recedeTimer.acting) {
			timePassed = System.currentTimeMillis() - recedeTimer.startTime;
			float j_float = Easers.easeInSine(timePassed, 0, recedeTimer.distance, recedeTimer.duration);
			resultRadius = recedeTimer.distance - j_float;

			float headTurn = Easers.easeInSine(timePassed, (float) Math.PI / 16, 0, punchTimer.duration);
			if (!left) headTurn *= -1;
			player.getSprite().setRotation(MathFormulas.radiansToDegrees(playerAngle + headTurn));
			
			if (System.currentTimeMillis() >= recedeTimer.startTime + recedeTimer.duration) {
				recedeTimer.acting = false;
				resultRadius = r;
				currentPunchLocation = restLocation;
				player.getSprite().setRotation(MathFormulas.radiansToDegrees(playerAngle));
			} else {
				currentPunchLocation = MathFormulas.pointAlongCircumference(restLocation.x, restLocation.y, resultRadius, punchAngle);
				if (System.currentTimeMillis() >= punchTimer.startTime + punchTimer.duration/2) {
					float r_float = Easers.easeOutSine(timePassed, 0, 3, punchTimer.duration/2);
					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, r_float, punchAngle + hookAngle);
					currentPunchLocation.add(hookVector);
				} else{
					float i_float = Easers.easeInBack(timePassed, 3, 0, punchTimer.duration/2, 1.7f);
					Vector2 hookVector = MathFormulas.pointAlongCircumference(0, 0, i_float, punchAngle + hookAngle);
					currentPunchLocation.add(hookVector);
				}
			}
		}
		
		if (!punchTimer.acting && !recedeTimer.acting) {
			resultRadius = 0;
			currentPunchLocation = restLocation;
			
		}

		setPosition(currentPunchLocation);
	}

	public void punch() {
		if (!punchTimer.acting && !recedeTimer.acting) {
			punchTimer.setActing(true);
			punchTimer.startTime = System.currentTimeMillis();
		}
	}
}