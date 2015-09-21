package entities;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player extends Boxer {

	private static int inUseController = -1;
	private Controller controller;
	boolean lHeld;
	boolean rHeld;

	public Player(Vector2 position) {
		super(position);
		lHeld = false;
		rHeld = false;
		setController();
	}
	
	private void setController() {
		System.out.println("Setting up controller for player " + position.x);
		Array<Controller> controllers = Controllers.getControllers();
		System.out.println(controllers.size);
		for (int i = 0; i < controllers.size; i++) {
			boolean isGamepad = controllers.get(i).getName().toLowerCase()
					.contains("gamepad");
			boolean isController = controllers.get(i).getName().toLowerCase()
					.contains("controller");
			boolean inUse = inUseController == i;
			if (inUse) System.out.println("Controller " + i + " is in use.");
			if (isGamepad || isController && !inUse) {
				controller = controllers.get(i);
				inUseController = i;
				System.out.println("Controller set to: " + controller.getName() + ": " + i);
				break;
			}
		}
		if (controller == null)	System.out.println("No controller available");
	}
	
	public void getControllerInput() {

		float l = controller.getAxis(1);
		float r = controller.getAxis(0);
		float x = controller.getAxis(2);
		float y = controller.getAxis(3);
		float a = controller.getAxis(4);

		if (y < -0.2) { // UP
			move(0, y);
		}

		if (y > 0.2) { // DOWN
			move(0, y);
		}

		if (x < -0.2) { // LEFT
			move(-x, 0);
		}

		if (x > 0.2) { // RIGHT
			move(-x, 0);
		}

		if (a < -0.2) {
			rotate(-a);
		}

		if (a > 0.2) {
			rotate(-a);
		}

		if (l > 0.7) { // && r < 0.7) {
			if (!lHeld)
				leftGlove.punch();
			lHeld = true;
		} else {
			lHeld = false;
		}

		if (r > 0.7) { // && l < 0.7) {
			if (!rHeld)
				rightGlove.punch();
			rHeld = true;
		} else {
			rHeld = false;
		}
	}

}