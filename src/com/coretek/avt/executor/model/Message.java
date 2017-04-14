package com.coretek.avt.executor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息
 * 
 * @author David
 *
 */
public class Message
{
	private int		width;		// 消息的宽度，以字节为单位

	private String	uuid;		// 消息的UUID

	private int		srcId;		// 消息的发送源

	private int[]	destIds;	// 消息的目的

	private int		direction;	// 1表示发送，2表示接收

	private String	name;		// 消息名称

	private byte[]	data;		// 消息的二进制值
	
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