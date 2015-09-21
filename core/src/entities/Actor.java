package entities;

import handlers.MathFormulas;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Actor {

	public Vector2 position;
	private float radius;
	private float angle;
	private float speed;
	private float rotationSpeed;
	private Sprite sprite;
	private static ArrayList<Actor> actors = new ArrayList<Actor>();
	public boolean colliding;

	public Actor(Vector2 position) {
		this.position = position;
		setValues();
		actors.add(this);
	}

	public Actor() {
		position = new Vector2(0, 0);
		setValues();
		actors.add(this);
	}

	public void update() {
		updateSpritePosition();
	}

	public void render(ShapeRenderer shapeRenderer, SpriteBatch sb) {
		// shapeRenderer.begin(ShapeType.Line);
		// shapeRenderer.setColor(0.12f, 0.12f, 0.91f, 1.0f);
		// shapeRenderer.circle(position.x, position.y, radius);
		// Vector2 front = MathFormulas.pointAlongCircumference(position.x,
		// position.y, radius, angle);
		// shapeRenderer.line(this.position, front);
		// shapeRenderer.end();
		sb.begin();
		if (sprite != null)
			sprite.draw(sb);
		sb.end();
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		sprite.setOrigin(radius, radius);
	}

	public Sprite getSprite() {
		return sprite;
	}

	public float getAngle() {
		return angle;
	}

	public float getRadius() {
		return radius;
	}

	public boolean collidesWithOtherActor(Actor otherActor) {
		boolean colliding = MathFormulas.circleCollidingWithCircle(position,
				radius, otherActor.position, otherActor.radius);
		return colliding;
	}

	public void setRadius(float r) {
		radius = r;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setRotationSpeed(float rSpeed) {
		rotationSpeed = rSpeed;
	}

	public void move(float x, float y) {
		position.x -= x * speed;
		position.y -= y * speed;

	}

	public void rotate(float i) {
		angle += rotationSpeed * i;
		if (sprite != null)
			sprite.setRotation(MathFormulas.radiansToDegrees(angle));
	}

	public void updateSpritePosition() {
		if (sprite != null)
			sprite.setPosition(position.x - radius, position.y - radius);
	}
	
	private void setValues() {
		radius = 0;
		angle = 0;
		speed = 0;
	}
}