package com.coretek.avt.executor;

import java.util.Arrays;

/**
 * 执行器运行时管理器。
 * @author David
 *
 */
public class ParamManager
{
	//执行器的运行模式
	private String mode;
	//执行器在生成消息的内容时字节序
	private String endian;
	//开始执行的消息
	private String beginMsg;
	//结束执行的消息
	private String endMsg;
	//用例文件
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