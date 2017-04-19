package com.coretek.avt.executor;

import java.util.Arrays;

/**
 * ִ��������ʱ��������
 * 
 * @author David
 *
 */
public class ParamsManager
{
	//������Ϣ��ʱʱ��
	public final static int RECV_MSG_TIMEOUT = 3 * 1000;
	
	//��Ϣ�����������������
	public final static int RECVJOB_INVOKE_PERIOD = 15;
	
	private static ParamsManager INSTANCE = new ParamsManager();
	
	// ִ����������ģʽ
	private String		mode;
	// ִ������������Ϣ������ʱ�ֽ���
	private String		endian;
	// ��ʼִ�е���Ϣ
	private String		beginMsg;
	// ����ִ�е���Ϣ
	private String		endMsg;
	// �����ļ�
	private String[]	caseFiles;

	// ����˿ں�
	private int			clientPort;

	// ����ִ�в��������Ķ˿ں�
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