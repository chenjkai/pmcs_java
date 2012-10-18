package pmcs.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 创建菜单项的工具类
 * @author steven
 *
 */
public class MenuBarTool {
	/**
	 * 从菜单列表创建菜条
	 * @param menus 菜单列表
	 * @return 菜单条
	 */
	public static JMenuBar createMenuBar(List<JMenu> menus){
		JMenuBar menuBar = new JMenuBar();
		for(JMenu menu : menus){
			menuBar.add(menu);
		}
		return menuBar;
	}
	/**
	 * 从动作列表创建菜单列表
	 * @param actions 绑定的动作
	 * @return 菜单项
	 */
	public static List<JMenuItem> createMenuItmes(List<Action> actions){
		List<JMenuItem> menuItems = new ArrayList<JMenuItem>();
		for(Action action : actions){
			menuItems.add(new JMenuItem(action));
		}
		return menuItems;
	}
	
	/**
	 * 从菜单名和菜单项创建菜单
	 * @param menuName 菜单名
	 * @param menuItems 菜单项
	 * @return 菜单
	 */
	public static JMenu createMenu(String menuName, List<JMenuItem> menuItems){
		JMenu menu = new JMenu(menuName);
		for(JMenuItem menuItem : menuItems){
			menu.add(menuItem);
		}
		return menu;
	}
}