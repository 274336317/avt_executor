package com.coretek.avt.executor.message.handler;

import java.io.IOException;

import com.coretek.avt.executor.IMessageErrorListener;
import com.coretek.avt.executor.message.SendMessage;
import com.coretek.avt.executor.server.ChannelManager;
import com.coretek.avt.executor.util.MessageEncoder;

/**
 * 执行普通发送消息
 * @author David
 *
 */
public class SendMessageHandler extends AbstractMessageHandler
{
	private SendMessage sendMsg;
	
	public SendMessageHandler(SendMessage sendMsg)
	{
		this.sendMsg = sendMsg;
	}
	
	@Override
	public int handle()
	{
		byte[] data = MessageEncoder.Encode(sendMsg, 1);
		try
		{
			ChannelManager.GetInstance().getChannel(ChannelManager.KEY_CLIENT_APP).send(data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.fireErrorEvent(sendMsg, 1, IMessageErrorListener.ERROR_SEND_FAILED);
			return IMessageHandler.FAILED;
		}
		
		return IMessageHandler.SUCC;
	}
}