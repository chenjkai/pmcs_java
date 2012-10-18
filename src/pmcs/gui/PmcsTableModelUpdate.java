package pmcs.gui;
import javax.swing.table.DefaultTableModel;

public class PmcsTableModelUpdate extends DefaultTableModel {

	private static final long serialVersionUID = 3552369398056724358L;

	public int getRowCount() {
		return 50;
	}

	public int getColumnCount() {
		return 6;
	}

	public String getColumnName(int col) {
		switch (col) {
		case 0:
			return "GroupID";
		case 1:
			return "NodeID";
		case 2:
			return "Port";
		case 3:
			return "Type";
		case 4:
			return "Data";
		case 5:
			return "Time";
		}

		throw new AssertionError("invalid column");
	}

}
