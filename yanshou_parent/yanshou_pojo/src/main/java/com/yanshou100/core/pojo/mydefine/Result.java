package com.yanshou100.core.pojo.mydefine;

public class Result {
	private boolean flag;
	private String message;
	private String originFileName;
	private String newFileName;
	public Result() {
		super();
	}
	public Result(boolean flag, String message) {
		this.flag = flag;
		this.message = message;
	}
	
	public Result(boolean flag, String originFileName, String newFileName) {
		super();
		this.flag = flag;
		this.originFileName = originFileName;
		this.newFileName = newFileName;
	}
	public String getOriginFileName() {
		return originFileName;
	}
	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}
	public String getNewFileName() {
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
