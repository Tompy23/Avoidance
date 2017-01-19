package com.tuc.ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;


public class ClassPathProperties 
{
	public InputStream getResource(String resourceName) throws AIException
	{
		try
		{
			URL url = this.getClass().getResource(resourceName);
			return new FileInputStream( new File( url.toURI() ) );
		}
		catch( Exception e )
		{
			throw new AIException( e );
		}
	}
}
