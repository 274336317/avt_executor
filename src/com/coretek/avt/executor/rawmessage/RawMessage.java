package com.coretek.avt.executor.rawmessage;

public abstract class RawMessage
{
	//��Ϣ����
	private byte[] data;
	
	//��Ϣ������
	private int topicId;
	
	//��Ϣ����Դ
	private int srcId;
	
	//��Ϣ��Ŀ�ĵ�
	private int[] destIds;
	
	//��Ϣ����ʱ��ϵͳʱ��
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