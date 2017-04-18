package com.coretek.avt.executor.server;

public interface ISendMessageListener
{
	public void onSendMessage(int srcId, int topicId,int destIds[], long timestamp, byte[] data);
}