package ca.mcgill.ecse223.block.view;

public class VictoryAndGameoverMusic {

	static MusicClass gameover1;
	static MusicClass gameover2;
	
	static MusicClass victory;
	
	public static void startMusic(String activate)
	{
		if(activate.equals("gameover"))
		{
			if(PlayerGameUi.clip != null) {
				PlayerGameUi.clip.stopMusic();
			}
			gameover1= new MusicClass("Eerie.wav");
			gameover1.playMusic();
			gameover2= new MusicClass("gameOver.wav");
			gameover2.playMusic();
		} else if (activate.equals("victory")) {
			if(PlayerGameUi.clip != null) {
				PlayerGameUi.clip.stopMusic();
			}
			victory = new MusicClass("victoryTheme.wav");
			victory.playMusic();
		}
	}
}
