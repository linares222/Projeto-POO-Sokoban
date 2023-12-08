package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class BarModule implements ImageTile{

	private Point2D position;
	private String name;
	
	public BarModule(Point2D position, int module ) {
		this.position=position;
		this.name="barra"+String.valueOf(module);
	}
	
	//set position
	public void setName(String nome) {
		name=nome;
	}
	
	public void setPosition(Point2D Point2D) {
		position= Point2D;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public Point2D getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 0;
	}

}
