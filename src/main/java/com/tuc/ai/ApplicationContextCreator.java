package com.tuc.ai;

import java.io.File;

import org.springframework.context.ApplicationContext;

/**
 * Creates a Spring context 
 * @author jthomps
 *
 */
public class ApplicationContextCreator 
{
	private static MyPropertyConfigurationManager propsManager;
	private ArgumentListPropertyConfigurer alpc = null;
	/**
	 * 
	 * @param args
	 * @return
	 * @throws AIException
	 */
	public ApplicationContext createContext( String[] args, ArgumentListPropertyConfigurer alpc ) throws AIException
	{
		this.alpc = alpc;
		String propertiesFile = null;
		String[] commandLineArgs = null;
		String propHome = null;
		
		if ( args.length > 0 )
		{
			propHome = System.getenv(AIConstants.PROP_HOME ); 
			propertiesFile = ( null != propHome ? propHome + File.separator : "" ) + args[ 0 ];
			commandLineArgs = new String[ args.length - 1 ];
			System.arraycopy( args, 1, commandLineArgs, 0, args.length - 1 );
		}
		else
		{
			propHome = System.getenv(AIConstants.PROP_HOME ); 
			propertiesFile = ( "res/Avoid_ship_only.properties" );
			//commandLineArgs = new String[ args.length - 1 ];
			//System.arraycopy( args, 1, commandLineArgs, 0, args.length - 1 );
		}
//		}
//		else
//		{
//			throw new AIException( "Missing properties file in command line arguments list." );
//		}
		
		return createContext( propertiesFile, commandLineArgs );
	}

	/**
	 * 
	 * @param propertiesFile
	 * @param args
	 * @return
	 * @throws AIException
	 */
	public ApplicationContext createContext( String propertiesFile, String[] args ) throws AIException
	{
		alpc.setProperties( propertiesFile, args );
		
		String springMain = alpc.getProperties().getProperty( AIConstants.SPRING_MAIN ); 
		
		if ( null == springMain )
		{
			throw new AIException( "No [" + AIConstants.SPRING_MAIN + "] property" );
		}
		
		if ( null != propsManager )
		{
			alpc.setProps( propsManager.preContextCreation( alpc.getProperties() ) );
		}
		
		ApplicationContext returnContext = new AIClassPathXmlApplicationContext( springMain ); 
		
		if ( null != propsManager )
		{
			alpc.setProps( propsManager.postContextCreation( alpc.getProperties() ) );
		}

		return returnContext;
	}
	
	/**
	 * 
	 * @param manager
	 */
	public void registerPropertyManager( MyPropertyConfigurationManager manager )
	{
		propsManager = manager;
	}
}
