package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{
	protected GameEngine instancia = GameEngine.getInstance();
	private Point2D position;
	private String name;
	private int layer;
	
	public GameElement(Point2D position, String name, int layer) {
		this.position=position;
		this.name=name;
		this.layer=layer;
	}
	
	public static GameElement factory(char symbol, Point2D position) {
		switch (symbol) {
		case '#' :
			return new Parede(position);
		
		case '=' : 
			return new Vazio(position);	

		case 'C' : 
			return new Caixote(position);

		case 'X' : 
			return new Alvo(position);	

		case 'E' : 
			Empilhadora emp=new Empilhadora(position);
			GameEngine.getInstance().setBobcat(emp);
			return emp;

		case 'B' : 
			return new Bateria(position);	

		case 'T' : 
			return new Teleporte(position);	
		case '%' :
			return new ParedeRachada(position);
		case 'M' :
			return new Martelo(position);
		case 'P':
			return new Palete(position);
		case 'O':
			return new Buraco(position);
		case 'S':
			return new SmallStone(position);
		default:
			return null;
		}
	}
	//set position
	public void setName(String nome) {
		name=nome;
	}
	
	public void setPosition(Point2D Point2D) {
		position= Point2D;
	}
	
	public void setLayer(int camada) {
		layer=camada;
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return layer;
	}
	
}
