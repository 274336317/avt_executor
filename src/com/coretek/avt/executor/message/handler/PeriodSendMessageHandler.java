package com.coretek.avt.executor.message.handler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.model.PeriodSendMessage;
import com.coretek.avt.executor.server.ChannelManager;
import com.coretek.avt.executor.server.IChannel;
import com.coretek.avt.executor.util.MessageEncoder;

public class PeriodSendMessageHandler extends AbstractMessageHandler
{
	private PeriodSendMessage	sendMsg;

	private Timer				timer;

	private int					periodIndex;

	private int					errorCode;

	private CountDownLatch		latch	= new CountDownLatch(1);

	public PeriodSendMessageHandler(PeriodSendMessage sendMsg)
	{
		this.sendMsg = sendMsg;
	}

	@Override
	public int handle()
	{
		timer = new Timer();
		timer.schedule(new Job(), 0, this.sendMsg.getPeriod());
		long time = this.sendMsg.getPeriod() * this.sendMsg.getPeriodCount();
		try
		{
			if(this.latch.await(time, TimeUnit.MILLISECONDS))
			{
				if (errorCode != 0)
				{
					this.fireErrorEvent(sendMsg, periodIndex, errorCode);
				}
			}
			else
			{//ʱ�䳬ʱ
				this.fireErrorEvent(sendMsg, periodIndex, IMessageErrorListener.ERROR_TIMEOUT);
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
		if (this.timer != null)
		{
			this.timer.cancel();
			this.timer = null;
		}

	}

	private class Job extends TimerTask
	{
		@Override
		public void run()
		{
			try
			{
				IChannel channel = ChannelManager.GetInstance().getChannel(ChannelManager.KEY_CLIENT_APP);
				byte[] data = MessageEncoder.Encode(sendMsg, periodIndex);
				periodIndex++;
				try
				{
					channel.send(data);
				}
				catch (IOException e)
				{
					e.printStackTrace();
					errorCode = IMessageErrorListener.ERROR_SEND_FAILED;
				}
			}
			finally
			{
				latch.countDown();
			}
		}

	}

}