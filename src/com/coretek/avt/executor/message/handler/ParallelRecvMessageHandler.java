package com.coretek.avt.executor.message.handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.ParamsManager;
import com.coretek.avt.executor.message.ParallelRecvMessage;
import com.coretek.avt.executor.rawmessage.RawMessageManager;
import com.coretek.avt.executor.rawmessage.RecvRawMessage;

/**
 * 执行并行接收消息
 * @author David
 *
 */
public class ParallelRecvMessageHandler extends AbstractMessageHandler
{
	private ParallelRecvMessage	recvMsg;

	private Timer				timer;

	private CountDownLatch		latch	= new CountDownLatch(1);

	public ParallelRecvMessageHandler(ParallelRecvMessage recvMsg)
	{
		this.recvMsg = recvMsg;
	}

	@Override
	public int handle()
	{
		timer = new Timer();
		timer.schedule(new RecvJob(), 0, 15);
		try
		{
			if (!latch.await(ParamsManager.RECV_MSG_TIMEOUT, TimeUnit.MILLISECONDS))
			{// 超时
				this.fireErrorEvent(recvMsg, 1, IMessageErrorListener.ERROR_TIMEOUT);
				return IMessageHandler.FAILED;
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			this.fireErrorEvent(recvMsg, 1, IMessageErrorListener.ERROR_RECV_FAILED);
			return IMessageHandler.FAILED;
		} finally
		{
			this.dispose();
		}

		return IMessageHandler.SUCC;
	}
	
	@Override
	public void dispose()
	{
		if(this.timer != null)
		{
			this.timer.cancel();
			this.timer = null;
		}
	}

	private class RecvJob extends TimerTask
	{

		@Override
		public void run()
		{
			while (true)
			{
				RecvRawMessage msg = RawMessageManager.GetInstance().findPeriodRecvMsg(recvMsg.getSrcId(), recvMsg.getTopicId(), 1);
				if (msg != null)
				{
					break;
				}
			}
		}
	}
}