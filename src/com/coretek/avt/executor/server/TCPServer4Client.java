package com.coretek.avt.executor.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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

	private RecvJob						job;

	private List<IRecvMessageListener>	listeners	= new ArrayList<IRecvMessageListener>(2);

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
		this.send(data);
		
		if (this.job != null)
		{
			this.job.shutDown();
			this.job = null;
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
	public void send(byte[] data) throws IOException
	{
		if (this.outputStream != null)
			this.outputStream.write(data);

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
		private boolean	flag	= false;

		public void shutDown()
		{
			this.flag = true;
		}

		@Override
		public void run()
		{
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(isr);

			while (flag == false)
			{
				String line = null;
				try
				{
					while ((line = br.readLine()) != null)
					{
						for (IRecvMessageListener listener : listeners)
						{
							listener.onRecvMessage(line);
						}

						if ("STOP".equalsIgnoreCase(line))
						{// 接收到来自界面客户端发送的停止运行命令
							break;
						}
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					if (br != null)
					{
						try
						{
							br.close();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}

					if (isr != null)
					{
						try
						{
							isr.close();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}

		}

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
			this.job = new RecvJob();
			new Thread(this.job).start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
