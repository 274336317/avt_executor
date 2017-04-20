package com.coretek.avt.executor.message;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ��������������
 * @author David
 *
 */
public class TestCaseParser
{
	private String testCasePath;
	
	public TestCaseParser(String testCasePath)
	{
		this.testCasePath = testCasePath;
	}
	
	public TestCase parse()
	{
		try
		{
			TestCase tc = new TestCase(this.testCasePath);
			
	        DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();  
	        InputStream input = new FileInputStream(testCasePath);  
	        Document doc = domBuilder.parse(input);  
	        Element root = doc.getDocumentElement();  
	        NodeList nodeList = root.getChildNodes(); 
	        
	        for(int i = 0; i < nodeList.getLength(); i++)
	        {
	        	Node node = nodeList.item(i);
	        	if("testObjects".equals(node.getNodeName()))
	        	{
	        		this.parseTestedObjects(node, tc);
	        	}
	        	else if("simuObjects".equals(node.getNodeName()))
	        	{
	        		this.parseSimuObjects(node, tc);
	        	}
	        	else if("messageBlock".equals(node.getNodeName()))
	        	{
	        		this.parseMessages(node, tc);
	        	}
	        }
	        
		} catch(Exception e)
		{
			e.printStackTrace();
		}

        
		return null;
	}
	
	/**
	 * ������Ϣ
	 * @param node
	 * @param tc
	 */
	private void parseMessages(Node node, TestCase tc)
	{
		NodeList nodeList = node.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("message".equals(kid.getNodeName()))
			{
				NamedNodeMap nnm = kid.getAttributes();
				int periodCount = Integer.valueOf(nnm.getNamedItem("periodCount").getNodeValue());
				String direction = nnm.getNamedItem("direction").getNodeValue();
				int width = Integer.valueOf(nnm.getNamedItem("width").getNodeValue());
				int srcId = Integer.valueOf(nnm.getNamedItem("srcId").getNodeValue());
				int id = Integer.valueOf(nnm.getNamedItem("id").getNodeValue());
				int topicId = Integer.valueOf(nnm.getNamedItem("topicId").getNodeValue());
				String uuid = nnm.getNamedItem("uuid").getNodeValue();
				boolean parallel = Boolean.valueOf(nnm.getNamedItem("parallel").getNodeValue());
				boolean background = Boolean.valueOf(nnm.getNamedItem("background").getNodeValue());
				String name = nnm.getNamedItem("name").getNodeValue();
				int periodDuration = Integer.valueOf(nnm.getNamedItem("periodDuration").getNodeValue());
				
				if(this.isPeriodMessage(kid))
				{//������Ϣ
					if(background)
					{//����������Ϣ
						if(this.isRecvMessage(direction))
						{//�������ڽ�����Ϣ
							BackgroundRecvPeriodMessage rm = new BackgroundRecvPeriodMessage();
							rm.setDestIds(this.getDestIds(nnm));
							rm.setDirection(Message.DIRECTION_RECV);
							rm.setName(name);
							rm.setSrcId(srcId);
							rm.setTopicId(topicId);
							rm.setUuid(uuid);
							rm.setWidth(width);
							rm.setId(id);
							rm.setPeriod(periodDuration);
							rm.setPeriodCount(periodCount);
							
							this.parseBackgroundPeriodRecvMessage(kid, rm);
						}
						else
						{//�������ڷ�����Ϣ
							BackgroundSendPeriodMessage rm = new BackgroundSendPeriodMessage();
							rm.setDestIds(this.getDestIds(nnm));
							rm.setDirection(Message.DIRECTION_SEND);
							rm.setName(name);
							rm.setSrcId(srcId);
							rm.setTopicId(topicId);
							rm.setUuid(uuid);
							rm.setWidth(width);
							rm.setId(id);
							rm.setPeriod(periodDuration);
							rm.setPeriodCount(periodCount);
							
							this.parseBackgroundPeriodSendMessage(node, rm);
						}
					}
					else
					{//��ͨ������Ϣ
						if(this.isRecvMessage(direction))
						{//��ͨ���ڽ�����Ϣ
							PeriodRecvMessage rm = new PeriodRecvMessage();
							rm.setDestIds(this.getDestIds(nnm));
							rm.setDirection(Message.DIRECTION_RECV);
							rm.setName(name);
							rm.setSrcId(srcId);
							rm.setTopicId(topicId);
							rm.setUuid(uuid);
							rm.setWidth(width);
							rm.setId(id);
							rm.setPeriod(periodDuration);
							rm.setPeriodCount(periodCount);
							
							this.parsePeriodRecvMessage(kid, rm);
						}
						else
						{//��ͨ���ڷ�����Ϣ
							PeriodSendMessage rm = new PeriodSendMessage();
							rm.setDestIds(this.getDestIds(nnm));
							rm.setDirection(Message.DIRECTION_SEND);
							rm.setName(name);
							rm.setSrcId(srcId);
							rm.setTopicId(topicId);
							rm.setUuid(uuid);
							rm.setWidth(width);
							rm.setId(id);
							rm.setPeriod(periodDuration);
							rm.setPeriodCount(periodCount);
							
							this.parsePeriodSendMessage(kid, rm);
						}
					}
				} else
				{//��������Ϣ
					if(this.isRecvMessage(direction))
					{//������Ϣ
						if(parallel)
						{//���н�����Ϣ
							ParallelRecvMessage prm = new ParallelRecvMessage();
							prm.setDestIds(this.getDestIds(nnm));
							prm.setDirection(Message.DIRECTION_RECV);
							prm.setName(name);
							prm.setSrcId(srcId);
							prm.setTopicId(topicId);
							prm.setUuid(uuid);
							prm.setWidth(width);
							prm.setId(id);
							
							this.parseParallelRecvMessage(node, prm);
						}
						else
						{//��ͨ������Ϣ
							RecvMessage rm = new RecvMessage();
							rm.setDestIds(this.getDestIds(nnm));
							rm.setDirection(Message.DIRECTION_RECV);
							rm.setName(name);
							rm.setSrcId(srcId);
							rm.setTopicId(topicId);
							rm.setUuid(uuid);
							rm.setWidth(width);
							rm.setId(id);
							
							this.parseRecvMessage(kid, rm);
						}
					}
					else
					{//������Ϣ
						if(parallel)
						{//���з�����Ϣ
							ParallelSendMessage prm = new ParallelSendMessage();
							prm.setDestIds(this.getDestIds(nnm));
							prm.setDirection(Message.DIRECTION_RECV);
							prm.setName(name);
							prm.setSrcId(srcId);
							prm.setTopicId(topicId);
							prm.setUuid(uuid);
							prm.setWidth(width);
							prm.setId(id);
							
							this.parseParallelSendMessage(kid, prm);
						}
						else
						{//��ͨ������Ϣ
							SendMessage rm = new SendMessage();
							rm.setDestIds(this.getDestIds(nnm));
							rm.setDirection(Message.DIRECTION_SEND);
							rm.setName(name);
							rm.setSrcId(srcId);
							rm.setTopicId(topicId);
							rm.setUuid(uuid);
							rm.setWidth(width);
							rm.setId(id);
							
							this.parseSendMessage(kid, rm);
						}
					}
				}
			} else if("timeSpan".equals(kid.getNodeName()))
			{//����ʱ����
				TimeSpan span = new TimeSpan();
				this.parseTimeSpan(kid, span);
			}
		}
	}
	
	/**
	 * ����ʱ����
	 * @param node
	 * @param span
	 */
	private void parseTimeSpan(Node node, TimeSpan span)
	{
		String value = node.getAttributes().getNamedItem("value").getNodeValue();
		span.setExpression(value);
	}
	
	/**
	 * ������ͨ������Ϣ
	 * @param node
	 * @param msg
	 */
	private void parseRecvMessage(Node node, RecvMessage msg)
	{
		Period period = new Period();
		msg.getPeriods().add(period);
		
		NodeList nodeList = node.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("field".equals(kid.getNodeName()))
			{//�ֶ�
				this.parseField(kid, period);
			}
		}
	}
	
	/**
	 * ������ͨ������Ϣ
	 * @param node
	 * @param msg
	 */
	private void parseSendMessage(Node node, SendMessage msg)
	{
		Period period = new Period();
		msg.getPeriods().add(period);
		
		NodeList nodeList = node.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("field".equals(kid.getNodeName()))
			{//�ֶ�
				this.parseField(kid, period);
			}
		}
	}
	
	/**
	 * �������н�����Ϣ
	 * @param node
	 * @param msg
	 */
	private void parseParallelRecvMessage(Node node, ParallelRecvMessage msg)
	{
		Period period = new Period();
		msg.getPeriods().add(period);
		
		NodeList nodeList = node.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("field".equals(kid.getNodeName()))
			{//�ֶ�
				this.parseField(kid, period);
			}
		}
	}
	
	/**
	 * �������з�����Ϣ
	 * @param node
	 * @param msg
	 */
	private void parseParallelSendMessage(Node node, ParallelSendMessage msg)
	{
		Period period = new Period();
		msg.getPeriods().add(period);
		
		NodeList nodeList = node.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("field".equals(kid.getNodeName()))
			{//�ֶ�
				this.parseField(kid, period);
			}
		}
	}
	
	/**
	 * �����������ڽ�����Ϣ
	 * @param node
	 * @param msg
	 */
	private void parseBackgroundPeriodRecvMessage(Node node, BackgroundRecvPeriodMessage msg)
	{
		NodeList nodeList = node.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("period".equals(kid.getNodeName()))
			{
				this.parsePeriod(kid, msg);
			}
			
		}
	}
	
	/**
	 * �����������ڷ�����Ϣ
	 * @param node
	 * @param msg
	 */
	private void parseBackgroundPeriodSendMessage(Node node, BackgroundSendPeriodMessage msg)
	{
		NodeList nodeList = node.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("period".equals(kid.getNodeName()))
			{
				this.parsePeriod(kid, msg);
			}
			
		}
	}
	
	/**
	 * ������ͨ���ڷ�����Ϣ
	 * @param node
	 * @param msg
	 */
	private void parsePeriodSendMessage(Node node, PeriodSendMessage msg)
	{
		NodeList nodeList = node.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("period".equals(kid.getNodeName()))
			{
				this.parsePeriod(kid, msg);
			}
			
		}
	}
	
	/**
	 * ������ͨ���ڽ�����Ϣ
	 * @param node
	 * @param msg
	 */
	private void parsePeriodRecvMessage(Node node, PeriodRecvMessage msg)
	{
		NodeList nodeList = node.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("period".equals(kid.getNodeName()))
			{
				this.parsePeriod(kid, msg);
			}
			
		}
	}
	
	/**
	 * ����������Ϣ�µ����ڽڵ�
	 * @param periodNode
	 * @param msg
	 */
	private void parsePeriod(Node periodNode, PeriodMessage msg)
	{
		Period period = new Period();
		NamedNodeMap nnm = periodNode.getAttributes();
		String value = nnm.getNamedItem("value").getNodeValue();
		period.setIndex(Integer.valueOf(value));
		
		NodeList fieldNodes = periodNode.getChildNodes();
		for(int i = 0; i < fieldNodes.getLength(); i++)
		{
			Node fieldNode = fieldNodes.item(i);
			this.parseField(fieldNode, period);
		}
	}
	
	/**
	 * �����ֶ�
	 * @param fieldNode
	 * @param period
	 */
	private void parseField(Node fieldNode, Period period)
	{
		NamedNodeMap nnm = fieldNode.getAttributes();
		String id = nnm.getNamedItem("id").getNodeValue();
		int offsetword = Integer.valueOf(nnm.getNamedItem("offsetword").getNodeValue());
		boolean signed = Boolean.valueOf(nnm.getNamedItem("signed").getNodeValue());
		int width = Integer.valueOf(nnm.getNamedItem("width").getNodeValue());
		String name = nnm.getNamedItem("name").getNodeValue();
		int offsetbit = Integer.valueOf(nnm.getNamedItem("offsetbit").getNodeValue());
		
		Field field = new Field();
		field.setId(id);
		field.setName(name);
		field.setOffsetbit(offsetword * 32 + offsetbit);
		field.setSigned(signed);
		field.setWidth(width);
		
		//����Ƿ������ֶ�
		if(this.hasChildren(fieldNode))
		{//�����ֶ�
			this.parseSubField(fieldNode, field);
		} else
		{
			this.parseFieldValue(fieldNode, field);
		}
		
		period.getFields().add(field);
	}
	
	/**
	 * �ж��ֶ��Ƿ�ӵ�����ֶ�
	 * @param fieldNode
	 * @return
	 */
	private boolean hasChildren(Node fieldNode)
	{
		boolean result = false;
		
		NodeList children = fieldNode.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if("field".equals(child.getNodeName()))
			{
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * �������ֶ�
	 * @param parentNode
	 * @param parent
	 */
	private void parseSubField(Node parentNode, Field parent)
	{
		NodeList nodeList = parentNode.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node fieldNode = nodeList.item(i);
			NamedNodeMap nnm = fieldNode.getAttributes();
			String id = nnm.getNamedItem("id").getNodeValue();
			int offsetword = Integer.valueOf(nnm.getNamedItem("offsetword").getNodeValue());
			boolean signed = Boolean.valueOf(nnm.getNamedItem("signed").getNodeValue());
			int width = Integer.valueOf(nnm.getNamedItem("width").getNodeValue());
			String name = nnm.getNamedItem("name").getNodeValue();
			int offsetbit = Integer.valueOf(nnm.getNamedItem("offsetbit").getNodeValue());
			
			Field field = new Field();
			field.setId(id);
			field.setName(name);
			field.setOffsetbit(offsetword * 32 + offsetbit);
			field.setSigned(signed);
			field.setWidth(width);
			if(this.hasChildren(fieldNode))
			{//�������ֶ�
				this.parseSubField(fieldNode, field);
			}
			else
			{//�����ֶε�ֵ
				this.parseFieldValue(fieldNode, field);
			}
		}
	}
	
	/**
	 * �����ֶε�ֵ
	 * @param fieldNode
	 */
	private void parseFieldValue(Node fieldNode, Field field)
	{
		NodeList nodeList = fieldNode.getChildNodes();
		
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if("value".equals(node))
			{
				String value = node.getTextContent();
				field.setValue(value.trim());
				break;
			}
		}
	}
	
	/**
	 * �����Ϣ�Ƿ�Ϊ������Ϣ
	 * @param direction
	 * @return
	 */
	private boolean isRecvMessage(String direction)
	{
		return "recv".equals(direction);
	}
	
	/**
	 * ��ȡ����Ŀ��
	 * @param nnm
	 * @return
	 */
	private int[] getDestIds(NamedNodeMap nnm)
	{
		String strDesIds = nnm.getNamedItem("desId").getNodeValue();
		String[] segs = strDesIds.split(",");
		List<Integer> list = new ArrayList<Integer>(segs.length);
		for(String seg: segs)
		{
			if(seg.trim().length() != 0)
			{
				list.add(Integer.valueOf(seg));
			}
		}
		
		int[] arr = new int[list.size()];
		for(int i = 0; i < arr.length; i++)
		{
			arr[i] = list.get(i);
		}
		
		return arr;
	}
	
	/**
	 * �����Ϣ�Ƿ�Ϊ������Ϣ
	 * @param node
	 * @return
	 */
	private boolean isPeriodMessage(Node node)
	{
		boolean result = false;
		NodeList nodeList = node.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("period".equals(kid.getNodeName()))
			{
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * ������Ϣ
	 * @param node
	 * @param tc
	 */
	private void parseMessage(Node node, TestCase tc)
	{
		
	}
	
	/**
	 * �����������
	 * @param node
	 * @param tc
	 */
	private void parseTestedObjects(Node node, TestCase tc)
	{
		NodeList nodeList = node.getChildNodes();
		List<TestedObject> list = new ArrayList<TestedObject>();
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("object".equals(kid.getNodeName()))
			{
				TestedObject to = new TestedObject();
				String name = kid.getAttributes().getNamedItem("name").getNodeValue();
				String strId = kid.getAttributes().getNamedItem("id").getNodeValue();
				to.setName(name);
				to.setId(Integer.valueOf(strId));
				list.add(to);
			}
		}
		
		tc.setTestObjects(list.toArray(new TestedObject[list.size()]));
	}
	
	/**
	 * ����ģ�����
	 * @param node
	 * @param tc
	 */
	private void parseSimuObjects(Node node, TestCase tc)
	{
		NodeList nodeList = node.getChildNodes();
		List<SimuObject> list = new ArrayList<SimuObject>();
		for(int i = 0; i < nodeList.getLength(); i++)
		{
			Node kid = nodeList.item(i);
			if("simuObject".equals(kid.getNodeName()))
			{
				SimuObject to = new SimuObject();
				String name = kid.getAttributes().getNamedItem("name").getNodeValue();
				String strId = kid.getAttributes().getNamedItem("id").getNodeValue();
				to.setName(name);
				to.setId(Integer.valueOf(strId));
				list.add(to);
			}
		}
		
		tc.setSimuObjects(list.toArray(new SimuObject[list.size()]));
	}
}