package com.coretek.avt.executor.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.coretek.avt.executor.IDisposable;

/**
 * 通道管理器，负责管理所有的通信通道
 * 
 * @author David
 *
 */
public class ChannelManager implements IDisposable
{
	public final static String		KEY_CLIENT_SPTE_UI	= "SPTE_UI";

	public final static String		KEY_CLIENT_APP		= "APP";

	private Map<String, IChannel>	map					= new HashMap<String, IChannel>();

	private static ChannelManager	INSTANCE			= new ChannelManager();

	public static ChannelManager GetInstance()
	{
		return INSTANCE;
	}

	public void addChannel(String key, IChannel channel)
	{
		map.put(key, channel);
	}

	public IChannel getChannel(String key)
	{
		IChannel channel = map.get(key);
		return channel;
	}

	@Override
	public void dispose()
	{
		for (Entry<String, IChannel> entry : map.entrySet())
		{
			IChannel channel = entry.getValue();
			try
			{
				channel.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		map.clear();
	}
}
