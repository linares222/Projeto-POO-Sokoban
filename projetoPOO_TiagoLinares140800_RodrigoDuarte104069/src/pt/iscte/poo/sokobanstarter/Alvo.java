package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement{
	
	private Point2D Point2D;
	
	public Alvo(Point2D Point2D){
		super(Point2D, "Alvo", 1);
		this.Point2D= Point2D;
	}
	
	@Override
	public String getName() {
		return "Alvo";
	}

	@Override
	public Point2D getPosition() {
		return Point2D;
	}

	@Override
	public int getLayer() {
		return 1;
	}

}
