package geom;

import java.util.ArrayList;

public class BlockVolume
{
	public double x; // (x,y,z) = top left corner
	public double y;
	public double z;
	public double width;  // x-axis
	public double length; // z-axis
	public double height; // y-axis
	
	
	public BlockVolume(double _x, double _z, double _y, double w, double l, double h)
	{
		x = _x;
		z = _z;
		y = _y;
		width = w;
		length = l;
		height = h;
	}
	
	public Vector3D topleft() { return new Vector3D(x,y,z); }
	public Vector3D center() { return new Vector3D(x,y,z); }
	
	/**
	 * Points which line exactly on the boundaries are considered to be contained with the rectangle.
	 * @param pt
	 * @return
	 */
	public boolean contains(Vector3D pt)
	{
		boolean bInXRange = pt.x >= x && pt.x <= x + width;
		boolean bInYRange = pt.y >= y && pt.y <= y + height;
		boolean bInZRange = pt.z >= z && pt.z <= z + length;
		return bInXRange && bInYRange && bInZRange;
	}
	
	/**
	 * Compute the smallest box volume that will contain the input list of points.
	 * @param points
	 * @return
	 */
	public static BlockVolume computeBoundingVolume(ArrayList<Vector3D> points)
	{
		double min_x = Double.POSITIVE_INFINITY;
		double min_y = Double.POSITIVE_INFINITY;
		double min_z = Double.POSITIVE_INFINITY;
		double max_x = Double.NEGATIVE_INFINITY;
		double max_y = Double.NEGATIVE_INFINITY;
		double max_z = Double.NEGATIVE_INFINITY;
		
		for (Vector3D p : points)
		{
			min_x = Math.min(min_x, p.x);
			min_y = Math.min(min_y, p.y);
			min_z = Math.min(min_z, p.z);
			max_x = Math.max(max_x, p.x);
			max_y = Math.max(max_y, p.y);
			max_z = Math.max(max_z, p.z);
		}
		
		BlockVolume ret = new BlockVolume(min_x, min_y, min_z, max_x - min_x, max_y - min_y, max_z - min_z);
		return ret;
	}
}
