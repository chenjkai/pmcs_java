package pmcs.test;

import pmcs.gui.PmcsGui;

public class TestAddTreeNode {
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	PmcsGui gui = new PmcsGui();
            	Object object[] = {1, 2, 3, 4, 5, 6, 7};
            	gui.updateTree(object, gui.base);
            }
        });
		
	}
}
