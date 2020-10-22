package com.services.concurrent.util;

/**
 * Represent the different states of task along with a message
 * @author mitaly
 *
 */
public enum TaskStatus {
	START_NEW("New task started"),
	CANCEL_LAST("Cancelling the last executing task"),
	CANCEL_AND_SUBMIT_NEW("Last request stopped. New task started"),
	DISRUPTED("Process was disrupted by another request"),
	COMPLETED("Last process was completed successfully"),
	NO_TASKS("No tasks found");
	
	private String message;
	
	private TaskStatus(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
