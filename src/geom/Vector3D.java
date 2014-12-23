package geom;

public class Vector3D
{
	public double x;
	public double y;
	public double z;
	
	public Vector3D() { x = 0; y = 0; z = 0; }
	public Vector3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double distSq(double x, double y, double z)
	{
		double dx = this.x - x;
		double dy = this.y - y;
		double dz = this.z - z;
		return dx*dx + dy*dy + dz*dz;
	}
	public double distSq(Vector3D v)
	{
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		double dz = this.z - v.z;
		return dx*dx + dy*dy + dz*dz;
	}
	
	public double dist(Vector3D v) { return Math.sqrt(distSq(v)); }
	public double dist(double x, double y, double z) { return Math.sqrt(distSq(x,y,z)); }
	
	public String toString() { return "["+x+","+y+","+z+"]"; }
	
	/**
	 * Returns the dot product of two vectors a & b, in 3D.
	 * @param a
	 * @param b
	 * @return
	 */
	public static double Dot(Vector3D a, Vector3D b) { return a.x*b.x + a.y*b.y + a.z*b.z; }
	
	/**
	 * Returns the cross product of two vectors a & b.
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector3D Cross(Vector3D a, Vector3D b)
	{
		double cx = a.y*b.z - a.z*b.y;
		double cy = a.z*b.x - a.x*b.z;
		double cz = a.x*b.y - a.y*b.x;
		return new Vector3D(cx, cy, cz);
	}
}
