package pmcs.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import pmcs.configuration.Configuration;
import pmcs.exception.configuration.CanNotPassEmptyKey;
import pmcs.exception.configuration.KeyNotExitException;
import pmcs.gui.action.DoneAction;
import pmcs.gui.action.ResetAction;

/**
 * 配置界面
 * 
 * @author steven
 * 
 */
public class ConfigGui extends JFrame {

	private static final long serialVersionUID = -4127421827195308991L;
	private Configuration cfg = null;
	public JTextField ipTextField = null;
	public JTextField portTextField = null;
	public JTextField handlerTextField = null;
	public JTextField sidTextField = null;
	public JTextField dbIpTextField = null;
	public JTextField dbPortTextField = null;
	public JTextField usernameTextField = null;
	public JTextField passwdTextField = null;
	public JButton doneButton = null;
	public JButton restButton = null;

	public ConfigGui() {
		super();
		initialize();
		createContentPanel(this);
	}

	/**
	 * 初始化界面
	 */
	private void initialize() {
		this.setSize(600, 550);
		this.setTitle("配置运行参数");
		this.setLocation(100, 50);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 设定关闭动作
		this.setVisible(true);
		cfg = Configuration.getConfiguration();
		printParams();
	}

	private void createContentPanel(JFrame frame) {
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(3, 1);
		panel.setLayout(layout);
		createNorthPanel(panel);
		createSouthPanel(panel);
		createControlPanel(panel);
		frame.setContentPane(panel);
	}

	/**
	 * pmcs_java参数配置
	 * 
	 * @param panel
	 *            contentPane
	 */
	private void createNorthPanel(JPanel panel) {
		JPanel northPanle = new JPanel();
		JScrollPane northJScrollPane = new JScrollPane(northPanle);
		northPanle.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("pmcs_java参数配置"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		northPanle.setLayout(new GridLayout(2, 2));
		// ip或者域名输入
		JPanel ipPanel = new JPanel(new FlowLayout());
		JLabel ipLabel = new JLabel("地址:");
		ipTextField = new JTextField(10);
		ipLabel.setLabelFor(ipTextField);
		ipPanel.add(ipLabel);
		ipPanel.add(ipTextField);
		northPanle.add(ipPanel);
		// 端口配置
		JPanel portJPanel = new JPanel(new FlowLayout());
		JLabel portJLabel = new JLabel("端口号:");
		portTextField = new JTextField(10);
		portJLabel.setLabelFor(portTextField);
		portJPanel.add(portJLabel);
		portJPanel.add(portTextField);
		northPanle.add(portJPanel);
		// 处理线程数
		JPanel handlerJPanel = new JPanel(new FlowLayout());
		JLabel handlerJLabel = new JLabel("线程数:");
		handlerTextField = new JTextField(10);
		handlerJLabel.setLabelFor(handlerTextField);
		handlerJPanel.add(handlerJLabel);
		handlerJPanel.add(handlerTextField);
		northPanle.add(handlerJPanel);

		panel.add(northJScrollPane);
	}

	/**
	 * 数据库连接参数配置
	 * 
	 * @param panel
	 *            contentPane
	 */
	private void createSouthPanel(JPanel panel) {
		JPanel southJPanel = new JPanel();
		JScrollPane southJScrollPane = new JScrollPane(southJPanel);
		southJPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("数据库连接参数配置"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		southJPanel.setLayout(new GridLayout(3, 2));
		// oracle SID "pmcsdb2"
		JPanel sidJPanel = new JPanel(new FlowLayout());
		JLabel sidJLabel = new JLabel("实例名:");
		sidTextField = new JTextField(10);
		sidJLabel.setLabelFor(sidTextField);
		sidJPanel.add(sidJLabel);
		sidJPanel.add(sidTextField);
		southJPanel.add(sidJPanel);

		// oracle server ip
		JPanel ipJPanel = new JPanel(new FlowLayout());
		JLabel ipJLabel = new JLabel("ip地址:");
		dbIpTextField = new JTextField(10);
		ipJLabel.setLabelFor(dbIpTextField);
		ipJPanel.add(ipJLabel);
		ipJPanel.add(dbIpTextField);
		southJPanel.add(ipJPanel);

		// oracle instance port
		JPanel portJPanel = new JPanel(new FlowLayout());
		JLabel portJLabel = new JLabel("端口号:");
		dbPortTextField = new JTextField(10);
		portJLabel.setLabelFor(dbPortTextField);
		portJPanel.add(portJLabel);
		portJPanel.add(dbPortTextField);
		southJPanel.add(portJPanel);

		// oracle dba name
		JPanel usernameJPanel = new JPanel(new FlowLayout());
		JLabel usernameJLabel = new JLabel("用户名:");
		usernameTextField = new JTextField(10);
		usernameJLabel.setLabelFor(usernameTextField);
		usernameJPanel.add(usernameJLabel);
		usernameJPanel.add(usernameTextField);
		southJPanel.add(usernameJPanel);

		// oracle dba passwd
		JPanel passwdJPanel = new JPanel(new FlowLayout());
		JLabel passwdJLabel = new JLabel("密码:");
		passwdTextField = new JTextField(10);
		passwdJLabel.setLabelFor(passwdTextField);
		passwdJPanel.add(passwdJLabel);
		passwdJPanel.add(passwdTextField);
		southJPanel.add(passwdJPanel);
		panel.add(southJScrollPane);
	}
	/**
	 * 显示配置信息
	 */
	public void printParams() {
		new SwingWorker<Void, Void>() {
			@Override
			protected void done() {
				super.done();
				try {
					ipTextField.setText(cfg.getProperty("domain"));
					portTextField.setText(cfg.getProperty("port"));
					handlerTextField.setText(cfg.getProperty("handlerNum"));
					sidTextField.setText(cfg.getProperty("sid"));
					String[] params = cfg.getProperty("uri").split(":");
					dbIpTextField.setText(params[3].substring(1));
					dbPortTextField.setText(params[4]);
					usernameTextField.setText(cfg.getProperty("username"));
					passwdTextField.setText(cfg.getProperty("password"));
				} catch (CanNotPassEmptyKey e) {
					e.printStackTrace();
				} catch (KeyNotExitException e) {
					e.printStackTrace();
				}
			}
			@Override
			protected Void doInBackground() throws Exception {
				return null;
			}

		}.execute();
	}
	/**
	 * 创建控制按钮
	 * @param panel contentPane
	 */
	private void createControlPanel(JPanel panel) {
		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(BorderFactory.createCompoundBorder(null,
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		JScrollPane scrollPane = new JScrollPane(controlPanel);
		doneButton = new JButton(new DoneAction("生效", null, this));
		controlPanel.add(doneButton);
		restButton = new JButton(new ResetAction("重置", null, this));
		controlPanel.add(restButton);
		panel.add(scrollPane);
	}
	
	public void validateParams(){
		
	}
}
