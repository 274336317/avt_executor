package com.coretek.avt.executor.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.coretek.avt.executor.IDisposable;
import com.coretek.avt.executor.IStopListener;
import com.coretek.avt.executor.model.BackgroundSendPeriodMessage;
import com.coretek.avt.executor.model.IRecvMessage;
import com.coretek.avt.executor.model.ISendMessage;
import com.coretek.avt.executor.model.Message;
import com.coretek.avt.executor.model.TimeStamp;

public class MessageManager implements IDisposable, Runnable, IStopListener
{
	private static MessageManager	INSTANCE	= new MessageManager();

	private List<Object>			messages	= new ArrayList<Object>();

	private volatile boolean		flag		= true;

	public static MessageManager GetInstance()
	{
		return INSTANCE;
	}

	public void addMessage(Message msg)
	{
		this.messages.add(msg);
	}

	@Override
	public void run()
	{
		Iterator<Object> it = messages.iterator();
		
		int index = 0;
		
		while(it.hasNext() && flag)
		{
			Object obj = it.next();
			
			if(obj instanceof ISendMessage)
			{//发送消息
				this.executeSendMessage((ISendMessage)obj);
			} else if(obj instanceof IRecvMessage)
			{//接收消息
				this.executeRecvMessage((IRecvMessage)obj);
			} else if(obj instanceof TimeStamp)
			{//时间表达式
				this.executeTimeStamp((TimeStamp)obj);
			}
			
			index++;
		}
	}
	
	private void executeSendMessage(ISendMessage msg)
	{
		if(msg instanceof BackgroundSendPeriodMessage)
		{
			
		}
	}
	
	private void executeRecvMessage(IRecvMessage msg)
	{
		
	}
	
	private void executeTimeStamp(TimeStamp ts)
	{
		
	}

	@Override
	public void dispose()
	{
		this.messages.clear();
	}

	@Override
	public void onStop()
	{
		flag = false;
	}
}