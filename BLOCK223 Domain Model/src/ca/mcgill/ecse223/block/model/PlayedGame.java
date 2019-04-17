/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;

// line 11 "../../../../../Block223PlayMode.ump"
// line 118 "../../../../../Block223Persistence.ump"
// line 1 "../../../../../Block223States.ump"
public class PlayedGame implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------


  /**
   * at design time, the initial wait time may be adjusted as seen fit
   */
  public static final int INITIAL_WAIT_TIME = 30;
  private static int nextId = 1;
  public static final int NR_LIVES = 3;

  /**
   * the PlayedBall and PlayedPaddle are not in a separate class to avoid the bug in Umple that occurred for the second constructor of Game
   * no direct link to Ball, because the ball can be found by navigating to Game and then Ball
   */
  public static final int BALL_INITIAL_X = Game.PLAY_AREA_SIDE / 2;
  public static final int BALL_INITIAL_Y = Game.PLAY_AREA_SIDE / 2;

  /**
   * no direct link to Paddle, because the paddle can be found by navigating to Game and then Paddle
   * pixels moved when right arrow key is pressed
   */
  public static final int PADDLE_MOVE_RIGHT = 5;

  /**
   * pixels moved when left arrow key is pressed
   */
  public static final int PADDLE_MOVE_LEFT = -5;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayedGame Attributes
  private int score;
  private int lives;
  private int currentLevel;
  private double waitTime;
  private String playername;
  private double ballDirectionX;
  private double ballDirectionY;
  private double currentBallX;
  private double currentBallY;
  private double currentPaddleLength;
  private double currentPaddleX;
  private double currentPaddleY;

  //Autounique Attributes
  private int id;

  //PlayedGame State Machines
  public enum PlayStatus { Ready, Moving, Paused, GameOver }
  private PlayStatus playStatus;

  //PlayedGame Associations
  private Player player;
  private Game game;
  private List<PlayedBlockAssignment> blocks;
  private transient BouncePoint bounce;
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayedGame(String aPlayername, Game aGame, Block223 aBlock223)
  {
    // line 47 "../../../../../Block223PlayMode.ump"
    boolean didAddGameResult = setGame(aGame);
          if (!didAddGameResult)
          {
             throw new RuntimeException("Unable to create playedGame due to game");
          }
    // END OF UMPLE BEFORE INJECTION
    score = 0;
    lives = NR_LIVES;
    currentLevel = 1;
    waitTime = INITIAL_WAIT_TIME;
    playername = aPlayername;
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentBallX();
    resetCurrentBallY();
    currentPaddleLength = getGame().getPaddle().getMaxPaddleLength();
    resetCurrentPaddleX();
    currentPaddleY = Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH;
    id = nextId++;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create playedGame due to game");
    }
    blocks = new ArrayList<PlayedBlockAssignment>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create playedGame due to block223");
    }
    setPlayStatus(PlayStatus.Ready);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setLives(int aLives)
  {
    boolean wasSet = false;
    lives = aLives;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentLevel(int aCurrentLevel)
  {
    boolean wasSet = false;
    currentLevel = aCurrentLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setWaitTime(double aWaitTime)
  {
    boolean wasSet = false;
    waitTime = aWaitTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayername(String aPlayername)
  {
    boolean wasSet = false;
    playername = aPlayername;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionX(double aBallDirectionX)
  {
    boolean wasSet = false;
    ballDirectionX = aBallDirectionX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionX()
  {
    boolean wasReset = false;
    ballDirectionX = getDefaultBallDirectionX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionY(double aBallDirectionY)
  {
    boolean wasSet = false;
    ballDirectionY = aBallDirectionY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionY()
  {
    boolean wasReset = false;
    ballDirectionY = getDefaultBallDirectionY();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallX(double aCurrentBallX)
  {
    boolean wasSet = false;
    currentBallX = aCurrentBallX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallX()
  {
    boolean wasReset = false;
    currentBallX = getDefaultCurrentBallX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallY(double aCurrentBallY)
  {
    boolean wasSet = false;
    currentBallY = aCurrentBallY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallY()
  {
    boolean wasReset = false;
    currentBallY = getDefaultCurrentBallY();
    wasReset = true;
    return wasReset;
  }

  public boolean setCurrentPaddleLength(double aCurrentPaddleLength)
  {
    boolean wasSet = false;
    currentPaddleLength = aCurrentPaddleLength;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentPaddleX(double aCurrentPaddleX)
  {
    boolean wasSet = false;
    currentPaddleX = aCurrentPaddleX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentPaddleX()
  {
    boolean wasReset = false;
    currentPaddleX = getDefaultCurrentPaddleX();
    wasReset = true;
    return wasReset;
  }

  public int getScore()
  {
    return score;
  }

  public int getLives()
  {
    return lives;
  }

  public int getCurrentLevel()
  {
    return currentLevel;
  }

  public double getWaitTime()
  {
    return waitTime;
  }

  /**
   * added here so that it only needs to be determined once
   */
  public String getPlayername()
  {
    return playername;
  }

  /**
   * 0/0 is the top left corner of the play area, i.e., a directionX/Y of 0/1 moves the ball down in a straight line
   */
  public double getBallDirectionX()
  {
    return ballDirectionX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultBallDirectionX()
  {
    return getGame().getBall().getMinBallSpeedX();
  }

  public double getBallDirectionY()
  {
    return ballDirectionY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultBallDirectionY()
  {
    return getGame().getBall().getMinBallSpeedY();
  }

  /**
   * the position of the ball is at the center of the ball
   */
  public double getCurrentBallX()
  {
    return currentBallX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallX()
  {
    return BALL_INITIAL_X;
  }

  public double getCurrentBallY()
  {
    return currentBallY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallY()
  {
    return BALL_INITIAL_Y;
  }

  public double getCurrentPaddleLength()
  {
    return currentPaddleLength;
  }

  /**
   * the position of the paddle is at its top left corner
   */
  public double getCurrentPaddleX()
  {
    return currentPaddleX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentPaddleX()
  {
    return (Game.PLAY_AREA_SIDE - currentPaddleLength) / 2;
  }

  public double getCurrentPaddleY()
  {
    return currentPaddleY;
  }

  public int getId()
  {
    return id;
  }

  public String getPlayStatusFullName()
  {
    String answer = playStatus.toString();
    return answer;
  }

  public PlayStatus getPlayStatus()
  {
    return playStatus;
  }

  public boolean play()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Ready:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      case Paused:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pause()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        setPlayStatus(PlayStatus.Paused);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean move()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        if (hitPaddle())
        {
        // line 16 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBoundsAndLastLife())
        {
        // line 17 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds())
        {
        // line 18 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.Paused);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlockAndLastLevel())
        {
        // line 19 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlock())
        {
        // line 20 "../../../../../Block223States.ump"
          doHitBlockNextLevel();
          setPlayStatus(PlayStatus.Ready);
          wasEventProcessed = true;
          break;
        }
        if (hitBlock())
        {
        // line 21 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (hitWall())
        {
        // line 22 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        // line 23 "../../../../../Block223States.ump"
        doHitNothingAndNotOutOfBounds();
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setPlayStatus(PlayStatus aPlayStatus)
  {
    playStatus = aPlayStatus;

    // entry actions and do activities
    switch(playStatus)
    {
      case Ready:
        // line 11 "../../../../../Block223States.ump"
        doSetup();
        break;
      case GameOver:
        // line 29 "../../../../../Block223States.ump"
        doGameOver();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public PlayedBlockAssignment getBlock(int index)
  {
    PlayedBlockAssignment aBlock = blocks.get(index);
    return aBlock;
  }

  public List<PlayedBlockAssignment> getBlocks()
  {
    List<PlayedBlockAssignment> newBlocks = Collections.unmodifiableList(blocks);
    return newBlocks;
  }

  public int numberOfBlocks()
  {
    int number = blocks.size();
    return number;
  }

  public boolean hasBlocks()
  {
    boolean has = blocks.size() > 0;
    return has;
  }

  public int indexOfBlock(PlayedBlockAssignment aBlock)
  {
    int index = blocks.indexOf(aBlock);
    return index;
  }
  /* Code from template association_GetOne */
  public BouncePoint getBounce()
  {
    return bounce;
  }

  public boolean hasBounce()
  {
    boolean has = bounce != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removePlayedGame(this);
    }
    if (aPlayer != null)
    {
      aPlayer.addPlayedGame(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removePlayedGame(this);
    }
    game.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayedBlockAssignment addBlock(int aX, int aY, Block aBlock)
  {
    return new PlayedBlockAssignment(aX, aY, aBlock, this);
  }

  public boolean addBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasAdded = false;
    if (blocks.contains(aBlock)) { return false; }
    PlayedGame existingPlayedGame = aBlock.getPlayedGame();
    boolean isNewPlayedGame = existingPlayedGame != null && !this.equals(existingPlayedGame);
    if (isNewPlayedGame)
    {
      aBlock.setPlayedGame(this);
    }
    else
    {
      blocks.add(aBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlock, as it must always have a playedGame
    if (!this.equals(aBlock.getPlayedGame()))
    {
      blocks.remove(aBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockAt(PlayedBlockAssignment aBlock, int index)
  {  
    boolean wasAdded = false;
    if(addBlock(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockAt(PlayedBlockAssignment aBlock, int index)
  {
    boolean wasAdded = false;
    if(blocks.contains(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockAt(aBlock, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setBounce(BouncePoint aNewBounce)
  {
    boolean wasSet = false;
    bounce = aNewBounce;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlock223(Block223 aBlock223)
  {
    boolean wasSet = false;
    if (aBlock223 == null)
    {
      return wasSet;
    }

    Block223 existingBlock223 = block223;
    block223 = aBlock223;
    if (existingBlock223 != null && !existingBlock223.equals(aBlock223))
    {
      existingBlock223.removePlayedGame(this);
    }
    block223.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (player != null)
    {
      Player placeholderPlayer = player;
      this.player = null;
      placeholderPlayer.removePlayedGame(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayedGame(this);
    }
    while (blocks.size() > 0)
    {
      PlayedBlockAssignment aBlock = blocks.get(blocks.size() - 1);
      aBlock.delete();
      blocks.remove(aBlock);
    }
    
    bounce = null;
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removePlayedGame(this);
    }
  }

  // line 124 "../../../../../Block223Persistence.ump"
   public static  void reinitializePlayedGameAutouniqueID(List<PlayedGame> playedGames){
    nextId = 1;
        for (PlayedGame aPlayedGame : playedGames) {
          if (aPlayedGame.getId() > nextId) {
            nextId = aPlayedGame.getId();
          }
        }
        nextId++;
  }


  /**
   * Guards
   */
  // line 36 "../../../../../Block223States.ump"
   private boolean hitPaddle(){
    BouncePoint bp = calculateBouncePointPaddle();
	if(bp != null) {
		setBounce(bp);
		return true;
	}
    return false;
  }

  // line 45 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointPaddle(){
    Line2D ball = calculateTrajectory();
	   BouncePoint bp = null;
	   BouncePoint bp_temp = null;
	   double radius = Ball.BALL_DIAMETER / 2;
	   Rectangle2D A = new Rectangle2D.Double(currentPaddleX, currentPaddleY - radius, currentPaddleLength, radius);
	   Rectangle2D B = new Rectangle2D.Double(currentPaddleX - radius, currentPaddleY, radius, Paddle.PADDLE_WIDTH);
	   Rectangle2D C = new Rectangle2D.Double(currentPaddleX + currentPaddleLength, currentPaddleY, radius, Paddle.PADDLE_WIDTH);
	   Rectangle2D E = new Rectangle2D.Double(currentPaddleX - radius, currentPaddleY - radius, radius, radius);
	   Rectangle2D F = new Rectangle2D.Double(currentPaddleX + currentPaddleLength, currentPaddleY - radius, radius, radius);

	   if(A.intersectsLine(ball)) {
		   Line2D line = new Line2D.Double(currentPaddleX, currentPaddleY - radius, currentPaddleX + currentPaddleLength, currentPaddleY - radius);
		   bp = findIntersection(ball, line);
		   if(bp != null) {
			   bp.setDirection(BouncePoint.BounceDirection.FLIP_Y);
		   }
	   }
	   if(B.intersectsLine(ball)) {
		   Line2D line = new Line2D.Double(currentPaddleX - radius, currentPaddleY, currentPaddleX - radius, currentPaddleY + Paddle.PADDLE_WIDTH);
		   bp_temp = findIntersection(ball, line);
		   if(bp_temp != null) {
			   bp_temp.setDirection(BouncePoint.BounceDirection.FLIP_X);
			   if(bp != null) {
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
				   if(distance1 < distance2) {
					   bp = bp_temp;
				   }
			   }
			   else
				   bp = bp_temp;
		   }
	   }
	   if(C.intersectsLine(ball)) {
		   Line2D line = new Line2D.Double(currentPaddleX + currentPaddleLength + radius, currentPaddleY, currentPaddleX + currentPaddleLength + radius, currentPaddleY + Paddle.PADDLE_WIDTH);
		   bp_temp = findIntersection(ball, line);
		   if(bp_temp != null) {
			   bp_temp.setDirection(BouncePoint.BounceDirection.FLIP_X);
			   if(bp != null) {
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
				   if(distance1 < distance2) {
					   bp = bp_temp;
				   }
			   }
			   else
				   bp = bp_temp;
		   }
	   }
	   
	   if(E.intersectsLine(ball)) {
		   double centerX = E.getX() + radius;
		   double centerY = E.getY() + radius;	
		   Point2D circle = new Point2D.Double(centerX, centerY);
		   List<Point2D> intersects = findIntersectionCircle(ball, circle, radius);
		   if (!intersects.isEmpty()) {
			   BouncePoint bp_temp1 = null;
			   BouncePoint bp_temp2 = null;
			   
			   for(Point2D point : intersects) {
				   if(E.contains(point)) {
					   bp_temp1 = bp_temp2;
					   bp_temp2 = new BouncePoint(point.getX(), point.getY(), BouncePoint.BounceDirection.FLIP_X);
					   if(ball.getX1() < ball.getX2())
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_X);
					   else
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_Y);
				   }
			   }
			   if(bp_temp1 != null && bp_temp2 != null) {
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp1.getX(), bp_temp1.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp2.getX(), bp_temp2.getY());
				   if(distance1 < distance2)
					   bp_temp = bp_temp1;
				   else
					   bp_temp = bp_temp2;
			   }
			   else if(bp_temp1 != null)
				   bp_temp = bp_temp1;
			   else
				   bp_temp = bp_temp2;
				   
			   if(bp_temp != null) {
				   if(bp != null) {
					   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
					   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
					   if(distance1 < distance2) {
						   bp = bp_temp;
					   }
				   }
				   else
					   bp = bp_temp;
			   }
		   }
	   }
	   if(F.intersectsLine(ball)) {
		   double centerX = F.getX();
		   double centerY = F.getY() + radius;
		   Point2D circle = new Point2D.Double(centerX, centerY);
		   List<Point2D> intersects = findIntersectionCircle(ball, circle, radius);
		   if (!intersects.isEmpty()) {
			   BouncePoint bp_temp1 = null;
			   BouncePoint bp_temp2 = null;
			   for(Point2D point : intersects) {
				   if(F.contains(point)) {
					   bp_temp1 = bp_temp2;
					   bp_temp2 = new BouncePoint(point.getX(), point.getY(), BouncePoint.BounceDirection.FLIP_X);
					   if(ball.getX1() <= ball.getX2())
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_Y);
					   else
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_X);
				   }
			   }
			   if(bp_temp1 != null && bp_temp2 != null) {
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp1.getX(), bp_temp1.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp2.getX(), bp_temp2.getY());
				   if(distance1 < distance2)
					   bp_temp = bp_temp1;
				   else
					   bp_temp = bp_temp2;
			   }
			   else if(bp_temp1 != null)
				   bp_temp = bp_temp1;
			   else
				   bp_temp = bp_temp2;
			   if(bp_temp != null) { 
				   if(bp != null) {
					   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
					   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
					   if(distance1 < distance2) {
						   bp = bp_temp;
					   }
				   }
				   else
					   bp = bp_temp;
			   }
			   
		   }
	   }  
	return bp;
  }

  // line 188 "../../../../../Block223States.ump"
   private List<Point2D> findIntersectionCircle(Line2D ball, Point2D center, double radius){
    List<Point2D> intersects = new ArrayList<Point2D>();

	  double deltaX = ball.getX2() - ball.getX1();
	  double deltaY = ball.getY2() - ball.getY1();
	  double cX = center.getX() - ball.getX1();
	  double cY = center.getY() - ball.getY1();

	  double a = deltaX * deltaX + deltaY * deltaY;
	  double circle = (cX * cX + cY * cY - radius * radius) / a;
	  double b2c = (deltaX * cX + deltaY * cY) / a;

	  double disc = b2c * b2c - circle;
	  if(disc < 0)
		  return intersects;

	  double sqrtDisc = Math.sqrt(disc);
	  double scaling1 = -b2c + sqrtDisc;
	  double scaling2 = -b2c - sqrtDisc;

	  double p1X = ball.getX1() - deltaX * scaling1;
	  double p1Y = ball.getY1() - deltaY * scaling1;

	  if(p1X >= Math.min(ball.getX1(), ball.getX2()) && p1X <= Math.max(ball.getX1(), ball.getX2())
			  && p1Y >= Math.min(ball.getY1(), ball.getY2()) && p1Y <= Math.max(ball.getY1(), ball.getY2())) {

		  Point2D p1 = new Point2D.Double(p1X, p1Y);
		  intersects.add(p1);
		  if (disc == 0)
			  return intersects;

		  double p2X = ball.getX1() - deltaX * scaling2;
		  double p2Y = ball.getY1() - deltaY * scaling2;

		  if(p2X >= Math.min(ball.getX1(), ball.getX2()) && p2X <= Math.max(ball.getX1(), ball.getX2())
				  && p2Y >= Math.min(ball.getY1(), ball.getY2()) && p2Y <= Math.max(ball.getY1(), ball.getY2())) {
			  Point2D p2 = new Point2D.Double(p2X, p2Y);
			  intersects.add(p2);
		  }
	  }
		  return intersects;
  }

  // line 231 "../../../../../Block223States.ump"
   private BouncePoint findIntersection(Line2D ball, Line2D line){
    BouncePoint bp = null;
    double slope, b, intersect;
	// check vertical
	if(line.getX1() == line.getX2()){
		if (ball.getX1() == ball.getX2()){
			if(line.getX1() == ball.getX1()) {
				return new BouncePoint(ball.getX2(), ball.getY2(), BouncePoint.BounceDirection.FLIP_BOTH);
			}
			return bp;
		}
		else{
			if (ball.getX2() != ball.getX1()) {
				slope = (ball.getY2() - ball.getY1()) / (ball.getX2() - ball.getX1());
				b = ball.getY1() - slope*ball.getX1();
				intersect = slope*line.getX1() + b; // y = aX + b
			}
			else
				intersect = ball.getY1();
			if(intersect >= Math.min(ball.getY1(), ball.getY2()) && intersect <= Math.max(ball.getY1(), ball.getY2())
	&& intersect >= Math.min(line.getY1(), line.getY2()) && intersect <= Math.max(line.getY1(), line.getY2())) {
				return new BouncePoint(line.getX1(), intersect, BouncePoint.BounceDirection.FLIP_BOTH);
			}
			return bp;
		}
	}
	// case horizontal
	else{
		
		if(ball.getY1() == ball.getY2()) {
			if(ball.getY1() == line.getY1())
				return new BouncePoint(ball.getX2(), ball.getY2(), BouncePoint.BounceDirection.FLIP_BOTH);
		}
		
		if (ball.getX2() != ball.getX1()) {
			slope = (ball.getY2() - ball.getY1()) / (ball.getX2() - ball.getX1());
			b = ball.getY1() - slope*ball.getX1();
			intersect = (line.getY1() - b)/ slope;
		}
		else
		intersect = ball.getX1();
		if(intersect >= Math.min(ball.getX1(), ball.getX2()) && intersect <= Math.max(ball.getX1(), ball.getX2())
	&& intersect >= Math.min(line.getX1(), line.getX2()) && intersect <= Math.max(line.getX1(), line.getX2())) {
			return new BouncePoint(intersect, line.getY1(), BouncePoint.BounceDirection.FLIP_BOTH);
		}
		return bp;
	}
  }

  // line 280 "../../../../../Block223States.ump"
   private Line2D calculateTrajectory(){
    double radius = Ball.BALL_DIAMETER / 2;
		double newX = currentBallX + ballDirectionX;
		double newY =  currentBallY + ballDirectionY;
		Line2D trajectory = new Line2D.Double(currentBallX, currentBallY, newX, newY);
		return trajectory;
  }

  // line 288 "../../../../../Block223States.ump"
   private boolean isOutOfBoundsAndLastLife(){
    boolean outOfBounds = false;
  	if(lives == 1)
  	{
  		outOfBounds = isBallOutOfBounds();
  	}
  	return outOfBounds;
  }

  // line 297 "../../../../../Block223States.ump"
   private boolean isOutOfBounds(){
    return isBallOutOfBounds();
  }

  // line 302 "../../../../../Block223States.ump"
   private boolean isBallOutOfBounds(){
    int aPlaySize = Game.PLAY_AREA_SIDE;
  	int aVerticalDistance = Paddle.VERTICAL_DISTANCE;
  	
  	if(currentBallY >= (aPlaySize - aVerticalDistance))
  	{
  		return true;
  	}
  	return false;
  }

  // line 313 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointBlock(PlayedBlockAssignment b){
    Line2D ball = this.calculateTrajectory();
	   BouncePoint bp = null;
	   BouncePoint bp_temp = null;
	   
	   double radius = Ball.BALL_DIAMETER/2;
	   
	   Rectangle2D A = new Rectangle2D.Double(b.getX(),b.getY() - radius,Block.SIZE,radius);
	   
	   Rectangle2D B = new Rectangle2D.Double(b.getX() - radius,b.getY(),radius,Block.SIZE);
	   
	   Rectangle2D C = new Rectangle2D.Double(b.getX() + Block.SIZE,b.getY(),radius,Block.SIZE);
	   Rectangle2D D = new Rectangle2D.Double(b.getX(),b.getY() + Block.SIZE,Block.SIZE,radius);
	   
	   Rectangle2D E = new Rectangle2D.Double(b.getX()- radius,b.getY() - radius,radius,radius);
	   
	   Rectangle2D F = new Rectangle2D.Double(b.getX() + Block.SIZE,b.getY() - radius,radius,radius);
	   
	   Rectangle2D G = new Rectangle2D.Double(b.getX() - radius,b.getY() + Block.SIZE,radius,radius);
	   
	   Rectangle2D H = new Rectangle2D.Double(b.getX() + Block.SIZE,b.getY() + Block.SIZE,radius,radius);
	   
	   
	   
	   if(A.intersectsLine(ball)) {
		   
		   Line2D line = new Line2D.Double(b.getX(),b.getY() - radius,b.getX() + Block.SIZE,b.getY() - radius);
		   bp = findIntersection(ball, line);
		   if(bp!=null && bp.getY()==ball.getY2()) {
			   bp = null;
		   }
		   if(bp != null) {
			   bp.setDirection(BouncePoint.BounceDirection.FLIP_Y);
		   }
	   }
	   
	   if(B.intersectsLine(ball)) {
		   
		   Line2D line = new Line2D.Double(b.getX() - radius,b.getY(),b.getX() - radius,b.getY() + Block.SIZE);
		   bp_temp = findIntersection(ball, line);
		   if(bp_temp!=null) {
			   if(!(bp_temp.getX()==ball.getX2())) {
				   
			   
				   if(bp_temp != null) {
					   bp_temp.setDirection(BouncePoint.BounceDirection.FLIP_X);
					   if(bp != null) {
						   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
						   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
						   if(distance1 < distance2) {
							   
							   bp = bp_temp;
						   }
					   }
					   else
						   bp = bp_temp;
				   }
			   
			   }
		   }
	   }
	   if(C.intersectsLine(ball)) {
		   
		   Line2D line = new Line2D.Double(b.getX() + radius + Block.SIZE,b.getY(),b.getX() + radius + Block.SIZE,b.getY() + Block.SIZE);
		   bp_temp = findIntersection(ball, line);
		   if(bp_temp !=null && !(bp_temp.getX()==ball.getX2())) {
			   if(bp_temp != null) {
				   bp_temp.setDirection(BouncePoint.BounceDirection.FLIP_X);
				   if(bp != null) {
					   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
					   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
					   if(distance1 < distance2) {
						   bp = bp_temp;
						   
					   }
				   }
				   else
					   bp = bp_temp;
			   }
		   
		   }
	   }
	   
	   if(D.intersectsLine(ball)) {
		   
		   Line2D line = new Line2D.Double(b.getX(),b.getY() + Block.SIZE + radius,b.getX() + Block.SIZE,b.getY() + Block.SIZE + radius);
		   bp_temp = findIntersection(ball, line);
		   
		   if(bp_temp != null) {
			   bp_temp.setDirection(BouncePoint.BounceDirection.FLIP_Y);
			   if(bp != null) {
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
				   if(distance1 < distance2) {
					   bp = bp_temp;
					   
				   }
			   }
			   else
				   bp = bp_temp;
		   }
		   if(bp_temp!= null && bp_temp.getY()==ball.getY2()) {
			   bp = null;
		   }
	   }
	   
	   if(E.intersectsLine(ball)) {
		   
		   double centerX = E.getX() + radius;
		   double centerY = E.getY() + radius;	
		   Point2D circle = new Point2D.Double(centerX, centerY);
		   List<Point2D> intersects = findIntersectionCircle(ball, circle, radius);
		   if (!intersects.isEmpty()) {
			   
			   BouncePoint bp_temp1 = null;
			   BouncePoint bp_temp2 = null;
			   
			   for(Point2D point : intersects) {
				   
				   if(E.contains(point)) {
					   
					   if(point.getX()==ball.getX2() || point.getY() == ball.getY2()) {
						   bp = null; continue;
					   }
					   bp_temp1 = bp_temp2;
					   bp_temp2 = new BouncePoint(point.getX(), point.getY(), BouncePoint.BounceDirection.FLIP_X);
					   if(ball.getX1() < ball.getX2())
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_X);
					   else
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_Y);
				   }
			   }
			   if(bp_temp1 != null && bp_temp2 != null) {
				   
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp1.getX(), bp_temp1.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp2.getX(), bp_temp2.getY());
				   if(distance1 < distance2)
					   bp_temp = bp_temp1;
				   else
					   bp_temp = bp_temp2;
			   }
			   else if(bp_temp1 != null)
				   bp_temp = bp_temp1;
			   else
				   bp_temp = bp_temp2;
				   
			   if(bp_temp != null) {
				   if(bp != null) {
					   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
					   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
					   if(distance1 < distance2) {
						   
						   bp = bp_temp;
					   }
				   }
				   else
					   bp = bp_temp;
				   
			   }
		   }
	   }
	   
	   if(F.intersectsLine(ball)) {
		   
		   double centerX = F.getX();
		   double centerY = F.getY() + radius;
		   Point2D circle = new Point2D.Double(centerX, centerY);
		   
		   List<Point2D> intersects = findIntersectionCircle(ball, circle, radius);
		   if (!intersects.isEmpty()) {
			   
			   BouncePoint bp_temp1 = null;
			   BouncePoint bp_temp2 = null;
			   for(Point2D point : intersects) {
				   
				   if(F.contains(point)) {
					   
					   if(point.getX()==ball.getX2() || point.getY() == ball.getY2()) {
						   bp = null; continue;
					   }
					   bp_temp1 = bp_temp2;
					   bp_temp2 = new BouncePoint(point.getX(), point.getY(), BouncePoint.BounceDirection.FLIP_X);
					   if(ball.getX1() <= ball.getX2())
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_Y);
					   else
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_X);
				   }
			   }
			   if(bp_temp1 != null && bp_temp2 != null) {
				   
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp1.getX(), bp_temp1.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp2.getX(), bp_temp2.getY());
				   if(distance1 < distance2)
					   bp_temp = bp_temp1;
				   else
					   bp_temp = bp_temp2;
			   }
			   else if(bp_temp1 != null)
				   bp_temp = bp_temp1;
			   else
				   bp_temp = bp_temp2;
			   if(bp_temp != null) {  
				   
				   if(bp != null) {
					   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
					   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
					   if(distance1 < distance2) {
						   
						   bp = bp_temp;
					   }
				   }
				   else
					   bp = bp_temp;
				   
			   }
			   
		   }
	   } 
	   
	   if(G.intersectsLine(ball)) {
		   
		   double centerX = G.getX() + radius;
		   double centerY = G.getY();	
		   Point2D circle = new Point2D.Double(centerX, centerY);
		   List<Point2D> intersects = findIntersectionCircle(ball, circle, radius);
		   if (!intersects.isEmpty()) {
			   
			   BouncePoint bp_temp1 = null;
			   BouncePoint bp_temp2 = null;
			   
			   for(Point2D point : intersects) {
				   
				   if(G.contains(point)) {
					   if(point.getX()==ball.getX2() || point.getY() == ball.getY2()) {
						   bp = null; continue;
					   }
					   bp_temp1 = bp_temp2;
					   bp_temp2 = new BouncePoint(point.getX(), point.getY(), BouncePoint.BounceDirection.FLIP_X);
					   if(ball.getX1() < ball.getX2())
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_X);
					   else
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_Y);
				   }
			   }
			   if(bp_temp1 != null && bp_temp2 != null) {
				   
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp1.getX(), bp_temp1.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp2.getX(), bp_temp2.getY());
				   if(distance1 < distance2)
					   bp_temp = bp_temp1;
				   else
					   bp_temp = bp_temp2;
			   }
			   else if(bp_temp1 != null)
				   bp_temp = bp_temp1;
			   else
				   bp_temp = bp_temp2;
				   
			   if(bp_temp != null) {
				   if(bp != null) {
					   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
					   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
					   if(distance1 < distance2) {
						   
						   bp = bp_temp;
					   }
				   }
				   else
					   bp = bp_temp;
				   
			   }
		   }
	   }
	   
	   if(H.intersectsLine(ball)) {
		   
		   double centerX = H.getX();
		   double centerY = H.getY();
		   Point2D circle = new Point2D.Double(centerX, centerY);
		   
		   List<Point2D> intersects = findIntersectionCircle(ball, circle, radius);
		   if (!intersects.isEmpty()) {
			   
			   BouncePoint bp_temp1 = null;
			   BouncePoint bp_temp2 = null;
			   for(Point2D point : intersects) {
				   
				   if(H.contains(point)) {
					   
					   if(point.getX()==ball.getX2() || point.getY() == ball.getY2()) {
						   bp = null; continue;
					   }
					   bp_temp1 = bp_temp2;
					   bp_temp2 = new BouncePoint(point.getX(), point.getY(), BouncePoint.BounceDirection.FLIP_X);
					   if(ball.getX1() <= ball.getX2())
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_Y);
					   else
						   bp_temp2.setDirection(BouncePoint.BounceDirection.FLIP_X);
				   }
			   }
			   if(bp_temp1 != null && bp_temp2 != null) {
				   
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp1.getX(), bp_temp1.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp2.getX(), bp_temp2.getY());
				   if(distance1 < distance2)
					   bp_temp = bp_temp1;
				   else
					   bp_temp = bp_temp2;
			   }
			   else if(bp_temp1 != null)
				   bp_temp = bp_temp1;
			   else
				   bp_temp = bp_temp2;
			   if(bp_temp != null) {  
				   
				   if(bp != null) {
					   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
					   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
					   if(distance1 < distance2) {
						   
						   bp = bp_temp;
					   }
				   }
				   else
					   bp = bp_temp;
				   
			   }
			   
		   }
	   }
	   
	   return bp;
  }

  // line 648 "../../../../../Block223States.ump"
   private boolean hitLastBlockAndLastLevel(){
    Game game = this.getGame();
    int numLevels = game.numberOfLevels();
    this.setBounce(null);
    if(numLevels == currentLevel) {
    	
    	int numBlocks = this.numberOfBlocks();
    	if (numBlocks == 1) {
    		PlayedBlockAssignment b = this.getBlock(0);
    		BouncePoint bp = this.calculateBouncePointBlock(b);
    		if(bp != null) {
    			bp.setHitBlock(b);
    		}
    		
    		this.setBounce(bp);
    		return bp != null;
    	}
    	
    }
    
    return false;
  }

  // line 671 "../../../../../Block223States.ump"
   private boolean hitLastBlock(){
    int numBlocks = this.numberOfBlocks();
	   this.setBounce(null);
	   if (numBlocks == 1) {
		   PlayedBlockAssignment block = this.getBlock(0);
		   BouncePoint bp = calculateBouncePointBlock(block);
		   if(bp != null) {
			   bp.setHitBlock(block); 
		   }
		   
		   setBounce(bp);
		   return bp != null;
	   }
	   return false;
  }

  // line 688 "../../../../../Block223States.ump"
   private boolean isCloser(BouncePoint b1, BouncePoint b2){
    if(b2 == null) {
		   return true;
	   }
	   if(b1 == null) {
		   return false;
	   }
	   double distance1 = Point2D.distance(this.getCurrentBallX(), this.getCurrentBallY(), b1.getX(), b1.getY());
	   double distance2 = Point2D.distance(this.getCurrentBallX(), this.getCurrentBallY(), b2.getX(), b2.getY()); 
	   return (distance1 < distance2);
  }

  // line 701 "../../../../../Block223States.ump"
   private boolean hitBlock(){
    int numBlocks = this.numberOfBlocks();
	   this.setBounce(null);
	   for (int i = 0; i < numBlocks; i++) {
		   
		   PlayedBlockAssignment block = this.getBlock(i);
		   BouncePoint bp = calculateBouncePointBlock(block);
		   bounce = this.getBounce();
		   
		   boolean closer = isCloser(bp,bounce);
		   if(closer) {
			   
			   if(bp != null) {
				   bp.setHitBlock(block); 
			   }
			   setBounce(bp);
			   
		   }
		   
	   }
	   return this.getBounce() != null;
  }

  // line 724 "../../../../../Block223States.ump"
   private boolean hitWall(){
    BouncePoint bp = calculateBouncePointWall();
	if(bp != null) {
		setBounce(bp);
		return true;
	}
    return false;
  }

  // line 733 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointWall(){
    Line2D ball = calculateTrajectory();
	   BouncePoint bp = null;
	   BouncePoint bp_temp = null;
	   double radius = Ball.BALL_DIAMETER / 2;
	   
	   Rectangle2D A = new Rectangle2D.Double(0, 0, radius, Game.PLAY_AREA_SIDE - radius);
	   Rectangle2D B = new Rectangle2D.Double(radius, 0, Game.PLAY_AREA_SIDE - 2*radius, radius);
	   Rectangle2D C = new Rectangle2D.Double(Game.PLAY_AREA_SIDE - radius, 0, radius, Game.PLAY_AREA_SIDE - radius);
	   
	   if(A.intersectsLine(ball)) {
		   Line2D line = new Line2D.Double(radius, radius, radius, Game.PLAY_AREA_SIDE - radius);
		   bp = findIntersection(ball, line);
		   if(bp != null)
			   bp.setDirection(BouncePoint.BounceDirection.FLIP_X);
	   }
	   if(B.intersectsLine(ball)) {
		   Line2D line = new Line2D.Double(radius, radius, Game.PLAY_AREA_SIDE - radius, radius);
		   bp_temp = findIntersection(ball, line);
		   if(bp_temp != null) {
			   bp_temp.setDirection(BouncePoint.BounceDirection.FLIP_Y);
			   if(bp != null) {
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
				   if(distance1 < distance2)
					   bp = bp_temp;
			   }
			   else
				   bp = bp_temp;
		   }
	   }
	   if(C.intersectsLine(ball)) {
		   Line2D line = new Line2D.Double(Game.PLAY_AREA_SIDE - radius, radius, Game.PLAY_AREA_SIDE - radius, Game.PLAY_AREA_SIDE - radius);
		   bp_temp = findIntersection(ball, line);
		   if(bp_temp != null) {
			   bp_temp.setDirection(BouncePoint.BounceDirection.FLIP_X);
			   if(bp != null) {
				   double distance1 = Point2D.distance(ball.getX1(), ball.getY1(), bp_temp.getX(), bp_temp.getY());
				   double distance2 = Point2D.distance(ball.getX1(), ball.getY1(), bp.getX(), bp.getY());
				   if(distance1 < distance2)
					   bp = bp_temp;
			   }
			   else
				   bp = bp_temp;
		   }
	   }
	   if(bp != null) {
		   if((bp.getX() == radius && bp.getY() == radius) || (bp.getX() == Game.PLAY_AREA_SIDE - radius && bp.getY() == radius))
			   bp.setDirection(BouncePoint.BounceDirection.FLIP_BOTH);
	   }
	   return bp;
  }


  /**
   * Actions
   */
  // line 788 "../../../../../Block223States.ump"
   private void doSetup(){
    resetCurrentBallX();
        resetCurrentBallY();
        resetBallDirectionX();
        resetBallDirectionY();
        resetCurrentPaddleX();
        Game game = this.getGame();
        Level level = game.getLevel(currentLevel-1);
        List<BlockAssignment> assignments = level.getBlockAssignments();

      for(BlockAssignment assignment: assignments){
        PlayedBlockAssignment pblock = new PlayedBlockAssignment(Game.WALL_PADDING+(Block.SIZE+Game.COLUMNS_PADDING)*(assignment.getGridHorizontalPosition()-1),
                Game.WALL_PADDING+(Block.SIZE+Game.ROW_PADDING)*(assignment.getGridVerticalPosition()-1),
                assignment.getBlock(), this);
      }

        int maxX = game.getMaxNumberOfHorizontalBlocks();
        int maxY = game.getMaxNumberOfVerticalBlocks();
        while(this.numberOfBlocks() < game.getNrBlocksPerLevel()){
           int x = (int)(Math.random()*(maxX-1) + 1);
        	int y = (int)(Math.random()*(maxY-1) + 1);

          while(true){
            int pixelX = Game.WALL_PADDING+(Block.SIZE+Game.COLUMNS_PADDING)*(x-1);
        	int pixelY = Game.WALL_PADDING+(Block.SIZE+Game.ROW_PADDING)*(y-1);
          if(openSpot(pixelX,pixelY)){
            PlayedBlockAssignment pblock = new PlayedBlockAssignment(pixelX,pixelY,game.getRandomBlock(), this);
            break;
          }
          x++;
          if(x == maxX+1) {
            y++;
            if (y == maxY+1) {
              y = 1;
            }
            x = 1;
          }
        }
      }

  }

  // line 833 "../../../../../Block223States.ump"
   private boolean openSpot(int x, int y){
    for(PlayedBlockAssignment block: blocks){
         if(block.getX()==x && block.getY()==y)
           return false;
       }
       return true;
  }

  // line 841 "../../../../../Block223States.ump"
   private void doHitPaddleOrWall(){
    bounceBall();
  }

  // line 845 "../../../../../Block223States.ump"
   private void bounceBall(){
    if(bounce.getDirection() == BouncePoint.BounceDirection.FLIP_X) {
		  double rem = ballDirectionX - (bounce.getX() - currentBallX);
		  double factor = rem;
		  if(ballDirectionX != 0)
			  factor = rem / ballDirectionX;
		  
		  // for case of 0  
		  if (rem == 0) {
			  setCurrentBallX(bounce.getX());
			  setCurrentBallY(bounce.getY());
			  return;
		  }
			  
		  setBallDirectionX(-ballDirectionX);
		  setCurrentBallX(bounce.getX() + factor * ballDirectionX);
		  if(ballDirectionY < 0) {
			  setBallDirectionY(ballDirectionY - 0.1 * Math.abs(ballDirectionX));
		  }
		  else {
			  setBallDirectionY(ballDirectionY + 0.1 * Math.abs(ballDirectionX));
		  }
		  setCurrentBallY(bounce.getY() + factor * ballDirectionY);
		  if (currentBallX < 0)
			  setCurrentBallX(5);
		  if (currentBallY < 0)
			  setCurrentBallY(5);
	  }
	  if(bounce.getDirection() == BouncePoint.BounceDirection.FLIP_Y) {
		  double rem = ballDirectionY - (bounce.getY() - currentBallY);
		  double factor = rem / ballDirectionY;
		  
		  // for case of 0  
		  if (rem == 0) {
			  setCurrentBallX(bounce.getX());
			  setCurrentBallY(bounce.getY());
			  return;
		  }
		  
		  setBallDirectionY(-1*ballDirectionY);
		  setCurrentBallY(bounce.getY() + factor * ballDirectionY);
		  if(ballDirectionX < 0) {
			  
			  setBallDirectionX(ballDirectionX - 0.1 * Math.abs(ballDirectionY));
		  }
		  else {
			  setBallDirectionX(ballDirectionX + 0.1 * Math.abs(ballDirectionY));
		  }
		  setCurrentBallX(bounce.getX() + factor * ballDirectionX);  
		  if (currentBallX < 0)
			  setCurrentBallX(5);
		  if (currentBallY < 0)
			  setCurrentBallY(5);
	  }
	  if(bounce.getDirection() == BouncePoint.BounceDirection.FLIP_BOTH) {
		  double remY = ballDirectionY - (bounce.getY() - currentBallY);
		  double remX = ballDirectionX - (bounce.getX() - currentBallX);
		  if (remY == 0 || remX == 0) {
			  setCurrentBallX(bounce.getX());
			  setCurrentBallY(bounce.getY());
			  return;
		  }
		  
		  setCurrentBallY(2*bounce.getY() - ballDirectionY - currentBallY);
		  setCurrentBallX(2*bounce.getX() - ballDirectionX - currentBallX);
		  setBallDirectionY(-ballDirectionY);
		  setBallDirectionX(-ballDirectionX);
		  
		 if (currentBallX < 0)
			  setCurrentBallX(5);
		  if (currentBallY < 0)
			  setCurrentBallY(5);
	  }
	   if (Math.abs(ballDirectionX) >= 10 || Math.abs(ballDirectionY) >= 10) {
		  setBallDirectionX(ballDirectionX/10);
		  setBallDirectionY(ballDirectionY/10);
	  }
  }

  // line 925 "../../../../../Block223States.ump"
   private void doOutOfBounds(){
    setLives(lives - 1);
  	resetCurrentBallX();
  	resetCurrentBallY();
  	resetBallDirectionX();
  	resetBallDirectionY();
  	resetCurrentPaddleX();
  }

  // line 934 "../../../../../Block223States.ump"
   private void bounceBallBlock(){
    if(bounce.getDirection() == BouncePoint.BounceDirection.FLIP_X) {
			  double rem = ballDirectionX - (bounce.getX() - currentBallX);
			  double factor = rem;
			  if(ballDirectionX != 0)
				  factor = rem / ballDirectionX;
			  
			  // for case of 0  
			  if (rem == 0) {
				  setCurrentBallX(bounce.getX());
				  setCurrentBallY(bounce.getY());
				  return;
			  }
				  
			  setBallDirectionX(-ballDirectionX);
			  setCurrentBallX(bounce.getX() + factor * ballDirectionX);
			  if(ballDirectionY < 0) {
				  setBallDirectionY(ballDirectionY - 0.1 * Math.abs(ballDirectionX));
			  }
			  else {
				  setBallDirectionY(ballDirectionY + 0.1 * Math.abs(ballDirectionX));
			  }
			  setCurrentBallY(bounce.getY() + factor * ballDirectionY);
		  }
		  if(bounce.getDirection() == BouncePoint.BounceDirection.FLIP_Y) {
			  double rem = ballDirectionY - (bounce.getY() - currentBallY);
			  double factor = rem / ballDirectionY;
			  
			  // for case of 0  
			  
			  setBallDirectionY(-1*ballDirectionY);
			  setCurrentBallY(bounce.getY() + factor * ballDirectionY);
			  if(ballDirectionX < 0) {
				  
				  setBallDirectionX(ballDirectionX - 0.1 * Math.abs(ballDirectionY));
			  }
			  else {
				  setBallDirectionX(ballDirectionX + 0.1 * Math.abs(ballDirectionY));
			  }
			  
			  setCurrentBallX(bounce.getX() + factor * ballDirectionX);  
			  
			  
		  }if(bounce.getDirection() == BouncePoint.BounceDirection.FLIP_BOTH) {
			  double remY = ballDirectionY - (bounce.getY() - currentBallY);
			  double remX = ballDirectionX - (bounce.getX() - currentBallX);
			  if (remY == 0 || remX == 0) {
				  setCurrentBallX(bounce.getX());
				  setCurrentBallY(bounce.getY());
				  return;
			  }
			  
			  setCurrentBallY(2*bounce.getY() - ballDirectionY - currentBallY);
			  setCurrentBallX(2*bounce.getX() - ballDirectionX - currentBallX);
			  setBallDirectionY(-ballDirectionY);
			  setBallDirectionX(-ballDirectionX);
		  }
		   if (Math.abs(ballDirectionX) >= 10 || Math.abs(ballDirectionY) >= 10) {
		  setBallDirectionX(ballDirectionX/10);
		  setBallDirectionY(ballDirectionY/10);
	  }
  }

  // line 997 "../../../../../Block223States.ump"
   private void doHitBlock(){
    int score = this.getScore();
	   BouncePoint bp = this.getBounce();
	   
	   PlayedBlockAssignment pBlock = bp.getHitBlock();
	   Block block = pBlock.getBlock();
	   int points = block.getPoints();
	   this.setScore(points + score);
	   pBlock.delete();
	   bounceBallBlock();
	   
	   bounce = null;
  }

  // line 1011 "../../../../../Block223States.ump"
   private void doHitBlockNextLevel(){
    int level = this.getCurrentLevel();
    doHitBlock();
    resetBallDirectionX();
	   resetBallDirectionY();
	   this.setCurrentLevel(level + 1);
	   this.setCurrentPaddleLength(getGame().getPaddle().getMaxPaddleLength() - (getGame().getPaddle().getMaxPaddleLength()- 
			   getGame().getPaddle().getMinPaddleLength())/(getGame().numberOfLevels() - 1) * (getCurrentLevel() - 1));
	   this.setWaitTime(INITIAL_WAIT_TIME * (Math.pow(getGame().getBall().getBallSpeedIncreaseFactor(), (getCurrentLevel() - 1))));
	   

	   bounce = null;
  }

  // line 1023 "../../../../../Block223States.ump"
   private void doHitNothingAndNotOutOfBounds(){
    double x = getCurrentBallX();
     double y = getCurrentBallY();
     double dx = getBallDirectionX();
     double dy = getBallDirectionY();
     setCurrentBallX(x+dx);
     setCurrentBallY(y+dy);
      if (currentBallX < 0)
		  setCurrentBallX(5);
	  if (currentBallY < 0)
		  setCurrentBallY(5);
	  if (currentBallX > 390)
		  setCurrentBallX(385);
	  if (currentBallY > 390)
		  setCurrentBallY(390);
     return;
  }

  // line 1041 "../../../../../Block223States.ump"
   private void doGameOver(){
    block223 = getBlock223();
    Player p = getPlayer();
    
    if(p != null)
    {
    	game = getGame();
    	HallOfFameEntry hof = new HallOfFameEntry(score, playername, p, game, block223);
    	game.setMostRecentEntry(hof);
    }
    delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "score" + ":" + getScore()+ "," +
            "lives" + ":" + getLives()+ "," +
            "currentLevel" + ":" + getCurrentLevel()+ "," +
            "waitTime" + ":" + getWaitTime()+ "," +
            "playername" + ":" + getPlayername()+ "," +
            "ballDirectionX" + ":" + getBallDirectionX()+ "," +
            "ballDirectionY" + ":" + getBallDirectionY()+ "," +
            "currentBallX" + ":" + getCurrentBallX()+ "," +
            "currentBallY" + ":" + getCurrentBallY()+ "," +
            "currentPaddleLength" + ":" + getCurrentPaddleLength()+ "," +
            "currentPaddleX" + ":" + getCurrentPaddleX()+ "," +
            "currentPaddleY" + ":" + getCurrentPaddleY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bounce = "+(getBounce()!=null?Integer.toHexString(System.identityHashCode(getBounce())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 121 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 8597675110221231714L ;

  
}