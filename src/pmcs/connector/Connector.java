package pmcs.connector;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import pmcs.configuration.Configuration;
import pmcs.exception.configuration.CanNotPassEmptyKey;
import pmcs.exception.configuration.ConfigureFileNotExitException;
import pmcs.exception.configuration.KeyNotExitException;
import pmcs.exception.connector.ConnectorAlreadyStartException;
import pmcs.exception.connector.ConnectorAlreadyStopException;
import pmcs.exception.connector.ConnetorInitialException;
import pmcs.exception.connector.XmlListOverFlowException;
import pmcs.exception.parser.ReadXmlBytesException;
import pmcs.exception.util.UnCorrectToGetDomainException;
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
	private String address = null;
	private int port = 0;
	private Configuration cfg = null;
	private Socket socket = null;
	private volatile boolean running = false;
	private List<byte[]> xmlList = new ArrayList<byte[]>();
	private int handlerNum = 0;
	private Handler[] handlerList = null;

	/**
	 * 初始化connector
	 * 
	 * @throws Exception
	 */
	private void initial() throws Exception {
		try {
			cfg = new Configuration();
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

		} catch (CanNotPassEmptyKey e) {
			e.printStackTrace();
		} catch (KeyNotExitException e) {
			e.printStackTrace();
		} catch (ConfigureFileNotExitException e) {
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
	 */
	public void tryToConnect() {
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
	 * @throws Exception
	 */
	@Override
	public synchronized void start() throws Exception {
		if (running) {
			throw new ConnectorAlreadyStartException();
		} else {
			initial();
			new Thread(this).start();
			running = true;
		}

		for (int i = 0; i < handlerNum; i++) {
			try {
				Handler handler = new Handler(xmlList);
				handler.start();
				handlerList[i] = handler;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 停止connector
	 */
	@Override
	public synchronized void stop() throws ConnectorAlreadyStopException {
		if (!running) {
			throw new ConnectorAlreadyStopException();
		}
		for (int i = 0; i < handlerNum; i++) {
			try {
				handlerList[i].stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		xmlList.clear();
		running = false;
		if (socket != null) {
			try {
				socket.close();
				socket = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 重启connector
	 * 
	 * @throws Exception
	 */
	@Override
	public synchronized void tryToRecover() throws Exception {
		try {
			stop();
		} catch (ConnectorAlreadyStopException e1) {
			e1.printStackTrace();
		}
		int count = 1;
		while (count <= 10 && !isConnect()) {
			try {
				Thread.sleep(180000);
				System.out.println("the " + count
						+ " times to try to re-connect.");
				count++;
				start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		try {
			XmlReader xmlReader = new XmlReader();
			while (running) {
				if (!isConnect()) {
					tryToConnect();
				}
				if (xmlList.size() > 100) {
					throw new XmlListOverFlowException();
				} else {
					byte[] xmlBytes = xmlReader.readXmlBytesFromSocket(socket);
					synchronized (xmlList) {
						xmlList.add(xmlBytes);
						xmlList.notifyAll();
					}
				}
			}
		} catch (ReadXmlBytesException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlListOverFlowException e) {
			e.printStackTrace();
		} finally {
			try {
				tryToRecover();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
