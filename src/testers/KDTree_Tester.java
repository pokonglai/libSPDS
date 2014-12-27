package testers;

import geom.VectorND;

import java.util.ArrayList;

import spt.kdtree.KDTree;

public class KDTree_Tester
{

	/**
	 * Simple test routine found on: http://en.wikipedia.org/wiki/K-d_tree#Construction
	 */
	public static void SimpleTest()
	{
		KDTree tree = new KDTree(2);
		ArrayList<VectorND> points = new ArrayList<VectorND>();
		
		double[] pt1 = {2.0, 3.0}; points.add(new VectorND(pt1));
		double[] pt2 = {5.0, 4.0}; points.add(new VectorND(pt2));
		double[] pt3 = {9.0, 6.0}; points.add(new VectorND(pt3));
		double[] pt4 = {4.0, 7.0}; points.add(new VectorND(pt4));
		double[] pt5 = {8.0, 1.0}; points.add(new VectorND(pt5));
		double[] pt6 = {7.0, 2.0}; points.add(new VectorND(pt6));
		
		tree.build(points);
		tree.print();
	}
	

	public static void main(String[] args)
	{
		SimpleTest();
	}
}
