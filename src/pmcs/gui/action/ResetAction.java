package pmcs.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import pmcs.gui.ConfigGui;
/**
 * 重置参数
 * @author steven
 *
 */
public class ResetAction extends AbstractAction {
	private static final long serialVersionUID = -246877341519388686L;
	private ConfigGui gui = null;
	public ResetAction(String text, ImageIcon icon, ConfigGui gui) {
		super(text, icon);
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.printParams();
	}

}
