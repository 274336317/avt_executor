package com.coretek.avt.executor.message.handler;

import com.coretek.avt.executor.IDisposable;
import com.coretek.avt.executor.IMessageErrorListener;

public interface IMessageHandler extends IDisposable
{
	public final static int	SUCC	= 0;

	public final static int	FAILED	= 1;
	
	public void addMessageErrorListener(IMessageErrorListener listener);

	public int handle();
}
