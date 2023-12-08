package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Buraco extends GameElement implements HoleElement {
	private GameEngine instancia = GameEngine.getInstance();
	public Buraco(Point2D Point2D){
		super(Point2D, "Buraco", 0);
	}


	@Override
	public void activate(GameElement elem, Point2D pos, Vector2D v) {
		Point2D newPos = pos.plus(v);
		if(elem instanceof Empilhadora) {
			List<GameElement> elemsInPos= instancia.getElemsInPos(newPos);
			for(GameElement e: elemsInPos) {
				if(e instanceof Palete) {
					return;
				}
			}
			
					instancia.removeGameElement(instancia.getBobcat(), newPos);
					instancia.addScore(instancia.getBobcat().getMoves());
					instancia.endGame(instancia.getLevel());
		}
		if(elem instanceof PushableElement) {
			  List<GameElement> elemsInPos = instancia.getElemsInPos(newPos);
			    boolean isBuracoFilled = elemsInPos.stream().anyMatch(e -> e instanceof Palete && ((Palete) e).getNoBuraco());

			    for (GameElement e : elemsInPos) {
			        if (e instanceof Caixote) {
			        	if(!isBuracoFilled) {
			            instancia.removeGameElement(e, newPos);
			            if(instancia.selectedElemPosList(Caixote.class).size() < instancia.selectedElemPosList(Alvo.class).size()) {
			                instancia.addScore(instancia.getBobcat().getMoves());
			                instancia.endGame(instancia.getLevel());
			            }
			        	}else {
			        		return;
			        	}
			        	
			            break;
			        } else if (e instanceof Palete) {
			            if (!isBuracoFilled) {
			                ((Palete) e).setNoBuraco(true);
			            }else {
			            	return;
			            }
			            break; 
			        }
			    }
		}
		
	}
}
