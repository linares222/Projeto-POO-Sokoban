package pt.iscte.poo.sokobanstarter;


import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement{
private Point2D Point2D;
	
	public Parede(Point2D Point2D){
		super(Point2D, "Parede", 1);
		this.Point2D= Point2D;
	}
	
	@Override
	public String getName() {
		return "Parede";
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
