package ca.mcgill.ecse223.block.view;

import java.util.List;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.controller.TOGridCell;

public class RefreshData {

	public static void refreshData(String type) {
		if(type == "block") {
			DeleteBlock.refreshData();
			//CreateBlock.refreshData();
			ChangeBlock.refreshData();
			UpdateBlock.refreshData();
		}
		if(type == "level") {
			LevelConfig.refreshData();
			DeleteBlock.refreshData();
			//CreateBlock.refreshData();
			ChangeBlock.refreshData();
			UpdateBlock.refreshData();
			AdminUi.refreshPanel();
		}
		if(type == "game") {
			GameMenu.refreshData();
			//LoadGame.refreshData();
			LevelConfig.refreshData();
			DeleteBlock.refreshData();
			//CreateBlock.refreshData();
			ChangeBlock.refreshData();
			UpdateBlock.refreshData();
			AdminUi.refreshPanel();
		}
		if(type == "delete game") {
			GameMenu.refreshData();
			LevelConfig.refreshData();
		}

		if(type == "panel")
			AdminUi.refreshPanel();
		
		if(type == "buttons") {
			TOGame cGame;
			try {
				cGame = Block223Controller.getCurrentDesignableGame();
				List<TOGridCell> blocksForCurrentLevel = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(DetailsPanel.getCurrentLevel());
				BlockGeneration.regenerateBlocks(blocksForCurrentLevel);
				
				DetailsPanel.setNumBlocks(blocksForCurrentLevel.size());
				DetailsPanel.setNumBlocksLeft(cGame.getNrBlocksPerLevel() - blocksForCurrentLevel.size());
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
}
