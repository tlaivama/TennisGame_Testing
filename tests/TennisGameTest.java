import static org.junit.Assert.*;

import org.junit.Test;

public class TennisGameTest {
	
// Here is the format of the scores: "player1Score - player2Score"
// "love - love"
// "15 - 15"
// "30 - 30"
// "deuce"
// "15 - love", "love - 15"
// "30 - love", "love - 30"
// "40 - love", "love - 40"
// "30 - 15", "15 - 30"
// "40 - 15", "15 - 40"
// "player1 has advantage"
// "player2 has advantage"
// "player1 wins"
// "player2 wins"
	@Test
	public void testTennisGame_Start() {
		//Arrange
		TennisGame game = new TennisGame();
		//Act
		String score = game.getScore() ;
		// Assert
		assertEquals("Initial score incorrect", "love - love", score);		
	}
	
	@Test
	public void testTennisGame_EahcPlayerWin4Points_Score_Deuce() throws TennisGameException {
		//Arrange
		TennisGame game = new TennisGame();
		
		game.player1Scored();
		game.player1Scored();
		game.player1Scored();
		
		game.player2Scored();
		game.player2Scored();
		game.player2Scored();
		
		game.player1Scored();
		game.player2Scored();
		//Act
		String score = game.getScore() ;
		// Assert
		assertEquals("Tie score incorrect", "deuce", score);		
	}
	
	@Test (expected = TennisGameException.class)
	public void testTennisGame_Player1WinsPointAfterGameEnded_ResultsException() throws TennisGameException {
		//Arrange
		TennisGame game = new TennisGame();
		//Act
		game.player1Scored();
		game.player1Scored();
		game.player1Scored();
		game.player1Scored();
		//Act
		// This statement should cause an exception
		game.player1Scored();			
	}
	
	@Test (expected = TennisGameException.class)
	public void testTennisGame_Player2WinsPointAfterGameEnded_ResultsException() throws TennisGameException {
		//Arrange
		TennisGame game = new TennisGame();
		//Act
		game.player2Scored();
		game.player2Scored();
		game.player2Scored();
		game.player2Scored();
		//Act
		// This statement should cause an exception
		game.player2Scored();			
	}
	
	@Test
	public void testTennisGame_CorrectOrderOfGetScoreReturnString() throws TennisGameException {
		TennisGame game = new TennisGame();
		
		game.player1Scored();
		
		assertEquals("Score returned has the players in the wrong order", "15 - love", game.getScore());
	}
	
	@Test
	public void testTennisGame_GetScoreReturnsCorrectTies() throws TennisGameException {
		TennisGame game = new TennisGame();
		
		assertEquals("Incorrect score returned for 0 - 0", "love - love", game.getScore());
		
		bothPlayersScoreNTimes(game, 1);
		assertEquals("Incorrect score returned for 1 - 1", "15 - 15", game.getScore());
		
		bothPlayersScoreNTimes(game, 1);
		assertEquals("Incorrect score returned for 2 - 2", "30 - 30", game.getScore());
		
		bothPlayersScoreNTimes(game, 1);
		assertEquals("Incorrect score returned for 3 - 3", "deuce", game.getScore());
		
		bothPlayersScoreNTimes(game, 3);
		assertEquals("Incorrect score returned for 6 - 6", "deuce", game.getScore());
	}
	
	@Test
	public void testTennisGame_Player1Advantage() throws TennisGameException {
		TennisGame game = new TennisGame();
		
		bothPlayersScoreNTimes(game, 3);
		game.player1Scored();
		
		assertEquals("Score is 4 - 3 but player1 does not have advantage", "player1 has advantage", game.getScore());
	}
	
	@Test
	public void testTennisGame_Player2Advantage() throws TennisGameException {
		TennisGame game = new TennisGame();
		
		bothPlayersScoreNTimes(game, 5);
		game.player2Scored();
		
		assertEquals("Score is 5 - 6 but player2 does not have advantage", "player2 has advantage", game.getScore());
	}
	
	@Test
	public void testTennisGame_Player1Points4_Player2Points0_Player1Wins() throws TennisGameException {
		TennisGame game = new TennisGame();
		
		for (int i = 0; i < 4; ++i) {
			game.player1Scored();

		}
		
		assertEquals("Score is 4 - 0 but player1 has not won", "player1 wins", game.getScore());
	}
	
	@Test
	public void testTennisGame_Player1Points0_Player2Points4_Player1Wins() throws TennisGameException {
		TennisGame game = new TennisGame();
		
		for (int i = 0; i < 4; ++i) {
			game.player2Scored();

		}
		
		assertEquals("Score is 0 - 4 but player2 has not won", "player2 wins", game.getScore());
	}
	
	@Test
	public void testTennisGame_Player1Points5_Player2Points3_Player1Wins() throws TennisGameException {
		TennisGame game = new TennisGame();
		
		bothPlayersScoreNTimes(game, 3);
		game.player1Scored();
		game.player1Scored();
		
		assertEquals("Score is 5 - 3 but player1 has not won", "player1 wins", game.getScore());
	}
	
	@Test
	public void testTennisGame_Player1Points4_Player2Points6_Player2Wins() throws TennisGameException {
		TennisGame game = new TennisGame();
		
		bothPlayersScoreNTimes(game, 4);
		game.player2Scored();
		game.player2Scored();
		
		assertEquals("Score is 4 - 6 but player2 has not won", "player2 wins", game.getScore());
	}
	
	@Test
	public void testTennisGame_30_love() throws TennisGameException {
		TennisGame game = new TennisGame();
		
		for (int i = 0; i < 2; ++i) {
			game.player1Scored();
		}
		
		assertEquals("Score is 2 - 0 but getScore did not return 30 - love", "30 - love", game.getScore());
	}
	
	@Test
	public void testTennisGame_15_40() throws TennisGameException {
		TennisGame game = new TennisGame();
		
		bothPlayersScoreNTimes(game, 1);
		game.player2Scored();
		game.player2Scored();
		
		assertEquals("Score is 2 - 0 but getScore did not return 15 - 40", "15 - 40", game.getScore());
	}
	
	private void bothPlayersScoreNTimes(TennisGame game, int n) throws TennisGameException {
		for (int i = 0; i < n; ++i) {
			game.player1Scored();
			game.player2Scored();
		}
	}
}
