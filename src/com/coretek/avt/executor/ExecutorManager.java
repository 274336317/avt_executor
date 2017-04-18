package com.coretek.avt.executor;

import java.util.concurrent.CountDownLatch;

import com.coretek.avt.executor.server.ChannelManager;
import com.coretek.avt.executor.server.IRecvMessageListener;

public class ExecutorManager implements Runnable, IRecvMessageListener
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
		{// 界面通知执行器停止执行
			ChannelManager.GetInstance().dispose();

			this.latch.countDown();
		}
	}

}
