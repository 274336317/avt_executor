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

	public ParamsManager(String[] args)
	{

	}

	public ParamsManager(String mode, String endian, String beginMsg, String endMsg, String[] caseFiles)
	{
		this.mode = mode;
		this.endian = endian;
		this.beginMsg = beginMsg;
		this.endMsg = endMsg;
		this.caseFiles = Arrays.copyOf(caseFiles, caseFiles.length);
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