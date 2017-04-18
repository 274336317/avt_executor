/**
 * 
 */
package com.coretek.avt.executor;

import java.util.concurrent.CountDownLatch;

/**
 * 程序入口
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

	public static ParamManager	PARAM_MANAGER;

	private CountDownLatch		latch		= new CountDownLatch(1);

	public Launcher()
	{

	}

	/**
	 * 
	 * @param args
	 *            命令行参数： </br> 1.mode=debug/run:运行模型，执行器支持调试模式和运行模式</br>
	 *            2.endian=big/little:大小端设置 </br> 3.beginMsg=uuid:开始消息的UUID
	 *            </br> 4.endMsg=uuid:结束消息的UUID </br> 5.caseFile=路径：测试用例的路径
	 */
	public static void main(String[] args)
	{
		Launcher launcher = new Launcher();

		PARAM_MANAGER = new ParamManager(args);
		ExecutorManager em = new ExecutorManager(launcher.latch);
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