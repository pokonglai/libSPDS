package spt.octree;

import java.util.ArrayList;

import geom.BlockVolume;
import geom.Vector3D;

public class Octree
{
	public OctNode root;
	public int height;
	
	public Octree()
	{
		root = null;
	}
	
	public Octree(OctNode r)
	{
		root = r;
	}
	
	public void add(Vector3D pt)
	{
		OctNode n = find(pt);
		n.points.add(pt);
	}
	
	/**
	 * Find the smallest OctNode which contains the input point.
	 * @param pt
	 * @return
	 */
	public OctNode find(Vector3D pt) { return find(root, pt); }
	private OctNode find(OctNode n, Vector3D pt) // recursive helper method
	{
		if (n.isLeaf()) return n;
		else
		{
			if (n.topNE.volume.contains(pt)) return find(n.topNE, pt);
			if (n.topNW.volume.contains(pt)) return find(n.topNW, pt);
			if (n.topSE.volume.contains(pt)) return find(n.topSE, pt);
			if (n.topSW.volume.contains(pt)) return find(n.topSW, pt);
			
			if (n.botNE.volume.contains(pt)) return find(n.botNE, pt);
			if (n.botNW.volume.contains(pt)) return find(n.botNW, pt);
			if (n.botSE.volume.contains(pt)) return find(n.botSE, pt);
			else return find(n.botSW, pt);
		}
	}
	
	/**
	 * Return all the nodes at the input level.
	 * @param lvl
	 * @return
	 */
	public ArrayList<OctNode> getNodes(int lvl)
	{
		ArrayList<OctNode> nodes = new ArrayList<OctNode>();
		getNodes(root, nodes, lvl);
		return nodes;
	}
	private void getNodes(OctNode n, ArrayList<OctNode> nodes, int lvl) // recursive helper method
	{
		if (n.isLeaf()) return;
		if (lvl > 0)
		{
			getNodes(n.topNE, nodes, lvl - 1);
			getNodes(n.topNW, nodes, lvl - 1);
			getNodes(n.topSE, nodes, lvl - 1);
			getNodes(n.topSW, nodes, lvl - 1);
			
			getNodes(n.botNE, nodes, lvl - 1);
			getNodes(n.botNW, nodes, lvl - 1);
			getNodes(n.botSE, nodes, lvl - 1);
			getNodes(n.botSW, nodes, lvl - 1);
		}
		else
		{
			nodes.add(n);
		}
	}
	
	
	/**
	 * Construct the Octree which will be of height = levels and fill it with the input set of points.
	 * @param points
	 * @param levels
	 */
	public void build(ArrayList<Vector3D> points, int levels)
	{
		build(BlockVolume.computeBoundingVolume(points), levels);
		for (Vector3D pt : points) add(pt);
	}
	
	/**
	 * Construct an empty Octree over a block volume specified by bv with height = levels.
	 * @param r
	 * @param levels
	 */
	public void build(BlockVolume bv, int levels)
	{
		root = new OctNode(bv);
		build(root, levels - 1);
		height = levels;
	}
	
	private void build(OctNode n, int level)  // recursive helper method
	{
		if (level >= 0)
		{
			BlockVolume[] bounds = split(n.volume);

			n.topNE = new OctNode(bounds[OctNode.TOP_NORTH_EAST]);
			n.topNW = new OctNode(bounds[OctNode.TOP_NORTH_WEST]);
			n.topSE = new OctNode(bounds[OctNode.TOP_SOUTH_EAST]);
			n.topSW = new OctNode(bounds[OctNode.TOP_SOUTH_WEST]);
			
			n.botNE = new OctNode(bounds[OctNode.BOTTOM_NORTH_EAST]);
			n.botNW = new OctNode(bounds[OctNode.BOTTOM_NORTH_WEST]);
			n.botSE = new OctNode(bounds[OctNode.BOTTOM_SOUTH_EAST]);
			n.botSW = new OctNode(bounds[OctNode.BOTTOM_SOUTH_WEST]);
			
			build(n.topNE, level - 1);
			build(n.topNW, level - 1);
			build(n.topSE, level - 1);
			build(n.topSW, level - 1);
			
			build(n.botNE, level - 1);
			build(n.botNW, level - 1);
			build(n.botSE, level - 1);
			build(n.botSW, level - 1);
		}
	}
	
	private BlockVolume[] split(BlockVolume bv) // recursive helper method, splits a volume into 8 equal pieces
	{
		Vector3D c = bv.center();
		
		BlockVolume[] octs = new BlockVolume[8];
		octs[OctNode.TOP_NORTH_EAST] = new BlockVolume(bv.x, bv.z, bv.y, bv.width/2, bv.length/2, bv.height/2);
		octs[OctNode.TOP_NORTH_WEST] = new BlockVolume(c.x, bv.z, bv.y, bv.width/2, bv.length/2, bv.height/2);
		octs[OctNode.TOP_SOUTH_EAST] = new BlockVolume(bv.x, c.z, bv.y, bv.width/2, bv.length/2, bv.height/2);
		octs[OctNode.TOP_SOUTH_WEST] = new BlockVolume(c.x, c.z, bv.y, bv.width/2, bv.length/2, bv.height/2);

		octs[OctNode.BOTTOM_NORTH_EAST] = new BlockVolume(bv.x, bv.z, c.y, bv.width/2, bv.length/2, bv.height/2);
		octs[OctNode.BOTTOM_NORTH_WEST] = new BlockVolume(c.x, bv.z, c.y, bv.width/2, bv.length/2, bv.height/2);
		octs[OctNode.BOTTOM_SOUTH_EAST] = new BlockVolume(bv.x, c.z, c.y, bv.width/2, bv.length/2, bv.height/2);
		octs[OctNode.BOTTOM_SOUTH_WEST] = new BlockVolume(c.x, c.z, c.y, bv.width/2, bv.length/2, bv.height/2);
		
		return octs;
	}
}
