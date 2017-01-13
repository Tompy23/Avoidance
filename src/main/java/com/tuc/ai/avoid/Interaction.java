package com.tuc.ai.avoid;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class Interaction implements KeyEventDispatcher 
{
	private SpeedProcessor speedProcessor;
	private SpeedProcessor mainSpeedProcessor;
	
	public Interaction( SpeedProcessor sp, SpeedProcessor sp2 )
	{
		speedProcessor = sp;
		mainSpeedProcessor = sp2;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent arg0) 
	{
		if ( arg0.getID() == KeyEvent.KEY_PRESSED )
		{
			if ( arg0.getKeyChar() == 'f' )
			{
				speedProcessor.incrSpeed();
			}
			if ( arg0.getKeyChar() == 's' )
			{
				speedProcessor.decSpeed();
			}
			if ( arg0.getKeyChar() == 'x' )
			{
				mainSpeedProcessor.continuous();
			}
			if ( arg0.getKeyChar() == 'X' )
			{
				mainSpeedProcessor.restart();
			}
		}

		return false;
	}
	
}
