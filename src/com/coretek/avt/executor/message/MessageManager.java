package com.coretek.avt.executor.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.coretek.avt.executor.ControllManager;
import com.coretek.avt.executor.IDisposable;
import com.coretek.avt.executor.ParamsManager;
import com.coretek.avt.executor.message.handler.BackgroundRecvPeriodMessageHandler;
import com.coretek.avt.executor.message.handler.BackgroundSendPeriodMessageHandler;
import com.coretek.avt.executor.message.handler.IMessageHandler;
import com.coretek.avt.executor.message.handler.MessageHandlerManager;
import com.coretek.avt.executor.message.handler.ParallelRecvMessageHandler;
import com.coretek.avt.executor.message.handler.PeriodRecvMessageHandler;
import com.coretek.avt.executor.message.handler.PeriodSendMessageHandler;
import com.coretek.avt.executor.message.handler.RecvMessageHandler;
import com.coretek.avt.executor.message.handler.SendMessageHandler;
import com.coretek.avt.executor.rawmessage.IAllMessageDoneListener;

/**
 * ��Ϣ�����������������Ϣ�Ͱ�˳��ִ����Ϣ
 * 
 * @author David
 *
 */
public class MessageManager implements IDisposable, Runnable
{
	private static MessageManager			INSTANCE		= new MessageManager();

	//���������ļ��е�������Ϣ
	private List<TestCase>					testCaseList	= new ArrayList<TestCase>();

	//��Ҫ��ִ�е���Ϣ,��run������ָ��
	private List<Object>					toBeExecuted	= new ArrayList<Object>();

	private List<IAllMessageDoneListener>	listeners		= new ArrayList<IAllMessageDoneListener>();

	private volatile boolean				flag			= true;

	public static MessageManager GetInstance()
	{
		return INSTANCE;
	}

	public void addTestCase(TestCase testCase)
	{
		this.testCaseList.add(testCase);
	}

	public void addAllMessageDoneListener(IAllMessageDoneListener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	public void run()
	{
		String startUUID = ParamsManager.GetInstance().getBeginMsg();
		String endUUID = ParamsManager.GetInstance().getEndMsg();
		
		int startIndex = 0;
		int endIndex = testCaseList.size();
		
		
		if(startUUID != null)
		{
			for(int i = 0; i < testCaseList.size(); i++)
			{
				Object obj =  testCaseList.get(i);
				if(obj instanceof Message)
				{
					Message msg = (Message)obj;
					if(startUUID.equals(msg.getUuid()))
					{
						startIndex = i;
						break;
					}
				}
			}
		}
		
		if(endUUID != null)
		{
			for(int i = 0; i < testCaseList.size(); i++)
			{
				Object obj =  testCaseList.get(i);
				if(obj instanceof Message)
				{
					Message msg = (Message)obj;
					if(endUUID.equals(msg.getUuid()))
					{
						endIndex = i;
						endIndex++;
						break;
					}
				}
			}
		}
		
		toBeExecuted = this.testCaseList.get(0).getElements().subList(startIndex, endIndex);
		
		Iterator<Object> it = toBeExecuted.iterator();

		int messageIndex = 0;

		while (it.hasNext() && flag)
		{
			Object obj = it.next();

			if (obj instanceof ISendMessage)
			{// ������Ϣ
				int ret = this.executeSendMessage((ISendMessage) obj);
				if (ret != IMessageHandler.SUCC)
				{// ��Ϣִ��ʧ��
					break;
				}
			}
			else if (obj instanceof IRecvMessage)
			{// ������Ϣ
				int ret = this.executeRecvMessage((IRecvMessage) obj);
				if (ret != IMessageHandler.SUCC)
				{// ��Ϣִ��ʧ��
					break;
				}
			}
			else if (obj instanceof TimeSpan)
			{// ʱ����
				this.executeTimeStamp((TimeSpan) obj, messageIndex);
			}

			messageIndex++;
		}

		this.fireOnAllMessageDoneEvent();
	}

	private void fireOnAllMessageDoneEvent()
	{
		for (IAllMessageDoneListener listener : listeners)
		{
			listener.onAllMessageDone();
		}
	}

	/**
	 * ִ�з�����Ϣ
	 * 
	 * @param msg
	 * @return
	 */
	private int executeSendMessage(ISendMessage msg)
	{
		if (msg instanceof BackgroundSendPeriodMessage)
		{
			BackgroundSendPeriodMessageHandler handler = new BackgroundSendPeriodMessageHandler((BackgroundSendPeriodMessage) msg);
			handler.addMessageErrorListener(ControllManager.GetInstance());
			MessageHandlerManager.GetInstance().addHandler(handler);
			return handler.handle();
		}
		else if (msg instanceof PeriodSendMessage)
		{
			PeriodSendMessageHandler handler = new PeriodSendMessageHandler((PeriodSendMessage) msg);
			handler.addMessageErrorListener(ControllManager.GetInstance());
			MessageHandlerManager.GetInstance().addHandler(handler);
			int ret = handler.handle();

			return ret;
		}
		else if (msg instanceof SendMessage)
		{
			SendMessageHandler handler = new SendMessageHandler((SendMessage) msg);
			handler.addMessageErrorListener(ControllManager.GetInstance());
			MessageHandlerManager.GetInstance().addHandler(handler);
			int ret = handler.handle();
			MessageHandlerManager.GetInstance().removeHandler(handler);
			return ret;
		}
		else
		{
			return IMessageHandler.FAILED;
		}
	}

	/**
	 * ִ�н�����Ϣ
	 * @param msg
	 * @return
	 */
	private int executeRecvMessage(IRecvMessage msg)
	{
		if (msg instanceof BackgroundRecvPeriodMessage)
		{
			BackgroundRecvPeriodMessageHandler handler = new BackgroundRecvPeriodMessageHandler((BackgroundRecvPeriodMessage) msg);
			handler.addMessageErrorListener(ControllManager.GetInstance());
			int ret = handler.handle();
			return ret;
		}
		else if (msg instanceof PeriodRecvMessage)
		{
			PeriodRecvMessageHandler handler = new PeriodRecvMessageHandler((PeriodRecvMessage) msg);
			handler.addMessageErrorListener(ControllManager.GetInstance());
			int ret = handler.handle();
			MessageHandlerManager.GetInstance().removeHandler(handler);

			return ret;
		}
		else if (msg instanceof ParallelRecvMessage)
		{
			ParallelRecvMessageHandler handler = new ParallelRecvMessageHandler((ParallelRecvMessage) msg);
			handler.addMessageErrorListener(ControllManager.GetInstance());
			int ret = handler.handle();
			MessageHandlerManager.GetInstance().removeHandler(handler);

			return ret;
		}
		else if (msg instanceof RecvMessage)
		{
			RecvMessageHandler handler = new RecvMessageHandler((RecvMessage) msg);
			handler.addMessageErrorListener(ControllManager.GetInstance());
			int ret = handler.handle();
			MessageHandlerManager.GetInstance().removeHandler(handler);

			return ret;
		}
		else
		{
			return IMessageHandler.FAILED;
		}
	}

	private void executeTimeStamp(TimeSpan ts, int messageIndex)
	{

	}

	@Override
	public void dispose()
	{
		flag = false;
		this.testCaseList.clear();
	}
}