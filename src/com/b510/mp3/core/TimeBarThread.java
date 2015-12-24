/**
 * 
 */
package com.b510.mp3.core;

import com.b510.mp3.ui.MainUI;

/**
 * Time bar thread
 * @author Hongten
 * @created 22 Dec, 2015
 */
public class TimeBarThread implements Runnable {

	private volatile int current;
	private int amount;
	public boolean stop; 

	public TimeBarThread(int amount) {
		current = 0;
		this.amount = amount;
	}
	
	public void stopTimeBar(){
		stop = true;
	}
	
	public int getAmount() {
		return amount;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public void run() {
		while (!stop && current <= amount) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			if(!MainUI.PAUSE_STATUS){
				current = current + 1;
			}
		}
	}
}
