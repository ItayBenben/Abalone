package AbaloneGame;

import java.util.BitSet;
import java.util.ArrayList;

public class Board 
{
	//data structure:
	private BitSet EdgeOfBoard;
	private BitSet BlueSet;
	private BitSet RedSet;
	
	//directions matrix
	private byte pathArr[];

	//score count
	private byte scoreBlue;
	private byte scoreRed;
	

	/**
	 * Constructor
	 * initializes patharr
	 */
	public Board() 
	{
		pathArr = new byte[6];
		initpathArr();
	}
	
	
	
	public BitSet getEdgeOfBoard() {
		return EdgeOfBoard;
	}

	public void setEdgeOfBoard(BitSet edgeOfBoard) {
		EdgeOfBoard = edgeOfBoard;
	}

	public BitSet getBlueSet() {
		return BlueSet;
	}

	public void setBlueSet(BitSet blueSet) {
		BlueSet = blueSet;
	}

	public BitSet getRedSet() {
		return RedSet;
	}

	public void setRedSet(BitSet redSet) {
		RedSet = redSet;
	}

		public byte getScoreBlue() {
		return scoreBlue;
	}

	public void setScoreBlue(byte scoreBlue) {
		this.scoreBlue = scoreBlue;
	}

	public byte getScoreRed() {
		return scoreRed;
	}

	public void setScoreRed(byte scoreRed) {
		this.scoreRed = scoreRed;
	}

	
	
		/**
		 *program initializes the initpathArr
		*/
		public void initpathArr()
		{
			pathArr = new byte[6];
			pathArr[0] = 1;
			pathArr[1]= -12;
			pathArr[2]= -11;
			pathArr[3]= -1;
			pathArr[4]= 12;
			pathArr[5]= 11;
		}
		
	
	

	/**
	 * program creats new 3 bitsets -
	 * blueSet, RedSet, EdgeOfBoard. 
	 * @param BoardLayout - sets the start game layout according to the BoardLayout
	 */
	public void initializeBoard(int BoardLayout)
	{
		initpathArr();
		scoreBlue = 0;
		scoreRed = 0;
		
		EdgeOfBoard = new BitSet(121);
		BlueSet = new BitSet(121);
		RedSet = new BitSet(121);
		
		///board edges layout.
		EdgeOfBoard.set(12, 17);
		EdgeOfBoard.set(23, 29);
		EdgeOfBoard.set(34, 41);
		EdgeOfBoard.set(45, 53);
		EdgeOfBoard.set(56, 65);
		EdgeOfBoard.set(68, 76);
		EdgeOfBoard.set(80, 87);
		EdgeOfBoard.set(92, 98);
		EdgeOfBoard.set(104, 109);
		
		//classic layout 
		if(BoardLayout==0)
		{
			///blueSet, player 1
			BlueSet.set(12,14);
			BlueSet.set(23,25 );
			BlueSet.set(34,37 );
			BlueSet.set(45,48);
			BlueSet.set(56,59);
			BlueSet.set(68);
			
			//Redset, player -1
			RedSet.set(52);
			RedSet.set(62,65);
			RedSet.set(73, 76);
			RedSet.set(84, 87);
			RedSet.set(96, 98);
			RedSet.set(107, 109);	
		}
		
		//pro layout
		if(BoardLayout ==1)
		{
			//top left
			BlueSet.set(39,41);
			BlueSet.set(50,53);
			BlueSet.set(62,64);
			
			//top right
			RedSet.set(83, 85);
			RedSet.set(94, 97);
			RedSet.set(106, 108);
			
			//bottom left
			RedSet.set(13, 15);
			RedSet.set(24, 27);
			RedSet.set(36, 38);
			
			//bottom right
			BlueSet.set(57,59);
			BlueSet.set(68,71);
			BlueSet.set(80,82);
		}
		
		//snake layout
		if(BoardLayout == 2)
		{
			//start in bottom left
			BlueSet.set(12);
			BlueSet.set(23);
			BlueSet.set(34);
			BlueSet.set(45);
			BlueSet.set(56);
			BlueSet.set(68);
			BlueSet.set(80);
			BlueSet.set(92);
			BlueSet.set(93);
			BlueSet.set(94);
			BlueSet.set(83);
			BlueSet.set(71);
			BlueSet.set(59);
			BlueSet.set(48);
			
			//starts in top right
			RedSet.set(108);
			RedSet.set(97);
			RedSet.set(86);
			RedSet.set(75);
			RedSet.set(64);
			RedSet.set(52);
			RedSet.set(40);
			RedSet.set(28);
			RedSet.set(27);
			RedSet.set(26);
			RedSet.set(37);
			RedSet.set(49);
			RedSet.set(61);
			RedSet.set(72);
		}
		
		//Wall layout
		if(BoardLayout ==3)
		{
			//top left
			RedSet.set(28);
			RedSet.set(39);
			RedSet.set(50,52);
			RedSet.set(61,63);
			RedSet.set(72,74);
			RedSet.set(83,85);
			RedSet.set(94,96);
			RedSet.set(105);
			RedSet.set(86);

			
			
			
			//bottom left
			BlueSet.set(15);
			BlueSet.set(25, 27);
			BlueSet.set(36,38);
			BlueSet.set(47,49);
			BlueSet.set(58,60);
			BlueSet.set(69,71);
			BlueSet.set(81);
			BlueSet.set(92);			
			BlueSet.set(34);			

		}
	}
	
	
	
	/**
	 * program sends to server end point all of the positions in the current board.
	 * @param point - sends messages to.
	 */
	public void sendIntireBoardToClient(serverEndPoint point) 
	{
		for (int i = EdgeOfBoard.nextSetBit(0); i != -1; i = EdgeOfBoard.nextSetBit(i+1)) 
		{
			point.sendmessage(i, 0);
		}
		for (int i = BlueSet.nextSetBit(0); i != -1; i = BlueSet.nextSetBit(i+1)) 
		{
			point.sendmessage(i, 1);
		}
		for (int i = RedSet.nextSetBit(0); i != -1; i = RedSet.nextSetBit(i+1)) 
		{
			point.sendmessage(i, -1);
		}
	}


	
	

	/**
	 * function return the value of position in board.
	 * @param position - position to check value for.
	 * @return -   -9 if not in board, 1 if in blueset, -1 if in redset ,0 if not pressed (not in both).
	 */
	//function gets bit position and return 1 if in blueset,
	//-1 if in redset and 0 if not pressed (not in both).
	//return -9 if not in board.
	public byte GetValueInPosition(byte position)
	{
		//not in board
		if(!isPositionInBoard(position))
			return -9;
		
		if(BlueSet.get(position))
			return 1;

		if(RedSet.get(position))
			return -1;
		//if reached than no one is true so empty.
		return 0;
	}
	
	
	
	/**
	 * program sets value in given position
	 * @param position - position to change.
	 * @param value - the value to change to.
	 */
	public void SetValueInPosition(byte position, byte value)
	{
		switch (value) {
		case 1: 
		{
			BlueSet.set(position, true);
			RedSet.set(position, false);
			break;
		}
		case 0:
		{
			BlueSet.set(position, false);
			RedSet.set(position, false);
			break;
		}
		case -1:
		{
			BlueSet.set(position, false);
			RedSet.set(position, true);
			break;
		}
		
	}
	}
	
	
	/**
	 *program gets position and return true if in board.
	 * @param position - position to check
	 * @return - true if in board, false if not.
	 */
	public boolean isPositionInBoard(byte position)
	{
		return EdgeOfBoard.get(position);
	}
	
	
	
	

	/**
	 * program check if teo positions are next to each other.
	 * !! does not check if 2 positions are in board. 
	 * @param posA - first position
	 * @param posB - second position
	 * @return true if both positions are next to each other.
	 */
	public boolean IsPositionsTogether(byte posA,byte posB)
	{
		byte givenDirection = (byte) (posB - posA);
		for (byte direction : pathArr) 
		{
			//checks if direction is in directions array
			if(direction==givenDirection)
				return true;
		}
		//if reached than they are not together.
		return false;
				
	}
	
	
	
	/**
	 * program gets 2 position and return the next position in line.
	 * @param posA - first position
	 * @param posB - second position
	 * @return posC if position found, -9 if position is not in board.
	 */
	public byte getNextPositionInLine(byte posA, byte posB) 
	{
		byte posC = (byte) (posB- posA+ posB);
		if(isPositionInBoard(posC))
			return posC;
		
		return -9;
	}
	
	
	

	/**
	 * program returns the position of posSideA
	 * @param posA - first position
	 * @param posB - second position, next to posA
	 * @param posSideB - second side position, in side to posB
	 * @return -9 if posSideA is not in board, else returns posA.
	 */
	public byte get4thPosInSideMove(byte posA, byte posB, byte posSideB) 
	{
		byte direction = (byte) (posSideB- posB);
		if(!isPositionInBoard((byte)(posA+direction)))
			return -9;
		return  (byte) (posA+direction);
		
	}

	
	
	
	
	
	/**
	 * program gets a valid move and updates current board from the move.
	 * @param move - valid move to implement.
	 * @return 1 if Winfound, 0 of not.
	 */
	public int makeMove(Move move) 
	{
		//System.out.println("started MakMove fnc.");
		//move indxex:
		byte indexesarray [] = move.getIndexs();
		//in all moves the first position will be 0;
		byte ownV = GetValueInPosition(indexesarray[0]);
		
	
		if(move.isRowMove())
		{
			
			SetValueInPosition(indexesarray[0], (byte) 0);
			//send all own moves including next.
			for(int i=1 ; i<move.getNumOfOwn()+1; i++)
			{
				SetValueInPosition(indexesarray[i], ownV);
			}
			
			///if(move.isScore)
			//	move.numToPush--; // updates -1 balls.
			
			//updates enemy balls.
			for(int i1=0;i1<move.getNumToPush();i1++)
			{
				//System.out.println("moves enemy to index " + move.numOfOwn+1+i1);
				//System.out.println(" in " + move.indexs[move.numOfOwn+i1]);
				SetValueInPosition(indexesarray[move.getNumOfOwn()+1+i1], (byte) (ownV*-1));

			}
		}
		else
		{// side move.
			for(int i = 0 ; i<move.getNumOfOwn(); i++)
			{
				SetValueInPosition(indexesarray[i], (byte)0);
				SetValueInPosition(indexesarray[i+move.getNumOfOwn()],(byte) ownV);			
			}
			
		}
		
		
		//checks if win found
		if(move.isScore())
		{
			if(ownV==1)
			{
				scoreBlue++;
				if(scoreBlue>=6)
					return 1;
			}
			else
			{
				scoreRed++;
				if(scoreRed>=6)
					return 1;
			}
		}
		//win not found
		return 0;
		
	}
	
	
	
	
	
	
	/////////////////////////////////////////////////Moves check/////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * program gets two points: A and B are next to each other.
	 * the programs return if posC is in line after B.
	 * @param posA - first position
	 * @param posB - second position (next to posA)
	 * @param posC - third position
	 * @return true if posA, posB and posC are in a line.
	 */
	public boolean isPositionsInLine3(byte posA, byte posB, byte posC)
	{
		//checks if the change between x and y values is the same.
		return ((posB-posA) == (posC - posB));
	}
	
	
	
	/**
	 * the program switches the valueS of two given positions.
	 * @param posA - first position
	 * @param posB - second position
	 */
	public void switchPositions(byte posA,  byte posB)
	{
		byte posAValue = GetValueInPosition(posA);
		byte posBValue = GetValueInPosition(posB);
		
		BlueSet.set(posA, posBValue);
		RedSet.set(posB, posAValue);

	}
	
	
	
	/**
	 * program tries to create a move of push 2 balls in the direction of posC.
	 * @param posA - first position (own)
	 * @param posB - second position(next to posA , own)
	 * @param posC - third position (in line) can be (valueof posA)*-1 or 0
	 * @return move if found a valid move, null if not.
	 */
	public Move TryToPush2(byte posA, byte posB, byte posC)
	{
		//if enpty then switch posA and posC
		byte VposC = GetValueInPosition(posC);
		
		//2 balls push.
		if(VposC==0)
		{
			//creats new Move
			Move mov = new Move();
			mov.new2BallsMove0Push(posA, posB, posC);

			return mov;
		}
	
		//posC is Enemy
		//to check where is posD
		
		byte posD = getNextPositionInLine(posB, posC);
		if(posD == -9)
		{//point push.
			//creats new Move
			Move mov = new Move();
			mov.new2BallsMove1PushWithScore(posA, posB, posC);
			return mov;
		}
		
		byte VposD = GetValueInPosition(posD);
		
		//2 own and 2 enemy -> cannot push
		if(VposD != 0)
			return null;
		
		//if reached than 2 own 1 enemy 1 empty, can push.
		Move mov = new Move();
		mov.new2BallsMove1PushNoScore(posA, posB, posC, posD);
		return mov;
		
		
	}
	
	

	
	
	/**
	 * program tries to create a move of push 3 balls in the direction of posD.
	 * @param posA - first position (own)
	 * @param posB - second position(next to posA , own)
	 * @param posC - second position(in line , own)
	 * @param posD - third position (in line) can be (valueof posA)*-1 or 0
	 * @return move if found a valid move, null if not.
	 */
	public Move TryToPush3(byte posA,byte posB,byte posC,byte posD)
	{
		
		if(GetValueInPosition(posD)==0)
		{//3 balls move no push.
			Move mov = new Move();
			mov.new3BallsMove0Push(posA, posB, posC, posD);
			return mov;
		}
		
		byte Vown = GetValueInPosition(posA);
		//checks if 4 balls same color.
		if(GetValueInPosition(posD) == Vown)
			return null;
		
		//if reached than 3 own 1 enemy.
		byte posE = getNextPositionInLine(posC, posD);
		if(posE == -9)
		{//3 balls move 1 push with score.
			Move mov = new Move();
			mov.new3BallsMove1PushWithScore(posA, posB, posC, posD);
			return mov;
		}
		
		//checks if 3 own 1 enemy 1 own.
		byte VposE = GetValueInPosition(posE);
		if(VposE==Vown)
			return null;
		
		if(VposE == 0)
		{//3 own 1 enemy push no score.
			Move mov = new Move();
			mov.new3BallsMove1PushNoScore(posA, posB, posC, posD, posE);
			return mov;
		}
		
		
		//if reached than 3 own 2 enemy.
		
		byte posF = getNextPositionInLine(posD, posE);
		if(posF == -9)
		{//3 balls move 2 push with score.
			Move mov = new Move();
			mov.new3BallsMove2PushWithScore(posA, posB, posC, posD, posE);
			return mov;
		}
		
		//checks if 3 own 2 enemy 1 own.
		byte VposF = GetValueInPosition(posF);
		if(VposF==Vown)
			return null;
		
		
		if(VposF == 0)
		{//3 own 2 enemy push no score.
			Move mov = new Move();
			mov.new3BallsMove2PushNoScore(posA, posB, posC, posD, posE, posF);
			return mov;
		}		
		
		//3 own 3 enemy -> cannot do anything.
		return null;
	}
	
	
	
	
	
	/**
	 * program check if can do a side move of posA and posB to posSideA and posSideB
	 * @param posA - first position
	 * @param posB - second position (next to posA , own)
	 * @param posSideB - first sids position , empty.
	 * @return move if found a valid move, null if not.
	 */
	public Move TryToSideMove2(byte posA, byte posB, byte posSideB)
	{
		///both side poses must be 0;
		if(GetValueInPosition(posSideB)!=0)
			return null;
		
		//checks if possideB is next to one of given positions.
		if(!IsPositionsTogether(posB, posSideB))
			return null;
					
		//if reached than posSideB is Valid.
		
		//checks posSideA.
		byte posSideA = get4thPosInSideMove(posA, posB, posSideB);
		if(posSideA==-9 || GetValueInPosition(posSideA)!=0)
			return null;
		
		//if reached than it is a valid 2 side move.
		//checks if 2nd move position is in board
		Move mov = new Move();
		mov.new2BallsSideMove(posA, posB, posSideA, posSideB);
		return mov;
		
	}	
	


	/**
	 * program check if can do a side move of posA, posB and posC to posSideA and posSideB and posSideC
	 * @param posA - first position
	 * @param posB - second position (next to posA , own)
	 * @param posB - second position (in line , own)
	 * @param posSideC - first side position , empty.
	 * @return move if found a valid move, null if not.
	 */
	public Move TryToSideMove3(byte posA, byte posB, byte posC, byte posSideC)
	{
		
		///both side poses must be 0;
		if(GetValueInPosition(posSideC)!=0)
			return null;
		
		//checks if possideC is next to one of given positions.
		if(!IsPositionsTogether(posC, posSideC))
			return null;
					
		//if reached than posSideB is Valid.
		
		//checks posSideB.
		byte posSideB = get4thPosInSideMove(posB, posC, posSideC);
		if(posSideB==-9 || GetValueInPosition(posSideB)!=0)
			return null;
		
		//checks posSideA.
		byte posSideA = get4thPosInSideMove(posA, posB, posSideB);
		if(posSideA==-9 || GetValueInPosition(posSideA)!=0)
			return null;
		
		

		//if reached than it is a valid 2 side move.
		//checks if 2nd move position is in board
		Move mov = new Move();
		mov.new3BallsSideMove(posA, posB, posC, posSideA, posSideB, posSideC);
		return mov;	
	}
		
		
		
	
	


	///////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////AI functions//////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * program finds all the possible moves of Currentplayer from current board.
	 * @param currentPlayer - player to search moves for.
	 * @return list of all possible moves of current player.
	 */
	public ArrayList<Move> getmoves(byte currentPlayer)
	{
		//ArrayList<Move> MoveList = new ArrayList<Move>();
		ArrayList<Move> MoveList = new ArrayList<Move>(60);

		Move move = new Move();
		byte possiblePoisitionsArray [], sideArray[];
		
		//set own and enemy sets
		BitSet ownSet = (currentPlayer==1)? BlueSet: RedSet;
		//BitSet enemySet = (currentPlayer==1)? RedSet: BlueSet;		
		
		//go ovet all balls in bitset to put as first click.
		for (byte posA = (byte)ownSet.nextSetBit(0); posA != -1; posA = (byte)ownSet.nextSetBit(posA + 1)) 
		{
		
			//gest all neighbors positions.
			possiblePoisitionsArray = getNeighborsOfPossition(posA);
			/// go over each of neighbors positions in posB.
			for (byte posB : possiblePoisitionsArray) 
			{
				
				//if position is not a valid neighbor.
				if(posB ==-1)
					continue;
				//checks if posB is Empty
				byte VposB = GetValueInPosition(posB);
				if(VposB==0)
				{
					//add 2 points, 1 of own, 0 push, 0 score
					move = new Move();
					move.new1BallMove(posA, posB);
					//adds move to list
					MoveList.add(move);
				}
				else
				{//posB is not empty
					
					//if enemy then cannot push (move already checked) then break. 
					if(VposB == currentPlayer *-1) continue;
					
					
					//next postition in line, return -1 if not in board
					byte posC = getNextPositionInLine(posA, posB);
					if(posC!=-9)
					{//posC is inBoard.
						if(GetValueInPosition(posC) == currentPlayer)
						{
							byte posD = getNextPositionInLine(posB, posC);
							if(posD!=-9)
							{
								move = new Move();
								move = TryToPush3(posA, posB, posC, posD);
								if(move != null)
									MoveList.add(move);		
							}	
						}
						else
						{//can be 2 own push.
							move = new Move();
							move = TryToPush2(posA, posB, posC);
							if(move != null)
								MoveList.add(move);							
						}	
					}
					
					
					//to check all possible side moves of 2 balls.
					sideArray = getNeighborsOfPossition(posB);
					/// go over each of neighbors positions from posB.
					for (byte posSideB : sideArray)
					{
						//if position is not a valid neighbor.
						if(posSideB ==-1)
							continue;

						
						//doesnt check posA (already pressed) or posC (already checked in line move).
						if( posSideB == posA || posSideB == posB)
							continue;
						
						if(GetValueInPosition(posSideB) != 0)
							continue;
						
						//gets posSideA index, if not in board then -1.
						byte posSideA = get4thPosInSideMove(posA, posB, posSideB); 
						if(posSideA == -9 || GetValueInPosition(posSideA) != 0)
							continue;
						
						//if reached then it is a sideMove in positions posA, posB to posSideA and posSideB
						//
						move = new Move();
						move.new2BallsSideMove(posA, posB, posSideA, posSideB);
						MoveList.add(move);

						
						//3 SIDE MOVE
						//checks if the next position in posC and posSideC are empty.
						byte posCC = getNextPositionInLine(posA, posB);
						if(posC!= VposB)
							continue;
						byte posSideC = getNextPositionInLine(posSideA, posSideB);
						if(posSideC==-9)
							continue;
					
						//checks if both posC and sideC are empty
						if(GetValueInPosition(posCC) == 0 && GetValueInPosition(posSideC)==0)
						{//3 balls side move.
							move = new Move();
							move.new3BallsSideMove(posA, posB,posCC,  posSideA, posSideB, posSideC);
							MoveList.add(move);
						}
					}
					
				}
			}
		}
		return MoveList;
	}

	

	/*
	 * 	program returns a byte arr of all positions next to the given position.
	 * if position is not in board than it is -1.
	 */
	/**
	 * program creates a bute array of all positions next to a given position.
	 * if a positions is not in board, its -1 in the array.
	 * @param position - position to creates neighbors array 
	 * @return byte array of neighbors , -1 for each one is out of board.
	 */
	public byte [] getNeighborsOfPossition(byte position)
	{
		byte pos;
		byte arr [] = new byte [6];
		for(byte i=0;i<6;i++)
		{
			//neighbor position
			pos = (byte) (position+pathArr[i]);
			if(isPositionInBoard(pos))
				arr[i] = pos;
			else
				arr[i] = -1;
				
		}
		
		return arr;
	}
	
	
	
	

	/**
	 * program gets a board instance and sets current (this) board the values of the given board.
	 * @param b - board to clone
	 */
	public void cloneBoard(Board b) 
	{
		this.BlueSet = (BitSet) b.BlueSet.clone();
		this.RedSet = (BitSet) b.RedSet.clone();
		
		//to find a better way for edge of board
		this.EdgeOfBoard = (BitSet) b.EdgeOfBoard.clone();
		this.scoreBlue = b.scoreBlue;
		this.scoreRed = b.scoreRed;
		//initpathArr();
	}
	
	
}
