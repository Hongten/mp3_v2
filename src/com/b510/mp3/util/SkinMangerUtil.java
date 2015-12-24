/**
 * 
 */
package com.b510.mp3.util;

import org.apache.log4j.Logger;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.MainUI;
import com.b510.mp3.ui.SkinManagerUI;

/**
 * Skin manager util
 * @author Hongten
 * @created 24 Dec, 2015
 */
public class SkinMangerUtil extends MainUI {

	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(SkinMangerUtil.class);

	private static SkinManagerUI skinManagerUI;

	public SkinMangerUtil(String title) {
		super(title);
	}

	public void skin(MainUI mainUI) {
		if (null == skinManagerUI) {
			skinManagerUI = new SkinManagerUI(Common.SKIN);
			skinManagerUI.setSkinMangerUtil(SkinMangerUtil.this);
		} else {
			skinManagerUI.setVisible(true);
			skinManagerUI.setFocusable(true);
		}
	}

	public void distorySkinManagerUI() {
		if (null != skinManagerUI) {
			skinManagerUI = null;
		}
	}

}
