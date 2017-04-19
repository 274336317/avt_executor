package com.coretek.avt.executor;

import java.util.Arrays;

/**
 * 执行器运行时管理器。
 * 
 * @author David
 *
 */
public class ParamsManager
{
	//接收消息超时时间
	public final static int RECV_MSG_TIMEOUT = 3 * 1000;
	
	//消息接收任务的运行周期
	public final static int RECVJOB_INVOKE_PERIOD = 15;
	
	private static ParamsManager INSTANCE = new ParamsManager();
	
	// 执行器的运行模式
	private String		mode;
	// 执行器在生成消息的内容时字节序
	private String		endian;
	// 开始执行的消息
	private String		beginMsg;
	// 结束执行的消息
	private String		endMsg;
	// 用例文件
	private String[]	caseFiles;

	// 界面端口号
	private int			clientPort;

	// 用于执行测试用例的端口号
	private int			executionPort;
	
	public static ParamsManager GetInstance()
	{
		return INSTANCE;
	}
	
	public void setEndian(String endian)
	{
		this.endian = endian;
	}

	public void setBeginMsg(String beginMsg)
	{
		this.beginMsg = beginMsg;
	}

	public void setEndMsg(String endMsg)
	{
		this.endMsg = endMsg;
	}

	public void setCaseFiles(String[] caseFiles)
	{
		this.caseFiles = caseFiles;
	}

	public void setClientPort(int clientPort)
	{
		this.clientPort = clientPort;
	}

	public void setExecutionPort(int executionPort)
	{
		this.executionPort = executionPort;
	}

	public int getClientPort()
	{
		return clientPort;
	}

	public int getExecutionPort()
	{
		return executionPort;
	}

	public String getMode()
	{
		return mode;
	}
	
	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getEndian()
	{
		return endian;
	}

	public String getBeginMsg()
	{
		return beginMsg;
	}

	public String getEndMsg()
	{
		return endMsg;
	}

	public String[] getCaseFiles()
	{
		return caseFiles;
	}
}