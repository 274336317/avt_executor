/**
 * 
 */
package com.coretek.avt.executor;

import java.util.concurrent.CountDownLatch;

/**
 * �������
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
	 *            �����в����� </br> 1.mode=debug/run:����ģ�ͣ�ִ����֧�ֵ���ģʽ������ģʽ</br>
	 *            2.endian=big/little:��С������ </br> 3.beginMsg=uuid:��ʼ��Ϣ��UUID
	 *            </br> 4.endMsg=uuid:������Ϣ��UUID </br> 5.caseFile=·��������������·��
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