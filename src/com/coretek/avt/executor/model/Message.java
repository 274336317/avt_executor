package com.coretek.avt.executor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ��Ϣ
 * 
 * @author David
 *
 */
public class Message
{
	private int		width;		// ��Ϣ�Ŀ�ȣ����ֽ�Ϊ��λ

	private String	uuid;		// ��Ϣ��UUID

	private int		srcId;		// ��Ϣ�ķ���Դ

	private int[]	destIds;	// ��Ϣ��Ŀ��

	private int		direction;	// 1��ʾ���ͣ�2��ʾ����

	private String	name;		// ��Ϣ����

	private byte[]	data;		// ��Ϣ�Ķ�����ֵ
	
	private List<Period> periods = new ArrayList<Period>(1);

	public List<Period> getPeriods()
	{
		return periods;
	}

	public void setPeriods(List<Period> periods)
	{
		this.periods = periods;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public int getSrcId()
	{
		return srcId;
	}

	public void setSrcId(int srcId)
	{
		this.srcId = srcId;
	}

	public int[] getDestIds()
	{
		return destIds;
	}

	public void setDestIds(int[] destIds)
	{
		this.destIds = destIds;
	}

	public int getDirection()
	{
		return direction;
	}

	public void setDirection(int direction)
	{
		this.direction = direction;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public byte[] getData()
	{
		return data;
	}

	public void setData(byte[] data)
	{
		this.data = data;
	}
}