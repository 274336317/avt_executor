package com.coretek.avt.executor.handler;

import java.util.Timer;
import java.util.TimerTask;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.message.RawMessageManager;
import com.coretek.avt.executor.message.RecvRawMessage;
import com.coretek.avt.executor.model.BackgroundRecvPeriodMessage;
import com.coretek.avt.executor.util.MessageEncoder;

public class BackgoundRecvPeriodMessageHandler extends AbstractMessageHandler
{
	private BackgroundRecvPeriodMessage	msg;

	private Timer						timer;

	public BackgoundRecvPeriodMessageHandler(BackgroundRecvPeriodMessage msg)
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
		return 0;
	}

	private class Job extends TimerTask
	{
		private int	periodIndex	= 0;

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
			if (raw == null)
			{
				fireErrorEvent(msg, periodIndex, IMessageErrorListener.ERROR_RECV_FAILED);
			}
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
		}

	}
}
