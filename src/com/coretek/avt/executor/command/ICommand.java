package com.coretek.avt.executor.command;

/**
 * �������<br/>
 * ����ĸ�ʽΪ��<br/>
 * <h1>���,�����ַ���,����</h1>
 * <li>��ţ����룬���������֣�û������ı�Ŷ���Ψһ�ģ����Ұ�˳�����</li>
 * <li>�����ַ��������룬���ڱ�ʶ���ĸ�����</li>
 * <li>��������ѡ�����ڴ���ִ���������������</li>
 * @author David
 *
 */
public interface ICommand
{
	/**
	 * ��ȡ����������ı���ʽ
	 * @return
	 */
	public String getText();
		
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