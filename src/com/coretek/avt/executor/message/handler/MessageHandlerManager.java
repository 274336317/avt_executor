package com.coretek.avt.executor.message.handler;

import java.util.ArrayList;
import java.util.List;

import com.coretek.avt.executor.IDisposable;

/**
 * 消息处理器管理器
 * @author David
 *
 */
public class MessageHandlerManager implements IDisposable
{
	private final static MessageHandlerManager	INSTANCE	= new MessageHandlerManager();

	private List<IMessageHandler>				handlers	= new ArrayList<IMessageHandler>();

	public static MessageHandlerManager GetInstance()
	{
		return INSTANCE;
	}

	public void addHandler(IMessageHandler handler)
	{
		this.handlers.add(handler);
	}
	
	public void removeHandler(IMessageHandler handler)
	{
		this.handlers.remove(handler);
	}

	@Override
	public void dispose()
	{
		synchronized (handlers)
		{
			for (IMessageHandler handler : handlers)
			{
				handler.dispose();
			}

			handlers.clear();
		}
	}
}
