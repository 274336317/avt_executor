package com.coretek.avt.executor;

import java.util.concurrent.CountDownLatch;

import com.coretek.avt.executor.command.CommandManager;
import com.coretek.avt.executor.command.ExitCommand;
import com.coretek.avt.executor.command.ICommand;
import com.coretek.avt.executor.command.ICommandListener;
import com.coretek.avt.executor.command.RunCommand;
import com.coretek.avt.executor.command.SetEnvCommand;
import com.coretek.avt.executor.command.SetRunModeCommand;
import com.coretek.avt.executor.command.StopCommand;
import com.coretek.avt.executor.message.Message;
import com.coretek.avt.executor.message.handler.MessageHandlerManager;
import com.coretek.avt.executor.rawmessage.IAllMessageDoneListener;
import com.coretek.avt.executor.rawmessage.MessageManager;
import com.coretek.avt.executor.server.ChannelManager;

/**
 * 此类负责管理执行器的生命周期
 * 
 * @author David
 *
 */
public class ControllManager implements Runnable, IMessageErrorListener, ICommandListener, IAllMessageDoneListener
{
	private static ControllManager	INSTANCE	= null;

	private CountDownLatch			latch;

	public ControllManager(CountDownLatch latch)
	{
		this.latch = latch;
		INSTANCE = this;
	}

	public static ControllManager GetInstance()
	{
		if (INSTANCE == null)
		{
			throw new RuntimeException("You Should Invoke This Method Later!");
		}
		return INSTANCE;
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
	{// 处理消息执行错误
		System.err.printf("OnMessageError, srcId=%d, topicId=%d,period=%d,errorCode=%d", msg.getSrcId(), msg.getTopicId(), period, errorCode);
		this.shutdown();
	}

	/**
	 * 关闭执行器的执行流程
	 */
	private void shutdown()
	{
		MessageManager.GetInstance().dispose();
		MessageHandlerManager.GetInstance().dispose();
		ChannelManager.GetInstance().dispose();
	}

	@Override
	public void onCommand(ICommand command)
	{
		if (command instanceof StopCommand)
		{// 停止运行
			ChannelManager.GetInstance().dispose();
			this.shutdown();

		}
		else if (command instanceof SetEnvCommand)
		{// 设置环境变量

		}
		else if (command instanceof RunCommand)
		{// 运行消息
			MessageManager mm = MessageManager.GetInstance();
			new Thread(mm).start();
		}
		else if (command instanceof SetRunModeCommand)
		{// 设置运行模式

		}
		else if(command instanceof ExitCommand)
		{//退出
			this.latch.countDown();
		}
	}

	@Override
	public void onAllMessageDone()
	{// 所有消息都被执行完毕
		ICommand cmd = CommandManager.GetInstance().getRunCommand();
		cmd.setSucc();
		CommandManager.GetInstance().writeCommandResult(cmd);
	}
}