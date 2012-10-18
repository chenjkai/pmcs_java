package pmcs.gui;

import pmcs.util.Util;
import pmcs.xml.parser.XmlParser;

class DataConvertor {
	/**
	 * 将数据对象转换为数据表的一行数据
	 * @param data 解析之后的xml数据对象
	 * @return 返回数据表显示的数据
	 */
	public static Object[] convert(XmlParser data){
		Object[] result = null;
		switch (data.packetType) {
		case 0:
		case 1:
		case 2:
			return null;
		case 3:
			result = convertPacketTypeThree(data);break;
		case 4:
			result = convertPacketTypeFour(data);break;
		case 5:
			result = convertPacketTypeFive(data);break;
		}
		return result;
	}
	
	/**
	 * es1201
	 * @param data 解析之后的xml数据对象
	 * @return 返回数据表显示的数据
	 */
	public static Object[] convertPacketTypeThree(XmlParser data){
		Object[] object = new Object[6];
		object[0] = data.parsedElementList.get(1).getConvertedValue();
		object[1] = data.packetHead.getNodeId();
		object[2] = data.packetHead.getPortId();
		object[3] = (data.internalTypeUnion.internalType1.getSensorTable().split("_"))[0];
		object[4] = "tem=" + data.parsedElementList.get(5).getConvertedValue() + ";hum=" + data.parsedElementList.get(6).getConvertedValue() + ";dew=" + data.parsedElementList.get(7).getConvertedValue();
		object[5] = Util.getCurrentTime();
		return object;
	}
	/**
	 * es1100
	 * @param data 解析之后的xml数据对象
	 * @return 返回数据表显示的数据
	 */
	public static Object[] convertPacketTypeFour(XmlParser data){
		Object[] object = new Object[6];
		object[0] = data.parsedElementList.get(1).getConvertedValue();
		object[1] = data.packetHead.getNodeId();
		object[2] = data.packetHead.getPortId();
		object[3] = (data.internalTypeUnion.internalType1.getSensorTable().split("_"))[0];
		object[4] = "soil_moisture=" + data.parsedElementList.get(5).getConvertedValue() + ";";
		object[5] = Util.getCurrentTime();
		return object;
	}
	
	/**
	 * es1401
	 * @param data 解析之后的xml数据对象
	 * @return 返回数据表显示的数据
	 */
	public static Object[] convertPacketTypeFive(XmlParser data){
		Object[] object = new Object[6];
		object[0] = data.parsedElementList.get(1).getConvertedValue();
		object[1] = data.packetHead.getNodeId();
		object[2] = data.packetHead.getPortId();
		object[3] = (data.internalTypeUnion.internalType1.getSensorTable().split("_"))[0];
		object[4] = "SolarRadiation=" + data.parsedElementList.get(7).getConvertedValue() + ";";
		object[5] = Util.getCurrentTime();
		return object;
	}
}
