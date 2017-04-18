package com.coretek.avt.executor.message;

import java.util.ArrayList;
import java.util.List;

import com.coretek.avt.executor.IDisposable;
import com.coretek.avt.executor.server.IRecvMessageListener;
import com.coretek.avt.executor.server.ISendMessageListener;

public class RawMessageManager implements IRecvMessageListener, ISendMessageListener, IDisposable
{
	private static RawMessageManager	INSTANCE	= new RawMessageManager();

	private List<SendRawMessage>	sendMessages	= new ArrayList<SendRawMessage>();

	private List<RecvRawMessage>	recvMessages	= new ArrayList<RecvRawMessage>();

	public static RawMessageManager GetInstance()
	{
		return INSTANCE;
	}

	public void addMessage(SendRawMessage msg)
	{
		sendMessages.add(msg);
	}

	public RecvRawMessage getLatestRecvMsg()
	{
		RecvRawMessage msg = null;

		synchronized (this.recvMessages)
		{
			int length = this.recvMessages.size();
			if (length != 0)
				msg = this.recvMessages.get(length - 1);
		}

		return msg;
	}

	public void addRecvMsg(RecvRawMessage recvMsg)
	{
		synchronized (this.recvMessages)
		{
			this.recvMessages.add(recvMsg);
		}

	}

	@Override
	public void onRecvMessage(int srcId, int destId, int topicId, long timestamp, byte[] data)
	{
		synchronized (recvMessages)
		{
			RecvRawMessage msg = new RecvRawMessage();
			msg.setData(data);
			msg.setSrcId(srcId);
			msg.setTopicId(topicId);
			int[] destIds = new int[1];
			destIds[0] = destId;
			msg.setDestIds(destIds);
			msg.setTimestamp(timestamp);
			this.recvMessages.add(msg);
		}
	}

	@Override
	public void onRecvMessage(String data)
	{

	}

	@Override
	public void onSendMessage(int srcId, int topicId, int[] destIds, long timestamp, byte[] data)
	{
		synchronized (sendMessages)
		{
			SendRawMessage msg = new SendRawMessage();
			msg.setData(data);
			msg.setSrcId(srcId);
			msg.setTopicId(topicId);
			msg.setDestIds(destIds);
			msg.setTimestamp(timestamp);
			this.sendMessages.add(msg);
		}
	}

	@Override
	public void dispose()
	{
		synchronized(this.recvMessages)
		{
			this.recvMessages.clear();
		}
		synchronized(this.sendMessages)
		{
			this.sendMessages.clear();
		}
		
	}
}
