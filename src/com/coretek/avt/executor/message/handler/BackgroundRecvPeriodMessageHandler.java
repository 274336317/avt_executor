package com.coretek.avt.executor.message.handler;

import java.util.Timer;
import java.util.TimerTask;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.message.BackgroundRecvPeriodMessage;
import com.coretek.avt.executor.rawmessage.RawMessageManager;
import com.coretek.avt.executor.rawmessage.RecvRawMessage;
import com.coretek.avt.executor.util.MessageEncoder;

/**
 * 处理背景周期接收消息
 * @author David
 *
 */
public class BackgroundRecvPeriodMessageHandler extends AbstractMessageHandler
{
	private BackgroundRecvPeriodMessage	msg;

	private Timer						timer;

	private int							periodIndex	= 0;

	public BackgroundRecvPeriodMessageHandler(BackgroundRecvPeriodMessage msg)
	{
		this.msg = msg;
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
		private int	srcId;

		private int	topicId;

		public Job()
		{
			this.srcId = msg.getSrcId();
			this.topicId = msg.getTopicId();
		}

		@Override
		public void run()
		{
			RecvRawMessage raw = RawMessageManager.GetInstance().findPeriodRecvMsg(srcId, topicId, periodIndex);
			if (raw != null)
			{
				// 判断接收到的消息与预期消息是否匹配
				byte[] expectedData = MessageEncoder.Encode(msg, periodIndex);
				byte[] actualData = raw.getData();

				for (int i = 0; i < expectedData.length; i++)
				{
					if (expectedData[i] != actualData[i])
					{
						fireErrorEvent(msg, periodIndex, IMessageErrorListener.ERROR_VALUE_NOT_MATCHED);
						break;
					}
				}

				periodIndex++;
			}
		}

	}
}
