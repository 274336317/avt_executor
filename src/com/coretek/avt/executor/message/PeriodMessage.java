package com.coretek.avt.executor.message;

/**
 * 周期消息
 * @author David
 *
 */
public class PeriodMessage extends Message
{
	private int period;//周期
	
	private int periodCount;//周期数

	public int getPeriod()
	{
		return period;
	}

	public void setPeriod(int period)
	{
		this.period = period;
	}

	public int getPeriodCount()
	{
		return periodCount;
	}

	public void setPeriodCount(int periodCount)
	{
		this.periodCount = periodCount;
	}
}
