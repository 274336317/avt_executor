package com.coretek.avt.executor.command;

/**
 * 所有控制命令的父类
 * @author David
 *
 */
public abstract class AbstractCommand implements ICommand
{
	//命令的编号
	private int			index;

	//文本形式的文本内容
	private String		text;

	//失败的原因描述
	private String		reason;
	
	//用于标记命令是否执行成功
	private boolean		succ;

	//命令的名字
	private String		command;

	//命令的参数
	private String[]	params;

	public AbstractCommand(String text, int index, String command, String[] params)
	{
		this.index = index;
		this.text = text;
		this.command = command;
		this.params = params;
	}

	@Override
	public String getText()
	{
		return this.text;
	}

	@Override
	public int getIndex()
	{
		return this.index;
	}

	@Override
	public String getCommand()
	{
		return this.command;
	}

	@Override
	public String[] getParams()
	{
		return this.params;
	}

	@Override
	public String getResult()
	{
		return null;
	}

	@Override
	public void setSucc()
	{
		this.succ = true;
	}

	@Override
	public void setFailed(String reason)
	{
		this.reason = reason;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.index).append(",");
		if (this.succ)
		{
			sb.append(DONE);
		}
		else
		{
			sb.append(ERR).append(",");
			sb.append("{");
			sb.append(this.reason);
			sb.append("}");
		}
		return sb.toString();
	}
}