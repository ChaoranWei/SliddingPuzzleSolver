package edu.wm.cs.cs301.slidingpuzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import edu.wm.cs.cs301.slidingpuzzle.PuzzleState.Operation;

public class BFSSolver implements PuzzleSolver {
	
	private int[] initialState;
	private int[] finalState;
	private int counter;
	private PuzzleState curState;

	@Override
	public boolean configure(int[] initial, int[] goal) {
		// TODO Auto-generated method stub
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
	@Override
	public Operation[] movesToSolve() {
		// TODO Auto-generated method stub
		Queue<Operation> OpList = new LinkedList<Operation>();
		Queue<PuzzleState> q = new LinkedList<PuzzleState>();
		Set<int[]> garbage = new HashSet<int[]>();
		curState = this.getSolverInitialState();
		q.add(curState);
		
		while (!q.isEmpty()) {
			//System.out.println(q.size());
			curState = q.remove();
			//System.out.println(q.size());
			if (!Arrays.equals(curState.getState(), this.getSolverFinalState().getState())) {
				garbage.add(curState.getState());
				if (curState.moveUp() != null) {
				    if (!CheckMember(garbage, curState.moveUp().getState())) {
					    q.add(curState.moveUp());
					    //garbage.add(curState.moveUp().getState());
				    } //else {System.out.println('1');}
				}
				if (curState.moveDown() != null) {
				    if (!CheckMember(garbage, curState.moveDown().getState())) {
				    	q.add(curState.moveDown());
				    	//garbage.add(curState.moveDown().getState());
				    } //else {System.out.println('1');}
				}
				if (curState.moveRight() != null) {
				    if (!CheckMember(garbage, curState.moveRight().getState())) {
				    	q.add(curState.moveRight());
				    	//garbage.add(curState.moveRight().getState());
				    } //else {System.out.println('1');}
				}
				if (curState.moveLeft() != null) {
					if (!CheckMember(garbage, curState.moveLeft().getState())) {
					//if (!garbage.contains(curState.moveLeft().getState())) {
						q.add(curState.moveLeft());
						//garbage.add(curState.moveLeft().getState());
					} //else {System.out.println('1');}
				}
			} else {
				while (curState.getParent() != null) {
					OpList.add(curState.getOperation());
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
		return 1000000000;  // do we need to set internally or by users
	}

}
