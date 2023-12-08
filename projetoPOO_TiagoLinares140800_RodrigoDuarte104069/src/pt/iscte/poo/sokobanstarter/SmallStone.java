package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class SmallStone extends GameElement implements ExtraElement {

	public SmallStone(Point2D position) {
		super(position, "SmallStone", 0);
	}

	@Override
	public void activate() {
		int energyUpdated = instancia.getBobcat().getEnergyPoints()-5;
		if(energyUpdated<0) {
			instancia.getBobcat().setEnergyPoints(0);
		}else {
			instancia.getBobcat().setEnergyPoints(energyUpdated);
		}
	}

}
