package com.coretek.avt.executor.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 用于向SPTE界面端发送执行结果
 * 
 * @author David
 *
 */
public class TCPServer4Client implements IChannel, Runnable
{
	private ServerSocket				serverSocket;

	private Socket						socket;

	private InputStream					inputStream;

	private OutputStream				outputStream;

	private int							port;

	public TCPServer4Client(int port)
	{
		this.port = port;
	}

	@Override
	public void close() throws IOException
	{
		//发送一个全0的消息给SPTE UI端
		byte[] data = new byte[2048];
		this.send(0,0,null, data);

		if (this.inputStream != null)
		{
			this.inputStream.close();
			this.inputStream = null;
		}

		if (this.outputStream != null)
		{
			this.outputStream.close();
			this.outputStream = null;
		}

		if (this.socket != null)
		{
			this.socket.close();
			this.socket = null;
		}

		if (this.serverSocket != null)
		{
			this.serverSocket.close();
			this.serverSocket = null;
		}

	}

	@Override
	public void send(int srcId, int topicId, int [] destIds, byte[] data) throws IOException
	{
		if (this.outputStream != null)
			this.outputStream.write(data);
	}

	@Override
	public void addRecvMessageListener(IRecvMessageListener listener)
	{
		
	}

	@Override
	public void removeRecvMessageListener(IRecvMessageListener listener)
	{
		
	}

	@Override
	public void run()
	{
		try
		{
			this.serverSocket = new ServerSocket(port);
			socket = this.serverSocket.accept();
			this.inputStream = socket.getInputStream();
			this.outputStream = socket.getOutputStream();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void addSendMessageListener(ISendMessageListener listener)
	{
		
	}

	@Override
	public void removeSendMessageListener(ISendMessageListener listener)
	{
		
	}
}
