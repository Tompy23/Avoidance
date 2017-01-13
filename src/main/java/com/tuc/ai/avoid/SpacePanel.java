package com.tuc.ai.avoid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

public class SpacePanel extends JPanel implements SpeedProcessor 
{
	private static final long serialVersionUID = 1L;
	private SpaceObjectController ships;
	private int epochCount = 0;
	private int graphSize = 50;
	
	private boolean showShips = true;
	private boolean continuous = false;
	
	public SpacePanel( SpaceObjectController s, int graphSize )
	{
		super();
		this.ships = s;
		this.graphSize = graphSize;
	}
	
	public void updateContinuous( boolean continuous )
	{
		this.continuous = continuous;
	}
	
	public void updateEpochCount( int count )
	{
		epochCount = count;
	}
	
	public void paintComponent(Graphics g)
	{
		String msg = String.format( "Epoch: %05d [%s]", epochCount, ( continuous ? "continuous" : "learning" ) );
		g.drawString( msg, 0, 20 );
		if ( showShips )
		{
			ships.display( (Graphics2D)g );
		}
		else
		{
			List< long[] > statsList = ships.getStats();
			if ( statsList.size() < 1 )
			{
				return;
			}
			Graphics2D g2d = (Graphics2D)g;
			
			int index = 0;
			if ( ships.getStats().size() > graphSize )
			{
				index = ships.getStats().size() - graphSize;
			}

			long maxWorst = 0;
			for ( int i = index; i < ships.getStats().size(); i++ )
			{
				long[] stats = ships.getStats().get( i );
				if ( stats[ SpaceObjectController.STAT_WORST ] > maxWorst )
				{
					maxWorst = stats[ SpaceObjectController.STAT_WORST ];
				}
			}

			double yRatio = (double)this.getBounds().height / (double)maxWorst;
			double xRatio = this.getBounds().width / ( graphSize );

			long x1Best = 0;
			long y1Best = (long)(statsList.get( index )[ SpaceObjectController.STAT_BEST ] * yRatio);
			long x1Avg = 0;
			long y1Avg = (long)(statsList.get( index )[ SpaceObjectController.STAT_AVG ] * yRatio );
			long x1Worst = 0;
			long y1Worst = (long)(statsList.get( index )[ SpaceObjectController.STAT_WORST ] * yRatio );
			
			for ( int i = index; i < ships.getStats().size(); i++ )
			{
				long[] stats = ships.getStats().get( i );
				g2d.setColor( new Color( 0, 250, 0 ) );
				g2d.drawLine( (int)x1Best, (int)y1Best, (int)(x1Best + xRatio), (int)(stats[ SpaceObjectController.STAT_BEST ] * yRatio) );
				g2d.setColor( new Color( 0, 0, 250 ) );
				g2d.drawLine( (int)x1Avg, (int)y1Avg, (int)(x1Avg + xRatio), (int)(stats[ SpaceObjectController.STAT_AVG ] * yRatio) );
				g2d.setColor( new Color( 250, 0, 0 ) );
				g2d.drawLine( (int)x1Worst, (int)y1Worst, (int)(x1Worst + xRatio), (int)(stats[ SpaceObjectController.STAT_WORST ] * yRatio) );
				
				x1Best += xRatio;
				y1Best = (int)(stats[ SpaceObjectController.STAT_BEST ] * yRatio);
				x1Avg += xRatio;
				y1Avg = (int)(stats[ SpaceObjectController.STAT_AVG ] * yRatio);
				x1Worst += xRatio;
				y1Worst = (int)(stats[ SpaceObjectController.STAT_WORST ] * yRatio);

				g2d.setColor( new Color( 0, 250, 0 ) );
				g2d.drawString( "" + (int)(y1Best / yRatio), x1Best - 20, y1Best + 10 );
				g2d.setColor( new Color( 0, 0, 250 ) );
				g2d.drawString( "" + (int)(y1Avg / yRatio), x1Avg - 20, y1Avg );
				g2d.setColor( new Color( 250, 0, 0 ) );
				g2d.drawString( "" + (int)(y1Worst / yRatio), x1Worst - 20, y1Worst );
			}
		}
	}

	@Override
	public void incrSpeed() 
	{
		showShips = false;
	}

	@Override
	public void decSpeed() 
	{
		showShips = true;
	}

	@Override
	public boolean delay() 
	{
		return showShips;
	}

	@Override
	public void continuous() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}
}
