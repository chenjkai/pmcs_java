package pmcs.test;

import pmcs.gui.PmcsGui;

public class TestUI {
	
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new PmcsGui();
            }
        });
		
	}

}
