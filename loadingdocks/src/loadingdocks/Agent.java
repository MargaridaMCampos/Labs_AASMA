package loadingdocks;

import java.awt.Color;
import java.awt.Point;
import loadingdocks.Block.Shape;

/**
 * Agent behavior
 * @author Rui Henriques
 */
public class Agent extends Entity {

	public int direction = 90;
	public Box cargo;

	public Agent(Point point, Color color){ 
		super(point, color);
	} 
	
	
	/**********************
	 **** A: decision ***** 
	 **********************/
	
	public void agentDecision() {
		if(!isWallAhead()){
			if(isBoxAhead()){
				grabBox((Box) Board.getEntity(aheadPosition()));
			}
			if(isFreeCell()) moveAhead();
			else rotateLeft();
		}
		
	  	
		else rotateLeft();
	}
	
	/********************/
	/**** B: sensors ****/
	/********************/
	
	/* Check if the cell ahead is floor (which means not a wall, not a shelf nor a ramp) and there are any robot there */
	protected boolean isFreeCell() {
	  Point ahead = aheadPosition();
	  return Board.getBlock(ahead).shape.equals(Shape.free);
	}

	/* Check if the cell ahead is a wall */
	protected boolean isWallAhead() {
		Point ahead = aheadPosition();
		return ahead.x<0 || ahead.y<0 || ahead.x>=Board.nX || ahead.y>=Board.nY;
	}

	protected boolean isBoxAhead(){
		return Board.getEntity(aheadPosition()) instanceof Box;
	}

	/**********************/
	/**** C: actuators ****/
	/**********************/
	/* Move agent forward */
	public void moveAhead() {
		Point ahead = aheadPosition();
		Board.updateEntityPosition(point,ahead);
		this.point = ahead;
		if(this.cargo != null) cargo.point = this.point;
	}

	public void rotateLeft(){
		this.direction -= 90;
	}

	public void grabBox(Box box){
		this.cargo = box;
		Board.removeEntity(box.point);
		this.cargo.point = this.point;
	}
	
	/**********************/
	/**** D: auxiliary ****/
	/**********************/

	/* Position ahead */
	private Point aheadPosition() {
		Point newpoint = new Point(point.x,point.y);
		switch(direction) {
			case 0: newpoint.y++; break;
			case 90: newpoint.x++; break;
			case 180: newpoint.y--; break;
			default: newpoint.x--; 
		}
		return newpoint;
	}
}
