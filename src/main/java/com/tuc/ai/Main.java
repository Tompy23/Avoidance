package com.tuc.ai;

import org.springframework.context.ApplicationContext;

public class Main 
{

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
		
		try
		{
			ApplicationContext ctx = ApplicationContextCreator.createContext( args );
			AIProcess mainProcess = (AIProcess)ctx.getBean( ArgumentListPropertyConfigurer.getProperties().getProperty( AIConstants.MAIN_PROCESS ) );
			exitStatus = mainProcess.process( ctx, ArgumentListPropertyConfigurer.getProperties() );
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
