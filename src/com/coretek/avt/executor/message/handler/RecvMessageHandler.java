package com.coretek.avt.executor.message.handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.ParamsManager;
import com.coretek.avt.executor.message.RawMessageManager;
import com.coretek.avt.executor.message.RecvRawMessage;
import com.coretek.avt.executor.model.RecvMessage;
import com.coretek.avt.executor.util.MessageEncoder;

/**
 * 普通接收消息处理器
 * 
 * @author David
 *
 */
public class RecvMessageHandler extends AbstractMessageHandler
{
	private RecvMessage		recvMsg;

	private int				srcId;
	private int				topicId;
	private int				period		= 1;

	private int				errorCode	= 0;

	private Timer			timer;

	private CountDownLatch	latch		= new CountDownLatch(1);

	public RecvMessageHandler(RecvMessage recvMsg)
	{
		this.recvMsg = recvMsg;
		srcId = recvMsg.getSrcId();
		topicId = recvMsg.getTopicId();
	}

	@Override
	public int handle()
	{
		try
		{
			timer = new Timer();
			timer.schedule(new RecvJob(), 0, ParamsManager.RECVJOB_INVOKE_PERIOD);

			boolean ret = this.latch.await(ParamsManager.RECV_MSG_TIMEOUT, TimeUnit.MILLISECONDS);
			if (!ret)
			{// 超时
				this.fireErrorEvent(recvMsg, 1, IMessageErrorListener.ERROR_TIMEOUT);
			}
			else
			{
				if (errorCode != 0)
				{
					this.fireErrorEvent(recvMsg, period, errorCode);
				}
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			this.fireErrorEvent(recvMsg, period, IMessageErrorListener.ERROR_RECV_FAILED);
		}
		finally
		{
			this.dispose();
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

	private class RecvJob extends TimerTask
	{

		@Override
		public void run()
		{
			RecvRawMessage msg = RawMessageManager.GetInstance().findPeriodRecvMsg(srcId, topicId, period);
			if (msg != null)
			{
				try
				{
					byte[] expectedData = MessageEncoder.Encode(recvMsg, period);
					byte[] actualData = msg.getData();
					for (int i = 0; i < actualData.length; i++)
					{
						if (actualData[i] != expectedData[i])
						{
							errorCode = IMessageErrorListener.ERROR_VALUE_NOT_MATCHED;
						}
					}
				} finally
				{
					latch.countDown();
				}
			}
		}

	}
}
