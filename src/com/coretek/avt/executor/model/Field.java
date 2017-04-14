package com.coretek.avt.executor.model;

/**
 * �ֶ�
 * 
 * @author David
 *
 */
public class Field
{
	private String	id;

	private int		offsetbit;	// ƫ�Ƶ�λ

	private int		width;		// ���

	private String	name;		// ����

	private String	value;		// �ֶε�ֵ���п����Ǳ��ʽ

	private boolean	signed;		// �Ƿ��з���
	
	private Field[] children;//���ֶ�
	
	public Field()
	{
		
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getOffsetbit()
	{
		return offsetbit;
	}

	public void setOffsetbit(int offsetbit)
	{
		this.offsetbit = offsetbit;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public boolean isSigned()
	{
		return signed;
	}

	public void setSigned(boolean signed)
	{
		this.signed = signed;
	}

	public Field[] getChildren()
	{
		return children;
	}

	public void setChildren(Field[] children)
	{
		this.children = children;
	}
}