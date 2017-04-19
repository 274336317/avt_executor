package com.coretek.avt.executor.message.handler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.model.BackgroundSendPeriodMessage;
import com.coretek.avt.executor.server.ChannelManager;
import com.coretek.avt.executor.server.IChannel;
import com.coretek.avt.executor.util.MessageEncoder;

/**
 * 执行背景周期发送消息
 * @author David
 *
 */
public class BackgroundSendPeriodMessageHandler extends AbstractMessageHandler
{
	private BackgroundSendPeriodMessage	msg;

	private Timer						timer;

	public BackgroundSendPeriodMessageHandler(BackgroundSendPeriodMessage msg)
	{
		this.msg = msg;
	}

	@Override
	public void dispose()
	{
		this.timer.cancel();
	}

	@Override
	public int handle()
	{
		int period = msg.getPeriod();
		timer = new Timer();
		timer.schedule(new Job(), 0, period);

		return IMessageHandler.SUCC;
	}

	private class Job extends TimerTask
	{
		private int	periodIndex;

		@Override
		public void run()
		{
			byte[] data = MessageEncoder.Encode(msg, periodIndex);
			IChannel channel = ChannelManager.GetInstance().getChannel(ChannelManager.KEY_CLIENT_APP);
			try
			{
				channel.send(data);
				periodIndex++;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				fireErrorEvent(msg, periodIndex, IMessageErrorListener.ERROR_SEND_FAILED);
			}
		}
	}
}