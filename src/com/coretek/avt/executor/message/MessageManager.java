package com.coretek.avt.executor.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.coretek.avt.executor.IDisposable;
import com.coretek.avt.executor.message.handler.BackgroundRecvPeriodMessageHandler;
import com.coretek.avt.executor.message.handler.BackgroundSendPeriodMessageHandler;
import com.coretek.avt.executor.message.handler.IMessageHandler;
import com.coretek.avt.executor.message.handler.MessageHandlerManager;
import com.coretek.avt.executor.message.handler.ParallelRecvMessageHandler;
import com.coretek.avt.executor.message.handler.PeriodRecvMessageHandler;
import com.coretek.avt.executor.message.handler.PeriodSendMessageHandler;
import com.coretek.avt.executor.message.handler.RecvMessageHandler;
import com.coretek.avt.executor.message.handler.SendMessageHandler;
import com.coretek.avt.executor.model.BackgroundRecvPeriodMessage;
import com.coretek.avt.executor.model.BackgroundSendPeriodMessage;
import com.coretek.avt.executor.model.IRecvMessage;
import com.coretek.avt.executor.model.ISendMessage;
import com.coretek.avt.executor.model.Message;
import com.coretek.avt.executor.model.ParallelRecvMessage;
import com.coretek.avt.executor.model.PeriodRecvMessage;
import com.coretek.avt.executor.model.PeriodSendMessage;
import com.coretek.avt.executor.model.RecvMessage;
import com.coretek.avt.executor.model.SendMessage;
import com.coretek.avt.executor.model.TimeStamp;

/**
 * 消息管理器。负责管理消息和安顺序执行消息
 * 
 * @author David
 *
 */
public class MessageManager implements IDisposable, Runnable
{
	private static MessageManager			INSTANCE	= new MessageManager();

	private List<Object>					messages	= new ArrayList<Object>();

	private List<IAllMessageDoneListener>	listeners	= new ArrayList<IAllMessageDoneListener>();

	private volatile boolean				flag		= true;

	public static MessageManager GetInstance()
	{
		return INSTANCE;
	}

	public void addMessage(Message msg)
	{
		this.messages.add(msg);
	}

	public void addAllMessageDoneListener(IAllMessageDoneListener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	public void run()
	{
		Iterator<Object> it = messages.iterator();

		int messageIndex = 0;

		while (it.hasNext() && flag)
		{
			Object obj = it.next();

			if (obj instanceof ISendMessage)
			{// 发送消息
				int ret = this.executeSendMessage((ISendMessage) obj);
				if (ret != IMessageHandler.SUCC)
				{// 消息执行失败
					break;
				}
			}
			else if (obj instanceof IRecvMessage)
			{// 接收消息
				int ret = this.executeRecvMessage((IRecvMessage) obj);
				if (ret != IMessageHandler.SUCC)
				{// 消息执行失败
					break;
				}
			}
			else if (obj instanceof TimeStamp)
			{// 时间表达式
				this.executeTimeStamp((TimeStamp) obj, messageIndex);
			}

			messageIndex++;
		}
	}

	/**
	 * 执行发送消息
	 * 
	 * @param msg
	 * @return
	 */
	private int executeSendMessage(ISendMessage msg)
	{
		if (msg instanceof BackgroundSendPeriodMessage)
		{
			BackgroundSendPeriodMessageHandler handler = new BackgroundSendPeriodMessageHandler((BackgroundSendPeriodMessage) msg);
			MessageHandlerManager.GetInstance().addHandler(handler);
			return handler.handle();
		}
		else if (msg instanceof PeriodSendMessage)
		{
			PeriodSendMessageHandler handler = new PeriodSendMessageHandler((PeriodSendMessage) msg);
			MessageHandlerManager.GetInstance().addHandler(handler);
			return handler.handle();
		}
		else if (msg instanceof SendMessage)
		{
			SendMessageHandler handler = new SendMessageHandler((SendMessage) msg);
			MessageHandlerManager.GetInstance().addHandler(handler);
			return handler.handle();
		}
		else
		{
			return IMessageHandler.FAILED;
		}
	}

	private int executeRecvMessage(IRecvMessage msg)
	{
		if (msg instanceof BackgroundRecvPeriodMessage)
		{
			BackgroundRecvPeriodMessageHandler handler = new BackgroundRecvPeriodMessageHandler((BackgroundRecvPeriodMessage) msg);
			return handler.handle();
		}
		else if (msg instanceof PeriodRecvMessage)
		{
			PeriodRecvMessageHandler handler = new PeriodRecvMessageHandler((PeriodRecvMessage) msg);
			return handler.handle();
		}
		else if (msg instanceof ParallelRecvMessage)
		{
			ParallelRecvMessageHandler handler = new ParallelRecvMessageHandler((ParallelRecvMessage) msg);
			return handler.handle();
		}
		else if (msg instanceof RecvMessage)
		{
			RecvMessageHandler handler = new RecvMessageHandler((RecvMessage) msg);
			return handler.handle();
		}
		else
		{
			return IMessageHandler.FAILED;
		}
	}

	private void executeTimeStamp(TimeStamp ts, int messageIndex)
	{

	}

	@Override
	public void dispose()
	{
		flag = false;
		this.messages.clear();
	}
}