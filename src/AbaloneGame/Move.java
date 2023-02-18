package AbaloneGame;

public class Move 
{
	//number of own balls to move.
	private byte numOfOwn;
	
	// number of enemy balls to push 
	private byte numToPush;
	
	// is point move
	private boolean isScore;
	
	//indexes array.
	private byte [] indexs;
	
	
	//isrowMove
	private boolean isRowMove; 
	
	
	/**
	 * Constructor.
	 */
	public Move() 
	{
		
	}

	
	
	
//****************************getters and setters***********************/

	public byte [] getIndexs() 
	{
		return indexs;
	}



	public void setIndexs(byte[] indexs) 
	{
		this.indexs = indexs;
	}




	public boolean isRowMove() 
	{
		return isRowMove;
	}




	public void setRowMove(boolean isRowMove) 
	{
		this.isRowMove = isRowMove;
	}




	public void setNumOfOwn(byte numOfOwn) 
	{
		this.numOfOwn = numOfOwn;
	}




	public void setNumToPush(byte numToPush) 
	{
		this.numToPush = numToPush;
	}




	public void setScore(boolean isScore) 
	{
		this.isScore = isScore;
	}




	public byte getNumOfOwn() {
		return numOfOwn;
	}



	public byte getNumToPush() {
		return numToPush;
	}



	public boolean isScore() {
		return isScore;
	}

//***********************************functions***************************

	/**
	 * program sends to server end point the updated board from this move.
	 * @param point - endpoint to send move to
	 * @param board - board to send indexes update.
	 */
	public void sendMoveToClient(serverEndPoint point, Board board) 
	{
		for(int i=0;i<this.indexs.length;i++)
		{
			point.sendmessage(indexs[i], board.GetValueInPosition(indexs[i]));
		}
		if(this.isScore)
		{
			int index =0;
			//always indexes[i] is own ball.
			if(board.GetValueInPosition(this.indexs[1]) == 1)
			{ //player 1 score
				index = -9 -board.getScoreBlue();
				point.sendmessage(index, -3);
			}
			else
			{// player -1 score
				index = -19 -board.getScoreRed();
				point.sendmessage(index, 3);
			}
		}
	}

	///////////////////////////create moves///////////////////////////
	/**
	 * program updates the current class with those parameters.
	 * @param posA - first position, own
	 * @param posB - second position, empty
	 */
	public void new1BallMove(byte posA, byte posB) 
	{
		//System.out.println("Move.new1BallMove()");
		//initialize array
		indexs = new byte [2];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		

		
		//put other parameters to class
		numToPush = 0;
		numOfOwn = 1;
		isRowMove = false; ///maybe to change
		isScore = false;
	
	}



	/**
	 * program updates the current class with those parameters.
	 * @param posA - first position, own
	 * @param posB - second position, own
	 * @param posSideA - first side position, empty
	 * @param posSideB - second side position, empty
	 */
	public void new2BallsSideMove(byte posA, byte posB, byte posSideA, byte posSideB) 
	{
		//System.out.println("Move.new2BallsSideMove()");
		//initialize array
		indexs = new byte [4];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		indexs[2] = posSideA;
		indexs[3] = posSideB;
		
		//put other parameters to class
		numToPush = 0;
		numOfOwn = 2;
		isRowMove = false;
		isScore = false;
	}

	

	
	/**
	 * program updates the current class with those parameters.
	 * @param posA  - first position, own
	 * @param posB - second position, own
	 * @param posC - third position, own
	 * @param posSideA - first side position, empty
	 * @param posSideB - second side position , empty
	 * @param posSideC - third side position , empty
	 */
	public void new3BallsSideMove(byte posA, byte posB, byte posC, byte posSideA,
			byte posSideB, byte posSideC) 
	{
		//System.out.println("Move.new3BallsSideMove()");
		//initialize array
		indexs = new byte [6];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		indexs[2] = posC;
		indexs[3] = posSideA;
		indexs[4] = posSideB;
		indexs[5] = posSideC;

		
		//put other parameters to class
		numToPush = 0;
		numOfOwn = 3;
		isRowMove = false;
		isScore = false;
	}
	
	
	
	


	/**
	 * program updates the current class with those parameters.
	 * @param posA - first position , own
	 * @param posB -second position , own
	 * @param posC- third position , empty
	 */
	public void new2BallsMove0Push(byte posA, byte posB, byte posC) 
	{
		//System.out.println("Move.new2BallsMove0Push()");
		//initialize array
		indexs = new byte [3];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		indexs[2] = posC;
		
		//put other parameters to class
		numToPush = 0;
		numOfOwn = 2;
		isRowMove = true;
		isScore = false;
	}
	
	

	/**
	 * program updates the current class with those parameters.
	 * @param posA - first position, own
	 * @param posB - second position, own
	 * @param posC - third position, enemy
	 * @param posD - forth position, empty
	 */
	public void new2BallsMove1PushNoScore(byte posA, byte posB, byte posC, byte posD) 
	{
		//System.out.println("Move.new2BallsMove1PushNoScore()");
		//initialize array
		indexs = new byte [4];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		indexs[2] = posC;
		indexs[3] = posD;
		
		//put other parameters to class
		numToPush = 1;
		numOfOwn = 2;
		isRowMove = true;
		isScore = false;
	}
	
	
	
	
	/**
	 * program updates the current class with those parameters.
	 * @param posA - first position, own
	 * @param posB - second position, own
	 * @param posC - third position, enemy
	 */
	public void new2BallsMove1PushWithScore(byte posA, byte posB, byte posC) 
	{
		//System.out.println("Move.new2BallsMove1PushWithScore()");
		//initialize array
		indexs = new byte [3];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		indexs[2] = posC;
		
		//put other parameters to class
		numToPush = 0;
		numOfOwn = 2;
		isRowMove = true;
		isScore = true;
	}
	

	

	/**
	 * program updates the current class with those parameters.
	 * @param posA - first position, own
	 * @param posB - second position, own
	 * @param posC - third position, own
	 * @param posD - forth position, empty
	 */
	public void new3BallsMove0Push(byte posA, byte posB, byte posC, byte posD) 
	{
		//System.out.println("Move.new3BallsMove0Push()");
		//initialize array
		indexs = new byte [4];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		indexs[2] = posC;
		indexs[3] = posD;
		
		//put other parameters to class
		numToPush = 0;
		numOfOwn = 3;
		isRowMove = true;
		isScore = false;
	}
	
	
	
	
	

	
	/**
	 * program updates the current class with those parameters.
	 * @param posA - first position, own
	 * @param posB - second position, own
	 * @param posC - third position, own
	 * @param posD - forth position, enemy
	 * @param posE - fifth position, empty
	 */
	public void new3BallsMove1PushNoScore(byte posA, byte posB, byte posC, byte posD, byte posE) 
	{
		//System.out.println("Move.new3BallsMove1PushNoScore()");
		//initialize array
		indexs = new byte [5];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		indexs[2] = posC;
		indexs[3] = posD;
		indexs[4] = posE;
		
		//put other parameters to class
		numToPush = 1;
		numOfOwn = 3;
		isRowMove = true;
		isScore = false;
	}
	
	
	
	

	/**
	 * program updates the current class with those parameters.
	 * @param posA - first position, own
	 * @param posB - second position, own
	 * @param posC - third position, own
	 * @param posD - forth position, enemy
	 */
	public void new3BallsMove1PushWithScore(byte posA, byte posB, byte posC, byte posD) 
	{
		//System.out.println("Move.new3BallsMove1PushWithScore()");
		//initialize array
		indexs = new byte [4];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		indexs[2] = posC;
		indexs[3] = posD;
		
		//put other parameters to class
		numToPush = 0;
		numOfOwn = 3;
		isRowMove = true;
		isScore = true;
	}
	
	
	
	/**
	 * program updates the current class with those parameters.
	 * @param posA - first position, own
	 * @param posB - second position, own
	 * @param posC - third position, own
	 * @param posD - forth position, enemy
	 * @param posE - fifth position,enemy
	 * @param posF - sixth position,empty
	 */
	public void new3BallsMove2PushNoScore(byte posA, byte posB, byte posC, byte posD, byte posE, byte posF) 
	{
		//System.out.println("Move.new3BallsMove2PushNoScore()");
		//initialize array
		indexs = new byte [6];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		indexs[2] = posC;
		indexs[3] = posD;
		indexs[4] = posE;
		indexs[5] = posF;
		
		//put other parameters to class
		numToPush = 2;
		numOfOwn = 3;
		isRowMove = true;
		isScore = false;
	}
	
	


	/**
	 * program updates the current class with those parameters.
	 * @param posA - first position, own
	 * @param posB - second position, own
	 * @param posC - third position, own
	 * @param posD - forth position, enemy
	 * @param posE - fifth position, enemy
	 */
	public void new3BallsMove2PushWithScore(byte posA, byte posB, byte posC, byte posD, byte posE) 
	{
		//System.out.println("Move.new3BallsMove2PushWithScore()");
		//initialize array
		indexs = new byte [5];
		
		//put positions in array
		indexs[0] = posA;
		indexs[1] = posB;
		indexs[2] = posC;
		indexs[3] = posD;
		indexs[4] = posE;
		
		
		//put other parameters to class
		numToPush = 1;
		numOfOwn = 3;
		isRowMove = true;
		isScore = true;
	}
	


}
