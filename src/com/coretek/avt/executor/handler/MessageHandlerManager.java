package com.coretek.avt.executor.handler;

import java.util.ArrayList;
import java.util.List;

import com.coretek.avt.executor.IStopListener;

public class MessageHandlerManager implements IStopListener
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
}
