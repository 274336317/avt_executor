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

/**
 * 运行周期发送消息
 * @author David
 *
 */
public class PeriodSendMessageHandler extends AbstractMessageHandler
{
	private PeriodSendMessage	sendMsg;

	private Timer				timer;

	private volatile int		periodIndex;

	private volatile int		errorCode;

	private CountDownLatch		latch	= new CountDownLatch(1);

	public PeriodSendMessageHandler(PeriodSendMessage sendMsg)
	{
		this.sendMsg = sendMsg;
	}

	@Override
	public int handle()
	{
		timer = new Timer();
		timer.schedule(new SendJob(), 0, this.sendMsg.getPeriod());
		long time = this.sendMsg.getPeriod() * this.sendMsg.getPeriodCount();
		try
		{
			if (this.latch.await(time, TimeUnit.MILLISECONDS))
			{
				if (errorCode != 0)
				{
					this.fireErrorEvent(sendMsg, periodIndex, errorCode);
					
					return IMessageHandler.FAILED;
				}
			}
			else
			{// 时间超时
				this.fireErrorEvent(sendMsg, periodIndex, IMessageErrorListener.ERROR_TIMEOUT);
				return IMessageHandler.FAILED;
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			this.fireErrorEvent(sendMsg, periodIndex, IMessageErrorListener.ERROR_SEND_FAILED);
			return IMessageHandler.FAILED;
		}
		finally
		{
			this.dispose();
		}

		return IMessageHandler.SUCC;
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

	private class SendJob extends TimerTask
	{
		@Override
		public void run()
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
				latch.countDown();
			}
			if (periodIndex >= sendMsg.getPeriodCount())
			{
				latch.countDown();
			}
		}

	}

}