package com.coretek.avt.executor.handler;

import com.coretek.avt.executor.model.BackgroundSendPeriodMessage;

public class BackgroundSendPeriodMessageHandler implements IBackgroundPeriodMessageHandler
{
	private BackgroundSendPeriodMessage msg;
	
	public BackgroundSendPeriodMessageHandler(BackgroundSendPeriodMessage msg)
	{
		this.msg = msg;
	}
	
	@Override
	public void dispose()
	{
		

	}

	@Override
	public void run()
	{
		
	}

}
