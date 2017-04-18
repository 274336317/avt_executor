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
	public void send(byte[] data) throws IOException;
	
	public void addRecvMessageListener(IRecvMessageListener listener);
	
	public void removeRecvMessageListener(IRecvMessageListener listener);
}