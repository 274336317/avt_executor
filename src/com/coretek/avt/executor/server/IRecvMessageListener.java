package com.coretek.avt.executor.server;

/**
 * 
 * @author David
 *
 */
public interface IRecvMessageListener
{
	public void onRecvMessage(int srcId, int destId, int topicId, long timestamp, byte[] data);
}