package pmcs.dto;

public class MotePacketHead {
	private String packetName;
	private float nodeId;
	private float portId;
	public String getPacketName() {
		return packetName;
	}
	public void setPacketName(String packetName) {
		this.packetName = packetName;
	}
	public float getNodeId() {
		return nodeId;
	}
	public void setNodeId(float nodeId) {
		this.nodeId = nodeId;
	}
	public float getPortId() {
		return portId;
	}
	public void setPortId(float portId) {
		this.portId = portId;
	}
}
