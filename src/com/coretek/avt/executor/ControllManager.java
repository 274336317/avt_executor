package com.coretek.avt.executor;

import java.util.concurrent.CountDownLatch;

import com.coretek.avt.executor.command.CommandManager;
import com.coretek.avt.executor.command.ExitCommand;
import com.coretek.avt.executor.command.ICommand;
import com.coretek.avt.executor.command.ICommandListener;
import com.coretek.avt.executor.command.RunCommand;
import com.coretek.avt.executor.command.SetEnvCommand;
import com.coretek.avt.executor.command.StopCommand;
import com.coretek.avt.executor.message.Message;
import com.coretek.avt.executor.message.MessageManager;
import com.coretek.avt.executor.message.handler.MessageHandlerManager;
import com.coretek.avt.executor.rawmessage.IAllMessageDoneListener;
import com.coretek.avt.executor.rawmessage.RawMessageManager;
import com.coretek.avt.executor.server.ChannelManager;
import com.coretek.avt.executor.server.TCPServer4App;
import com.coretek.avt.executor.server.TCPServer4Client;

/**
 * ���ฺ�����ִ��������������
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
	{// ������Ϣִ�д���
		System.err.printf("OnMessageError, srcId=%d, topicId=%d,period=%d,errorCode=%d", msg.getSrcId(), msg.getTopicId(), period, errorCode);
		this.shutdown();
	}

	/**
	 * �ر�ִ������ִ������
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
		{// ֹͣ����
			ChannelManager.GetInstance().dispose();
			this.shutdown();
		}
		else if (command instanceof SetEnvCommand)
		{// ���û�������
			//������������
			
			//�����뱻��Ӧ�ýڵ�֮���ͨ�ŷ���
			TCPServer4App app = new TCPServer4App(ParamsManager.GetInstance().getExecutionPort());
			app.addRecvMessageListener(RawMessageManager.GetInstance());
			app.addSendMessageListener(RawMessageManager.GetInstance());
			ChannelManager.GetInstance().addChannel(ChannelManager.KEY_CLIENT_APP, app);
			new Thread(app).start();
			
			//������SPTE UI֮���ͨ�ŷ���
			TCPServer4Client client = new TCPServer4Client(ParamsManager.GetInstance().getClientPort());
			ChannelManager.GetInstance().addChannel(ChannelManager.KEY_CLIENT_SPTE_UI,client);
			new Thread(client).start();
		}
		else if (command instanceof RunCommand)
		{// ������Ϣ
			MessageManager mm = MessageManager.GetInstance();
			new Thread(mm).start();
		}
		else if(command instanceof ExitCommand)
		{//�˳�
			this.latch.countDown();
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