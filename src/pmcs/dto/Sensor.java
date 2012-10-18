package pmcs.dto;
/**
 * 传感器数据对象
 * @author steven
 *
 */
public class Sensor {
	private String sensorName;
	private String port;
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
}
