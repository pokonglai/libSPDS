package spt.kdtree;

import geom.VectorND;

public class KDNode
{
	public VectorND loc;
	public KDNode left;
	public KDNode right;
	
	public KDNode(VectorND _loc)
	{
		loc = _loc;
		left = null;
		right = null;
	}
}
