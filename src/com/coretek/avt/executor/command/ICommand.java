package com.coretek.avt.executor.command;

/**
 * 控制命令。<br/>
 * 命令的格式为：<br/>
 * <h1>编号,命令字符串,参数</h1>
 * <li>编号，必须，类型是数字，没跳命令的编号都是唯一的，并且按顺序递增</li>
 * <li>命令字符串，必须，用于标识是哪个命令</li>
 * <li>参数，可选，用于传递执行命令所需的数据</li>
 * @author David
 *
 */
public interface ICommand
{
	/**
	 * 获取控制命令的文本形式
	 * @return
	 */
	public String getText();
		
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