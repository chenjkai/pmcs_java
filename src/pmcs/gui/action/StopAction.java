package pmcs.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import pmcs.connector.Connector;
import pmcs.util.Util;
/**
 * 停止动作响应
 * @author steven
 *
 */
public class StopAction extends AbstractAction {
	
	private static final long serialVersionUID = 5480369057600799757L;
	private static Logger logger = Util.getLogger(StopAction.class);
	private Connector connector = null;
	public StopAction(String text, ImageIcon icon, Connector connector){
		super(text, icon);
		this.connector = connector;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		logger.info("停止程序");
		if(connector != null){
			new Thread(new Runnable() {				
				public void run() {
			        connector.stop();	            
				}
			}).start();   
		}
	}

}
