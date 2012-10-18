package pmcs.xml.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import pmcs.dto.InternalTypeUnion;
import pmcs.dto.MotePacketHead;
import pmcs.dto.ParsedDataElement;
import pmcs.exception.parser.ParseException;
import pmcs.exception.parser.ParseInternalException;
import pmcs.exception.parser.ParseParsedDataElementException;
import pmcs.exception.parser.ReadXmlBytesException;

/**
 * 解析xml数据流
 * 
 * @author steven
 * 
 */

public class XmlParser {
	public List<ParsedDataElement> parsedElementList = new ArrayList<ParsedDataElement>();
	public InternalTypeUnion internalTypeUnion = new InternalTypeUnion();
	public MotePacketHead packetHead = new MotePacketHead();
	public int packetType = 0;
	public String packetName = null;
	
	
	/**
	 * 解析xml数据
	 * 
	 * @param result 
	 *            从socket获取的xml流
	 * @throws IOException
	 * @throws ReadXmlBytesException
	 *             读取xml字节流异常
	 * @throws ParseException
	 *             解析xml文档异常
	 */
	public void parse(byte[] result) throws IOException,
			ReadXmlBytesException, ParseException {

		try {
			Document document = getDocument(result);
			Element rootElement = getRootElement(document);
			if (rootElement == null)
				throw new ParseException();

			List<Element> elements = rootElement.elements();
			List<Element> parsedDataElements = rootElement
					.elements("ParsedDataElement");
			Element internalDataElement = rootElement.element("internal");
			Iterator<Element> iterator = elements.iterator();
			
			// 解析第一部分
			// 获取数据包头部信息
			packetName = iterator.next().getText();
			packetHead.setPacketName(packetName);
			packetHead.setNodeId(Float.parseFloat(iterator.next().getText()));
			packetHead.setPortId(Float.parseFloat(iterator.next().getText()));
			
			// 解析第二部分数据
			// 根据包名判断数据包类型
			packetType = getPacketTypeByPacketName(packetName);
			if(parsedDataElements != null)
				parseParsedDataElement(parsedDataElements);
			if(parsedDataElements != null)
				parseInternal(internalDataElement, packetType);

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ParseException();
		} catch (ParseParsedDataElementException e) {
			e.printStackTrace();
			throw new ParseException();
		} catch (ParseInternalException e) {
			e.printStackTrace();
			throw new ParseException();
		}

	}
	
	/**
	 * 从xml字节榴获取xml文档
	 * 
	 * @param result
	 *            xml 字节流
	 * @return Doeument xml数据文档
	 * @throws DocumentException
	 */
	private Document getDocument(byte[] result) throws DocumentException {
		ByteArrayInputStream bais = new ByteArrayInputStream(result);
		SAXReader reader = new SAXReader();
		Document document = reader.read(bais);
		return document;
	}

	/**
	 * 返回文档的根节点
	 * 
	 * @param doc
	 *            xml文档对象
	 * @return 返回根节点
	 */
	private Element getRootElement(Document doc) {
		return doc.getRootElement();
	}

	/**
	 * 根据packetName判断packetType
	 * 
	 * @param packetName
	 *            数据包名
	 * @return packetType 数据包类型
	 */
	private int getPacketTypeByPacketName(String packetName) {
		if (packetName.equals("Physical Health Mesh 2.4 XML")) {
			return 0;
		} else if (packetName.equals("Neighbor Health Mesh 2.4 XML")) {
			return 1;
		} else if (packetName.equals("eN2100 Internal Sensors v1.2")) {
			return 2;
		} else if (packetName
				.equals("eS1201 Ambient Temperature and Humidity Sensor")) {
			return 3;
		} else if (packetName.equals("eS1100 Soil Moisture Sensor v1")) {
			return 4;
		} else if (packetName.equals("eS1401 Solar Radiation Sensor v2")) {
			return 5;
		} else {
			return 6;
		}
	}

	/**
	 * 解析ParsedDataElement标签内的数据
	 * 
	 * @param list
	 *            包含所有ParsedDataElement节点
	 * @throws ParseParsedDataElementException
	 */
	private void parseParsedDataElement(List<Element> list)
			throws ParseParsedDataElementException {
		String name = null;
		String convertedValue = null;
		String convertedValueType = null;
		for (Element element : list) {
			ParsedDataElement pde = new ParsedDataElement();
			name = element.element("Name").getText();
			if (name != null) {
				pde.setElementName(name);
			} else {
				throw new ParseParsedDataElementException("name");
			}

			convertedValue = element.element("ConvertedValue").getText();
			if (convertedValue != null) {
				pde.setConvertedValue(convertedValue);
			} else {
				throw new ParseParsedDataElementException("ConvertedValue");
			}

			convertedValueType = element.element("ConvertedValueType")
					.getText();
			if (convertedValueType != null) {
				pde.setConvertedValueType(convertedValueType);
			} else {
				throw new ParseParsedDataElementException("ConvertedValueType");
			}
			parsedElementList.add(pde);
		}
	}

	/**
	 * 解析Internal节点内数据
	 * 
	 * @param list
	 *            包含所有Internal节点
	 * @param packetType
	 *            数据包类型
	 * @throws ParseInternalException
	 *             解析Internal节点失败
	 */
	private void parseInternal(Element el, int packetType)
			throws ParseInternalException {
		switch (packetType) {
		case 0:
			parseInternal0(el);
			break;
		case 1:
			parseInternal1(el);
			break;
		case 2:
			parseInternal2(el);
			break;
		case 3:
		case 4:
		case 5:
			parseInternal5(el);
			break;

		}
	}

	/**
	 * 解析packetType=0的inernal数据
	 * 
	 * @param el
	 *            intenal节点
	 * @throws ParseInternalException
	 *             解析internal失败
	 */
	private void parseInternal0(Element el) throws ParseInternalException {
		String nodeId = el.element("nodeId").getText();
		String healthParentId = el.element("healthParentId").getText();
		if (nodeId != null) {
			internalTypeUnion.internalType3.setNodeId(Float.parseFloat(nodeId));
		} else {
			throw new ParseInternalException("nodeId");
		}
		if (healthParentId != null) {
			internalTypeUnion.internalType3.setHealthParentId(Float
					.parseFloat(healthParentId));
		} else {
			throw new ParseInternalException("healthParentId");
		}
	}

	/**
	 * 解析packetType=1的inernal数据
	 * 
	 * @param el
	 *            intenal节点
	 * @throws ParseInternalException
	 *             解析internal失败
	 */
	private void parseInternal1(Element el) throws ParseInternalException {
		String nodeId = el.element("nodeId").getText();
		String healthNeighborIds = el.element("healthNeighborIds").getText();
		if (nodeId != null) {
			internalTypeUnion.internalType4.setNodeId(Float.parseFloat(nodeId));
		} else {
			throw new ParseInternalException("nodeId");
		}
		if (healthNeighborIds != null) {
			internalTypeUnion.internalType4
					.setHealthNeighborIds(healthNeighborIds);
		} else {
			throw new ParseInternalException("healthNeighborIds");
		}
	}

	/**
	 * 解析packetType=2的inernal数据
	 * 
	 * @param el
	 *            intenal节点
	 * @throws ParseInternalException
	 *             解析internal失败
	 */
	private void parseInternal2(Element el) throws ParseInternalException {
		String nodeId = el.element("nodeId").getText();
		String sensorDeviceParentNodeId = el
				.element("sensorDeviceParentNodeId").getText();
		String sensorDeviceSubAddress = el.element("sensorDeviceSubAddress")
				.getText();
		String sensorDeviceSensorId = el.element("sensorDeviceSensorId")
				.getText();
		String yieldAppId = el.element("yieldAppId").getText();
		String sensorTable = el.element("sensorTable").getText();

		if (nodeId != null) {
			internalTypeUnion.internalType2.setNodeId(Float.parseFloat(nodeId));
		} else {
			throw new ParseInternalException("nodeId");
		}
		if (sensorDeviceParentNodeId != null) {
			internalTypeUnion.internalType2.setSensorDeviceParentNodeId(Float
					.parseFloat(sensorDeviceParentNodeId));
		} else {
			throw new ParseInternalException("sensorDeviceParentNodeId");
		}
		if (sensorDeviceSubAddress != null) {
			internalTypeUnion.internalType2.setSensorDeviceSubAddress(Float
					.parseFloat(sensorDeviceSubAddress));
		} else {
			throw new ParseInternalException("sensorDeviceSubAddress");
		}
		if (sensorDeviceSensorId != null) {
			internalTypeUnion.internalType2.setSensorDeviceSensorId(Float
					.parseFloat(sensorDeviceSensorId));
		} else {
			throw new ParseInternalException("sensorDeviceSensorId");
		}
		if (yieldAppId != null) {
			internalTypeUnion.internalType2.setYieldAppId(Float
					.parseFloat(yieldAppId));
		} else {
			throw new ParseInternalException("yieldAppId");
		}
		if (sensorTable != null) {
			internalTypeUnion.internalType2.setSensorTable(sensorTable);
		} else {
			throw new ParseInternalException("sensorTable");
		}
	}

	/**
	 * 解析packetType=5的inernal数据
	 * 
	 * @param el
	 *            intenal节点
	 * @throws ParseInternalException
	 *             解析internal失败
	 */
	private void parseInternal5(Element el) throws ParseInternalException {
		String nodeId = el.element("nodeId").getText();
		String sensorDeviceParentNodeId = el
				.element("sensorDeviceParentNodeId").getText();
		String sensorDeviceSubAddress = el.element("sensorDeviceSubAddress")
				.getText();
		String sensorDeviceSensorId = el.element("sensorDeviceSensorId")
				.getText();
		String sensorTable = el.element("sensorTable").getText();

		if (nodeId != null) {
			internalTypeUnion.internalType1.setNodeId(Float.parseFloat(nodeId));
		} else {
			throw new ParseInternalException("nodeId");
		}
		if (sensorDeviceParentNodeId != null) {
			internalTypeUnion.internalType1.setSensorDeviceParentNodeId(Float
					.parseFloat(sensorDeviceParentNodeId));
		} else {
			throw new ParseInternalException("sensorDeviceParentNodeId");
		}
		if (sensorDeviceSubAddress != null) {
			internalTypeUnion.internalType1.setSensorDeviceSubAddress(Float
					.parseFloat(sensorDeviceSubAddress));
		} else {
			throw new ParseInternalException("sensorDeviceSubAddress");
		}
		if (sensorDeviceSensorId != null) {
			internalTypeUnion.internalType1.setSensorDeviceSensorId(Float
					.parseFloat(sensorDeviceSensorId));
		} else {
			throw new ParseInternalException("sensorDeviceSensorId");
		}
		if (sensorTable != null) {
			internalTypeUnion.internalType1.setSensorTable(sensorTable);
		} else {
			throw new ParseInternalException("sensorTable");
		}
	}
}
