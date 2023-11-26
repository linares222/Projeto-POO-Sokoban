package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Palete extends GameElement implements Movable {
	public GameEngine instancia = GameEngine.getInstance();
	public Palete(Point2D Point2D){
		super(Point2D, "Palete", 1);
	}

	@Override
	public Boolean isMovable(Point2D p, Vector2D v) {
		if(GameEngine.getInstance().checkBounds(p,v)==false){
			return true;
		}
		return false;
	}

	@Override
	public void move(Point2D p, Vector2D v) {{
		if(isMovable(p,v)) {
		List<GameElement> elems = GameEngine.getInstance().getElemsInPos(p.plus(v));
		for(GameElement elem: elems) {
			if(elem instanceof Movable && ((Movable) elem).isMovable(p.plus(v), v)) {
				((Movable)elem).move(p.plus(v), v);
			}
		}
			setPosition(p.plus(v));
		}
	}
	
		
	}
}
