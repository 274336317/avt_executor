package com.coretek.avt.executor.command;

import com.coretek.avt.executor.ParamsManager;

public class CommandParser
{
	public ICommand parse(String contents)
	{
		ICommand command = null;
		
		String[] segs = contents.split(",");
		//����������
		int index = Integer.valueOf(segs[0]);
		//����
		String cmd = segs[1];
		
		switch(cmd)
		{
			case "run":
			{//��������
				String data = segs[2];
				int leftBraceIndex = data.indexOf("{");
				int rightBraceIndex = data.indexOf("}");
				data = data.substring(leftBraceIndex + 1, rightBraceIndex);
				segs = data.split(" ");
				String testCase = null;
				String startMsg = null;
				String endMsg = null;
				
				for(String seg: segs)
				{
					if("testCase".indexOf(seg) >= 0)
					{
						testCase = seg.split("=")[1];
						ParamsManager.GetInstance().setCaseFiles(new String[]{testCase});
					}
					else if("startMsg".indexOf(seg) >= 0)
					{
						startMsg = seg.split("=")[1];
						ParamsManager.GetInstance().setBeginMsg(startMsg);
					}
					else if("endMsg".indexOf("seg") >= 0)
					{
						endMsg = seg.split("=")[1];
						ParamsManager.GetInstance().setEndMsg(endMsg);
					}
				}
				
				command = new RunCommand(contents, index, "run",segs, testCase, startMsg, endMsg);
				
				break;
			}
			case "setEnv":
			{//�������л�������
				break;
			}
			case "exit":
			{//�˳�����
				break;
			}
			default:
			{
				throw new RuntimeException("Unkown Command.");
			}
		}
		
		return command;
	}
	
	/**
	 * ������ݵĸ�ʽ�Ƿ������������ĸ�ʽ
	 * @param contents
	 * @return
	 */
	public boolean isCommand(String contents)
	{
		boolean result = false;
		
		return false;
	}
}