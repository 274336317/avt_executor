package com.coretek.avt.executor.handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.message.RawMessageManager;
import com.coretek.avt.executor.message.RecvRawMessage;
import com.coretek.avt.executor.model.PeriodRecvMessage;
import com.coretek.avt.executor.util.MessageEncoder;

public class PeriodRecvMessageHandler extends AbstractMessageHandler
{
	private PeriodRecvMessage	recvMessage;

	private Timer				timer;

	private Job					job;

	private int					errorCode	= 0;

	private int					periodIndex	= 0;

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
			latch.await();
			if(this.errorCode != 0)
			{
				 fireErrorEvent(recvMessage, periodIndex, errorCode);
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void dispose()
	{
		timer.cancel();
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
			try
			{
				for (int i = 1; i <= recvMessage.getPeriodCount(); i++)
				{
					RecvRawMessage raw = RawMessageManager.GetInstance().findPeriodRecvMsg(srcId, topicId, i);
					if (raw == null)
					{
						errorCode = IMessageErrorListener.ERROR_RECV_FAILED;
						return;
					}
					else
					{
						byte[] expectedData = MessageEncoder.Encode(recvMessage, i);
						byte[] actualData = raw.getData();
						for (int k = 0; k < actualData.length; i++)
						{
							if (actualData[k] != expectedData[k])
							{
								errorCode = IMessageErrorListener.ERROR_VALUE_NOT_MATCHED;
								return;
							}
						}
					}
				}
			}
			finally
			{
				dispose();
				latch.countDown();
			}
		}
	}
}