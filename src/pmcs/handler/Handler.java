package pmcs.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pmcs.gui.PmcsGui;
import pmcs.lifecycle.Lifecycle;
import pmcs.oracop.OraOp;
import pmcs.util.Util;
import pmcs.xml.parser.XmlParser;

/**
 * 
 * 处理器，调用parse解析xml，调用oraop进行存储
 * 
 * @author steven
 * 
 */
public class Handler implements Lifecycle, Runnable {
	private static Logger logger = Util.getLogger(Handler.class);

	public static int count = 0;
	public int num = 0;
	private List<byte[]> xmlList = new ArrayList<byte[]>();
	private volatile boolean running = false;
	private PmcsGui gui = null;
	

	public void setGui(PmcsGui gui) {
		this.gui = gui;
	}

	public Handler(List<byte[]> xmlList, PmcsGui gui) {
		super();
		countPlus();
		this.xmlList = xmlList;
		if(gui != null)
			this.gui = gui;
	}

	public synchronized void countPlus() {
		num = ++count;
	}

	@Override
	public void run() {
		while (running) {
			synchronized (xmlList) {
				if (xmlList.isEmpty()) {
					try {
						xmlList.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					XmlParser parser = new XmlParser();
					try {
						logger.info("处理器:handler" + num + "开始处理xml文档");
						logger.info("解析xml文档");
						parser.parse(xmlList.remove(0));
						OraOp oraOp = new OraOp();
						logger.info("进行数据库操作");
						oraOp.dbInsertData(parser.parsedElementList,
								parser.internalTypeUnion, parser.packetHead,
								parser.packetType);
						if(gui != null)
							gui.printTimelyData(parser);
						logger.info("处理器:handler" + num + "成功处理xml文档");
						xmlList.notifyAll();
					} catch (Exception e) {
						e.printStackTrace();
						try {
							logger.info("处理器：handler"+ num +"尝试重启");
							tryToRecover();
							logger.info("处理器：handler"+ num +"重启成功");
						} catch (Exception e1) {
							logger.warn("handler"+ num + "尝试自动恢复出现异常",e1);
							e1.printStackTrace();
						}

					}
				}
			}
		}
	}

	@Override
	public void start() {
		if (!running) {
			logger.info("尝试启动处理器：handler" + num);
			running = true;
			new Thread(this).start();
			logger.info("成功启动处理器:handler" + num);
		} 
	}

	@Override
	public void stop(){
		if (running) {
			logger.info("尝试停止处理器:handler" + num);
			count = 0;
			running = false;
			logger.info("停止处理器:handler" + num + "成功");
		} 
	}

	@Override
	public void tryToRecover() {
		stop();
		start();
	}

}
