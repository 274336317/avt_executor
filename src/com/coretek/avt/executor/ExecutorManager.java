package com.coretek.avt.executor;

import java.util.concurrent.CountDownLatch;

import com.coretek.avt.executor.message.MessageManager;
import com.coretek.avt.executor.model.Message;
import com.coretek.avt.executor.server.ChannelManager;
import com.coretek.avt.executor.server.IRecvMessageListener;

/**
 * ���ฺ��ִ�в��������е�������Ϣ
 * @author David
 *
 */
public class ExecutorManager implements Runnable, IRecvMessageListener, IMessageErrorListener
{
	private CountDownLatch	latch;

	public ExecutorManager(CountDownLatch latch)
	{
		this.latch = latch;
	}

	@Override
	public void run()
	{

	}

	@Override
	public void onRecvMessage(int srcId, int destId, int topicId, long timestamp, byte[] data)
	{
		
	}

	@Override
	public void onRecvMessage(String data)
	{
		if ("STOP".equals(data))
		{// ����ִ֪ͨ����ִֹͣ��
			ChannelManager.GetInstance().dispose();

			this.latch.countDown();
		}
	}

	@Override
	public void onMessageError(Message msg, int period, int errorCode)
	{
		System.err.println("errorCode:" + errorCode);
		MessageManager.GetInstance().dispose();
	}

}
