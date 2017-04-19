package com.coretek.avt.executor.message.handler;

import java.util.ArrayList;
import java.util.List;

import com.coretek.avt.executor.IDisposable;
import com.coretek.avt.executor.IStopListener;

/**
 * 消息处理器管理器
 * @author David
 *
 */
public class MessageHandlerManager implements IStopListener, IDisposable
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

	@Override
	public void onStop()
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

	@Override
	public void dispose()
	{
		for(IMessageHandler handler: handlers)
		{
			handler.dispose();
		}
	}
}
