package com.coretek.avt.executor.message;

/**
 * ������Ϣ
 * @author David
 *
 */
public class PeriodMessage extends Message
{
	private int period;//����
	
	private int periodCount;//������

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
