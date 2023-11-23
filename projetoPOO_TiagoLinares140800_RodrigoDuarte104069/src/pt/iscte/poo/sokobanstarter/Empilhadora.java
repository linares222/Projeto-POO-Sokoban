package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Empilhadora extends GameElement implements Movable {

    private String imageName = "Empilhadora_D";
    private int energyPoints = 100;
    private int moves = 0;
    private Direction newDirection = Direction.DOWN;

    public Empilhadora(Point2D initialPosition) {
        super(initialPosition, "Empilhadora_D", 2);
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


	
	public void handleChangeDirection(int keyCode) {
		Direction d = Direction.directionFor(keyCode);
		if (keyCode == KeyEvent.VK_UP) {
    	    setName("Empilhadora_U");
    	}
    	if (keyCode == KeyEvent.VK_DOWN) {
    	    setName("Empilhadora_D");
    	}
    	if (keyCode == KeyEvent.VK_LEFT) {          
    	    setName("Empilhadora_L");
    	}
    	if (keyCode == KeyEvent.VK_RIGHT) {
    	    setName("Empilhadora_R");
    	}
    	Point2D oldPosition = getPosition();
    	move(oldPosition, d.asVector());
	}

	@Override
	public Boolean isMovable(Point2D p, Vector2D v) {
		if(GameEngine.getInstance().checkBounds(p,v)){
			return false;
		}
		return true;
	}


	@Override
	public void move(Point2D position, Vector2D v) {

    	
    	if (isMovable(position, v)){
    	
    	Point2D newPosition = getPosition().plus(v);

    	if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10) {
    		if(isMovable(position, v)){
    			setPosition(newPosition);
    			moves++;
    			energyPoints--;
				System.out.println(energyPoints);
    			
    		}
    	    
    	}
    }
		
	}


}
