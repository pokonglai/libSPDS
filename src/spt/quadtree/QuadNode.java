package spt.quadtree;

import java.util.ArrayList;

import geom.Rectangle;
import geom.Vector2D;

/**
 * Only leaves should contain points.
 * @author Po
 *
 */

public class QuadNode
{
	// used as indices to arrays
	public static final int NORTH_EAST = 0;
	public static final int NORTH_WEST = 1;
	public static final int SOUTH_EAST = 2;
	public static final int SOUTH_WEST = 3;
	
	public Rectangle bound;
	public ArrayList<Vector2D> points;
	
	public QuadNode NE;
	public QuadNode NW;
	public QuadNode SE;
	public QuadNode SW;
	
	public QuadNode(Rectangle b)
	{
		bound = b;
		points = new ArrayList<Vector2D>();
		
		NE = null;
		NW = null;
		SE = null;
		SW = null;
	}
	
	/**
	 * A QuadNode is a leaf if it has no children.
	 * @return
	 */
	public boolean isLeaf() { return NE == null && NW == null && SE == null && SW == null; }
	
	/**
	 * Return the set of points that are contain inside this QuadNode.
	 * @return
	 */
	public ArrayList<Vector2D> getPoints() { return getPoints(this); }
	private ArrayList<Vector2D> getPoints(QuadNode n) // recursive helper method
	{
		ArrayList<Vector2D> pts = new ArrayList<Vector2D>();
		if (n == null) return pts;
		else if (n.isLeaf()) pts = n.points;
		else
		{
			pts.addAll(getPoints(n.NE));
			pts.addAll(getPoints(n.NW));
			pts.addAll(getPoints(n.SE));
			pts.addAll(getPoints(n.SW));
		}
		return pts;
	}
}
