/**
 * 
 */
package com.b510.mp3.ui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;

import org.apache.log4j.Logger;

import com.b510.mp3.common.Common;
import com.b510.mp3.util.SkinMangerUtil;

/**
 * @author Hongten
 * @created 23 Dec, 2015
 */
public class SkinManagerUI extends MainUI {

	private static final long serialVersionUID = 5789858308684963606L;
	
	static Logger logger = Logger.getLogger(SkinManagerUI.class);
	
	private JLabel currentSkinDescJLabel;
	private JLabel currentSkinJLabel;
	private JLabel descJlabel;
	private JSeparator line;
	private JComboBox<String> sinkJComboBox;

	private SkinMangerUtil skinMangerUtil;

	public String[][] skins = Common.SKINS;

	private String[] skinNames() {
		String[] os = new String[skins.length];
		for (int i = 0; i < skins.length; i++) {
			os[i] = skins[i][0];
		}
		return os;
	}

	private Object[] getSkinDetails(Object obj) {
		for (int i = 0; i < skins.length; i++) {
			if (skins[i][0].equals(obj)) {
				Object[] os = new Object[skins[i].length - 1];
				for (int j = 0; j < os.length; j++) {
					os[j] = skins[i][j + 1];
				}
				return os;
			}
		}
		return new Object[] {};
	}

	public SkinManagerUI(String title) {
		super(title);
		initComponents();

		initSelf();
		setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SkinManagerUI.this.setVisible(false);
				skinMangerUtil.distorySkinManagerUI();
			}
		});
	}

	public void initSelf() {
		this.setVisible(true);
		setResizable(false);
		this.setLocation(MainUI.pointX >= 7 ? MainUI.pointX - 7 : MainUI.pointX, MainUI.pointY + Common.WINDOW_MINI_HEIGHT);
	}

	private void initComponents() {
		initElement();
		currentSkinJLabel.setText(Common.CURRENT_SINK);

		String[] skinNames = skinNames();
		sinkJComboBox.setModel(new DefaultComboBoxModel<String>(skinNames));
		sinkJComboBox.setSelectedIndex(skinNum - 1);
		sinkJComboBox.addActionListener(this);

		descJlabel.setText(Common.DESCRIPTION_WITH_COLOR);

		currentSkinDescJLabel.setText(skins[skinNum][2]);
		currentSkinDescJLabel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				try {
					// Access the URL
					String command = Common.ACCESS_URL_COMMAND + Common.SUBSTANCE_SKINS_PAGE + Common.POUND_SIGN + sinkJComboBox.getSelectedItem();
					logger.debug("access the URL command : " + command);
					Runtime.getRuntime().exec(command);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}
		});
		pageGourpLayout();
	}

	private void initElement() {
		currentSkinJLabel = new JLabel();
		sinkJComboBox = new JComboBox<String>();
		descJlabel = new JLabel();
		currentSkinDescJLabel = new JLabel();
		line = new JSeparator();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sinkJComboBox) {
			updateSkin();
		}
	}

	/**
	 * refresh skin
	 */
	public synchronized void updateSkin() {
		Object[] os = getSkinDetails(sinkJComboBox.getSelectedItem());
		String index = (String) os[0];
		String desc = (String) os[1];
		skinNum = Integer.valueOf(index);
		currentSkinDescJLabel.setText(desc);
		setJUI();
	}

	public void setSkinMangerUtil(SkinMangerUtil skinMangerUtil) {
		this.skinMangerUtil = skinMangerUtil;
	}

	/**
	 * If not necessary, please do not change
	 */
	private void pageGourpLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		horizontalGroupLayout(layout);
		verticalGroupLayout(layout);
		pack();
	}

	private void verticalGroupLayout(GroupLayout layout) {
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(40, 40, 40).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(currentSkinJLabel).addComponent(sinkJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(26, 26, 26).addComponent(line, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(descJlabel).addGap(18, 18, 18).addComponent(currentSkinDescJLabel).addContainerGap(47, Short.MAX_VALUE)));
	}

	private void horizontalGroupLayout(GroupLayout layout) {
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(currentSkinDescJLabel).addComponent(descJlabel).addGroup(layout.createSequentialGroup().addComponent(currentSkinJLabel).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(sinkJComboBox, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(line, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)));
	}
}
