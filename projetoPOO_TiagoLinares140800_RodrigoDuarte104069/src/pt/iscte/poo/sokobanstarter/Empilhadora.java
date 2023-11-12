package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.util.Random;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Empilhadora implements ImageTile{

	private Point2D position;
	private String imageName;
	private int energyPoints=100;
	private int moves=0;
	private Direction newDirection=Direction.DOWN;
	
	public Empilhadora(Point2D initialPosition){
		position = initialPosition;
		imageName = "Empilhadora_D";
	}
	
	public int getEnergyPoints() {
		return energyPoints;
	}
	public void setEnergyPoints(int energyPoints) {
		this.energyPoints=energyPoints;
	}
	
	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getMoves() {
		return moves;
	}
	
	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 1;
	}

public void move(int keyCode) {

		// Gera uma direcao aleatoria para o movimento
//		Direction[] possibleDirections = Direction.values();
//		Random randomizer = new Random();
//		int randomNumber = randomizer.nextInt(possibleDirections.length);
//		Direction randomDirection = Direction.DOWN;
		
		if(keyCode==KeyEvent.VK_UP) {
			newDirection = Direction.UP;	
			imageName="Empilhadora_U";
		}
		if(keyCode==KeyEvent.VK_DOWN) {
			newDirection = Direction.DOWN;
			imageName="Empilhadora_D";
			
		}
		if(keyCode==KeyEvent.VK_LEFT) {
			newDirection = Direction.LEFT;
			imageName="Empilhadora_L";
		}
		if(keyCode==KeyEvent.VK_RIGHT) {
			newDirection = Direction.RIGHT;
			imageName="Empilhadora_R";
		}
		
		// Move segundo a direcao gerada, mas so' se estiver dentro dos limites
		Point2D newPosition = position.plus(newDirection.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && 
			newPosition.getY()>=0 && newPosition.getY()<10 )
			{
			position = newPosition;
			setMoves(getMoves()+1);
			setEnergyPoints(getEnergyPoints()-1);
		}
	}
		
		
}