package com.coretek.avt.executor.message;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示测试用例
 * @author David
 *
 */
public class TestCase
{
	private String path;//测试用例文件的路径
	
	private List<Message> messages = new ArrayList<Message>();
	
	public TestCase(String path)
	{
		this.path = path;
	}
	
	public void addMessage(Message msg)
	{
		this.messages.add(msg);
	}
	
	public List<Message> getMessages()
	{
		return messages;
	}
}