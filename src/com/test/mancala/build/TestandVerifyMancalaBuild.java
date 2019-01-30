package com.test.mancala.build;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestandVerifyMancalaBuild {
	
	@Test
	  public void callMethodMancalaRule() {
	
		  MancalaGameRule.printGameRules();
	  }
	  @Test
	  public void callPlayMancalaWithComputer() throws IOException {
		
		  PlayAndBuildMancalaGame playGameMancals = new PlayAndBuildMancalaGame(null, null,"Y");
		  playGameMancals.playMancala();
	  }
	  /*@Test
	  public void callPlayMancalaWithComputerComputer() throws IOException {
		
		  PlayAndBuildMancalaGame playGameMancals = new PlayAndBuildMancalaGame("Shashi", null,"Y");
		  playGameMancals.playMancala();
	  }
	  @Test
	  public void callPlayMancalaWithHuman() throws IOException {
		
		  PlayAndBuildMancalaGame playGameMancals = new PlayAndBuildMancalaGame("Shashi", "Aryan","Y");
		  playGameMancals.playMancala();
	  }
	  
	  @Test
	  public void callPlayMancalaWithHumanWithSuffle() throws IOException {
		
		  PlayAndBuildMancalaGame playGameMancals = new PlayAndBuildMancalaGame("Shashi", "Aryan","Y");
		  playGameMancals.playMancala();
	  }
	  @Test
	  public void callPlayMancalaWithHumanWithoutSuffle() throws IOException {
		
		  PlayAndBuildMancalaGame playGameMancals = new PlayAndBuildMancalaGame("Shashi", "Aryan","N");
		  playGameMancals.playMancala();
	  }*/
	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }

}