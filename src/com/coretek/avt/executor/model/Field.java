package com.coretek.avt.executor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 字段
 * 
 * @author David
 *
 */
public class Field
{
	private String	id;

	private int		offsetbit;	// 偏移的位

	private int		width;		// 宽度

	private String	name;		// 名称

	private String	value;		// 字段的值，有可能是表达式

	private boolean	signed;	// 是否有符号

	private List<Field>	children = new ArrayList<Field>();	// 子字段

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