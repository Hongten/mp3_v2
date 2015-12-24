/**
 * 
 */
package com.b510.mp3.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.mp3transform.Decoder;

/**
 * @author Hongten
 * @created 21 Dec, 2015
 */
public class PlayerThread implements Runnable {

	static Logger logger = Logger.getLogger(PlayerThread.class);

	private Decoder decoder = new Decoder();
	private File currentFile;
	private Thread thread;
	private boolean stop;

	public void stopPlaying() {
		stop = true;
		decoder.stop();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean pause() {
		return decoder.pause();
	}

	public static PlayerThread startPlaying(File file) {
		PlayerThread t = new PlayerThread();
		t.currentFile = file;
		Thread thread = new Thread(t);
		thread.setName(t.getClass().getName());
		thread.setPriority(Thread.MAX_PRIORITY);
		t.thread = thread;
		thread.start();
		return t;
	}

	public void run() {
		try {
			while (!stop) {
				play(currentFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void play(File file) throws IOException {
		stop = false;
		if (!file.getName().endsWith(".mp3")) {
			return;
		}
		logger.debug("playing : " + file.getName());
		FileInputStream in = new FileInputStream(file);
		BufferedInputStream bin = new BufferedInputStream(in, 128 * 1024);
		decoder.play(file.getName(), bin);
	}

	public void playNext() {
		decoder.stop();
		currentFile = null;
	}
}
