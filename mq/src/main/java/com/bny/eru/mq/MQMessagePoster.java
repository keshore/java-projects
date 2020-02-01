package com.bny.eru.mq;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

public class MQMessagePoster {
	private MQQueue queue;
	private String inputFileLocation;
	private String queueName;
	private static Logger log = LoggerFactory.getLogger(MQMessagePoster.class);

	public MQMessagePoster(MQQueueManager queueManager, String queueName, String inputFileLocation) {
		this.inputFileLocation = inputFileLocation;
		this.queueName = queueName;
		try {
			this.queue = queueManager.accessQueue(queueName, CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_OUTPUT);
		} catch (MQException e) {
			log.error("{}: Error while accessing queue. Reason code: {} error message:{}", queueName, e.getReason(),
					e.getMessage());
		}
	}

	public void post() {
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFileLocation));) {
			int counter = 0;
			String line = reader.readLine();
			while (line != null) {
				MQMessage message = new MQMessage();
				message.writeUTF(line);
				this.queue.put(message);
				counter++;
				line = reader.readLine();
			}
			log.info("{}: Written {} messages to MQ", this.queueName, counter);
		} catch (IOException e1) {
			log.error("{}: Error accessing drain file {}. error message: {}", this.queueName, this.inputFileLocation,
					e1.getMessage());
		} catch (MQException e) {
			log.error("{}: Error pushing message to MQ. Reason code:{}. error message: {}", this.queueName,
					e.getReason(), e.getMessage());
		}
	}
}
