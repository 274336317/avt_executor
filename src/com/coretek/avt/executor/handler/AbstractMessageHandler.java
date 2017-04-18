package com.coretek.avt.executor.handler;

import java.util.ArrayList;
import java.util.List;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.model.Message;

public abstract class AbstractMessageHandler implements IMessageHandler
{
	private List<IMessageErrorListener> listeners = new ArrayList<IMessageErrorListener>();
	
	public void addMessageErrorListener(IMessageErrorListener listener)
	{
		this.listeners.add(listener);
	}
	
	public void fireErrorEvent(Message msg, int period, int errorCode)
	{
		for(IMessageErrorListener listener: listeners)
		{
			listener.onMessageError(msg, period, errorCode);
		}
	}
}
