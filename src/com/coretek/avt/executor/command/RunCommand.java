package com.coretek.avt.executor.command;

/**
 * ����������ִ֪ͨ������ʼִ����Ϣ������ĸ�ʽΪ�����,run,startUUID=,endUUID=
 * @author David
 *
 */
public class RunCommand implements ICommand
{
	private String startUUID;
	
	private String endUUID;
	
	public String getStartUUID()
	{
		return startUUID;
	}
	
	public String getEndUUID()
	{
		return endUUID;
	}
	
	@Override
	public int getIndex()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCommand()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParams()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResult()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSucc()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFailed(String errMsg)
	{
		
	}

	@Override
	public String getText()
	{
		// TODO Auto-generated method stub
		return null;
	}
}