package com.coretek.avt.executor;

import java.util.Arrays;

/**
 * ִ��������ʱ��������
 * @author David
 *
 */
public class ParamManager
{
	//ִ����������ģʽ
	private String mode;
	//ִ������������Ϣ������ʱ�ֽ���
	private String endian;
	//��ʼִ�е���Ϣ
	private String beginMsg;
	//����ִ�е���Ϣ
	private String endMsg;
	//�����ļ�
	private String[] caseFiles;
	
	public ParamManager(String[] args)
	{
		
	}
	
	public ParamManager(String mode, String endian, String beginMsg, String endMsg, String[] caseFiles)
	{
		this.mode = mode;
		this.endian = endian;
		this.beginMsg = beginMsg;
		this.endMsg = endMsg;
		this.caseFiles = Arrays.copyOf(caseFiles, caseFiles.length);
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