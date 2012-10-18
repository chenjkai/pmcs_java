package pmcs.dto;

import java.util.HashMap;
import java.util.Map;
/**
 * 节点对象
 * @author steven
 *
 */
public class Node {
	private String groupId;
	private String nodeName;
	private Map<String, Sensor> sensorList = new HashMap<String, Sensor>();
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public Map<String, Sensor> getSensorList() {
		return sensorList;
	}
}
