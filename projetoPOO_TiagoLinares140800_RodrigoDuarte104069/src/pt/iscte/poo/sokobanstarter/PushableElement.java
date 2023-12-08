package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class PushableElement extends GameElement implements Movable{
	private GameEngine instancia = GameEngine.getInstance();
	List<Point2D> buracos = instancia.selectedElemPosList(Buraco.class);
	public PushableElement(Point2D position, String name, int layer) {
		super(position, name, layer);
	}
	
	public Boolean isMovable(Point2D p, Vector2D v) {
		if(this instanceof Palete) {
			for(Point2D pos: buracos) {
				Palete palete = (Palete) this;
				if(pos.equals(this.getPosition())) {
					return !palete.getNoBuraco();
				}
			}
			
			
		}
		if(GameEngine.getInstance().checkPushableBounds(p,v)==false){
			return true;
		}
		return false;
	}

	@Override
	public void move(Point2D p, Vector2D v) {
	    
		   if(isMovable(p, v)) {
		        List<GameElement> elemsInNewPos = instancia.getElemsInPos(p.plus(v));
		        List<GameElement> elemsInPos = instancia.getElemsInPos(p);

		        boolean isLeavingAlvo = false;
		        setPosition(p.plus(v));
		        instancia.getBobcat().setEnergyPoints(instancia.getBobcat().getEnergyPoints()-1);
		        if(this instanceof Caixote) {
		            for(GameElement element : elemsInPos) {
		                if(element instanceof Alvo) {
		                    isLeavingAlvo = true; 
		                    break;
		                }
		            }
		        }
		       
		   

	        for(GameElement elem : elemsInNewPos) {
	            if(elem instanceof PushableElement && ((PushableElement) elem).isMovable(p.plus(v), v)) {
	                ((PushableElement)elem).move(p.plus(v), v);
	            }
	            if(elem instanceof Alvo && this instanceof Caixote) {
	                instancia.removeAlvo(elem.getPosition());
	                
	                isLeavingAlvo = false; 
	            }
	            if(elem instanceof HoleElement) {
	            	((HoleElement) elem).activate(this, p, v);
	            }
	        }
	        if(isLeavingAlvo) {
	            instancia.addAlvo(p);
	           
	        }  
	       
	    }
	}
	


}
