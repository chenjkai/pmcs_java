package pmcs.connector;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pmcs.configuration.Configuration;
import pmcs.exception.connector.ConnetorInitialException;
import pmcs.exception.connector.XmlListOverFlowException;
import pmcs.exception.handler.HandlerAlreadyStartException;
import pmcs.exception.handler.HandlerAlreadyStopException;
import pmcs.exception.parser.ReadXmlBytesException;
import pmcs.exception.util.UnCorrectToGetDomainException;
import pmcs.gui.PmcsGui;
import pmcs.handler.Handler;
import pmcs.lifecycle.Lifecycle;
import pmcs.util.Util;
import pmcs.xml.reader.XmlReader;

/**
 * 连接远程ip或者域名(domain)
 * 
 * @author steven
 * 
 */
public class Connector implements Lifecycle, Runnable {

	private static Logger logger = Util.getLogger(Connector.class);

	private String address = null;
	private int port = 0;
	private Configuration cfg = null;
	private Socket socket = null;
	private volatile boolean running = false;
	private List<byte[]> xmlList = new ArrayList<byte[]>();
	private int handlerNum = 0;
	private Handler[] handlerList = null;
	private PmcsGui gui = null;

	public void setGui(PmcsGui gui) {
		this.gui = gui;
	}

	/**
	 * 初始化connector
	 * 
	 * @throws UnCorrectToGetDomainException
	 * 
	 * @throws Exception
	 */
	private void initial() {
		try {
			logger.info("pmcs_java初始化");
			cfg = Configuration.getConfiguration();
			address = cfg.getProperty("domain");
			port = Integer.parseInt(cfg.getProperty("port"));
			handlerNum = Integer.parseInt(cfg.getProperty("handlerNum"));
			handlerList = new Handler[handlerNum];
			if (Util.isDomain(address)) {
				address = Util.getIpByDomain(address);
				if (address == null) {
					throw new UnCorrectToGetDomainException();
				}
			} else if (Util.isIp(address)) {
				// something to do
			} else {
				throw new ConnetorInitialException();
			}

		} catch (Exception e) {
			logger.warn("pmcs_java初始化失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否成功与网关连接
	 * 
	 * @param socket
	 * @return
	 */
	public boolean isConnect() {
		return (socket != null && socket.isConnected());
	}

	/**
	 * 通过tcp/ip协议和网关进行连接
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public void tryToConnect() {
		logger.info("连接:" + address + ":" + port);
		try {
			socket = new Socket(address, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动connector
	 * 
	 * @throws AlreadyStartException
	 * @throws HandlerAlreadyStartException
	 * 
	 * @throws Exception
	 */
	@Override
	public synchronized void start() {
		if (!running) {
			initial();
			logger.info("初始化成功，尝试启动连接器");
			Thread th = new Thread();
			th.setDaemon(true);
			running = true;
			new Thread(this).start();
			logger.info("成功启动连接器，尝试启动处理器");
		}
		for (int i = 0; i < handlerNum; i++) {
			Handler handler = new Handler(xmlList, gui);
			handler.start();
			handlerList[i] = handler;
		}

	}

	/**
	 * 停止connector
	 * 
	 * @throws HandlerAlreadyStopException
	 */
	@Override
	public synchronized void stop() {
		for (int i = 0; i < handlerNum; i++) {
			handlerList[i].stop();
		}
		if (running) {
			logger.info("尝试停止connector");
			xmlList.clear();
			running = false;
		}

	}

	/**
	 * 重启connector
	 * 
	 * @throws Exception
	 */
	@Override
	public synchronized void tryToRecover() {
		logger.warn("180000毫秒后尝试重新启动");
		try {
			Thread.sleep(180000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		xmlList.clear();
		initial();
		if (!isConnect()) {
			tryToConnect();
		}
	}

	@Override
	public void run() {
		XmlReader xmlReader = new XmlReader();
		while (running) {
			try {
				if (!isConnect()) {
					tryToConnect();
				}
				if (xmlList.size() > 300) {
					throw new XmlListOverFlowException();
				} else {
					byte[] xmlBytes = xmlReader.readXmlBytesFromSocket(socket);
					logger.info("成功读取xml，长度为:" + xmlBytes.length);
					synchronized (xmlList) {
						xmlList.add(xmlBytes);
						logger.info("数据缓存队列存容量为：300，已占用：" + xmlList.size());
						xmlList.notifyAll();
					}
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
				tryToRecover();
			} catch (IOException e) {
				e.printStackTrace();
				tryToRecover();
			} catch (ReadXmlBytesException e) {
				e.printStackTrace();
				tryToRecover();
			} catch (XmlListOverFlowException e) {
				e.printStackTrace();
				tryToRecover();
			}

		}
		logger.info("成功停止connector");
	}
}
