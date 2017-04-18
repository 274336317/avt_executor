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
	public void send(byte[] data) throws IOException;
	
	public void addRecvMessageListener(IRecvMessageListener listener);
	
	public void removeRecvMessageListener(IRecvMessageListener listener);
}