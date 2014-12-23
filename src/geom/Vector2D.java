package geom;

public class Vector2D
{
	public double x;
	public double y;
	
	public Vector2D() { x = 0; y = 0; }
	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double distSq(double x, double y)
	{
		double dx = this.x - x;
		double dy = this.y - y;
		return dx*dx + dy*dy;
	}
	public double distSq(Vector2D v)
	{
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		return dx*dx + dy*dy;
	}
	public double dist(Vector2D v) { return Math.sqrt(distSq(v)); }
	public double dist(double x, double y) { return Math.sqrt(distSq(x,y)); }
	
	public String toString() { return "["+x+","+y+"]"; }
	
	/**
	 * Returns the dot product of two vectors a & b, in 2D.
	 * @param a
	 * @param b
	 * @return
	 */
	public static double Dot(Vector2D a, Vector2D b) { return a.x*b.x + a.y*b.y; }
}
