package pmcs.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;
import pmcs.connector.Connector;
import pmcs.util.Util;
/**
 * 开启动作响应
 * @author steven
 *
 */
public class StartAction extends AbstractAction {
	private static final long serialVersionUID = 6302789668455097128L;
	private static Logger logger = Util.getLogger(StartAction.class);
	private Connector connector = null;
	public StartAction(String text, ImageIcon icon, Connector connector){
		super(text, icon);
		this.connector = connector;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			try {
				logger.info("启动程序");	
				new Thread(new Runnable() {				
					public void run() {
						connector.start();   
					}
				}).start();           		            			
			} catch (Exception ex) {
				logger.warn("程序启动出现异常，启动失败。",ex);
				ex.printStackTrace();
			}
	}

}
