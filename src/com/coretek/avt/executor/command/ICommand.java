package com.coretek.avt.executor.command;

/**
 * 控制命令
 * @author David
 *
 */
public interface ICommand
{
	/**
	 * 获取命令的索引号
	 * @return
	 */
	public int getIndex();

	/**
	 * 获取命令名称
	 * @return
	 */
	public String getCommand();

	/**
	 * 获取命令参数
	 * @return
	 */
	public String[] getParams();

	/**
	 * 获取命令执行结果
	 * @return
	 */
	public String getResult();
	
	/**
	 * 将命令执行结果设置为成功状态
	 */
	public void setSucc();
	
	/**
	 * 将命令执行结果设置为失败状态
	 * @param errMsg
	 */
	public void setFailed(String errMsg);
}