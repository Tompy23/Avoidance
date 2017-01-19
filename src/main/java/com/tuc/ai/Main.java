package com.tuc.ai;

import org.springframework.context.ApplicationContext;

public class Main 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Main m = new Main();
		
		System.exit( m.process( args ) );
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	public int process( String[] args )
	{
		int exitStatus = AIConstants.SUCCESS_STATUS;
		ApplicationContextCreator acc = new ApplicationContextCreator();
		ArgumentListPropertyConfigurer alpc = new ArgumentListPropertyConfigurer();
		
		try
		{
			ApplicationContext ctx = acc.createContext( args, alpc );
			AIProcess mainProcess = (AIProcess)ctx.getBean( alpc.getProperties().getProperty( AIConstants.MAIN_PROCESS ) );
			exitStatus = mainProcess.process( ctx, alpc.getProperties() );
		}
		catch ( AIException aie )
		{
			aie.printStackTrace();
			exitStatus = aie.getStatus();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			exitStatus = AIConstants.FAILURE_STATUS;
		}
		
		return exitStatus;
	}

}
