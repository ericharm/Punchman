package states;

import handlers.GameStateManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ericharm.Punchman.Game;

import entities.Boxer;
import entities.Player;

public class Play extends GameState {

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private Player player;
	private Boxer opponent;

	public Play(GameStateManager gsm) {
		super(gsm);

		player = new Player(new Vector2(10, 10));
		opponent = new Boxer(new Vector2(20, 20));

		player.setPosition(new Vector2(300, 100));
		opponent.setPosition(new Vector2(100, 100));

		shapeRenderer = new ShapeRenderer();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
	}

	@Override
	public void handleInput() {
	}

	@Override
	public void update(float dt) {
		camera.update();

		player.update();
		opponent.update();

		player.getControllerInput();
//		opponent.getControllerInput();

		if (player.checkCollision(opponent)) {
			player.modifyHealth(-1);
			System.out.println("P1: " + player.getHealth() + " P2: " + opponent.getHealth());
		}
		
		if (opponent.checkCollision(player)) {
			opponent.modifyHealth(-1);
			System.out.println("P1: " + player.getHealth() + " P2: " + opponent.getHealth());
		}
	}

	@Override
	public void render() {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.render(shapeRenderer, sb);
		opponent.render(shapeRenderer, sb);
	}

	@Override
	public void dispose() {
	}
}
