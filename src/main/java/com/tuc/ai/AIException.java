package com.tuc.ai;

public class AIException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int status = 0;

	public AIException()
	{
		super();
	}
	
	public AIException( String message )
	{
		super( message );
		status = AIConstants.FAILURE_STATUS;
	}
	
	public AIException( Exception exception )
	{
		super( exception );
		status = AIConstants.FAILURE_STATUS;
	}

	public AIException( String message, int status )
	{
		super( message );
		this.status = status;
	}
	
	public AIException( Exception exception, int status )
	{
		super( exception );
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
