package pt.iscte.poo.sokobanstarter;


import pt.iscte.poo.utils.Point2D;

public class Palete extends PushableElement {
	private Boolean noBuraco= false;
	public GameEngine instancia = GameEngine.getInstance();
	public Palete(Point2D Point2D){
		super(Point2D, "Palete", 1);
	}
	public void setNoBuraco(Boolean b) {
		noBuraco=b;
	}
	public Boolean getNoBuraco() {
		return noBuraco;
	}

}
