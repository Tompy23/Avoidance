package com.tuc.ai;

import java.util.Properties;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @author jthomps
 *
 */
public interface AIProcess 
{
	/**
	 * 
	 * @param ctx
	 * @param properties
	 * @return
	 */
	public int process( ApplicationContext ctx, Properties properties ) throws AIException;
}
