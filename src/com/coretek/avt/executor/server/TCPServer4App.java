package com.coretek.avt.executor.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.coretek.avt.executor.util.MessageDecoder;

/**
 * 用于与应用进行通信的TCP服务端。
 * 
 * @author David
 *
 */
public class TCPServer4App implements IChannel, Runnable
{
	private ServerSocket				serverSocket;

	private Socket						socket;

	private InputStream					inputStream;

	private OutputStream				outputStream;

	private int							port;

	private List<IRecvMessageListener>	listeners		= new ArrayList<IRecvMessageListener>(2);

	private List<ISendMessageListener>	sendListeners	= new ArrayList<ISendMessageListener>(2);

	private RecvJob						recvJob;

	public TCPServer4App(int port)
	{
		this.port = port;
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

			this.recvJob = new RecvJob();
			new Thread(this.recvJob).start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException
	{
		if (recvJob != null)
		{
			this.recvJob.shutDown();
			this.recvJob = null;
		}

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
	public void send(int srcId, int topicId, int[] destIds, byte[] data) throws IOException
	{
		if (this.outputStream != null)
		{
			this.outputStream.write(data);
			this.fireOnMessageSendEvent(srcId, topicId, destIds, System.currentTimeMillis(), data);
		}
	}

	private void fireOnMessageSendEvent(int srcId, int topicId, int[] destIds, long timestamp, byte[] data)
	{
		for (ISendMessageListener listener : sendListeners)
		{
			listener.onSendMessage(srcId, topicId, destIds, timestamp, data);
		}
	}

	@Override
	public void addRecvMessageListener(IRecvMessageListener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	public void removeRecvMessageListener(IRecvMessageListener listener)
	{
		this.listeners.remove(listener);
	}

	private class RecvJob implements Runnable
	{
		private final int	packageSize	= 2048;

		private boolean		flag		= false;

		public void shutDown()
		{
			this.flag = true;
		}

		@Override
		public void run()
		{
			byte[] data = new byte[2048];

			while (flag == false)
			{
				try
				{
					int position = inputStream.read(data);
					while (position < packageSize)
					{
						position += inputStream.read(data, position, packageSize - position);
					}

					int srcId = MessageDecoder.GetDestId(data);
					int topicId = MessageDecoder.GetTopicId(data);
					int destId = MessageDecoder.GetDestId(data);
					long timestamp = System.currentTimeMillis();

					for (IRecvMessageListener listener : listeners)
					{
						listener.onRecvMessage(srcId, destId, topicId, timestamp, data);
					}

					// 结束接收
					if (data[0] == 0 && data[1] == 0 && data[2] == 0 && data[3] == 0)
					{
						break;
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void addSendMessageListener(ISendMessageListener listener)
	{
		this.sendListeners.add(listener);
	}

	@Override
	public void removeSendMessageListener(ISendMessageListener listener)
	{
		this.sendListeners.remove(listener);
	}
}