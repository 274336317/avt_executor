package com.coretek.avt.executor.rawmessage;

public abstract class RawMessage
{
	//消息内容
	private byte[] data;
	
	//消息主题编号
	private int topicId;
	
	//消息发送源
	private int srcId;
	
	//消息的目的地
	private int[] destIds;
	
	//消息发生时的系统时间
	private long timestamp;

	public int[] getDestIds()
	{
		return destIds;
	}

	public void setDestIds(int[] destIds)
	{
		this.destIds = destIds;
	}

	public byte[] getData()
	{
		return data;
	}

	public void setData(byte[] data)
	{
		this.data = data;
	}

	public int getTopicId()
	{
		return topicId;
	}

	public void setTopicId(int topicId)
	{
		this.topicId = topicId;
	}

	public int getSrcId()
	{
		return srcId;
	}

	public void setSrcId(int srcId)
	{
		this.srcId = srcId;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}
}