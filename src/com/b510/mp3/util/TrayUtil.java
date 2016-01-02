/**
 * 
 */
package com.b510.mp3.util;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.MainUI;

/**
 * @author Hongten
 * @created 23 Dec, 2015
 */
public class TrayUtil extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = -1825941505298386444L;

	static Logger logger = Logger.getLogger(TrayUtil.class);
	
	private Image icon;
	private TrayIcon trayIcon;
	private SystemTray systemTray;
	private PopupMenu popupMenu = new PopupMenu();

	private MenuItem addItem;
	private MenuItem miniItem;
	private MenuItem restoreItem;
	private MenuItem exitItem;

	private MenuItem playModeItem;
	private MenuItem skinItem;

	private MenuItem previousItem;
	private MenuItem nextItem;

	private MainUI mainUI;

	public TrayUtil(MainUI mainUI) {
		this.mainUI = mainUI;
		initTray();
	}

	/**
	 * init tray
	 */
	public void initTray() {
		if (SystemTray.isSupported()) {
			generateTrayIcon();
			generatePopuppMenu();
			systemTray = SystemTray.getSystemTray();
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * tray icon setting
	 */
	private void generateTrayIcon() {
		icon = new ImageIcon(this.getClass().getClassLoader().getResource(Common.IMAGE_TRAY)).getImage();
		trayIcon = new TrayIcon(icon, Common.OPEN_MAIN_PANEL, popupMenu);
		trayIcon.addMouseListener(this);
	}

	/**
	 * all tray menus will be initialized
	 */
	private void generatePopuppMenu() {
		previousItem = new MenuItem(Common.PREVIOUS);
		previousItem.addActionListener(this);
		popupMenu.add(previousItem);

		nextItem = new MenuItem(Common.NEXT);
		nextItem.addActionListener(this);
		popupMenu.add(nextItem);

		popupMenu.addSeparator();

		skinItem = new MenuItem(Common.SKIN);
		skinItem.addActionListener(this);
		popupMenu.add(skinItem);
		
		playModeItem = new MenuItem(Common.SHUFFLE_MODE);
		playModeItem.addActionListener(this);
		popupMenu.add(playModeItem);

		popupMenu.addSeparator();

		addItem = new MenuItem(Common.ADD);
		addItem.addActionListener(this);
		popupMenu.add(addItem);

		miniItem = new MenuItem(Common.MINI);
		miniItem.addActionListener(this);
		popupMenu.add(miniItem);

		restoreItem = new MenuItem(Common.RESTORE);
		restoreItem.addActionListener(this);
		popupMenu.add(restoreItem);

		popupMenu.addSeparator();

		exitItem = new MenuItem(Common.EXIT);
		exitItem.addActionListener(this);
		popupMenu.add(exitItem);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if ((e.getClickCount() == 1 || e.getClickCount() == 2) && e.getButton() != MouseEvent.BUTTON3) {
			if (mainUI.isVisible()) {
				mainUI.setVisible(false);
			} else {
				mainUI.setVisible(true);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == previousItem) {
			// play previous song
			logger.debug("play previous ....from tray...");
			mainUI.playWithPalyMode(true, false);
		} else if (e.getSource() == nextItem) {
			// play next song
			logger.debug("play next ....from tray...");
			mainUI.playWithPalyMode(false, true);
		} else if (e.getSource() == exitItem) {
			// exist system
			mainUI.existSystem();
		} else if (e.getSource() == restoreItem) {
			// show main user interface
			mainUI.setVisible(true);
		} else if (e.getSource() == addItem) {
			// add new songs
			logger.debug("add new song ....from tray...");
			mainUI.setVisible(true);
			mainUI.addNewSongs();
		} else if (e.getSource() == miniItem) {
			// show the main user interface and exchange the normal/mini window.
			mainUI.setVisible(true);
			if (MainUI.MINI_STATUS) {
				mainUI.setSize(Common.WINDOW_WIDTH, Common.WINDOW_MINI_HEIGHT);
				mainUI.setMiniItemText(Common.NORMAL);
				setMiniItemlabel(Common.NORMAL);
			} else {
				mainUI.setSize(Common.WINDOW_WIDTH, Common.WINDOW_HEIGHT);
				mainUI.setMiniItemText(Common.MINI);
				setMiniItemlabel(Common.MINI);
			}
			MainUI.MINI_STATUS = !MainUI.MINI_STATUS;
		} else if (e.getSource() == playModeItem) {
			logger.debug("play mode ....from tray...");
			mainUI.playModeOperation();
		} else if (e.getSource() == skinItem) {
			logger.debug("play mode ....from tray...");
			mainUI.setVisible(true);
			mainUI.changeSkin();
		}
	}

	public void setPlayModeItemText(String playModeStr) {
		playModeItem.setLabel(playModeStr);
	}
	
	public void setMiniItemlabel(String text){
		miniItem.setLabel(text);
	}

}
