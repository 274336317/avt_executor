package com.coretek.avt.executor.rawmessage;

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
