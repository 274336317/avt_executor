package com.coretek.avt.executor;

import java.util.concurrent.CountDownLatch;

import com.coretek.avt.executor.command.ICommand;
import com.coretek.avt.executor.command.ICommandHandler;
import com.coretek.avt.executor.command.StopCommand;
import com.coretek.avt.executor.message.MessageManager;
import com.coretek.avt.executor.model.Message;
import com.coretek.avt.executor.server.ChannelManager;
import com.coretek.avt.executor.server.IRecvMessageListener;

/**
 * 此类负责执行测试用例中的所有消息
 * @author David
 *
 */
public class ExecutorManager implements Runnable, IRecvMessageListener, IMessageErrorListener, ICommandHandler
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

	@Override
	public void onMessageError(Message msg, int period, int errorCode)
	{
		System.err.println("errorCode:" + errorCode);
		MessageManager.GetInstance().dispose();
	}

	@Override
	public void handle(ICommand command)
	{
		if(command instanceof StopCommand)
		{
			ChannelManager.GetInstance().dispose();
		}
	}

}
