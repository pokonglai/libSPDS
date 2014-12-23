package ui;

import geom.Rectangle;
import geom.Vector2D;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import spt.quadtree.QuadNode;
import spt.quadtree.QuadTree;

public class QuadTree_Pane extends DrawPane
{
	public QuadTree tree;
	
	public QuadTree_Pane(int w, int h)
	{
		super(w,h);
		tree = new QuadTree();
	}
	
	public void generateRandom(int nPoints, int levels)
	{
		tree.build(new Rectangle(0.0, 0.0, this.width, this.height), levels);
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < nPoints; i++)
		{
			double rw = r.nextDouble() * this.width;
			double rh = r.nextDouble() * this.height;
			Vector2D pt  = new Vector2D(rw, rh);
			tree.add(pt);
		}
	}

	@Override
	public void render(Graphics g)
	{
		for (int i = 0; i < tree.height; i++)
		{
			ArrayList<QuadNode> nodes = tree.getNodes(i);
			for (QuadNode n : nodes)
			{
				ArrayList<Vector2D> pts = n.getPoints();
				
				// only draw the quad-node children if there are points inside it
				if (pts.size() > 0)
				{
					if (n.NE.isLeaf() && n.NE.points.size() > 0) g.setColor(Color.GREEN);
					else g.setColor(Color.RED);
					g.drawRect((int)n.NE.bound.x, (int)n.NE.bound.y, (int)n.NE.bound.width, (int)n.NE.bound.height);
					
					if (n.NW.isLeaf() && n.NW.points.size() > 0) g.setColor(Color.GREEN);
					else g.setColor(Color.RED);
					g.drawRect((int)n.NW.bound.x, (int)n.NW.bound.y, (int)n.NW.bound.width, (int)n.NW.bound.height);
					
					if (n.SE.isLeaf() && n.SE.points.size() > 0) g.setColor(Color.GREEN);
					else g.setColor(Color.RED);
					g.drawRect((int)n.SE.bound.x, (int)n.SE.bound.y, (int)n.SE.bound.width, (int)n.SE.bound.height);
					
					if (n.SW.isLeaf() && n.SW.points.size() > 0) g.setColor(Color.GREEN);
					else g.setColor(Color.RED);
					g.drawRect((int)n.SW.bound.x, (int)n.SW.bound.y, (int)n.SW.bound.width, (int)n.SW.bound.height);
				}
				
				// draw all the points
				g.setColor(Color.BLACK);
				for (Vector2D v : pts) g.fillOval((int)(v.x - 2.0), (int)(v.y - 2.0), 4, 4);
			}
		}
	}

}
