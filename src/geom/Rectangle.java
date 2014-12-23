/**
 * Simple implementation of a Rectangle in 2D defined using a (x,y) top-left coordinate and a (width, height) dimension pair.
 * 
 * @author Po
 *
 */

package geom;

import java.util.ArrayList;

public class Rectangle
{
	public double x; // (x,y) = top left
	public double y;
	public double width;
	public double height;
	
	public Rectangle() { x = y = width = height = 0; }
	
	public Rectangle(double _x, double _y, double w, double h)
	{
		x = _x;
		y = _y;
		width = w;
		height = h;
	}
	
	public Vector2D topleft() { return new Vector2D(x,y); }
	public Vector2D bottomright() { return new Vector2D(x + width, y + height); }
	public Vector2D center() { return new Vector2D(x + width/2, y + height/2); }
	
	/**
	 * Points which line exactly on the boundaries are considered to be contained with the rectangle.
	 * @param pt
	 * @return
	 */
	public boolean contains(Vector2D pt)
	{
		boolean bInXRange = pt.x >= x && pt.x <= x + width;
		boolean bInYRange = pt.y >= y && pt.y <= y + height;
		return bInXRange && bInYRange;
	}
	
	/**
	 * Compute the smallest box that will contain the input list of points.
	 * @param points
	 * @return
	 */
	public static Rectangle computeBoundingBox(ArrayList<Vector2D> points)
	{
		double min_x = Double.POSITIVE_INFINITY;
		double min_y = Double.POSITIVE_INFINITY;
		double max_x = Double.NEGATIVE_INFINITY;
		double max_y = Double.NEGATIVE_INFINITY;
		
		for (Vector2D p : points)
		{
			min_x = Math.min(min_x, p.x);
			min_y = Math.min(min_y, p.y);
			max_x = Math.max(max_x, p.x);
			max_y = Math.max(max_y, p.y);
		}
		
		Rectangle ret = new Rectangle(min_x, min_y, max_x - min_x, max_y - min_y);
		return ret;
	}
}
