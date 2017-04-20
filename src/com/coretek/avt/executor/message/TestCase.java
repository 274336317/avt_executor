package com.coretek.avt.executor.message;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ʾ��������
 * @author David
 *
 */
public class TestCase
{
	private String path;//���������ļ���·��
	
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