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
	
	/**
	 * 
	 * @param args
	 * @return
	 * @throws AIException
	 */
	public static ApplicationContext createContext( String[] args ) throws AIException
	{
		String propertiesFile = null;
		String[] commandLineArgs = null;
		
		if ( args.length > 0 )
		{
			propertiesFile = System.getenv( AIConstants.PROP_HOME ) + File.separator + args[ 0 ];
			commandLineArgs = new String[ args.length - 1 ];
			System.arraycopy( args, 1, commandLineArgs, 0, args.length - 1 );
		}
		else
		{
			throw new AIException( "Missing properties file in command line arguments list." );
		}
		
		return createContext( propertiesFile, commandLineArgs );
	}

	/**
	 * 
	 * @param propertiesFile
	 * @param args
	 * @return
	 * @throws AIException
	 */
	public static ApplicationContext createContext( String propertiesFile, String[] args ) throws AIException
	{
		ArgumentListPropertyConfigurer.setProperties( propertiesFile, args );
		
		String springMain = ArgumentListPropertyConfigurer.getProperties().getProperty( AIConstants.SPRING_MAIN ); 
		
		if ( null == springMain )
		{
			throw new AIException( "No [" + AIConstants.SPRING_MAIN + "] property" );
		}
		
		if ( null != propsManager )
		{
			ArgumentListPropertyConfigurer.setProps( propsManager.preContextCreation( ArgumentListPropertyConfigurer.getProperties() ) );
		}
		
		ApplicationContext returnContext = new AIClassPathXmlApplicationContext( springMain ); 
		
		if ( null != propsManager )
		{
			ArgumentListPropertyConfigurer.setProps( propsManager.postContextCreation( ArgumentListPropertyConfigurer.getProperties() ) );
		}

		return returnContext;
	}
	
	/**
	 * 
	 * @param manager
	 */
	public static void registerPropertyManager( MyPropertyConfigurationManager manager )
	{
		propsManager = manager;
	}
}
