package com.coretek.avt.executor.message;

/**
 * 接收消息
 * @author David
 *
 */
public interface IRecvMessage
{
	/**
	 * 设置实际值
	 * @param value
	 */
	public void setActualValue(byte[] value);
	
	/**
	 * 获取设计值
	 * @return
	 */
	public byte[] getActualValue();
	
	/**
	 * 设置预期消息
	 * @param value
	 */
	public void setExpectValue(byte[] value);
	
	/**
	 * 获取预期消息
	 * @return
	 */
	public byte[] getExpectValue();
}