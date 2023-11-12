package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{

	private Point2D position;
	private String name;
	private int layer;
	
	public GameElement(Point2D position, String name, int layer) {
		this.position=position;
		this.name=name;
		this.layer=layer;
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return layer;
	}
	
}
