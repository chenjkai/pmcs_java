package pmcs.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import pmcs.gui.ConfigGui;
/**
 * 点击配置按钮，触发显示配置界面
 * @author steven
 *
 */
public class ConfigAction extends AbstractAction {

	private static final long serialVersionUID = 359140062412066573L;
	
	public ConfigAction(String text, ImageIcon icon) {
		super(text, icon);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new ConfigGui();
            }
        });
	}

}
