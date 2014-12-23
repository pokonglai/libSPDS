package testers;

import geom.Vector2D;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import ui.QuadTree_Pane;
import ui.UIConst;

public class QuadTree_Tester extends JFrame
{
	private QuadTree_Pane pane;
	
	public QuadTree_Tester(int w, int h)
	{	
		super("QuadTree Tester");
		setLayout(new BorderLayout(2, 2));

		pane = new QuadTree_Pane(w, h);
		pane.generateRandom(100, 5);
		add(pane, BorderLayout.CENTER);
		
		// add mouse listeners for clicking and adding points
		addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent e) { }
			public void mousePressed(MouseEvent e) { }
			public void mouseExited(MouseEvent e) { }
			public void mouseEntered(MouseEvent e) { }
			public void mouseClicked(MouseEvent e)
			{
				Point pt = e.getPoint();
				pane.tree.add(new Vector2D(pt.x - 8, pt.y - 40));
				pane.repaint();
			}

		});

		// add 1 to the JFrame padding to include the rectangle lines
		setSize(w + UIConst.JFRAME_WPAD + 1, h + UIConst.JFRAME_HPAD + 1);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	

	public static void main(String[] args)
	{
		QuadTree_Tester qtt = new QuadTree_Tester(800, 600);
	}
}
