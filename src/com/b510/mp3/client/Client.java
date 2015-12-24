/**
 * 
 */
package com.b510.mp3.client;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.Mp3UI;

/**
 * The entrance for the this application.<br>
 * @author Hongten
 * @created Jul 28, 2014
 */
public class Client {

	public static void main(String[] args) {
		Mp3UI mp3ui = new Mp3UI(Common.MP3_PLAYER);
		mp3ui.init();
	}
}
