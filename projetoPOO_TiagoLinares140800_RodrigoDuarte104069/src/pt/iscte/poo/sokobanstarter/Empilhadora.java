package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Empilhadora extends GameElement implements Movable {

    private String imageName = "Empilhadora_D";
    private int energyPoints = 100;
    private int moves = 0;
    private boolean hasMartelo = false; 
    
    
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
	public boolean getHasMartelo() {
		return hasMartelo;
	}

	public void setHasMartelo(boolean hasMartelo) {
		this.hasMartelo = hasMartelo;
	}



	
	public void handleChangeDirection(int keyCode) {
		Direction d = Direction.directionFor(keyCode);
		 switch (keyCode) {
	        case KeyEvent.VK_UP:
	            setName("Empilhadora_U");
	            break;
	        case KeyEvent.VK_DOWN:
	            setName("Empilhadora_D");
	            break;
	        case KeyEvent.VK_LEFT:
	            setName("Empilhadora_L");
	            break;
	        case KeyEvent.VK_RIGHT:
	            setName("Empilhadora_R");
	            break;
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
    	
    	Point2D oldPosition = getPosition();
    	Point2D newPosition = getPosition().plus(v);
    

    	if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10) {
    		List<GameElement> elems = instancia.getElemsInPos(newPosition);
    		setPosition(newPosition);
    		moves++;
			energyPoints--;
		
    		for(GameElement elem: elems) {
    			
    			if(elem instanceof PushableElement) {		
    				((PushableElement) elem).move(newPosition, v);
    			}
    			if(elem instanceof ExtraElement) {
    				((ExtraElement) elem).activate();
    			}

    			if(elem instanceof HoleElement) {
    				((HoleElement)elem).activate(this,oldPosition, v);
    			}
    		}	
    		instancia.updateEnergyBar(energyPoints);
    			if(energyPoints==0) {
    				instancia.addScore(moves);
    				instancia.endGame(instancia.getLevel());
    			}
    			
    	   
    	}
    }
		
	}



}
