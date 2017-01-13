package com.tuc.ai;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AILoggerFileImpl implements AILogger 
{
	private OutputStream os = null;
	private boolean on = false;
	
	public AILoggerFileImpl( String name )
	{
		if ( ! name.endsWith( "NULL" ) )
		{
			on = true;
			try
			{
				os = new FileOutputStream( name );
			}
			catch ( FileNotFoundException fnfe )
			{
				fnfe.printStackTrace();
				System.exit( AIConstants.FAILURE_STATUS );
			}
		}
	}
	
	public void info( String msg )
	{
		if ( on )
		{
			try
			{
				os.write( ( msg + "\n" ).getBytes() );
			}
			catch ( IOException ioe )
			{
				ioe.printStackTrace();
				System.exit( AIConstants.FAILURE_STATUS );
			}
		}
	}
}
