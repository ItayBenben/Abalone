package AbaloneGame;

public class PlayerTurnManager 
{
	//mini datastruct.
	private byte [] pressedbuttons;
	
	private serverEndPoint endpoint;
	
	private Board board;
	
	public PlayerTurnManager(serverEndPoint point, Board b)
	{
		resetMiniDataStruct();
		this.endpoint = point;
		this.board = b;
	}

	
	/**
	 * program initializes pressedbuttons array.
	 */
	private void resetMiniDataStruct()
	{
		pressedbuttons = new byte [6];
		pressedbuttons[0] = -1;
		pressedbuttons[1] = -1;
		pressedbuttons[2] = -1;
		pressedbuttons[3] = -1;
		pressedbuttons[4] = -1;
		pressedbuttons[5] = -1;
	}
	
	
	

	/**
	 * program receives client press index and checks if click is:
	 * first click / second click (rowcheck) / third click(rowcheck) / direction click (side or push check)
	 * if it is the start of turn then pressedbuttons[0] =[1]= [2] = -1;
	 * @param index - pressed button index
	 * @param currentplayer - who pressed.
	 * @return Move if end of a valid turn , null if not.
	 */
	public Move recivedPress(byte index, int currentplayer) 
	{
		System.out.println("reacehd recive press.");
		//first press
		if(pressedbuttons[0] == -1)
		{
			//own ball press
			if(board.GetValueInPosition(index)== currentplayer)
			{
				pressedbuttons[0] = index;
				endpoint.sendmessage(index, currentplayer*2);
				//finished click and in middle of turn.
				return null;
			}
		}
		return CaseNotFirstPress(index, currentplayer);
	}
	
	
		
		
		/**
		 * program checks if it is a false press, second /third/ 4th press and if it is the last press in turn.  
		 * @param index - pressed button index
		 * @param currentplayer - who pressed.
		 * @return Move if end of a valid turn , null if not.
		 */
		private Move CaseNotFirstPress(byte index, int currentplayer)
		{
			int CurrentPressValue = board.GetValueInPosition(index);
			
			if(CurrentPressValue  == currentplayer)
			{//this is own ball press, 2 3 or 4 press
				
				//case 2nd press
				if(pressedbuttons[1] == -1)
				{//this is the second press.
					
					//checks if the 2nd position is near first press.
					if(board.IsPositionsTogether(pressedbuttons[0],index))
					{
						//legal 2nd press in line same own color.
						pressedbuttons[1] = index;
						endpoint.sendmessage(index, currentplayer*2);
						return null;
					}
					else
					{
						CancleTurn(currentplayer); // not close both presses.
						return null;
					}
				
				}
				else
				{//can be 3rd press with same color
					//checks if positions are in line and it is the 3rd press.
					if(pressedbuttons[2] == -1 && board.isPositionsInLine3(pressedbuttons[0],
							pressedbuttons[1],index))
					{
						//legal 3rd press in line same own color.
						pressedbuttons[2] = index;
						endpoint.sendmessage(index, currentplayer*2);
						return null;
					}
					else
					{
						//4 presses with same color
						CancleTurn(currentplayer);
						return null;
					}
				}
			}
			else
			{// not own ball pressed in 2/3/4 press.
				
				//ball pressed (not first turn) is not own color
				return CaseLastPressInTurn(index, currentplayer);
			}
			
		}
		
		
		
	
		/**
		 * program checks if the last press is part of row/side move. and if it is a valid move.
		 * can be 2nd/3rd/4th press.
		 * @param index - pressed button index
		 * @param currentplayer - who pressed.
		 * @return Move if end of a valid turn , null if not.
		 */
		private Move CaseLastPressInTurn(byte index, int currentplayer) 
		{		
			//Move to fillin and return.
			Move m = null;
			//only one own ball pressed
			if(pressedbuttons[1] == -1)
			{
				//check if close to each other and target position empty
				if(board.IsPositionsTogether(pressedbuttons[0],index) &&
						board.GetValueInPosition(index)==0)
				{
					m = new Move();
					m.new1BallMove(pressedbuttons[0],  index);
					emptyArr();
					System.out.println("1ballmove");
					return m;
				}
				else
				{
					CancleTurn(currentplayer);
					return null;
				}
			}
			
			//if reached then only more then one ball already pressed
			
			if(pressedbuttons[2]==-1)
			{//only two own balls pressed
				//System.out.println("reached 22222");

				if(board.isPositionsInLine3(pressedbuttons[0], pressedbuttons[1],  index))
				{// case front summo

					m = board.TryToPush2(pressedbuttons[0], pressedbuttons[1], index);
					
					//if null than not a valid push.
					if(m==null)
					{
						CancleTurn(currentplayer);
						return null;
					}
					
					//is a valid move.
					emptyArr();
					return m;	
				}
				
				///if reached than possible side move. try to side move 2 balls.
				m = board.TryToSideMove2(pressedbuttons[0],pressedbuttons[1], index);
				if(m==null)
				{
					CancleTurn(currentplayer);
					return null;
				}
				emptyArr();
				return m;
			}
			
			
			///if reached than three own balls pressed
			if(board.isPositionsInLine3(pressedbuttons[1],pressedbuttons[2],index))
			{//try to summo 3.

				m = board.TryToPush3( pressedbuttons[0], pressedbuttons[1], pressedbuttons[2],index);
				if(m==null)	
				{
					CancleTurn(currentplayer);
					return null;
				}
				
				emptyArr();
				return m;
			}
			else
			{///possible side move. try to side move 3 balls.
				
				m = board.TryToSideMove3(pressedbuttons[0], pressedbuttons[1], pressedbuttons[2], index);
				if(m==null)
				{
					CancleTurn(currentplayer);
					return null;
				}
				emptyArr();
				return m;
			}							
		}

		
		
		/**
		 * program resets the pressedbuttons array.
		 * program sends messages to client to reset pressed colors there too.
		 * @param currentplayer - current player.
		 */
		private void CancleTurn(int currentplayer)
		{
			System.out.println("reached cancle turn");
			if(pressedbuttons[0]!= -1)
			{	
				//send message to cancle press
				endpoint.sendmessage(pressedbuttons[0] , currentplayer);
				//reset array
				pressedbuttons[0] = -1;
			}
			
			if(pressedbuttons[1]!= -1)
			{	
				//send message to cancle press
				endpoint.sendmessage(pressedbuttons[1], currentplayer);
				//reset array
				pressedbuttons[1] = -1;
			}
			
			if(pressedbuttons[2]!= -1)
			{	
				//send message to cancle press
				endpoint.sendmessage(pressedbuttons[2], currentplayer);
				//reset array
				pressedbuttons[2] = -1;
			}
			
		}

		/**
		 * program resets pressedbuttons array. (usually after a valid move has found)
		 */
		private void emptyArr()
		{
			System.out.println("reached empty arr");
			//reset array
			pressedbuttons[0] = -1;
			pressedbuttons[1] = -1;
			pressedbuttons[2] = -1;
		}
	
	
	
	
	
	
}
