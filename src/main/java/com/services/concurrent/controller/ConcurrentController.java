package com.services.concurrent.controller;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.concurrent.task.Task;
import com.services.concurrent.util.TaskStatus;

@RestController
@RequestMapping("/task")
public class ConcurrentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentController.class);

	private ExecutorService executor = Executors.newFixedThreadPool(1);
	private Future<Task> future;

	/**
	 * Starts a new task. Destroys the last task(if any)
	 * 
	 * @return Message
	 */
	@GetMapping("/start")
	public String start() {
		if (Objects.isNull(future) || future.isDone()) {
			future = executor.submit(new Task());

			LOGGER.info(TaskStatus.START_NEW.getMessage());
			return TaskStatus.START_NEW.getMessage();
		}

		future.cancel(true);
		LOGGER.info(TaskStatus.CANCEL_LAST.getMessage());

		future = executor.submit(new Task());
		LOGGER.info(TaskStatus.START_NEW.getMessage());

		return TaskStatus.CANCEL_AND_SUBMIT_NEW.getMessage();
	}

	/**
	 * Stops the last executing task(if any). Returns message which represents more
	 * about the last executing task
	 * 
	 * @return Message
	 */
	@GetMapping("/stop")
	public String stop() {
		if (Objects.isNull(future)) {
			LOGGER.info(TaskStatus.NO_TASKS.getMessage());
			return TaskStatus.NO_TASKS.getMessage();
		} else if (future.isCancelled()) {
			LOGGER.info(TaskStatus.DISRUPTED.getMessage());
			return TaskStatus.DISRUPTED.getMessage();
		} else if (future.isDone()) {
			LOGGER.info(TaskStatus.COMPLETED.getMessage());
			return TaskStatus.COMPLETED.getMessage();
		}

		future.cancel(true);
		LOGGER.info(TaskStatus.CANCEL_LAST.getMessage());

		return TaskStatus.CANCEL_LAST.getMessage();
	}
}