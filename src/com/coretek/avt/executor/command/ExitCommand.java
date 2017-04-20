package com.coretek.avt.executor.command;

/**
 * 退出命令。此命令告诉执行器，结束生命周期。
 * @author David
 *
 */
public class ExitCommand implements ICommand
{

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
		// TODO Auto-generated method stub

	}

	@Override
	public String getText()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
