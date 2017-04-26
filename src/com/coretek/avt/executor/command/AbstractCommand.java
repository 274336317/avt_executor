package com.coretek.avt.executor.command;

/**
 * ���п�������ĸ���
 * @author David
 *
 */
public abstract class AbstractCommand implements ICommand
{
	//����ı��
	private int			index;

	//�ı���ʽ���ı�����
	private String		text;

	//ʧ�ܵ�ԭ������
	private String		reason;
	
	//���ڱ�������Ƿ�ִ�гɹ�
	private boolean		succ;

	//���������
	private String		command;

	//����Ĳ���
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