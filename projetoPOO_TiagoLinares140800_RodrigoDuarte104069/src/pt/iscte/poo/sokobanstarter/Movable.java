package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public interface Movable {
	public Boolean isMovable(Point2D p, Vector2D v);
	

	void move(Point2D newPosition, Vector2D v);

	
}
