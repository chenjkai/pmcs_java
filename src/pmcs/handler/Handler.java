package pmcs.handler;

import java.util.ArrayList;
import java.util.List;

import pmcs.exception.connector.ConnectorAlreadyStartException;
import pmcs.exception.connector.ConnectorAlreadyStopException;
import pmcs.lifecycle.Lifecycle;
import pmcs.oracop.OraOp;
import pmcs.xml.parser.XmlParser;

/**
 * 
 * 处理器，调用parse解析xml，调用oraop进行存储
 * @author steven
 *
 */
public class Handler implements Lifecycle, Runnable {
	public static int  count = 0;
	public int num = 0;
	private List<byte[]> xmlList = new ArrayList<byte[]>();
	private volatile boolean running = false;
	public Handler(List<byte[]> xmlList) {
		super();
		countPlus();
		this.xmlList = xmlList;
	}
	public synchronized void countPlus() {
		num = ++count;
	}
	@Override
	public void run() {
		System.out.println("Handler" + num + ": is started");
		while(running){
			synchronized (xmlList) {
				if (xmlList.isEmpty()) {
					try {
						System.out.println("handler"+ num +":no task and wait");
						xmlList.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else {
					XmlParser parser = new XmlParser();
					try {
						System.out.println("handler"+ num +":process the xml");
						parser.parse(xmlList.remove(0));
						OraOp oraOp = new OraOp();
						oraOp.dbInsertData(parser.parsedElementList,
								parser.internalTypeUnion,
								parser.packetHead, parser.packetType);
						xmlList.notifyAll();
					} catch (Exception e) {
						e.printStackTrace();
						try {
							tryToRecover();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					} 	
				}
			}
		}
		
	}

	@Override
	public void start() throws Exception {
		if (running) {
			throw new ConnectorAlreadyStartException();
		} else {
			running = true;
			new Thread(this).start();
		}
	}

	@Override
	public void stop() throws Exception {
		if (!running) {
			throw new ConnectorAlreadyStopException();
		}else {
			System.out.println("stop");
			count = 0;
			running = false;
		}
	}

	@Override
	public void tryToRecover() throws Exception {
		stop();
		start();
	}
	
}
