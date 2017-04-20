package com.coretek.avt.executor.command;

/**
 * 设置运行模式。当前支持的运行模式包括连续执行模式、调试模式两种。<br/>
 * 命令格式为：<br/>
 * <h1>编号,setRunMode,run/debug</h1>
 * 如果是调试模式，则执行完run命令后执行器还需要等待
 * @author David
 *
 */
public class SetRunModeCommand implements ICommand
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