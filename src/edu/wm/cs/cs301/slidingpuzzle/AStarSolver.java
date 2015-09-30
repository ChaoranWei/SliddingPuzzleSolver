package edu.wm.cs.cs301.slidingpuzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;

import edu.wm.cs.cs301.slidingpuzzle.PuzzleState.Operation;

public class AStarSolver implements PuzzleSolver {
	
	private int[] initialState;
	private static int[] finalState;
	private int counter;
	private PuzzleState curState;

	@Override
	public boolean configure(int[] initial, int[] goal) {
		initialState = initial;
		finalState = goal;
		counter = 0;
		return initial.length == goal.length; 
	}
	
	private boolean CheckMember(Set<int[]> set, int[] state) {
		for (int[] item: set) {
			if (Arrays.equals(state, item)) {
				return true;
			}
		}
		return false;
	}
	
	public static Comparator<PuzzleState> Distance = new Comparator<PuzzleState>(){
        
        @Override
        public int compare(PuzzleState c1, PuzzleState c2) {
        	//System.out.println(c1.getDistance());
        	if (c1.getHeurisics(finalState) > c2.getHeurisics(finalState) ) {
        		return 1;
        	} 
        	else if (c1.getHeurisics(finalState) == c2.getHeurisics(finalState) ) {
        		return 0;
        	}
        	else {
        		return -1;
        	}
        }
    };
    
    private int setParameters(PuzzleState state) {
		int count = 0;
		PuzzleState temp = state;
		while (temp.getParent() != null) {
			temp = temp.getParent();
			count++;
		}
		//state.setDistance(count);
		//System.out.println(state.getDistance());
		return count;
	 
	}

	
	@Override
	public Operation[] movesToSolve() {
		// TODO Auto-generated method stub
		Queue<PuzzleState> q =  new PriorityQueue<PuzzleState>(1,Distance);
		//Queue<PuzzleState> q = new LinkedList<PuzzleState>();
		Set<int[]> garbage = new HashSet<int[]>();
		Stack<Operation> OpList = new Stack<Operation>();
		PuzzleState temp = new SimplePuzzleState();
		curState = this.getSolverInitialState();
		curState.setDistance(setParameters(curState));
		q.add(curState);
		while (!q.isEmpty() ){//&& q.size() < this.getMaxSizeOfQueue()) {
			curState = q.poll(); 
			
			if (!Arrays.equals(curState.getState(), this.getSolverFinalState().getState())) {
				garbage.add(curState.getState());
				
				if (curState.moveUp() != null) {
				    if (!CheckMember(garbage, curState.moveUp().getState())) {
				    	temp = curState.moveUp();
				    	temp.setDistance(setParameters(temp));  
				    	//System.out.println(curState.getDistance());
					    q.add(temp);
					
				    } //else {System.out.println('1');}
				    
				}
				if (curState.moveDown() != null) {
				    if (!CheckMember(garbage, curState.moveDown().getState())) {
				    	
				    	temp = curState.moveDown();
				    	temp.setDistance(setParameters(temp));
				    	q.add(temp);
				    	//garbage.add(curState.moveDown().getState());
				    } //else {System.out.println('1');}
				}
				if (curState.moveRight() != null) {
				    if (!CheckMember(garbage, curState.moveRight().getState())) {
				    	temp = curState.moveRight();
				    	temp.setDistance(setParameters(temp));
				    	q.add(temp);
				    	//garbage.add(curState.moveRight().getState());
				    } //else {System.out.println('1');}
				}
				if (curState.moveLeft() != null) {
					if (!CheckMember(garbage, curState.moveLeft().getState())) {
						temp = curState.moveLeft();
				    	temp.setDistance(setParameters(temp));
						q.add(temp);
						//garbage.add(curState.moveLeft().getState());
					} 
				}
			} else {
				while (curState.getParent() != null) {
					OpList.push(curState.getOperation());
					curState = curState.getParent();
				}
				Operation[] OpArray = new Operation[OpList.size()];
				int count = OpList.size();
				for (Operation Op: OpList){
					count--;
					OpArray[count] = Op;
					
				}
				//System.out.println(OpArray.length);
				return OpArray;
			}
		}
		return null;
		
	}

	@Override
	public PuzzleState getSolverInitialState() {
		// TODO Auto-generated method stub
		PuzzleState InitialState = new SimplePuzzleState();
		InitialState.setState(initialState);
		return InitialState;
	}

	@Override
	public PuzzleState getSolverFinalState() {
		// TODO Auto-generated method stub
		PuzzleState FinalState = new SimplePuzzleState();
		FinalState.setState(finalState);
		return FinalState;
	}

	@Override
	public int getNumberOfStatesExplored() {
		// TODO Auto-generated method stub
		return counter;
	}

	@Override
	public int getMaxSizeOfQueue() {
		// TODO Auto-generated method stub
		return 1000;
	}
	

}



