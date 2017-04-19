package com.coretek.avt.executor.command;

/**
 * ��������
 * @author David
 *
 */
public interface ICommand
{
	/**
	 * ��ȡ�����������
	 * @return
	 */
	public int getIndex();

	/**
	 * ��ȡ��������
	 * @return
	 */
	public String getCommand();

	/**
	 * ��ȡ�������
	 * @return
	 */
	public String[] getParams();

	/**
	 * ��ȡ����ִ�н��
	 * @return
	 */
	public String getResult();
	
	/**
	 * ������ִ�н������Ϊ�ɹ�״̬
	 */
	public void setSucc();
	
	/**
	 * ������ִ�н������Ϊʧ��״̬
	 * @param errMsg
	 */
	public void setFailed(String errMsg);
}