package com.coretek.avt.executor.handler;

import java.util.TimerTask;

import com.coretek.avt.executor.model.PeriodSendMessage;

public class PeriodSendMessageHandler extends AbstractMessageHandler
{
	private PeriodSendMessage sendMsg;
	
	private TimerTask timerTask;
	
	private int periodIndex;
	
	private int errorCode;
	
	public PeriodSendMessageHandler(PeriodSendMessage sendMsg)
	{
		this.sendMsg = sendMsg;
	}
	
	@Override
	public int handle()
	{
		return 0;
	}

	@Override
	public void dispose()
	{
		
	}
	
	private class Job extends TimerTask
	{

		@Override
		public void run()
		{
			
		}
		
	}

}