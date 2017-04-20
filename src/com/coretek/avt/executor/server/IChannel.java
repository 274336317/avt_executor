package com.coretek.avt.executor.server;

import java.io.Closeable;
import java.io.IOException;

/**
 * 用于与客户端、应用通信的通道的抽象
 * @author David
 *
 */
public interface IChannel extends Closeable
{
	/**
	 * 发送消息
	 * @param srcId
	 * @param topicId
	 * @param destIds
	 * @param data
	 * @throws IOException
	 */
	public void send(int srcId, int topicId, int [] destIds, byte[] data) throws IOException;
	
	public void addRecvMessageListener(IRecvMessageListener listener);
	
	public void removeRecvMessageListener(IRecvMessageListener listener);
	
	public void addSendMessageListener(ISendMessageListener listener);
	
	public void removeSendMessageListener(ISendMessageListener listener);
}