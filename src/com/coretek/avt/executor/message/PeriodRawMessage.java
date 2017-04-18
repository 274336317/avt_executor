package com.coretek.avt.executor.message;

public class PeriodRawMessage extends RawMessage
{
	//周期下标
	private int periodIndex;

	public int getPeriodIndex()
	{
		return periodIndex;
	}

	public void setPeriodIndex(int periodIndex)
	{
		this.periodIndex = periodIndex;
	}
}
