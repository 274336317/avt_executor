package com.coretek.avt.executor.server;

public interface IRecvMessageListener
{
	public void onRecvMessage(int srcId, int destId, int topicId, long timestamp, byte[] data);
	
	public void onRecvMessage(String data);
}