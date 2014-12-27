/**
 * A port of the python implementation found on: http://en.wikipedia.org/wiki/K-d_tree#Construction
 */


package spt.kdtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import geom.VectorND;

public class KDTree
{
	// class to allow sorting of VectorND by axis
	class VectorNDCompare implements Comparator<VectorND>
	{
		public int axis;
		
		public VectorNDCompare() { axis = 0; }
		public void setAxis(int a) { axis = a; }
		
	    public int compare(VectorND v1, VectorND v2)
	    {
	    	boolean samDim = v1.length() == v2.length();
	    	if (samDim)
	    	{
    			if (v1.get(axis) > v2.get(axis)) return 1;
    			if (v1.get(axis) < v2.get(axis)) return -1;
	    	}
	    	else
	    	{
	    		throw new AssertionError("Vectors are not the same dimension. " + v1.length() + " vs " + v2.length());
	    	}
	    	return 0;
	    }
	}
	
	public KDNode root;
	
	public int dimensions;
	public int height;
	
	private VectorNDCompare vndc;
	
	public KDTree(int d)
	{
		root = null;
		dimensions = d;
		height = 0;
		vndc = new VectorNDCompare();
	}	
	
	public KDTree(KDNode r, int d, int h)
	{
		root = r;
		dimensions = d;
		height = h;
		vndc = new VectorNDCompare();
	}
	
	
	/**
	 * Build a kd-tree from a set of n-dimensional points. Assume each point in the set is of the same dimension.
	 * @param points
	 * @return
	 */
	public void build(ArrayList<VectorND> points) { root = build(points, 0); }
	private KDNode build(ArrayList<VectorND> points, int d)
	{
		if (points.size() == 0) return null;
		
		// select axis based on depth
		int axis = d % dimensions;
		vndc.setAxis(axis);
		
		// find the median location
		Collections.sort(points, vndc);
		int median = points.size()/2;
		
		ArrayList<VectorND> leftSide = new ArrayList<VectorND>();
		ArrayList<VectorND> rightSide = new ArrayList<VectorND>();
		
		VectorND medianLoc = points.get(median);
		for (int i = 0; i < points.size(); i++)
		{
			VectorND pt = points.get(i);
			if (i < median) leftSide.add(pt); 
			if (i > median) rightSide.add(pt);
		}
		
		// create node and construct the subtrees
		KDNode n = new KDNode(medianLoc);
		n.left = build(leftSide, d + 1);
		n.right = build(rightSide, d + 1);
		return n;
	}
	
	/**
	 * Given two vectors which represent the ranges, return all the nodes that are within those ranges.
	 * @param s
	 * @param e
	 * @return
	 */
	public ArrayList<KDNode> rangeSearch(VectorND s, VectorND e)
	{
		ArrayList<KDNode> found = new ArrayList<KDNode>();
		rangeSearch(root, s, e, found);
		return found;
	}
	private void rangeSearch(KDNode n, VectorND s, VectorND e, ArrayList<KDNode> nodes)
	{
		if (n == null) return;
		
		boolean bInRange = true;
		
		// assume all vectors are of the same length
		for (int i = 0; i < n.loc.length(); i++)
		{
			double ele = n.loc.get(i);
			if (ele < s.get(i) || ele > e.get(i))
			{
				bInRange = false;
				break;
			}
		}
		
		if (bInRange) { nodes.add(n); }
		rangeSearch(n.left, s, e, nodes);
		rangeSearch(n.right, s, e, nodes);
	}
	
	public void print() { print(root, ""); }
	private void print(KDNode n, String str)
	{
		if (n == null)
		{
			System.out.println(str + n);
		}
		else
		{
			System.out.println(str + n.loc);
			print(n.left, str + "-");
			print(n.right, str + "-");
		}
	}
}
