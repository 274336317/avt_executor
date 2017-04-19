package com.coretek.avt.executor;

import java.util.concurrent.CountDownLatch;

import com.coretek.avt.executor.command.CommandManager;
import com.coretek.avt.executor.command.ICommand;
import com.coretek.avt.executor.command.ICommandListener;
import com.coretek.avt.executor.command.RunCommand;
import com.coretek.avt.executor.command.SetEnvCommand;
import com.coretek.avt.executor.command.StopCommand;
import com.coretek.avt.executor.message.IAllMessageDoneListener;
import com.coretek.avt.executor.message.MessageManager;
import com.coretek.avt.executor.message.handler.MessageHandlerManager;
import com.coretek.avt.executor.model.Message;
import com.coretek.avt.executor.server.ChannelManager;

/**
 * ���ฺ�����ִ��������������
 * 
 * @author David
 *
 */
public class ControllManager implements Runnable, IMessageErrorListener, ICommandListener, IAllMessageDoneListener
{
	private CountDownLatch	latch;

	public ControllManager(CountDownLatch latch)
	{
		this.latch = latch;
	}

	@Override
	public void run()
	{
		MessageManager.GetInstance().addAllMessageDoneListener(this);
		CommandManager cm = CommandManager.GetInstance();
		new Thread(cm).start();
	}

	@Override
	public void onMessageError(Message msg, int period, int errorCode)
	{// ������Ϣִ�д���
		System.err.printf("OnMessageError, srcId=%d, topicId=%d,period=%d,errorCode=%d", msg.getSrcId(), msg.getTopicId(), period, errorCode);
		this.shutdown();
	}

	private void shutdown()
	{
		MessageManager.GetInstance().dispose();
		MessageHandlerManager.GetInstance().dispose();
		ChannelManager.GetInstance().dispose();
		this.latch.countDown();
	}

	@Override
	public void onCommand(ICommand command)
	{
		if (command instanceof StopCommand)
		{// ֹͣ����
			ChannelManager.GetInstance().dispose();
			this.shutdown();

		}
		else if (command instanceof SetEnvCommand)
		{// ���û�������

		}
		else if (command instanceof RunCommand)
		{// ������Ϣ
			MessageManager mm = MessageManager.GetInstance();
			new Thread(mm).start();
		}
	}

	@Override
	public void onAllMessageDone()
	{// ������Ϣ����ִ�����
		ICommand cmd = CommandManager.GetInstance().getRunCommand();
		cmd.setSucc();
		CommandManager.GetInstance().writeCommandResult(cmd);
	}
}