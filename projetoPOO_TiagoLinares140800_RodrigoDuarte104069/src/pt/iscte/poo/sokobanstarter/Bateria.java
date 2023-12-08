package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement implements ExtraElement {

	public Bateria(Point2D position) {
		super(position, "Bateria", 0);
	}
	public void activate() {
		if(instancia.getBobcat().getEnergyPoints()<51) {
			instancia.getBobcat().setEnergyPoints(instancia.getBobcat().getEnergyPoints()+50);
			instancia.removeGameElement(this, getPosition());
		}else {
			instancia.getBobcat().setEnergyPoints(100);
			instancia.removeGameElement(this, getPosition());
		}
	}

	
}
