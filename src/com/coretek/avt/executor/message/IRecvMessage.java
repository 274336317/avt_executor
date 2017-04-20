package com.coretek.avt.executor.message;

/**
 * ������Ϣ
 * @author David
 *
 */
public interface IRecvMessage
{
	/**
	 * ����ʵ��ֵ
	 * @param value
	 */
	public void setActualValue(byte[] value);
	
	/**
	 * ��ȡ���ֵ
	 * @return
	 */
	public byte[] getActualValue();
	
	/**
	 * ����Ԥ����Ϣ
	 * @param value
	 */
	public void setExpectValue(byte[] value);
	
	/**
	 * ��ȡԤ����Ϣ
	 * @return
	 */
	public byte[] getExpectValue();
}