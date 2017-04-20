package com.coretek.avt.executor.message;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示测试用例
 * 
 * @author David
 *
 */
public class TestCase
{
	// 测试用例文件的路径
	private String			path;									

	private TestedObject[]	testedObjects;

	private SimuObject[]	simuObjects;

	private List<Object>	elements	= new ArrayList<Object>();

	public TestCase(String path)
	{
		this.path = path;
	}

	public void setTestObjects(TestedObject[] testedObjects)
	{
		this.testedObjects = testedObjects;
	}

	public void setSimuObjects(SimuObject[] simuObjects)
	{
		this.simuObjects = simuObjects;
	}

	public void addElement(Object element)
	{
		this.elements.add(element);
	}

	public List<Object> getElements()
	{
		return elements;
	}
}