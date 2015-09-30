package edu.wm.cs.cs301.slidingpuzzle;

import java.util.Arrays;

public class SimplePuzzleState implements PuzzleState {

	private PuzzleState state1;
	private Operation Op;
	private int[] state2;
	private int dist;
	
	@Override
	public void setState(int[] gameState) {
	    state2 = gameState;
	}

	@Override
	public int[] getState() {
		return state2;
	}

	@Override
	public PuzzleState getParent() {
		// TODO Auto-generated method stub
		return state1;
	}

	@Override
	public void setParent(PuzzleState parentState) {
		// TODO Auto-generated method stub
		state1 = parentState;
	}

	@Override
	public void setOperation(Operation op) {
		// TODO Auto-generated method stub
		Op = op;

	}

	@Override
	public Operation getOperation() {
		// TODO Auto-generated method stub
		return Op;
	}

	@Override
	public void setDistance(int distance) {
		// TODO Auto-generated method stub
		dist = distance;
		//System.out.println(dist);
	}

	@Override
	public int getDistance() {
		// TODO Auto-generated method stub
		//System.out.println(dist);
		return dist;
	}

	@Override
	public boolean equals(PuzzleState other) {
		// TODO Auto-generated method stub
		if (other == null) {
			return false;
		} else {
			return Arrays.equals(other.getState(), state2);
		}
	}

	@Override
	public PuzzleState moveUp() {
		
		int boardSize = (int) Math.sqrt(state2.length);
		for (int i = boardSize; i < state2.length; i++) {
			
		    if (state2[i] == 0) {
		    	int[] state3 = state2.clone();
		   	    state3[i]=state3[i-boardSize] ;
		   	    state3[i-boardSize] = 0;
		   	    PuzzleState NewState = new SimplePuzzleState();	   	    
		   	    NewState.setState(state3);
		   	 
		   	    PuzzleState ThisState = this;	  
		   	    NewState.setParent(ThisState);
		   	    NewState.setOperation(Operation.MOVEUP);
				return NewState;
		    	   	 
		    }
		}
		return null;
	}

	@Override
	public PuzzleState moveDown() {
		// TODO Auto-generated method stub
		int boardSize = (int) Math.sqrt(state2.length);
		for (int i = 0; i < state2.length-boardSize; i++) {
		    if (state2[i] == 0) {
		      int[] state3 = state2.clone();
		   	  state3[i]=state3[i+boardSize] ;
		   	  state3[i+boardSize] = 0;
		   	  PuzzleState NewState = new SimplePuzzleState();
			  NewState.setState(state3);
			 // System.out.println(Arrays.equals(NewState.moveUp().getState(), state2));
			  PuzzleState ThisState = this;	  
	   	      NewState.setParent(ThisState);
			  NewState.setOperation(Operation.MOVEDOWN);
			  return NewState;
		    }
		    
		}
		return null;
	}

	@Override
	public PuzzleState moveLeft() {
		// TODO Auto-generated method stub
		int boardSize = (int) Math.sqrt(state2.length);
		for (int i = 0; i < state2.length; i++) {
		    if (state2[i] == 0) {
		    	int[] state3 = state2.clone();
		    	if (i % boardSize != 0 ) {
		   	       state3[i]=state3[i-1] ;
		   	       state3[i-1] = 0;
		   	    PuzzleState NewState = new SimplePuzzleState();
				NewState.setState(state3);
				PuzzleState ThisState = this;	  
		   	    NewState.setParent(ThisState);
				NewState.setOperation(Operation.MOVELEFT);
				return NewState;
		    	}
		    }
		}
		return null;
	}

	@Override
	public PuzzleState moveRight() {
		// TODO Auto-generated method stub
		
		int boardSize = (int) Math.sqrt(state2.length);
		for (int i = 0; i < state2.length - 1; i++) {
		    if (state2[i] == 0) {
		    	int[] state3 = state2.clone();
		    	if ((i+1) % boardSize!=0) {
		   	       state3[i]=state3[i+1] ;
		   	       state3[i+1] = 0;
		   	    PuzzleState NewState = new SimplePuzzleState();
				NewState.setState(state3);
				PuzzleState ThisState = this;	  
		   	    NewState.setParent(ThisState);
				NewState.setOperation(Operation.MOVERIGHT);
				return NewState;
		    	}
		    }
		}
		return null;
	}
	
	private int mandist(int[] Final) {
		//System.out.println(dist);
		int count = 0;
		int boardsize = (int) Math.sqrt(Final.length);
		
		for (int i = 0; i < state2.length; i++) {
			
			for (int j = 0; j < Final.length; j++) {
				if (state2[i] == Final[j]) {
					//System.out.println(i - j);
					count = (int) (count + Math.floor(Math.abs((i - j) / boardsize) 
							+ Math.abs((i - j) % boardsize)));
				}
			}
		}
	    //System.out.println(count);
		return count;
	}
	
	private int heuristics(int[] state) {
		int count2 = getDistance();
		int count1 = mandist(state);
		//System.out.println(getDistance());
		return count1 + count2;
	}
	
	public int getHeurisics(int[] state) {
		//System.out.println(Final);
		return this.heuristics(state);
	}

}
