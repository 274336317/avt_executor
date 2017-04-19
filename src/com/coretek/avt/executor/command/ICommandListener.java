package com.coretek.avt.executor.command;

/**
 * 控制命令监听器，实现了此接口的对象会在接收到控制命令后被调用
 * 
 * @author David
 *
 */
public interface ICommandListener
{
	public void onCommand(ICommand cmd);
}