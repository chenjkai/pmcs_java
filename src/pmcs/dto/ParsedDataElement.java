package pmcs.dto;

public class ParsedDataElement {
	private String elementName;
	private String specialType;
	private String convertedValue;
	private String convertedValueType;
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public String getSpecialType() {
		return specialType;
	}
	public void setSpecialType(String specialType) {
		this.specialType = specialType;
	}
	public String getConvertedValue() {
		return convertedValue;
	}
	public void setConvertedValue(String convertedValue) {
		this.convertedValue = convertedValue;
	}
	public String getConvertedValueType() {
		return convertedValueType;
	}
	public void setConvertedValueType(String convertedValueType) {
		this.convertedValueType = convertedValueType;
	}
}
