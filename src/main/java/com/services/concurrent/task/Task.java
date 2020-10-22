package com.services.concurrent.task;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task to be executed
 * @author mitaly
 *
 */
public class Task implements Callable<Task> {
private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);
	@Override
	public Task call() {
		// Data to be processed
		List<Integer> list = IntStream.range(0, 10).boxed().collect(Collectors.toList());
		for(int data : list) {
			LOGGER.info("Consumed: {}", data);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOGGER.error("Thread ID: {} was interrupted. Stopping the task", Thread.currentThread().getId());
				return this;
			}
		}
		
		LOGGER.info("Task is completed. Thread ID: {}", Thread.currentThread().getId());
		return this;
	}

}
