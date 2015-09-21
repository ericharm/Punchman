package entities;

import handlers.MathFormulas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Boxer extends Actor {
	
	public Glove leftGlove;
	public Glove rightGlove;
	int health = 100;

	public Boxer(Vector2 position) {
		super(position);
		setRadius(28.0f);
		setSpeed(1.75f);
		setRotationSpeed(0.04f);

		leftGlove = new Glove(true);
		rightGlove = new Glove(false);

		setSprite(new Sprite(new Texture("head.png")));
	}
	
	@Override
	public void update() {
		super.update();
		leftGlove.update(position, getRadius(), getAngle(), this);
		rightGlove.update(position, getRadius(), getAngle(), this);
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	@Override
	public void render(ShapeRenderer shapeRenderer, SpriteBatch sb) {
		super.render(shapeRenderer, sb);
		leftGlove.render(shapeRenderer, sb);
		rightGlove.render(shapeRenderer, sb);
	}

	@Override
	public void move(float x, float y) {
		super.move(x, y);
		leftGlove.move(x, y);
		rightGlove.move(x, y);
	}

	@Override
	public void rotate(float a) {
		super.rotate(a);
		leftGlove.rotate(a);
		rightGlove.rotate(a);
	}
	
	public boolean checkCollision(Boxer opponent) {
		Vector2 position1 = position;
		Vector2 position2 = opponent.leftGlove.position;
		Vector2 position3 = opponent.rightGlove.position;
		float r1 = getRadius();
		float r2 = opponent.leftGlove.getRadius();
		float r3 = opponent.leftGlove.getRadius();
		boolean leftColliding = MathFormulas.circleCollidingWithCircle(position1, r1, position2, r2);
		boolean rightColliding = MathFormulas.circleCollidingWithCircle(position1, r1, position3, r3);
		return (leftColliding || rightColliding);
	}

	public void modifyHealth(int i) {
		health += i;	
	}
	
	public int getHealth() {
		return health;
	}
}