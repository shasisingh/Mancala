/**
 * 
 */
/**
 * @Author shasisingh
 * Game Rule: https://www.thesprucecrafts.com/how-to-play-mancala-409424
 */
package com.test.mancala.build;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;




class PlayAndBuildMancalaGame 
{
	int currentMancalaPlayer = 0;
	ManageMancalaBoard mancalaBoard;
	MancalaPlayersProfile[] playersListByName;
	int boardReshuffleInArray=0;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException 
	{

		/*
		 * Make a note: Class PlayAndBuildMancalaGame took 2 arguments ( player name 1 and player name 2)
		 * If the user doesn't provide Player name 2 make sure it will be used as null or not provided. eg.. null
		 * Once null is provided Code determined the second player as a computer.
		 */

		/*
		 *  Human Player vs Human Player
		 */

		//PlayAndBuildMancalaGame playGameMancals = new PlayAndBuildMancalaGame("Shashi As Player 1", "Aryan as Player 2","Y");

		/*
		 * 	Real Player vs Robbet
		 */

		MancalaGameRule.printGameRules();
		PlayAndBuildMancalaGame playGameMancals = new PlayAndBuildMancalaGame("Shashi", null,"Y");
		playGameMancals.playMancala();
	}

	/**
	 * @param firstPlayerName
	 * @param secondPlayerName
	 */
	PlayAndBuildMancalaGame(String firstPlayerName, String secondPlayerName,String boardReshuffle) 
	{

		mancalaBoard = new ManageMancalaBoard();
		mancalaBoard.initialSetUpForPlay();
		playersListByName = new MancalaPlayersProfile[2];

		// If you want to change the player isARealPlayername,Have a look into Main Method
		// If user not passed second player isARealPlayername,It will be used as Computer Player

		playersListByName[0] = new MancalaPlayersProfile(firstPlayerName, 0);
		playersListByName[1] = new MancalaPlayersProfile(secondPlayerName, 1);
		currentMancalaPlayer = 0;

		/*
		 *  board reshuffle is all About: Providing user view, how user play on game board, they always start from Right to Left,
		 *  But Current Array is Start from Left to Right, So If the user is thinking  Pits 6, eventually Internally about Array Index 1 ,
		 *  Internal array movements will be determined based on this. So reshuffle will change user Pits to predefined Pits.
		 */

		if(boardReshuffle.toUpperCase()=="Y") {
			boardReshuffleInArray=1;
		}else {
			boardReshuffleInArray=0;
		}
	}

	/**
	 * @throws IOException
	 */
	public void playMancala() throws IOException 
	{

		displayMancalaBoardConsole();
		while (!mancalaBoard.isGameOver()) 
		{
			String playerNamefromArray;
			if(currentMancalaPlayer==0)
			{
				playerNamefromArray=playersListByName[0].getName();	
			}else {
				playerNamefromArray=playersListByName[1].getName();	
			}

			/*
			 *  Calling Function: selectTheMove to Determine the Move, If users are human then ask for Input at main Console else best move will be determined.
			 */
			int pitNum=0;
			int sufflePitNumber = playersListByName[currentMancalaPlayer].selectTheMove(mancalaBoard); 
			if(boardReshuffleInArray==1) {
				pitNum=doBoardReshuffle(sufflePitNumber,currentMancalaPlayer);
			}else {
				pitNum=sufflePitNumber;
			}
			
				/*
				 *  Display Over Console, which player moved was along with Changed Mancala Board
				 */

			 System.out.println("Player [ " + playerNamefromArray + " ] moved from " + sufflePitNumber);

			//int pitNum = playersListByName[currentMancalaPlayer].selectTheMove(mancalaBoard,currentMancalaPlayer,boardReshuffleInArray); 

			// the move:-->  User Or Computer has Selected there Pits which need to be move , which are store in variable pitNum. 

			/*
			 *  If you run into your own store, deposit one piece in it. If you run into your opponent's store, skip it.
			 *  If the last piece you drop is in your own store, you get a free turn. ( variable : checkgoAgainCall store those information)
			 */
			boolean checkgoAgainCall = mancalaBoard.doPitsTheMove(currentMancalaPlayer, pitNum);

			/*
			 *  Display Over Console, which player moved was along with Changed Mancala Board
			 */

			displayMancalaBoardConsole();

			if (!checkgoAgainCall) 
			{	
				// If the current player does not go again,switch to the other player
				if (currentMancalaPlayer == 0) 	
					currentMancalaPlayer = 1;
				else
					currentMancalaPlayer = 0;
			}else
			{
				//If the last piece you drop is in your own Mancala , User get a free turn.
				System.out.println("Player [ " + playerNamefromArray + " ] goes again");
			}
		}
		/*
		 * The game ends when all six spaces on one side of the Mancala board are empty.
		 */

		// Game is over , mancalaBoard empty stones,
		mancalaBoard.emptyStonesIntoMancalas(); 

		// Display final mancalaBoard. 
		displayMancalaBoardConsole(); 			


		/*
		 * Below section determined Who is Winner And Winner Award goes to......
		 */
		if (mancalaBoard.stonesInMancala(0) > mancalaBoard.stonesInMancala(1)){
			System.out.println("************* Congratulations..! You Won this Game ******************************");
			System.out.println(playersListByName[0].getName() + " wins");
			System.out.println("*******************************************");
		}
		else if (mancalaBoard.stonesInMancala(0) < mancalaBoard.stonesInMancala(1)) {
			System.out.println("************* Congratulations..! You Won this Game ******************************");
			System.out.println(playersListByName[1].getName() + " wins");
			System.out.println("*******************************************");
		}
		else {
			System.out.println("************* If the score of a game is equal, then the game is a tie :) ******************************");
			System.out.println("Tie");// If this possible for safe side :)
			System.out.println("*******************************************");
		}

	}

	/**
	 * @param userInputasPit
	 * @param currentPlayer
	 * @return
	 */
	public int doBoardReshuffle(int userInputasPit,int currentPlayer)
	{
		int newPits = 0;
		if(currentPlayer==0) {
			if(userInputasPit==6)
				newPits= 1;
			else if(userInputasPit==5)
				newPits= 2;
			else if (userInputasPit==4)
				newPits= 3;
			else if (userInputasPit==3)
				newPits= 4;
			else if (userInputasPit==2)
				newPits= 5;
			else if (userInputasPit==1)
				newPits= 6;
		}else {
			newPits=userInputasPit;
		}
		return newPits;

	}
	/**
	 * 
	 */

	private void displayMancalaBoardConsole() 
	{

		/*
		 *  Function : displayMancalsBoardConsole
		 *  <<< This function get Called n times>>>, till game is not finished.
		 *  If you are able to see Console or Mancala Dashboard , this Function is created that, This function have 4 step which will
		 *  repeate each time or with each game move
		 *  step 1 : 	Display Player 2 Pits and Player 2 Information
		 *  Step 2 :  	Display Mancala information Both side
		 *  Step 3 : 	Display Player 1 Pits and Player 1 Information
		 *  Step 4 :	print Seperater ( ******* )
		 */

		try {
			/*
			 *  Have used the sleep method here, as Between 2 play, user have time to see and remember there move or Pits positions
			 */
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("\n");
		String sepraterMancalaLineFiller = ""; 
		System.out.println("*************************************");
		System.out.print("      ");

		//Step 1 : 	Display Player 2 Pits and Player 2 Information
		for (int i = 1; i <= ManageMancalaBoard.playingPits; i++) 
		{
			System.out.print(mancalaBoard.stonesInPit(1, i) + "    ");
			sepraterMancalaLineFiller += "     ";
		}

		//	Step 1.1 : 	Player 2 Information
		displayPlayer(1); 

		//	Step 2 :  	Display Mancala information Both side. Each player has a store (called a Mancala) to the right side of the Mancala board.

		System.out.print(mancalaBoard.stonesInMancala(1) + "    "); 
		System.out.print(sepraterMancalaLineFiller);
		System.out.println(mancalaBoard.stonesInMancala(0));

		System.out.print("      ");

		//	Step 3 : 	Display Player 1 Pits and Player 1 Information
		for (int i = ManageMancalaBoard.playingPits; i >= 1; i--)
			System.out.print(mancalaBoard.stonesInPit(0, i) + "    "); 

		//	Player 3.1 Information
		displayPlayer(0); 
		System.out.println("*************************************");
	}

	/**
	 * @param mancalaPlayerNum
	 */
	private void displayPlayer(int mancalaPlayerNum) 
	{
		/* 
		 *  In a Console User turn need to be printed which will indicate ,Who is Who and who is playing.
		 *  Below section is buld to determine the same
		 */

		// Check If it this player's turn,
		if (currentMancalaPlayer == mancalaPlayerNum){  
			System.out.print("            -->> ");        // --> Sign mean User Turn, Active user, Same can be visble in Console screen
		}else {
			System.out.print("                 ");       // User is not active , user loose his/her turn, Same can be visble in Console screen
		}

		/*
		 *  Below section will added to show User/Player Information to Main Console eg.. user name or User Number
		 */
		int playerCounter;
		if(mancalaPlayerNum==0) {
			playerCounter=1;
		}else {
			playerCounter=2;
		}

		System.out.println("Player " + playerCounter + " ( " + playersListByName[mancalaPlayerNum].getName() + ")");
	}





}

/**
 * @author shasisingh
 *
 */
class ManageMancalaBoard 
{
	CollectsPit[] pits;
	static final int playingPits = 6, totalPits = 2 * (playingPits + 1);


	ManageMancalaBoard() 
	{
		pits = new CollectsPit[totalPits];
		for (int pitNum = 0; pitNum < totalPits; pitNum++)
			pits[pitNum] = new CollectsPit();
	}

	public void initialSetUpForPlay() 
	{
		/*
		 *  Add 4 Stones In each Pits ( 4*6=24 )
		 */
		for (int pitNum = 0; pitNum < totalPits; pitNum++)
			if (!isAMancalaCall(pitNum))
				pits[pitNum].addStones(4);
		/*
		 *  Any Number of Stones can be added in Pits
		 */
	}

	public int stonesInMancala(int playerNum) 
	{
		return pits[getMancala(playerNum)].getStones();
	}

	public int stonesInPit(int playerNum, int pitNum) 
	{
		return pits[getPitNum(playerNum, pitNum)].getStones();
	}

	private int getPitNum(int playerNum, int pitNum) 
	{
		return playerNum * (playingPits + 1) + pitNum;
	}

	private int getMancala(int playerNum) 
	{
		return playerNum * (playingPits + 1);
	}

	private boolean isAMancalaCall(int pitNum) 
	{
		return pitNum % (playingPits + 1) == 0;
	}

	public ManageMancalaBoard makeACopyOfMancalaBoard() 
	{
		ManageMancalaBoard newBoard = new ManageMancalaBoard();
		for (int pitNum = 0; pitNum < totalPits; pitNum++)
			newBoard.pits[pitNum].addStones(this.pits[pitNum].getStones());
		return newBoard;
	}

	/**
	 * @param currentPlayerNum
	 * @param chosenPitNum
	 * @return
	 */
	public boolean doPitsTheMove(int currentPlayerNum, int chosenPitNum) 
	{
		boolean isPitesGetCalled=false;
		/*
		 * If the last piece Player drop is in an empty hole on His/her side, Player capture that piece and any pieces in the hole directly opposite.
		 */
		int pitNum = getPitNum(currentPlayerNum, chosenPitNum);
		int stones = pits[pitNum].removeStones();
		while (stones != 0) 
		{
			pitNum--;
			if (pitNum < 0)
				pitNum = totalPits - 1;
			if (pitNum != getMancala(otherPlayerNum(currentPlayerNum))) 
			{
				pits[pitNum].addStones(1);
				stones--;
			}
			isPitesGetCalled=true;
		}
		
		if(stones==0 && isPitesGetCalled==false) {
			/*
			 *  Player Selected Pits which have 0 stones in, So Ask player to goAgain and select again Pits, which value is >0;
			 */
			System.out.println("**********************************************************************************************************************************************");
			System.out.println("Warning -> Player [ "+( currentPlayerNum+1)+" ] has selected Pits without any stones in it (Pits=0),So Player has to Go Again and choose Pits from Console.");
			System.out.println("**********************************************************************************************************************************************");
			return true;
		}
		
		if (pitNum == getMancala(currentPlayerNum))
			return true;
		if (whoIsWho(pitNum) == currentPlayerNum && pits[pitNum].getStones() == 1) 
		{
			stones = pits[oppositePitNum(pitNum)].removeStones();
			pits[getMancala(currentPlayerNum)].addStones(stones);
		}
		return false;
	}

	private int whoIsWho(int pitNum) 
	{
		return pitNum / (playingPits + 1);
	}

	private int oppositePitNum(int pitNum) 
	{
		return totalPits - pitNum;
	}

	private int otherPlayerNum(int playerNum) 
	{
		if (playerNum == 0)
			return 1;
		else
			return 0;
	}

	public boolean isGameOver() 
	{
		for (int player = 0; player < 2; player++) 
		{
			int stones = 0;
			for (int pitNum = 1; pitNum <= playingPits; pitNum++)
				stones += pits[getPitNum(player, pitNum)].getStones();
			if (stones == 0)
				return true;
		}
		return false;
	}

	public void emptyStonesIntoMancalas() 
	{
		for (int player = 0; player < 2; player++)
			for (int pitNum = 0; pitNum <= playingPits; pitNum++) 
			{
				int stones = pits[getPitNum(player, pitNum)].removeStones();
				pits[getMancala(player)].addStones(stones);
			}
	}


}

/**
 * @author shasisingh
 *
 */
class CollectsPit 
{
	int stones;
	
	CollectsPit() 
	{
		this.stones = 0;
	}

	public int getStones() 
	{
		return stones;
	}

	public void addStones(int stones) 
	{
		this.stones += stones;
	}

	public int removeStones() {
		int stones = this.stones;
		this.stones = 0;
		return stones;
	}

	
}

/**
 * @author shasisingh
 *
 */
class MancalaPlayersProfile 
{
	String isARealPlayername;
	int mancalaPlayerNum;
	MancalaPlayersProfile(String name, int playerNum) 
	{
		this.isARealPlayername = name;
		this.mancalaPlayerNum = playerNum;
	}

	public String getName() 
	{
		if (isARealPlayername != null)
			return isARealPlayername;
		else
			return "Robbet!";
	}

	public int selectTheMove(ManageMancalaBoard board) throws IOException 
	{

		int pitNum = 0;
		if (isARealPlayername != null) 
		{ 
			/*
			 *  The below if will check : If Human is playing or Not, isARealPlayername!=null then , ask User to provide Input else calculate Move.
			 */
			// Real player - not the Robbet!
			try {


				System.out.println(" ______________________________________________________________________________");
				System.out.println(" Last/Recent Updated Board has been printed as above along with last user turn.");
				System.out.println(" ______________________________________________________________________________");
				System.out.println(" ---Who is Playing Check Below!---\n");
				Scanner scanner = new Scanner(System.in);
				System.out.print("<<<<<"+ isARealPlayername + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   "); // Prompt User Input need to be provided
				scanner:
					while(scanner.hasNext()) {
						if(scanner.hasNextInt()){
							pitNum = scanner.nextInt();
							if((pitNum<=6 && pitNum>=1))  {
								break scanner;
							} else {
								System.out.println("******************************************************************************");
								System.out.println("PLAYER HAS ENTERED A WRONG PIT... YOUR SELECTION SHOULD BE IN BETWEEN ( 1 TO 6 ), Please Provide correct Input Below");
								System.out.println("******************************************************************************");
								System.out.print("<<<<<"+ isARealPlayername + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   "); // Prompt User Input need to be provided
							}
						} else {
							System.out.println("******************************************************************************");
							System.out.println("ERROR: Invalid Input... YOUR SELECTION SHOULD BE IN BETWEEN ( 1 TO 6 ) and Numeric, Information is not in the correct format");
							System.out.println("******************************************************************************");
							System.out.print("<<<<<"+ isARealPlayername + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   "); // Prompt User Input need to be provided
							scanner.next();
						}
					}

			} catch (NumberFormatException ex) {
				System.err.println("Could not convert input string " + ex.getMessage()+"\n");
			}	

			/*
			 *  Once user Input is validated Move return to Control flow.
			 */
			return pitNum; 
		}else {

			// Robbet is playing - need to determine best move
			int bestMove = -1; // No best move initially
			int repeatMove = -1; // Or go again.
			int maxNewStones = -1; // no move has added stones to the
			// mancala.
			// Trying the possible moves
			for (pitNum = 1; pitNum <= ManageMancalaBoard.playingPits; pitNum++) 
			{
				if (board.stonesInPit(mancalaPlayerNum, pitNum) != 0) 
				{ // Only nonempty pits may be
					// moved from
					ManageMancalaBoard testBoard = board.makeACopyOfMancalaBoard(); 	  // Make a copy of the mancalaBoard
					boolean goAgain = testBoard.doPitsTheMove(mancalaPlayerNum, pitNum); // Try the move on the mancalaBoard copy.
					if (goAgain==true) // If move allows us to go again,
						repeatMove = pitNum; // remember the move.
					int newStones = testBoard.stonesInMancala(mancalaPlayerNum) - board.stonesInMancala(mancalaPlayerNum); // See how many stones this move added to our mancala.
					if (newStones > maxNewStones) 
					{ 
						// More stones than so far?
						maxNewStones = newStones; // Remember how many and the move.
						bestMove = pitNum; 
					}
				}
			}

			// Tried all possibilities, return the best one
			if (maxNewStones > 1) // maxNewStones > 1 means a
				// multistone capture occurred.
				return bestMove;
			else if (repeatMove != -1) // Barring that, use a "go
				// again".
				return repeatMove;
			else
				return bestMove; // 1 or possibly 0 stones added; oh well :)!
		}
	}


}










