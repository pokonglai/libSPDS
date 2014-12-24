package spt.octree;

import geom.BlockVolume;
import geom.Vector3D;

import java.util.ArrayList;

public class OctNode
{
	// used as indices to arrays
	public static final int TOP_NORTH_EAST = 0;
	public static final int TOP_NORTH_WEST = 1;
	public static final int TOP_SOUTH_EAST = 2;
	public static final int TOP_SOUTH_WEST = 3;
	
	public static final int BOTTOM_NORTH_EAST = 4;
	public static final int BOTTOM_NORTH_WEST = 5;
	public static final int BOTTOM_SOUTH_EAST = 6;
	public static final int BOTTOM_SOUTH_WEST = 7;
	
	public BlockVolume volume;
	public ArrayList<Vector3D> points;
	
	public OctNode topNE;
	public OctNode topNW;
	public OctNode topSE;
	public OctNode topSW;
	
	public OctNode botNE;
	public OctNode botNW;
	public OctNode botSE;
	public OctNode botSW;
	
	public OctNode(BlockVolume bv)
	{
		volume = bv;
		points = new ArrayList<Vector3D>();
		
		topNE = null;
		topNW = null;
		topSE = null;
		topSW = null;
		
		botNE = null;
		botNW = null;
		botSE = null;
		botSW = null;
	}
	
	/**
	 * A OctNode is a leaf if it has no children.
	 * @return
	 */
	public boolean isLeaf()
	{
		boolean bTop = topNE == null && topNW == null && topSE == null && topSW == null;
		boolean bBot = botNE == null && botNW == null && botSE == null && botSW == null;
		return bTop && bBot;
	}

	/**
	 * Return the set of points that are contain inside this OctNode.
	 * @return
	 */
	public ArrayList<Vector3D> getPoints() { return getPoints(this); }
	private ArrayList<Vector3D> getPoints(OctNode n) // recursive helper method
	{
		ArrayList<Vector3D> pts = new ArrayList<Vector3D>();
		if (n == null) return pts;
		else if (n.isLeaf()) pts = n.points;
		else
		{
			pts.addAll(getPoints(n.topNE));
			pts.addAll(getPoints(n.topNW));
			pts.addAll(getPoints(n.topSE));
			pts.addAll(getPoints(n.topSW));
			
			pts.addAll(getPoints(n.botNE));
			pts.addAll(getPoints(n.botNW));
			pts.addAll(getPoints(n.botSE));
			pts.addAll(getPoints(n.botSW));
		}
		return pts;
	}
}
