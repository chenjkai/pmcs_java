package pmcs.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import pmcs.gui.ConfigGui;

public class DoneAction extends AbstractAction {
	private static final long serialVersionUID = 3252936951916062230L;
	private ConfigGui gui = null;
	public DoneAction(String text, ImageIcon icon, ConfigGui gui) {
		super(text, icon);
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.dispose();
	}
	
}
