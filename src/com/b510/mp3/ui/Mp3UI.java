/**
 * 
 */
package com.b510.mp3.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;


/**
 * The <code>Mp3UI</code> class extends <code>JUI</code> and implements <code>ActionListener</code>.
 * @author Hongten
 * @created 23 Dec, 2015
 */
public class Mp3UI extends JUI implements ActionListener {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(Mp3UI.class);
	
	private MainUI mainUI;

	public Mp3UI(String title) {
		super(title);
		logger.debug("title = " + title);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public void init() {
		if (null == mainUI) {
			mainUI = new MainUI(title);
		}
		mainUI.init();
	}

}
