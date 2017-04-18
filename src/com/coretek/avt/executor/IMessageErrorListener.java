package com.coretek.avt.executor;

import com.coretek.avt.executor.model.Message;

/**
 * ��ִ����Ϣ����ʱ��ͨ���˽ӿ�֪ͨ����Ȥ�Ķ���
 * 
 * @author David
 *
 */
public interface IMessageErrorListener
{
	// ������Ϣʧ��
	public final static int	ERROR_SEND_FAILED		= 1;

	// ������Ϣʧ��
	public final static int	ERROR_RECV_FAILED		= 2;

	// Ԥ��ֵ��ʵ��ֵ��ƥ��
	public final static int	ERROR_VALUE_NOT_MATCHED	= 3;

	/**
	 * ��ִ����Ϣ����ʱ����ô˽ӿڡ�
	 * 
	 * @param msg
	 *            �������Ϣ
	 * @param period
	 *            ���ĸ����ڳ���
	 * @param errorCode
	 *            �����ԭ��
	 */
	public void onMessageError(Message msg, int period, int errorCode);
}