package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public interface HoleElement {

	public void activate(GameElement elem, Point2D pos, Vector2D v);
	
}
