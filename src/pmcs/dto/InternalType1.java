package pmcs.dto;

public class InternalType1 {
	private float nodeId;
	private float sensorDeviceParentNodeId;
	private float sensorDeviceSubAddress;
	private float sensorDeviceSensorId;
	private String sensorTable;
	
	public float getNodeId() {
		return nodeId;
	}
	public void setNodeId(float nodeId) {
		this.nodeId = nodeId;
	}
	public float getSensorDeviceParentNodeId() {
		return sensorDeviceParentNodeId;
	}
	public void setSensorDeviceParentNodeId(float sensorDeviceParentNodeId) {
		this.sensorDeviceParentNodeId = sensorDeviceParentNodeId;
	}
	public float getSensorDeviceSubAddress() {
		return sensorDeviceSubAddress;
	}
	public void setSensorDeviceSubAddress(float sensorDeviceSubAddress) {
		this.sensorDeviceSubAddress = sensorDeviceSubAddress;
	}
	public float getSensorDeviceSensorId() {
		return sensorDeviceSensorId;
	}
	public void setSensorDeviceSensorId(float sensorDeviceSensorId) {
		this.sensorDeviceSensorId = sensorDeviceSensorId;
	}
	public String getSensorTable() {
		return sensorTable;
	}
	public void setSensorTable(String sensorTable) {
		this.sensorTable = sensorTable;
	}
}
