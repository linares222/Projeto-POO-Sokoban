package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement {

    private String imageName = "Empilhadora_D";
    private int energyPoints = 100;
    private int moves = 0;
    private Direction newDirection = Direction.DOWN;

    public Empilhadora(Point2D initialPosition) {
        super(initialPosition, "Empilhadora_D", 1);
    }

    public int getEnergyPoints() {
        return energyPoints;
    }

    public void setEnergyPoints(int energyPoints) {
        this.energyPoints = energyPoints;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getMoves() {
        return moves;
    }

    public String getImageName() {
        return imageName;
    }

    public void move(int keyCode) {
    	
    	Direction d = Direction.directionFor(keyCode);
		Point2D newPos = getPosition().plus(d.asVector());
			if(GameEngine.getInstance().checkBounds(newPos)==false) {
			        if (keyCode == KeyEvent.VK_UP) {
			            newDirection = Direction.UP;
			            setName("Empilhadora_U");
			        }
			        if (keyCode == KeyEvent.VK_DOWN) {
			            newDirection = Direction.DOWN;
			            setName("Empilhadora_D");
			        }
			        if (keyCode == KeyEvent.VK_LEFT) {
			            newDirection = Direction.LEFT;
			            setName("Empilhadora_L");
			        }
			        if (keyCode == KeyEvent.VK_RIGHT) {
			            newDirection = Direction.RIGHT;
			            setName("Empilhadora_R");
			        }
			
			        Point2D newPosition = getPosition().plus(newDirection.asVector());
			        if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10) {
			            setPosition(newPosition);
			        }
			}
    }
}
