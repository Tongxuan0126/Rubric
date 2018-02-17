package project;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import cse131.ArgsProcessor;
import project.natural.Gravity;
import project.natural.Natural;
import project.natural.Wind;
import project.spirit.Building;
import project.spirit.CitySkyline;
import project.spirit.Player;
import project.spirit.Spirit;
import sedgewick.StdDraw;

/**
 * 1. Draws a game board that includes the ground (can be flat) and two players (can be simple shapes) (10 points)
 * 2. Accepts user input for angle and velocity (ArgsProcessor is OK) (10 points)
 * 3. Fires a projectile based on the given angle and velocity (10 points)
 * 4. Checks projectile collisions with player, ground, and edge of the screen appropriately (10 points).
 * 5. Displays a "Game Over" screen when a player is hit, stating the winner. Restarts the game. (10 points).
 * 6. Includes at least one interface. The interface must be applied to at least 2 classes to receive credit for this (10 points)
 * 
 * 1. Include a wind factor and a gravity factor that affects the movement of the projectile. 
 * The wind factor should be randomly chosen and displayed on each level. 
 * The gravity factor should be chosen by the user at the beginning of the game.
 * 
 * 2. Randomly generated a city skyline for each round. 
 * The projectiles need to collide with the skyline appropriately.
 * 
 * 
 * @author 
 *
 */

public class GameBoard {
	static ArgsProcessor ap;
	
	private List<Natural> naturals;
	private List<Spirit> allSpirits;
	
	private Player p1;
	private Player p2;

	public GameBoard() {
		allSpirits = new ArrayList<>();
	}
	
	public void start() {
		StdDraw.setCanvasSize(Consts.GAME_WIDTH, Consts.GAME_HEIGHT);
		StdDraw.setXscale(1, Consts.GAME_WIDTH - 1);
		StdDraw.setYscale(1, Consts.GAME_HEIGHT - 1);
		while(true){
			showStartUI();
			initSettings();
			Player winner = startPlay();
			drawWinner(winner);
		}
	}
	
	public void showStartUI(){
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setFont(new Font("Broadway",Font.ITALIC,60));
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.text(Consts.GAME_WIDTH/2, Consts.GAME_HEIGHT/2, Consts.GAME_NAME);
		
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setFont(new Font("Broadway",Font.ITALIC,40));
		StdDraw.text(Consts.GAME_WIDTH/2, 200, "Press Any Key To Start The Game");
		StdDraw.pause(500);
		
		while(true){
			if(StdDraw.hasNextKeyTyped()) {
				StdDraw.nextKeyTyped();
				break;
			}
		}	
	}
	
	public void initSettings() {
		StdDraw.clear(StdDraw.WHITE);
		StdDraw.setPenColor(StdDraw.BLACK);
		naturals = createNatural();

		StdDraw.clear(StdDraw.WHITE);
		StdDraw.setPenColor(StdDraw.BLACK);
		allSpirits.addAll(createCitySkyline());
		List<Building> buildings = createBuildings(8);
		allSpirits.addAll(buildings);
		createPlayers(buildings);
		allSpirits.add(p1);
		allSpirits.add(p2);
	}
	
	public List<CitySkyline> createCitySkyline() {
		List<CitySkyline> skylines = new ArrayList<>();

		double totalwidth = 0;
		for (int i=0;i<10;i++) {
			double width = 150;
			double height = 20 + Math.random()*80;
			CitySkyline skyline1 = new CitySkyline(totalwidth, Consts.GAME_HEIGHT-height, width, height);
			skyline1.draw();
			skylines.add(skyline1);
			totalwidth+=width;
		}
		
		CitySkyline skyline2 = new CitySkyline(-20, 0, 20, Consts.GAME_HEIGHT);
		CitySkyline skyline3 = new CitySkyline(Consts.GAME_WIDTH, 0, 20, Consts.GAME_HEIGHT);

		skyline2.draw();
		skyline3.draw();
		
		skylines.add(skyline2);
		skylines.add(skyline3);
		
		return skylines;
	}
	
	public List<Natural> createNatural() {
		List<Natural> naturals = new ArrayList<>();
		double g = ap.nextDouble("Please enter the value of gravity");
		Natural gravity = new Gravity(g);
		StdDraw.text(Consts.GAME_WIDTH/2, Consts.GAME_HEIGHT/2+100, "GRAVITY: "+g);
		double maxSpeed = ap.nextDouble("Please enter the max speed of wind");
		Natural wind = new Wind(maxSpeed);
		StdDraw.text(Consts.GAME_WIDTH/2, Consts.GAME_HEIGHT/2, "Wind Max Speed: "+maxSpeed);
		StdDraw.pause(500);	
		naturals.add(gravity);
		naturals.add(wind);
		return naturals;
	}
	
	public List<Building> createBuildings(int buildingNum) {
		List<Building> buildings = new ArrayList<>();
		double totalwidth = 0;
		for (int i=0;i<buildingNum;i++) {
			double width = 150;
			double height = Consts.GAME_HEIGHT/10 + Math.random()*Consts.GAME_HEIGHT*5/10;
			Building building = new Building(totalwidth,0,width,height);
			building.draw();
			buildings.add(building);
			totalwidth+=width;
		}
		return buildings;
	}
	
	public void createPlayers(List<Building> buildings) {
		int p1Pos=(int)(Math.random() * buildings.size() / 2);		
		String p1Name = ap.nextString("Please enter the name of Player1");
		
		Building building = buildings.get(p1Pos);
		p1 = new Player(p1Name, StdDraw.ORANGE, building.getLeft() + building.getWidth()/2,building.getHeight());
		//p1 = new Player(p1Name, StdDraw.ORANGE, 30,building.getHeight());
		p1.draw(100);
		
		int p2Pos=(int)(Math.random() * buildings.size() / 2+buildings.size() / 2);
		String p2Name = ap.nextString("Please enter the name of Player2");
		building = buildings.get(p2Pos);
		p2 = new Player(p2Name, StdDraw.GREEN, building.getLeft() + building.getWidth()/2,building.getHeight());
		p2.draw(1100);
	}
	
	public Player startPlay() {
		Player winner = null;
		while(true){
			for (Natural natural : naturals) {
				natural.update();
				natural.draw();	
			}
			winner = dealPlayer(p1,1);
			if (winner != null) {
				break;
			}
			
			winner = dealPlayer(p2,-1);
			if (winner != null) {
				break;
			}
		}
		System.out.println("Winner:"+winner.getName());
		return winner;
	}
	
	public Player dealPlayer(Player p,int orientation) {
		Player winner = null;
		double angle=ap.nextDouble("Player "+p.getName()+", enter angle");
		double velocit=ap.nextDouble("Player "+p.getName()+", enter velocity");
		Spirit hitSpirit = p.throwProjectile(orientation, angle, velocit, allSpirits, naturals);
		if (hitSpirit != null) {
			if (hitSpirit == p1) {
				winner = p2;
			} else if (hitSpirit == p2) {
				winner = p1;
			}
		}
		p1.draw(100);
		p2.draw(1100);
		for (Natural natural : naturals) {
			natural.draw();	
		}
		return winner;
	}
	
	public void drawWinner(Player player) {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(Consts.GAME_WIDTH/2, Consts.GAME_HEIGHT - 80, "Player: " + player.getName()+" win!!!!!!");
		StdDraw.show(1000);
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.setFont(new Font("Broadway",Font.ITALIC,60));
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(Consts.GAME_WIDTH/2, Consts.GAME_HEIGHT/2, "Game Over.\n Winner is " + player.getName());
		StdDraw.show();
		StdDraw.pause(3000);
	}
	
	public static void main(String[] args) {
		ap = new ArgsProcessor (args);
		GameBoard gameBoard = new GameBoard();
		gameBoard.start();
	}

}
