package com.coretek.avt.executor.handler;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.message.RawMessageManager;
import com.coretek.avt.executor.message.RecvRawMessage;
import com.coretek.avt.executor.model.RecvMessage;
import com.coretek.avt.executor.util.MessageEncoder;

/**
 * 普通接收消息处理器
 * @author David
 *
 */
public class RecvMessageHandler extends AbstractMessageHandler
{
	private RecvMessage recvMsg;
	
	public RecvMessageHandler(RecvMessage recvMsg)
	{
		this.recvMsg = recvMsg;
	}
	
	@Override
	public int handle()
	{
		int srcId = recvMsg.getSrcId();
		int topicId = recvMsg.getTopicId();
		int period = 1;
		
		RecvRawMessage msg = RawMessageManager.GetInstance().findPeriodRecvMsg(srcId, topicId, period);
		if(msg == null)
		{
			this.fireErrorEvent(recvMsg, period, IMessageErrorListener.ERROR_RECV_FAILED);
		}
		
		byte[] expectedData = MessageEncoder.Encode(recvMsg, 1);
		byte[] actualData = msg.getData();
		for(int i = 0 ; i < actualData.length; i++)
		{
			if(actualData[i] != expectedData[i])
			{
				this.fireErrorEvent(recvMsg, period, IMessageErrorListener.ERROR_VALUE_NOT_MATCHED);
			}
		}
		
		return 0;
	}

	@Override
	public void dispose()
	{

	}

}
