package com.coretek.avt.executor.server;

import java.io.Closeable;
import java.io.IOException;

/**
 * ������ͻ��ˡ�Ӧ��ͨ�ŵ�ͨ���ĳ���
 * @author David
 *
 */
public interface IChannel extends Closeable
{
	/**
	 * ������Ϣ
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