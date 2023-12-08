package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement implements ExtraElement {
	public Martelo(Point2D position) {
		super(position, "Martelo", 0);
	}
		
	@Override
	public void activate() {
		if(instancia.getBobcat().getHasMartelo()==false) {
			instancia.getBobcat().setHasMartelo(true);
			instancia.removeGameElement(this, getPosition());
		}
	
		
	}
}
