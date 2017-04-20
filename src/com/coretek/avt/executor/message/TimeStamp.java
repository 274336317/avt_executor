package com.coretek.avt.executor.message;

/**
 * 时间设置
 * @author David
 *
 */
public class TimeStamp
{
	private String expression;//可能是一个数字或者数学表达式

	public String getExpression()
	{
		return expression;
	}

	public void setExpression(String expression)
	{
		this.expression = expression;
	}
}