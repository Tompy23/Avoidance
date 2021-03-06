package com.tuc.ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;



/**
 * Overrides the Spring class and substitutes our own property file
 * for variable substitution into the spring configuration.
 * 
 * @author jthomps
 *
 */
public class ArgumentListPropertyConfigurer extends PropertyPlaceholderConfigurer 
{
	private static Log log = LogFactory.getLog( "ArgumentListPropertyConfigurer" );
	private static final String SEP = "=";
	private static final String NEWLINE = System.getProperty( "line.separator" );

	private static Properties myProps = null;
	
	protected void processProperties( ConfigurableListableBeanFactory beanFactory, Properties props ) throws BeansException
    {
		super.processProperties( beanFactory, myProps );
    }
	
	/**
	 * 
	 * @param props
	 */
	public void setProps( Properties props )
	{
		if ( null != props )
		{
			for ( Object key : props.keySet() )
			{
				myProps.put( key, props.getProperty( (String)key ) );
			}
		}
	}

	/**
	 * 
	 * @param propertyFile
	 * @param args
	 * @throws AIException
	 */
	public void setProperties( String propertyFile, String[] args ) throws AIException
	{
		myProps = new Properties();
		
		// Load the properties from the first argument
		try
		{
			if ( null != propertyFile && !propertyFile.startsWith("res") )
			{
				myProps.load( new FileInputStream( new File( propertyFile ) ) );
			}
			else
			{
				propertyFile = "Avoid_ship_only.properties";
				ClassLoader classLoader = getClass().getClassLoader();
				InputStream file = classLoader.getResourceAsStream(propertyFile);
				//InputStream file = new FileInputStream(new File( url.toURI()));

				myProps.load( file );
			}
		}
		catch ( Exception ioe )
		{
			throw new AIException( "Error with Property File: " + propertyFile + NEWLINE + ioe.getMessage() );
		}

		
		// The rest of the arguments are property overrides
		if ( null != args )
		{
			String keyValue;
			for ( int i = 0; i < args.length; i++ )
			{
				try
				{
					keyValue = args[ i ];
					myProps.setProperty( 
							keyValue.substring( 0, keyValue.indexOf( SEP ) ), 
							keyValue.substring( keyValue.indexOf( SEP ) + 1, keyValue.length() ) );
				}
				catch ( Exception e )
				{
					throw new AIException( e );
				}
			}
		}
		
	}
	
	/**
	 * 
	 */
	public void listProperties()
	{
		StringBuffer info = new StringBuffer();
		
		info.append( "Configuration Properties" + NEWLINE );
		
		for ( Object key : myProps.keySet() )
		{
			info.append( key + "=" + myProps.getProperty( (String)key ) + NEWLINE );
		}
		
		log.info( info.toString() );
	}
	
	/**
	 * 
	 * @return
	 */
	public Properties getProperties()
	{
		return myProps;
	}
}
