package pmcs.gui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;



/**
 * 从指定的输入流读取数据，展示在主界面的控制台
 * 
 * @author steven
 * 
 */
class TextAreaWriter {
	private JTextArea textArea = null;
	public TextAreaWriter(JTextArea textArea) {
		super();
		this.textArea = textArea;
	}

	public void redirectSystemErr() {
		
		OutputStream out = new OutputStream() {
		    @Override
		    public void write(int b) throws IOException {
		      updateTextArea(String.valueOf((char) b));
		    }

		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {
		      updateTextArea(new String(b, off, len));
		    }

		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };
		System.setErr(new PrintStream(out, true));
	}
	private void updateTextArea(final String str) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textArea.append(str);
			}
		});

	}

}
