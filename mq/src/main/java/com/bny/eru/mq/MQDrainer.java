package com.bny.eru.mq;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

public class MQDrainer {
	private MQQueue queue;
	private String outputFileLocation;
	private String queueName;
	private static Logger log = LoggerFactory.getLogger(MQDrainer.class);

	public MQDrainer(MQQueueManager queueManager, String queueName, String outputFileLocation) {
		this.outputFileLocation = outputFileLocation;
		this.queueName = queueName;
		try {
			this.queue = queueManager.accessQueue(queueName,
					CMQC.MQSO_CREATE | CMQC.MQSO_FAIL_IF_QUIESCING | CMQC.MQSO_MANAGED | CMQC.MQSO_NON_DURABLE);
		} catch (MQException e) {
			log.error("{}: Error while accessing queue. Reason code: {} error message:{}", queueName, e.getReason(),
					e.getMessage());
		}
	}

	public void drain() {
		MQGetMessageOptions options = new MQGetMessageOptions();
		options.matchOptions = CMQC.MQMO_NONE;
		options.waitInterval = 1000;
		options.options = CMQC.MQGMO_WAIT + CMQC.MQGMO_FAIL_IF_QUIESCING;
		MQMessage message = new MQMessage();
		boolean shouldDrain = true;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileLocation));) {
			while (shouldDrain) {
				try {
					queue.get(message, options);
					byte[] messageBytes = new byte[message.getMessageLength()];
					message.readFully(messageBytes);
					String fullMessage = new String(messageBytes);
					writer.append(fullMessage + "\n");
				} catch (MQException e) {
					if (e.reasonCode == CMQC.MQRC_NO_MSG_AVAILABLE) {
						log.warn("{}: No Message available in the queue now.", this.queueName);
					} else {
						log.error("{}: Error while reading queue. error message: {}", this.queueName, e.getMessage());
						shouldDrain = false;
					}
				} catch (IOException e) {
					log.error("{}: Interrupted due to I/O Exception: {}", this.queueName, e.getMessage());
					shouldDrain = false;
				}
			}
		} catch (IOException e1) {
			log.error("{}: Error accessing drain file {}. error message: {}", this.queueName, this.outputFileLocation,
					e1.getMessage());
		}
	}
}
