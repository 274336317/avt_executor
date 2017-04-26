package com.coretek.avt.executor.command;

/**
 * ����������ִ֪ͨ������ʼִ����Ϣ������ĸ�ʽΪ�����,run,{testCase=,start=,endUUID=}
 * 
 * @author David
 *
 */
public class RunCommand extends AbstractCommand
{
	private String	startUUID;

	private String	endUUID;

	private String	testCase;

	public RunCommand(String text, int index, String command, String[] params, String testCase, String startUUID, String endUUID)
	{
		super(text, index, command, params);
		this.testCase = testCase;
		this.startUUID = startUUID;
		this.endUUID = endUUID;
	}

	public String getTestCase()
	{
		return testCase;
	}

	public void setTestCase(String testCase)
	{
		this.testCase = testCase;
	}

	public void setStartUUID(String startUUID)
	{
		this.startUUID = startUUID;
	}

	public void setEndUUID(String endUUID)
	{
		this.endUUID = endUUID;
	}

	public String getStartUUID()
	{
		return startUUID;
	}

	public String getEndUUID()
	{
		return endUUID;
	}

	@Override
	public String getResult()
	{
		return this.toString();
	}
}