package com.bny.eru.mq;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

public class MQConnector {

	private static Logger log = LoggerFactory.getLogger(MQConnector.class);

	private String choice;
	private String queueManagerName;
	private String queueName;
	private String file;
	private Hashtable<String, Object> configuration;

	public MQConnector(String args[]) throws Exception {
		this.validateArguments(args);
		this.choice = isAlphaNumeric("choice", args[0]);
		this.configuration = this.getConfiguration(args[1], args[2], args[3], args[4], args[5]);
		this.queueManagerName = isAlphaNumeric("queueManagerName", args[6]);
		this.queueName = isAlphaNumeric("queueName", args[7]);
		this.file = args[8];
	}

	private Hashtable<String, Object> getConfiguration(String hostname, String port, String user, String password,
			String channel) throws Exception {
		Hashtable<String, Object> connection = new Hashtable<>();
		connection.put(CMQC.HOST_NAME_PROPERTY, isAlphaNumeric("hostname", hostname));
		connection.put(CMQC.PORT_PROPERTY, Integer.parseInt(isAlphaNumeric("port", port)));
		connection.put(CMQC.USER_ID_PROPERTY, isAlphaNumeric("user", user));
		connection.put(CMQC.PASSWORD_PROPERTY, isAlphaNumeric("password", password));
		connection.put(CMQC.CHANNEL_PROPERTY, isAlphaNumeric("channel", channel));
		return connection;
	}

	public static String isAlphaNumeric(String variableName, String s) throws Exception {
		if (s != null && s.matches("^[a-zA-Z0-9]*$")) {
			return s;
		} else {
			throw new Exception("variable " + variableName + " is not AlphaNumeric");
		}
	}

	private MQQueueManager getMQQueueManager() throws Exception {
		if (queueManagerName == null || queueManagerName.equals("")) {
			throw new Exception("QueueManager name cannot be blank or empty");
		}
		return new MQQueueManager(this.queueManagerName, this.configuration);
	}

	private void validateArguments(String args[]) throws Exception {
		if (args.length < 9) {
			log.error("Invalid no. of arguments. found:{}, required:9", args.length);
			throw new Exception("Invalid no. of arguments. required:9");
		}
	}

	public void exec() throws Exception {
		if (choice.equalsIgnoreCase("drain")) {
			new MQDrainer(this.getMQQueueManager(), this.queueName, this.file).drain();
		} else if (choice.equalsIgnoreCase("post")) {
			new MQMessagePoster(this.getMQQueueManager(), this.queueName, this.file).post();
		}
	}

	public static void main(String[] args) throws Exception {
		MQConnector connection = new MQConnector(args);
		connection.exec();
	}
}