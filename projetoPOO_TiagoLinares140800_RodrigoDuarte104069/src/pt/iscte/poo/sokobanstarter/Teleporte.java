package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Teleporte extends GameElement implements HoleElement{
	private GameEngine instancia = GameEngine.getInstance();
	
	public Teleporte(Point2D Point2D){
		super(Point2D, "Teleporte", 0);
	}
	

	@Override
	public void activate(GameElement elem, Point2D p, Vector2D v) {
		Point2D newPos = p.plus(v);
		if(elem instanceof Empilhadora) {
			
			List<Point2D> posPortais = instancia.selectedElemPosList(this.getClass());
			if(newPos.equals(posPortais.get(0))) {
				instancia.getBobcat().setPosition(posPortais.get(1));
				List<GameElement> elemsInPortal = instancia.getElemsInPos(posPortais.get(1));
				for(GameElement e: elemsInPortal) {
					if(e instanceof PushableElement) {
						((PushableElement) e).move(posPortais.get(1), v);
						
					}
				}
			}
			if(newPos.equals(posPortais.get(1))) {
				instancia.getBobcat().setPosition(posPortais.get(0));
				List<GameElement> elemsInPortal = instancia.getElemsInPos(posPortais.get(0));
				for(GameElement e: elemsInPortal) {
					if(e instanceof PushableElement) {
						((PushableElement) e).move(posPortais.get(0), v);
					}
				}
			}
		}		
		if(elem instanceof PushableElement) {

			List<Point2D> posPortais = instancia.selectedElemPosList(this.getClass());
			List<GameElement> elems = instancia.getElemsInPos(newPos);
			for(GameElement e: elems) {
				if(e instanceof PushableElement) {
					if(newPos.equals(posPortais.get(0))) {
						e.setPosition(posPortais.get(1));
						
					}
					if(newPos.equals(posPortais.get(1))) {
						e.setPosition(posPortais.get(0));
						
					}
				}
			}
		}
	}
	

}
