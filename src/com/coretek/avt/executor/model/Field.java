package com.coretek.avt.executor.model;

import java.util.ArrayList;
import java.util.List;

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

	private boolean	signed;	// �Ƿ��з���

	private List<Field>	children = new ArrayList<Field>();	// ���ֶ�

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

	public List<Field> getChildren()
	{
		return children;
	}

	public void setChildren(List<Field> children)
	{
		this.children = children;
	}
}