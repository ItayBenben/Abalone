package AbaloneGame;

import org.apache.tomcat.jni.Time;



public class GameManager 
{
	// Server endpoint to move messages to client
	private serverEndPoint endpoint;
	//
	private int currentplayer;
	//board
	private Board board;
	//player (client) turn manager
	private PlayerTurnManager Pturn;
	//AI manager
	private AIManager AI;
	//game mode
	boolean isPVP;
	//is waiting for user input.
	boolean isWaitingToPlayer = false;
	
	/**
	 * Constructor.
	 * @param point - server end point
	 */
	public GameManager(serverEndPoint point)
	{
		this.currentplayer = 1;
		this.endpoint = point;
		this.board = new Board();
	}
	
	
	/**
	 * the program get called once the client pressed the start game button.
	 * the program initializes the board  and sends messeges of the board to the client.
	 * @param BoardLayout - the game layout.
	 */
	public void StartGamePlayerVsPlayer(int BoardLayout)
	{
		//init the board values
		//and send data to client

		board.initializeBoard(BoardLayout);
		Pturn = new PlayerTurnManager(endpoint, board);
		isPVP = true;
		board.sendIntireBoardToClient(endpoint);
		resetScoreInClient(endpoint);
		isWaitingToPlayer = true;
	
	}
	
	/**
	 * program gets called when client presses "start game player vs AI"
	 * program initialize required parts in code.
	 * @param BoardLayout - the game layout.
	 */
	public void StartGamePlayerVsAI(int BoardLayout)
	{
		board.initializeBoard(BoardLayout);
		Pturn = new PlayerTurnManager(endpoint, board);
		isPVP = false;
		board.sendIntireBoardToClient(endpoint);
		resetScoreInClient(endpoint);
		AI= new AIManager(-1);
		isWaitingToPlayer = true;
	}
	
	
	
	
	/**
	 * program switches beetween players/AI.
	 * If the game mode is pvAI than its activates the ai.
	 */
	public void switchPlayers()
	{
		
		if(isPVP== true)
		{
			System.out.println("swithced player from " +currentplayer + "to " + currentplayer*-1);
			currentplayer = currentplayer *-1;
		}
		else
		{
			//switch to player -> AI -> AI  or AI-> player
			//1 is player
			if(currentplayer == 1)
			{
				//switch from player to AI
				System.out.println("swithced player from " +currentplayer + " to AI turn as " + currentplayer*-1);
				isWaitingToPlayer = false;
				
				//gets move from AI
				Move AIMove = AI.playTurn(board, currentplayer*-1);
				
				//mark move to client
				markMoveToClient(AIMove);

				
				// implementing move in game board
				int iswin = board.makeMove(AIMove);
				
				
				sendMoveToClient(AIMove);
				
				if(iswin==1)
					this.WinFound(currentplayer*-1);
				
				
				isWaitingToPlayer = true;
			}
			//after turn has been committed, its player turn again.
		}
	}
	
	
	/**
	 * program gets move (AI move) and mark move in client.
	 * includes time.wait so client can see the move.
	 * @param aIMove - AI move
	 */
	public void markMoveToClient(Move aIMove) 
	{
		byte [] indexes = aIMove.getIndexs();
		for(int i=0;i<aIMove.getNumOfOwn();i++)
		{
			endpoint.sendmessage(indexes[i], board.GetValueInPosition(indexes[i])*2);
		}
		Time.sleep(700000);
	}



	/**
	 * program gets called once a win was found.
	 * program sends the data to the endpoint to declare a winner to the client. 
	 * @param value - player number
	 */
	public void WinFound(int value)
	{
		System.out.println("GameManager.WinFound()");
		endpoint.sendWinMessage(-value);

	}
	
	
	
	/**
	 * program recives a press index of client, sends the index to the turn 
	 * manager which returns a move.
	 * if it is not end of turn than move = null.
	 * @param index - client press index.
	 */
	public void rereceivedMessage(int index)
	{
		int iswin=0;
		if(isWaitingToPlayer)
		{
			//manages the player turn input.
			Move move= Pturn.recivedPress((byte)index, currentplayer);
			if(move != null)
			{
				//implements the move inside the board.
				iswin = board.makeMove(move);
				//sends the given play to the client to see.
				sendMoveToClient(move);
				
			
				
				if(iswin==1)
					this.WinFound(currentplayer);
				
				//switches player.
				switchPlayers();
			}
		}
	}



	
	/**
	 * program gets move and sends client the updated positions of board.
	 * @param move - move to implement at client
	 */
	public void sendMoveToClient(Move move) 
	{
		move.sendMoveToClient(endpoint, this.board);
	}
	
	
	/**
	 * the program resets the value inside the score positions in client.
	 * from -10 to -16
	 * and from -20 to -26
	 * @param point - the server end point
	 */	
	public void resetScoreInClient(serverEndPoint point)
	{
		for(int i=0;i<=6;i++)
		{
			point.sendmessage(-10-i, 4);
			point.sendmessage(-20-i, 4);

		}
	}
	
	
}
