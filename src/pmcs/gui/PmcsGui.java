package pmcs.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import pmcs.connector.Connector;
import pmcs.dto.Node;
import pmcs.dto.Sensor;
import pmcs.gui.action.ConfigAction;
import pmcs.gui.action.StartAction;
import pmcs.gui.action.StopAction;
import pmcs.xml.parser.XmlParser;

public class PmcsGui extends JFrame {

	private static final long serialVersionUID = -6386180545635008741L;
	private Map<String, Node> nodeList = new HashMap<String, Node>();
	private Map<String, DefaultMutableTreeNode> treeNodeMap = new HashMap<String, DefaultMutableTreeNode>();
	private Connector connector = new Connector();
	private JTextArea textArea = new JTextArea(15, 30);
	private PmcsTableModelUpdate tableModel = new PmcsTableModelUpdate();
	private JTable table = new JTable(tableModel);
	public JButton startButton = null;
	public JButton stopButton = null;
	public JButton configButton = null;
	public JTree tree = null;
	public DefaultMutableTreeNode base = null;

	/**
	 * This is the default constructor
	 */
	public PmcsGui() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 600);
		this.setTitle("上海理工大学葡萄生长因子监控系统");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设定关闭动作
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new TextAreaWriter(textArea).redirectSystemErr();// 重定向标准错误流
		createMenuBar(this);
		createContentPane(this);
		connector.setGui(this);
	}

	public void updateTree(Object[] convertedData, DefaultMutableTreeNode base) {
		Node node = new Node();
		Sensor sensor = new Sensor();
		node.setGroupId(convertedData[0].toString());
		node.setNodeName("Node" + convertedData[1]);
		if (!nodeList.containsKey(node.getGroupId() + node.getNodeName())){
			nodeList.put(node.getGroupId() + node.getNodeName(), node);
			addNode(base, node.getNodeName());
		}else {
			node = nodeList.get(node.getGroupId() + node.getNodeName());
		}
		sensor.setPort(convertedData[2].toString());
		sensor.setSensorName(convertedData[3].toString());
		if (!node.getSensorList().containsKey(
				sensor.getSensorName() + sensor.getPort())) {
			node.getSensorList().put(sensor.getSensorName() + sensor.getPort(),
					sensor);
			addSensor(treeNodeMap.get(node.getNodeName()), sensor.getSensorName());
		}
	}

	/**
	 * 增加树节点
	 * 
	 * @param base
	 *            树的根节点，传感网base节点
	 * @param nodeName
	 *            节点名称
	 */
	private void addNode(DefaultMutableTreeNode base, String nodeName) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
		base.add(node);
		tree.updateUI();
		treeNodeMap.put(nodeName, node);
	}

	/**
	 * 增加传感器节点
	 * 
	 * @param treeNode
	 *            树节点
	 * @param sensorName
	 *            传感器名称
	 */
	private void addSensor(DefaultMutableTreeNode treeNode, String sensorName) {
		DefaultMutableTreeNode sensor = new DefaultMutableTreeNode(sensorName);
		treeNode.add(sensor);
		tree.updateUI();
	}

	/**
	 * 创建树形结构
	 * 
	 * @param panel
	 *            内容格
	 */
	private void createTree(JPanel panel) {
		base = new DefaultMutableTreeNode("base");
		tree = new JTree(base);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(tree);
		tree.setPreferredSize(new Dimension(200, 40));
		panel.add(scrollPane, BorderLayout.WEST);
	}

	/**
	 * 创建textarea输出控制台信息
	 * 
	 * @param panel
	 *            内容格
	 */
	private void createConsoleArea(JPanel panel) {
		textArea.setEditable(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel.add(scrollPane, BorderLayout.SOUTH);
	}

	/**
	 * 创建数据表格，显示数据
	 * 
	 * @param panel
	 *            内容格
	 */
	private void createDataTable(JPanel panel) {
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(table);
		panel.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * 创建工具条
	 * 
	 * @param panel
	 *            内容格
	 */
	private void createToolBar(JPanel panel) {
		JToolBar toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.NORTH);
		startButton = new JButton(new StartAction("开启", null, connector));
		stopButton = new JButton(new StopAction("停止", null, connector));
		configButton = new JButton(new ConfigAction("配置", null));
		toolBar.add(startButton);
		toolBar.add(stopButton);
		toolBar.add(configButton);
	}

	/**
	 * 创建内容格
	 * 
	 * @param frame
	 *            主容器
	 */
	private void createContentPane(JFrame frame) {
		JPanel panel = new JPanel();
		frame.setContentPane(panel);
		BorderLayout layout = new BorderLayout();
		panel.setLayout(layout);
		panel.setOpaque(true);
		createToolBar(panel);
		createTree(panel);
		createDataTable(panel);
		createConsoleArea(panel);
	}

	/**
	 * 创建菜单条
	 * 
	 * @param frame
	 *            主容器
	 */
	private void createMenuBar(JFrame frame) {
		List<JMenu> menus = new ArrayList<JMenu>();
		// 创建第一个菜单,创建菜单项目
		List<Action> fActionList = new ArrayList<Action>();
		fActionList.add(new StartAction("开启", null, connector));
		fActionList.add(new StopAction("停止", null, connector));
		List<JMenuItem> fMenuItems = MenuBarTool.createMenuItmes(fActionList);
		JMenu fMenu = MenuBarTool.createMenu("运行", fMenuItems);
		menus.add(fMenu);
		
		//创建第二个菜单项
		List<Action> sActionList = new ArrayList<Action>();
		sActionList.add(new ConfigAction("配置", null));
		List<JMenuItem> sMenuItems = MenuBarTool.createMenuItmes(sActionList);
		JMenu sMenu = MenuBarTool.createMenu("配置", sMenuItems);
		menus.add(sMenu);
		
		// 设置菜单条
		JMenuBar menuBar = MenuBarTool.createMenuBar(menus);
		frame.setJMenuBar(menuBar);
	}
	/**
	 * 更新数据表格实时数据,此方法会在Handler中被调用
	 * 
	 * @param data
	 * 			解析过后的数据
	 */
	public void printTimelyData(final XmlParser data) {
		new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				return null;
			}
			@Override
			protected void done() {
				super.done();
				Object[] convertedData = DataConvertor.convert(data);
				if (convertedData != null) {
					updateTree(convertedData, base);
					tableModel.insertRow(0, convertedData);
					table.updateUI();
				}
			}
		}.execute();
	}

}
