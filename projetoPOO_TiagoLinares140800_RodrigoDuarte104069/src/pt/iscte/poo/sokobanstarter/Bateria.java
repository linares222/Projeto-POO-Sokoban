package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement {
	Point2D position;

	public Bateria(Point2D position) {
		super(position, "Bateria", 1);
		this.position= position;
	}
	@Override
	public String getName() {
		return "Bateria";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	
}
