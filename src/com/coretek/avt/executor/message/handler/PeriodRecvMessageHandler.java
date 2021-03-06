package com.coretek.avt.executor.message.handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.ParamsManager;
import com.coretek.avt.executor.message.PeriodRecvMessage;
import com.coretek.avt.executor.rawmessage.RawMessageManager;
import com.coretek.avt.executor.rawmessage.RecvRawMessage;
import com.coretek.avt.executor.util.MessageEncoder;

/**
 * 执行周期接收消息
 * 
 * @author David
 *
 */
public class PeriodRecvMessageHandler extends AbstractMessageHandler
{
	private PeriodRecvMessage	recvMessage;

	private Timer				timer;

	private Job					job;

	private int					errorCode	= 0;

	private int					periodIndex	= 1;

	private CountDownLatch		latch		= new CountDownLatch(1);

	public PeriodRecvMessageHandler(PeriodRecvMessage recvMessage)
	{
		this.recvMessage = recvMessage;
	}

	@Override
	public int handle()
	{
		job = new Job();
		timer = new Timer();
		timer.schedule(job, 0, recvMessage.getPeriod());
		try
		{
			boolean ret = latch.await(recvMessage.getPeriod() * recvMessage.getPeriodCount() + ParamsManager.RECV_MSG_TIMEOUT, TimeUnit.MILLISECONDS);
			if (!ret)
			{// 超时
				this.fireErrorEvent(recvMessage, periodIndex, IMessageErrorListener.ERROR_TIMEOUT);
				return IMessageHandler.FAILED;
			}
			else if (this.errorCode != 0)
			{
				fireErrorEvent(recvMessage, periodIndex, errorCode);
				return IMessageHandler.FAILED;
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			this.fireErrorEvent(recvMessage, periodIndex, IMessageErrorListener.ERROR_RECV_FAILED);
			return IMessageHandler.FAILED;
		}
		return IMessageHandler.SUCC;
	}

	@Override
	public void dispose()
	{
		if(timer != null)
		{
			timer.cancel();
			timer = null;
		}
	}

	private class Job extends TimerTask
	{
		private int	srcId;

		private int	topicId;

		public Job()
		{
			this.srcId = recvMessage.getSrcId();
			this.topicId = recvMessage.getTopicId();
		}

		@Override
		public void run()
		{
			RecvRawMessage raw = RawMessageManager.GetInstance().findPeriodRecvMsg(srcId, topicId, periodIndex);
			if (raw != null)
			{
				byte[] expectedData = MessageEncoder.Encode(recvMessage, periodIndex);
				byte[] actualData = raw.getData();
				for (int k = 0; k < actualData.length; k++)
				{
					if (actualData[k] != expectedData[k])
					{
						errorCode = IMessageErrorListener.ERROR_VALUE_NOT_MATCHED;
						dispose();
						latch.countDown();
						return;
					}
				}

				periodIndex++;
			}
		}
	}
}