package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Caixote extends GameElement implements Movable{

	
	
	public Caixote(Point2D Point2D){
		super(Point2D, "Caixote",1);
	}
	

	@Override
	public Boolean isMovable(Point2D p, Vector2D v) {
		if(GameEngine.getInstance().checkBounds(p,v)==false) {
			return true;
		}
		return false;
	}


	@Override
	public void move(Point2D p, Vector2D v) {{
			setPosition(p.plus(v));
		}
		
	}

	
}
