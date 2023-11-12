package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

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
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<ImageTile> tileList;	// Lista de imagens
	private Empilhadora bobcat;	        // Referencia para a empilhadora
	private String playerName;
	public int level=4;


	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
		tileList = new ArrayList<>();   
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	//Getters 
	
	public Empilhadora getBobcat() {
		return bobcat;
	}
	public String getPlayerName() {
		return playerName;
	}
	private int getLevel() {
		return level;
	}
	private List<ImageTile> getTileList() {
		return tileList;
	}
	
	// Inicio
	public void start() {

		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que ser feitos!
		System.out.println("Insere o teu nome");
		Scanner keyboard = new Scanner(System.in);
		this.playerName = keyboard.nextLine();
		keyboard.close();
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI

		
		// Criar o cenario de jogo
		createWarehouse();      // criar o armazem
		createMoreStuff();      // criar mais algun objetos (empilhadora, caixotes,...)
		sendImagesToGUI();      // enviar as imagens para a GUI

		
		// Escrever uma mensagem na StatusBar
		gui.setStatusMessage("Level: "+ getLevel() + " - Player: "+getPlayerName()+" - Moves: "+ getBobcat().getMoves() + " - Energy:"+getBobcat().getEnergyPoints());
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado (neste caso a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();    // obtem o codigo da tecla pressionada
		
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {  // se a tecla for UP, manda a empilhadora mover
			bobcat.move(key);
			}
		
		
			
	

		gui.update();                  // redesenha a lista de ImageTiles na GUI, 
		                               // tendo em conta as novas posicoes dos objetos
	}



	// Criacao da planta do armazem - so' chao neste exemplo 
	private void createWarehouse() {

		for (int y=0; y<GRID_HEIGHT; y++)
			for (int x=0; x<GRID_HEIGHT; x++)
				tileList.add(new Chao(new Point2D(x,y)));		
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
	                    switch (symbol) {
						case '#' :
							tileList.add(new Parede(new Point2D(w,h)));	
						break;
						case '=' : 
							tileList.add(new Vazio(new Point2D(w,h)));	
						break;
						case 'C' : 
							tileList.add(new Caixote(new Point2D(w,h)));	
						break;
						case 'X' : 
							tileList.add(new Alvo(new Point2D(w,h)));	
						break;
						case 'E' : 
							this.bobcat= new Empilhadora(new Point2D(w,h));
							tileList.add(new Chao(new Point2D(w,h)));
							tileList.add(bobcat);	
						break;
						case 'B' : 
							tileList.add(new Bateria(new Point2D(w,h)));	
						break;
						case 'T' : 
							tileList.add(new Teleporte(new Point2D(w,h)));	
						break;
	                    }
	                }
	            }
	        }
	        s.close();
	    } catch (FileNotFoundException e) {
	    	e.printStackTrace();
	        System.out.println("Ficheiro com nome " + nomeFicheiro + " não existe");
	    }
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}
}
