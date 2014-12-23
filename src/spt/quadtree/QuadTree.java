package spt.quadtree;

import geom.Rectangle;
import geom.Vector2D;

import java.util.ArrayList;

/**
 * Divides space while keeping the aspect ratio of the input data.
 * @author Po
 *
 */

public class QuadTree
{
	public QuadNode root;
	public int height;

	public QuadTree()
	{
		root = null;
	}
	
	public QuadTree(QuadNode r)
	{
		root = r;
	}
	
	/**
	 * Add the input point to the the QuadTree
	 * @param pt
	 */
	public void add(Vector2D pt)
	{
		QuadNode n = find(pt);
		n.points.add(pt);
	}
	

	/**
	 * Find the QuadNode which contains the input point.
	 * @param pt
	 * @return
	 */
	public QuadNode find(Vector2D pt) { return find(root, pt); }
	private QuadNode find(QuadNode n, Vector2D pt) // recursive helper method
	{
		if (n.isLeaf()) return n;
		else
		{
			if (n.NE.bound.contains(pt)) return find(n.NE, pt);
			if (n.NW.bound.contains(pt)) return find(n.NW, pt);
			if (n.SE.bound.contains(pt)) return find(n.SE, pt);
			else return find(n.SW, pt);
		}
	}
	

	/**
	 * Return all the nodes at the input level.
	 * @param lvl
	 * @return
	 */
	public ArrayList<QuadNode> getNodes(int lvl)
	{
		ArrayList<QuadNode> nodes = new ArrayList<QuadNode>();
		getNodes(root, nodes, lvl);
		return nodes;
	}
	private void getNodes(QuadNode n, ArrayList<QuadNode> nodes, int lvl) // recursive helper method
	{
		if (n.isLeaf()) return;
		if (lvl > 0)
		{
			getNodes(n.NE, nodes, lvl - 1);
			getNodes(n.NW, nodes, lvl - 1);
			getNodes(n.SE, nodes, lvl - 1);
			getNodes(n.SW, nodes, lvl - 1);
		}
		else
		{
			nodes.add(n);
		}
	}

	/**
	 * Construct the QuadTree which will be of height = levels and fill it with the input set of points.
	 * @param points
	 * @param levels
	 */
	public void build(ArrayList<Vector2D> points, int levels)
	{
		build(Rectangle.computeBoundingBox(points), levels);
		for (Vector2D pt : points) add(pt);
	}
	
	/**
	 * Construct an empty QuadTree over an area specified by r with height = levels.
	 * @param r
	 * @param levels
	 */
	public void build(Rectangle r, int levels)
	{
		root = new QuadNode(r);
		build(root, levels - 1);
		height = levels;
	}
	
	private void build(QuadNode n, int level)  // recursive helper method
	{
		if (level >= 0)
		{
			Rectangle[] bounds = split(n.bound);

			n.NE = new QuadNode(bounds[QuadNode.NORTH_EAST]);
			n.NW = new QuadNode(bounds[QuadNode.NORTH_WEST]);
			n.SE = new QuadNode(bounds[QuadNode.SOUTH_EAST]);
			n.SW = new QuadNode(bounds[QuadNode.SOUTH_WEST]);
			
			build(n.NE, level - 1);
			build(n.NW, level - 1);
			build(n.SE, level - 1);
			build(n.SW, level - 1);
		}
	}
	
	private Rectangle[] split(Rectangle r) // recursive helper method, splits a rectangle into 4 equal pieces
	{
		Vector2D c = r.center();
		
		Rectangle[] quads = new Rectangle[4];
		quads[QuadNode.NORTH_EAST] = new Rectangle(r.x, r.y, r.width/2, r.height/2); 
		quads[QuadNode.NORTH_WEST] = new Rectangle(c.x, r.y, r.width/2, r.height/2); 
		quads[QuadNode.SOUTH_EAST] = new Rectangle(r.x, c.y, r.width/2, r.height/2); 
		quads[QuadNode.SOUTH_WEST] = new Rectangle(c.x, c.y, r.width/2, r.height/2); 
		
		return quads;
	}
}
