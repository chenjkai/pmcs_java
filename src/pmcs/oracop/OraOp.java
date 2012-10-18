package pmcs.oracop;

import java.util.List;

import org.apache.log4j.Logger;


import pmcs.dto.InternalTypeUnion;
import pmcs.dto.MotePacketHead;
import pmcs.dto.ParsedDataElement;
import pmcs.exception.oraop.OraopParamsCountException;
import pmcs.util.Util;

/**
 * 数据库操作类
 * 
 * @author steven
 * 
 */
public class OraOp {
	private static Logger logger = Util.getLogger(OraOp.class);

	private OraUtil oraUtil = null;

	public OraOp() {
		super();
		this.oraUtil = new OraUtil();
	}

	/**
	 * 插入数据
	 * 
	 * @param list
	 *            parsedDataElement
	 * @param internalTypeUnion
	 * @param packetHead
	 * @throws OraopParamsCountException 
	 */
	public void dbInsertData(List<ParsedDataElement> list,
			InternalTypeUnion internalTypeUnion, MotePacketHead packetHead,
			int packetType) throws OraopParamsCountException {
		
		dbCreateTable(packetType);
		StringBuilder sql = null;
		if (packetType == 0) {
			if(list.size() < 27){
				throw new OraopParamsCountException(packetType);
			}else {
				sql = new StringBuilder("insert into physical_health values (");
				sql.append("'").append(packetHead.getPacketName()).append("',").append(packetHead.getNodeId()).append(",").append(packetHead.getPortId()).append(",");
				for (ParsedDataElement el : list) {
					sql.append(el.getConvertedValue()).append(",");
				}
				sql.append(internalTypeUnion.internalType3.getNodeId()).append(",");
				sql.append(internalTypeUnion.internalType3.getHealthParentId()).append(",");
				sql.append(Util.getOraCurrentTimeString()).append(")");				
			}
		}
		
//		if (packetType == 1) {
//			if(list.size() < 28){
//				throw new OraopParamsCountException(packetType);
//			}else {
//				sql = new StringBuilder("insert into neighbor_health values (");
//				sql.append("'").append(packetHead.getPacketName()).append("',").append(packetHead.getNodeId()).append(",").append(packetHead.getPortId()).append(",");
//				for (ParsedDataElement el : list) {
//					sql.append(el.getConvertedValue()).append(",");
//				}
//				sql.append(internalTypeUnion.internalType4.getNodeId()).append(",");
//				sql.append(internalTypeUnion.internalType4.getHealthNeighborIds()).append(",");
//				sql.append(Util.getOraCurrentTimeString()).append(")");
//			}
//		}
		
		if (packetType == 2) {
			if(list.size() < 12){
				throw new OraopParamsCountException(packetType);
			}else {
				sql = new StringBuilder("insert into en2100 values (");
				sql.append("'").append(packetHead.getPacketName()).append("',").append(packetHead.getNodeId()).append(",").append(packetHead.getPortId()).append(",");
				for (ParsedDataElement el : list) {
					sql.append(el.getConvertedValue()).append(",");
				}
				sql.append(internalTypeUnion.internalType2.getNodeId()).append(",");
				sql.append(internalTypeUnion.internalType2.getSensorDeviceParentNodeId()).append(",");
				sql.append(internalTypeUnion.internalType2.getSensorDeviceSubAddress()).append(",");
				sql.append(internalTypeUnion.internalType2.getSensorDeviceSensorId()).append(",");
				sql.append(internalTypeUnion.internalType2.getYieldAppId()).append(",'");
				sql.append(internalTypeUnion.internalType2.getSensorTable()).append("',");
				sql.append(Util.getOraCurrentTimeString()).append(")");
			}
		}
		if (packetType == 3) {
			if(list.size() < 9){
				throw new OraopParamsCountException(packetType);
			}else {
				sql = new StringBuilder("insert into es1201 values (");
				sql.append("'").append(packetHead.getPacketName()).append("',").append(packetHead.getNodeId()).append(",").append(packetHead.getPortId()).append(",");
				for (ParsedDataElement el : list) {
					sql.append(el.getConvertedValue()).append(",");
				}
				sql.append(internalTypeUnion.internalType1.getNodeId()).append(",");
				sql.append(internalTypeUnion.internalType1.getSensorDeviceParentNodeId()).append(",");
				sql.append(internalTypeUnion.internalType1.getSensorDeviceSubAddress()).append(",");
				sql.append(internalTypeUnion.internalType1.getSensorDeviceSensorId()).append(",'");
				sql.append(internalTypeUnion.internalType1.getSensorTable()).append("',");
				sql.append(Util.getOraCurrentTimeString()).append(")");
			}
		}
		
		if (packetType == 4) {
			if(list.size() < 7){
				throw new OraopParamsCountException(packetType);
			}else {
				sql = new StringBuilder("insert into es1100 values (");
				sql.append("'").append(packetHead.getPacketName()).append("',").append(packetHead.getNodeId()).append(",").append(packetHead.getPortId()).append(",");
				for (ParsedDataElement el : list) {
					sql.append(el.getConvertedValue()).append(",");
				}
				sql.append(internalTypeUnion.internalType1.getNodeId()).append(",");
				sql.append(internalTypeUnion.internalType1.getSensorDeviceParentNodeId()).append(",");
				sql.append(internalTypeUnion.internalType1.getSensorDeviceSubAddress()).append(",");
				sql.append(internalTypeUnion.internalType1.getSensorDeviceSensorId()).append(",'");
				sql.append(internalTypeUnion.internalType1.getSensorTable()).append("',");
				sql.append(Util.getOraCurrentTimeString()).append(")");
			}
		}
		if (packetType == 5) {
			if(list.size() < 11){
				throw new OraopParamsCountException(packetType);
			}else {
				sql = new StringBuilder("insert into es1401 values (");
				sql.append("'").append(packetHead.getPacketName()).append("',").append(packetHead.getNodeId()).append(",").append(packetHead.getPortId()).append(",");
				for (ParsedDataElement el : list) {
					sql.append(el.getConvertedValue()).append(",");
				}
				sql.append(internalTypeUnion.internalType1.getNodeId()).append(",");
				sql.append(internalTypeUnion.internalType1.getSensorDeviceParentNodeId()).append(",");
				sql.append(internalTypeUnion.internalType1.getSensorDeviceSubAddress()).append(",");
				sql.append(internalTypeUnion.internalType1.getSensorDeviceSensorId()).append(",'");
				sql.append(internalTypeUnion.internalType1.getSensorTable()).append("',");
				sql.append(Util.getOraCurrentTimeString()).append(")");
			}
		}
		
		if (sql != null) {
			logger.info("准备持久化数据");
			oraUtil.executeUpdate(sql.toString());
			logger.info("持久化数据成功");
		}
	}

	/**
	 * 创建数据库表
	 * 
	 * @param packetType
	 *            数据包类型
	 */
	public void dbCreateTable(int packetType) {
		String sql = null;
		if (packetType == 0) {
			if (!oraUtil.isTableExit("PHYSICAL_HEALTH")) {
				sql = "create table physical_health(packet_name char(28) not null,nodeid1 number(12,6) not null,port number(12,6) not null,amtype number(4) not null,groupid number(4) not null,source_addr number(8) not null,nodeid3 number(12,6) not null,socketid number(4) not null,packet_version number(4) not null,route_type number(4) not null,baseid number(4) not null,node_count number(4) not null,high_power number(4) not null,version number(4) not null,type number(4) not null,seq_num number(8) not null,health_pkts number(8) not null,forwarded number(8) not null,dropped number(8) not null,retries number(8) not null,battery number(12,6) not null,power_sum number(8) not null,boardid number(4) not null,parent number(8) not null,quality_rx number(4) not null,quality_tx number(4) not null,path_cost number(4)  not null,parent_rssi number(4) not null,yield_count number(12) not null,yield_server_count  number(12) not null,nodeid2 number(12,6) not null,health_parentid number(12,6) not null,insert_time date)";
			}
		} 
		else if (packetType == 1) {
			if(!oraUtil.isTableExit("NEIGHBOR_HEALTH")){
				sql = "create table neighbor_health(packet_name char(28) not null,nodeid1 number(12,6) not null,port number(12,6) not null,amtype number(4) not null,groupid number(4) not null,source_addr number(8) not null,nodeid3 number(12,6) not null,socketid number(4) not null,packet_version number(4) not null,route_type number(4) not null,baseid number(4) not null,node_count number(4) not null,high_power number(4) not null,version number(4) not null,type number(4) not null,neighborid1 number(8),rquality1 number(4),lquality1 number(4),path_cost1 number(4),rssi1 number(4),neighborid2  number(8),rquality2 number(4),lquality2 number(4),path_cost2 number(4),rssi2 number(4),neighborid3 number(8),rquality3 number(4),lquality3 number(4),path_cost3 number(4),rssi3 number(4),nodeid2 number(12,6) not null,health_neighborid  varchar2(8) not null,insert_time date)";
			}
		}
		else if (packetType == 2) {
			if(!oraUtil.isTableExit("EN2100")){
				sql = "create table en2100(packet_name char(28) not null,nodeid1 number(12,6) not null,port number(12 ,6) not null,amtype number(4) not null,groupid number(4) not null,nodeid3 number(12,6) not null,socketid number(4) not null,boardid	number(4) not null,packetid number(4) not null,vref number(8) not null,batteryV number(12,6) not null,solarV number(12,6) not null,internal_temp number(12,6) not null,port_status  number(4) not null,packet_number number(4) not null,nodeid2 number(12,6) not null,sensor_device_parent_nodeid number(12,6) not null,sensor_device_sub_address	number(12,6) not null,sensor_device_sensorid number(12,6) not null,yield_appid number(12,6) not null,sensor_table char(30) not null,insert_time date)";
			}
		}
		else if (packetType == 3) {
			if(!oraUtil.isTableExit("ES1201")){
				sql = "create table es1201(packet_name char(46) not null,nodeid1 number(12,6) not null,port number(12 ,6) not null,amtype number(4) not null,groupid number(4) not null,nodeid3 number(12,6) not null,socketid number(4) not null,boardid number(4) not null,packetid number(4) not null,temperature number(12,6) not null,humidity number(12,6) not null,dewpoint     number(12,6) not null,nodeid2 number(12,6) not null,sensor_device_parent_nodeid number(12,6) not null,sensor_device_sub_address number(12,6) not null,sensor_device_sensorid number(12,6) not null,sensor_table char(21) not null,insert_time date)";
			}
		}
		else if (packetType == 4) {
			if(!oraUtil.isTableExit("ES1100")){
				sql = "create table es1100(packet_name char(30) not null,nodeid1 number(12,6) not null,port number(12,6) not null,amtype number(4) not null,groupid number(4) not null,nodeid3 number(12,6) not null,socketid number(4) not null,boardid number(4) not null,packetid number(4) not null,soil_moisture number(12,6) not null,nodeid2 number(12,6) not null,sensor_device_parent_nodeid number(12,6) not null,sensor_device_sub_address number(12,6) not null,sensor_device_sensorid number(12,6) not null,sensor_table  char(21) not null,insert_time date)";
			}
		}
		else if (packetType == 5) {
			if(!oraUtil.isTableExit("ES1401")){
				sql = "create table es1401(packet_name char(32) not null,nodeid1 number(12,6) not null,port number(12,6) not null,amtype number(4) not null,groupid number(4) not null,nodeid3 number(12,6) not null,socketid number(4) not null,boardid number(4) not null,packetid number(4) not null,E1ReferenceADC	number(8) not null,E1ExcitationV number(12,6) not null,SolarRadiation number(12) not null,SolarRadiationADC number(8) not null,E1ExcitationADC        number(8)  not null,nodeid2 number(12,6) not null,sensor_device_parent_nodeid number(12,6) not null,sensor_device_sub_address number(12,6) not null,sensor_device_sensorid number(12,6) not null,sensor_table  char(21) not null,insert_time date)";
			}
		}
		if (sql != null) {
			logger.info("准备创建数据表");
			oraUtil.executeUpdate(sql);
			logger.info("创建数据表成功");
		}
	}
	


}
