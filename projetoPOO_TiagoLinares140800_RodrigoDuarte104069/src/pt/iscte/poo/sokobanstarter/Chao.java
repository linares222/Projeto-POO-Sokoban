package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Chao extends GameElement{

	private Point2D Point2D;
	
	public Chao(Point2D Point2D){
		super(Point2D, "Chao", 0);
		this.Point2D= Point2D;
	}
	
	@Override
	public String getName() {
		return "Chao";
	}

	@Override
	public Point2D getPosition() {
		return Point2D;
	}

	@Override
	public int getLayer() {
		return 0;
	}

}
