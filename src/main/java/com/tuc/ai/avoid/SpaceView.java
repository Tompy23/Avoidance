package com.tuc.ai.avoid;

import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class SpaceView 
{
	private JFrame frame = new JFrame( "Ship Avoidance Scanner" );
	private SpacePanel panel = null;
	
	public SpaceView( int width, int height, SpaceObjectController ships, int graphSize )
	{
		panel = new SpacePanel( ships, graphSize );
		frame.setLocationByPlatform( true );
		frame.setSize( width, height );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setContentPane( panel );
		
		frame.setVisible( true );
		
	}

	public void init( SpeedProcessor sp )
	{
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher( new Interaction( panel, sp ) );
	}
	public boolean delay()
	{
		return panel.delay();
	}
	
	public void updateContinuous( boolean continuous )
	{
		panel.updateContinuous( continuous );
	}
	
	public void updateEpochCount( int count )
	{
		panel.updateEpochCount( count );
	}

	public Rectangle getBounds()
	{
		return panel.getBounds();
	}
	
	public void repaint()
	{
		frame.repaint();
	}
}
