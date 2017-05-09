/**
 * 
 */
package acsse.csc2a.game.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

import acsse.csc2a.game.model.*;

/**
 * @author Joel, DM, 201071264
 *
 */
public class FileReadWrite {
	public static Game loadSave(String filePath) {
		Game game = null;
		ObjectInputStream loadGame = null;
		try {
			loadGame = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
			game = (Game)loadGame.readObject();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			try {
			if (loadGame != null) {
				loadGame.close();
			}
			} catch (IOException ex) {
				ex.printStackTrace();
			} 
		}
		
		return game;
	}
	
	public static boolean saveGame(String filePath, Game game) {
		boolean isSuccessful = false;
		ObjectOutputStream saveFile = null;
		try {
			saveFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
			saveFile.writeObject(game);
			saveFile.close();
			isSuccessful = true;
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
			if (saveFile != null) {
				saveFile.close();
			}
			} catch (IOException ex) {
				ex.printStackTrace();
			} 
		}
		
		return isSuccessful;
	}
	
	public static void loadSettings(String filePath, Game game) {
		File file = new File(filePath);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			sc.nextLine();
			String line = sc.nextLine();
			StringTokenizer st = new StringTokenizer(line, ",");
			game.setTech(Integer.parseInt(st.nextToken()));
			game.setAir(Integer.parseInt(st.nextToken()));
			game.setWater(Integer.parseInt(st.nextToken()));
			game.setPower(Integer.parseInt(st.nextToken()));
			game.setMaterial(Integer.parseInt(st.nextToken()));
			game.setPopulation(Integer.parseInt(st.nextToken()));
			game.setFood(Integer.parseInt(st.nextToken()));
			game.setSize(Integer.parseInt(st.nextToken()));
			game.setEntertainment(Integer.parseInt(st.nextToken()));
			game.setHouses(Integer.parseInt(st.nextToken()));
			game.setTechs(Integer.parseInt(st.nextToken()));
			game.setDomeCost(Integer.parseInt(st.nextToken()));
			game.setEnterCost(Integer.parseInt(st.nextToken()));
			game.setEnviroCost(Integer.parseInt(st.nextToken()));
			game.setFabCost(Integer.parseInt(st.nextToken()));
			game.setFarmCost(Integer.parseInt(st.nextToken()));
			game.setHouseCost(Integer.parseInt(st.nextToken()));
			game.setPowerCost(Integer.parseInt(st.nextToken()));
			game.setResearchCost(Integer.parseInt(st.nextToken()));
			game.setRoadCost(Integer.parseInt(st.nextToken()));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			sc.close();
		}
	}
}
