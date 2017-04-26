/**
 * 
 */
package com.coretek.avt.executor;

import java.util.concurrent.CountDownLatch;

/**
 * ³ÌÐòÈë¿Ú
 * 
 * @author David
 *
 */
public class Launcher
{
	public static final String	MODE		= "mode";

	public static final String	ENDIAN		= "endian";

	public static final String	BEGIN_MSG	= "beginMsg";

	public static final String	END_MSG		= "endMsg";

	public static final String	CASE_FILE	= "caseFile";

	private CountDownLatch		latch		= new CountDownLatch(1);

	public Launcher()
	{

	}

	
	public static void main(String[] args)
	{
		Launcher launcher = new Launcher();
		ControllManager em = new ControllManager(launcher.latch);
		new Thread(em).start();
		try
		{
			launcher.latch.await();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}