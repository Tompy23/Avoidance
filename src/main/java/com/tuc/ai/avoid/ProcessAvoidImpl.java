package com.tuc.ai.avoid;

import java.util.Properties;

import org.springframework.context.ApplicationContext;

import com.tuc.ai.AIConstants;
import com.tuc.ai.AIException;
import com.tuc.ai.AILogger;
import com.tuc.ai.AIProcess;

public class ProcessAvoidImpl implements AIProcess, SpeedProcessor
{
	private SpaceObjectController controller;
	private SpaceView view;
	private int lengthEpoch;
	
	private double friction;
	
	private AILogger log;
	
	private int delay;
	
	private boolean continuous = false;
	
	public ProcessAvoidImpl( 
			SpaceObjectController controller, 
			SpaceView view, 
			int lengthEpoch, 
			double friction,
			AILogger log,
			int delay )
	{
		this.controller = controller;
		this.view = view;
		this.lengthEpoch = lengthEpoch;
		this.friction = friction;
		this.log = log;
		this.delay = delay;
		
		view.init( this );
	}
	
	@Override
	public int process(ApplicationContext ctx, Properties properties) throws AIException 
	{
		controller.init( ctx );
		int epochCount = 0;
		view.updateEpochCount( ++epochCount );
		int tmpLengthEpoch = lengthEpoch;

		while ( true )
		{
			try
			{
				log.info( String.format( "Epoch: %05d [%s]", epochCount, ( continuous ? "continuous" : "thinking" ) ) );
				int epochTime = 0;
				while ( continuous || epochTime++ < lengthEpoch )
				{
					controller.feedShipsInformation( view.getBounds() );
					
					controller.compute( view.getBounds(), friction );
					
					if ( view.delay() )
					{
						Thread.sleep( delay );
					}
					view.updateContinuous( continuous );
					view.repaint();
				}
				
				if ( controller.endOfEpoch() )
				{
					// We have reached a state of equilibrium
					lengthEpoch *= 10;
				}
				else
				{
					lengthEpoch = tmpLengthEpoch;
				}
				
				view.updateEpochCount( ++epochCount );
				
				controller.restart();
			}
			catch( Exception e )
			{
				e.printStackTrace();
				return AIConstants.FAILURE_STATUS;
			}
		}
	}

	@Override
	public void incrSpeed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decSpeed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delay() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void continuous() 
	{
		continuous = true;
	}

	@Override
	public void restart() 
	{
		continuous = false;
	}
}
