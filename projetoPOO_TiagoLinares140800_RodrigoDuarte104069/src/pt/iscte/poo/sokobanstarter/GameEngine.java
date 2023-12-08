package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!


public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	private static final int GRID_HEIGHT = 11;
	private static final int GRID_WIDTH = 10;
	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private Empilhadora bobcat;	        // Referencia para a empilhadora
	private String playerName;
	private int level=5;
	private List<GameElement> lista;
	private List<ImageTile> barra;
	private String username;
	private List<Integer> playerScores = new ArrayList<>();
	private List<Point2D> alvoPositions = new ArrayList<>();
	private int finalLevel=6;
	private int levelsPlayed=0;
	private Boolean endLevel = false;


	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
		lista = new ArrayList<GameElement>();   
		barra = new ArrayList<ImageTile>();  
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}
//setters
	public void setEndLevel(Boolean b) {
		endLevel= b;
	}
	public void setBobcat(Empilhadora e) {
		this.bobcat=e;
	}
	public void setAlvoPositions() {
		if(alvoPositions.size()>0) {
		alvoPositions.removeAll(alvoPositions);
		}
		alvoPositions = selectedElemPosList(Alvo.class);
	
	}
	//getters
	public List<Point2D> getAlvoPositions() {
		return selectedElemPosList(Alvo.class);
	}
	
	public Empilhadora getBobcat() {
		return bobcat;
	}
	public String getPlayerName() {
		return playerName;
	}
	public List<Integer> getPlayerScores(){
		return playerScores;
	}
	public List<GameElement> getLista(){
		return lista;
	}
	public int getLevel() {
		return level;
	}
	public List<GameElement> getElemsInPos(Point2D p) {
		List<GameElement> elems= new ArrayList<GameElement>();
		for(GameElement elem: lista) {
			if(elem.getPosition().equals(p) && !(elem instanceof Chao)) {
				elems.add(elem);
			}
		}
		
		return elems;
	}
	
	//
	public void addScore(int moves) {
		playerScores.add(moves);
	}
	public void removeAlvo(Point2D alvoPos) {
		alvoPositions.removeIf(pos -> pos.equals(alvoPos));
		if(alvoPositions.size()==0) {
			setEndLevel(true);
		}
	}
	public void addAlvo(Point2D alvoPos) {
		alvoPositions.add(alvoPos);
	}
	
	
	// Inicio
	public void start() {

		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que ser feitos!
	
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_WIDTH, GRID_HEIGHT);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI

		
		
		// Criar o cenario de jogo
		createBase();      // criar o armazem
		createMoreStuff();      // criar mais algun objetos (empilhadora, caixotes,...)
		createEnergyBar();

		
		// Escrever uma mensagem na StatusBar
		username=gui.askUser("Username: ");
		gui.setStatusMessage("SOKOBAN Starter Package - Name: "+username+"Turns: " + GameEngine.getInstance().getBobcat().getMoves() + " Energy Points: " + GameEngine.getInstance().getBobcat().getEnergyPoints());
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado (neste caso a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();    // obtem o codigo da tecla pressionada
		
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {  // se a tecla for UP, manda a empilhadora mover
			bobcat.handleChangeDirection(key);
			}
		if(key == KeyEvent.VK_R){
			gui.clearImages();
			lista.clear();
			level=0;
			createMoreStuff();
			createBase();
			setAlvoPositions();
			playerScores.clear();;
		}
		if(endLevel) {
			gui.clearImages();
			lista.clear();
			levelsPlayed++;
			playerScores.add(bobcat.getMoves());
			updateTopScore();
			if(level<finalLevel) {
			level++;
			setEndLevel(false);
			createMoreStuff();
			createBase();
			}else {   
				endGame(level);
			}
		}
		gui.setStatusMessage("SOKOBAN Starter Package - Name: "+username+" Turns: " + bobcat.getMoves() + " Energy Points: " + bobcat.getEnergyPoints());
		gui.update();                  
	}
	
	public void addGameElement(GameElement e) {
		lista.add(e);
		gui.addImage(e);
	}
	
	public void removeGameElement(GameElement e, Point2D p ) {
		lista.removeIf(elem->(e.getPosition().equals(p))&&(e.equals(elem)));
		gui.removeImage(e);
	}
	
	public void updateTopScore() {
		
		String nomeFicheiro = "levels/top3Level" + level + ".txt";
	    File file = new File(nomeFicheiro);
	    List<String> topScores = new ArrayList<>();

	    try {
	        if (!file.exists()) {
	            file.createNewFile();
	        } else {
	            Scanner scanner = new Scanner(file);
	            while (scanner.hasNextLine()) {
	                topScores.add(scanner.nextLine());
	            }
	            scanner.close();
	        }
	        String currentScore = username + ": " + playerScores.get(levelsPlayed-1);
	        topScores.add(currentScore);

	        Comparator<String> scoreComparator = (o1, o2) -> {
	            int score1 = Integer.parseInt(o1.split(": ")[1]);
	            int score2 = Integer.parseInt(o2.split(": ")[1]);
	            return Integer.compare(score1, score2);
	        };

	        topScores.sort(scoreComparator);

	        while (topScores.size() > 3) {
	            topScores.remove(topScores.size() - 1);
	        }


	        PrintWriter pw = new PrintWriter(new FileWriter(file));
	        for (String score : topScores) {
	            pw.println(score);
	        }
	        pw.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public boolean checkPushableBounds(Point2D initialPoint, Vector2D v) {
	    Point2D newPos = initialPoint.plus(v);
	    List<GameElement> elems = getElemsInPos(newPos);
	    if (!checkBounds(initialPoint, v)) {
	        for (GameElement elem : elems) {
	            if (elem instanceof SolidElement || elem instanceof ExtraElement) {
	                return true;
	            }
	            
	        }
	        return false;
	    }
	    return true; 
	}
	
	public boolean checkBounds(Point2D initialPoint, Vector2D v){ 
		Point2D newPos = initialPoint.plus(v);	
		List<GameElement> elems = getElemsInPos(newPos);
		boolean containsPaleteInBuraco = elems.stream()
                .anyMatch(e -> e.getPosition().equals(newPos) && e instanceof Palete && ((Palete) e).getNoBuraco());

		boolean containsBuraco = elems.stream()
                  .anyMatch(e -> e.getPosition().equals(newPos) && e instanceof Buraco);
		
		boolean containsPushable=  elems.stream()
                .anyMatch(e -> e.getPosition().equals(newPos) && e instanceof PushableElement && !(e instanceof Palete && ((Palete) e).getNoBuraco()));
		if(containsPaleteInBuraco && containsBuraco && containsPushable) {
			for(GameElement elem: elems) {
				if(elem instanceof PushableElement && !(elem instanceof Palete && ((Palete) elem).getNoBuraco())) {
					if(!checkPushableBounds(newPos, v)) {
						return false;
					}
					return true;
				}
			}
		}
		for(GameElement elem: elems) {
		if (elem instanceof SolidElement ) {
            if (elem instanceof ParedeRachada && bobcat.getHasMartelo()) {
                removeGameElement(elem, newPos);
                return false;
            }

            return true;
        }
		if(elem instanceof PushableElement) {
			 if (elem instanceof Palete && ((Palete) elem).getNoBuraco()) {
		            return false;
		        }
				
				if(!checkPushableBounds(newPos, v)) {
					return false;
				}
				return true;
				}
		
	}	
		return false;
		
	}
	
	//Retorna a posiçao de todos os elementos com classe igual à passada nos parametros presentes no mapa 
	
	public <T> List<Point2D> selectedElemPosList(Class<T> c){
		List<Point2D> positions= new ArrayList<>();
		for(GameElement ge : lista) {
			if(ge.getClass() == c)
				positions.add(ge.getPosition());
		}
		return positions;
	}
	

	public void endGame(int level) {
			Boolean won=true;
			int pontuacaoFinal=0;
			for(int i: playerScores) {
				pontuacaoFinal+=i;
			}
			gui.setStatusMessage("SOKOBAN Starter Package - Name: "+username+" Turns: " + bobcat.getMoves() + " Energy Points: " + bobcat.getEnergyPoints());
			gui.update();  
			List<GameElement> elementsAtBobcatPos = getElemsInPos(bobcat.getPosition());
		    for (GameElement elem : elementsAtBobcatPos) {
		        if (elem instanceof Buraco) {
		            won = false;
		            break;
		        }
		    }
			if(level<finalLevel || selectedElemPosList(Caixote.class).size()<selectedElemPosList(Alvo.class).size()) {
				won=false;
			}
			if(won) {
				gui.setMessage("Ganhaste!\n Movimentos: "+pontuacaoFinal);
			
			}else {
				gui.setMessage("Perdeste!\n Movimentos: "+pontuacaoFinal);
			}
			gui.dispose();
			
	}


	
	
	// Criacao da planta do armazem - so' chao neste exemplo 
	private void createBase() {
	    for (int y = 0; y < GRID_HEIGHT; y++) {
	        for (int x = 0; x < GRID_WIDTH; x++) {
	            if (y == 10) {
	              
	                addGameElement(new Vazio(new Point2D(x, y)));
	            } else {
	                addGameElement(new Chao(new Point2D(x, y)));
	            }
	        }
	    }
	}
	
	public void createEnergyBar() {
		for (int x = 0; x < 10; x++) {
			barra.add(new BarraModule(new Point2D(x, 10), 10));
		}
		gui.addImages(barra);
	}
	
	public void updateEnergyBar(int energia) {
	    gui.removeImages(barra);
	    barra.clear();

	    int modulosCheios = energia / 10;
	    int modulosParciais = energia % 10;

	    for (int x = 0; x < modulosCheios; x++) {
	        BarraModule moduloCheio = new BarraModule(new Point2D(x, 10), 10);
	        barra.add(moduloCheio);
	    }

	    if (modulosParciais > 0) {
	        BarraModule moduloParcial = new BarraModule(new Point2D(modulosCheios, 10), modulosParciais);
	        barra.add(moduloParcial);
	    }

	    gui.addImages(barra);
	}
	
	
	
	// Criacao de mais objetos
	private void createMoreStuff() {
		
		String nomeFicheiro = "levels/level" + getLevel() + ".txt";
	    File file = new File(nomeFicheiro);
	    
	    try {
	        Scanner s = new Scanner(file);
	        for (int h = 0; h < GRID_HEIGHT; h++) {
	            if (s.hasNextLine()) {
	                String line = s.nextLine();
	                for (int w = 0; w < GRID_WIDTH; w++) {
	                    char symbol = line.charAt(w);
	                    GameElement element = GameElement.factory(symbol, new Point2D(w, h));
	                    if (element != null) {
	                        addGameElement(element);
	                    }
	                }
	            }
	        }
	        s.close();
	        setAlvoPositions();
	    } catch (FileNotFoundException e) {
	    	e.printStackTrace();
	        System.out.println("Ficheiro com nome " + nomeFicheiro + " não existe");
	    }
	}
}
