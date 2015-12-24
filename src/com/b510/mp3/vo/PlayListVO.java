/**
 * 
 */
package com.b510.mp3.vo;

/**
 * @author Hongten
 * @created 24 Dec, 2015
 */
public class PlayListVO {
	private int id;
	private String name;
	private String time;
	private String path;
	private long size;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
