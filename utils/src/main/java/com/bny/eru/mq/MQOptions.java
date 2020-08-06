package com.bny.eru.mq;

import java.util.Hashtable;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

public class MQOptions {

	static MQQueueManager getConnection(CommandLine arguments) throws MQException {
		Hashtable<String, Object> config = new Hashtable<String, Object>();
		config.put(CMQC.HOST_NAME_PROPERTY, arguments.getOptionValue("host"));
		config.put(CMQC.PORT_PROPERTY, arguments.getOptionValue("port"));
		config.put(CMQC.USER_ID_PROPERTY, arguments.getOptionValue("user"));
		config.put(CMQC.PASSWORD_PROPERTY, arguments.getOptionValue("pass"));
		config.put(CMQC.CHANNEL_PROPERTY, arguments.getOptionValue("channel"));
		return new MQQueueManager(arguments.getOptionValue("manager"), config);
	}

	static Option getHostOption() {
		return Option.builder("h").required(true).hasArg(true).longOpt("host").desc("IBM MQ Hostname").build();
	}

	static Option getPortOption() {
		return Option.builder("P").required(true).hasArg(true).longOpt("port").desc("IBM MQ Port").build();
	}

	static Option getUsernameOption() {
		return Option.builder("u").required(true).hasArg(true).longOpt("user").desc("Client Username").build();
	}

	static Option getPasswordOption() {
		return Option.builder("p").required(true).hasArg(true).longOpt("pass").desc("Client Password").build();
	}

	static Option getChannelOption() {
		return Option.builder("c").required(true).hasArg(true).longOpt("channel").desc("IBM MQ Channel").build();
	}

	static Option getManagerOption() {
		return Option.builder("m").required(true).hasArg(true).longOpt("manager").desc("IBM MQ Queue Manager").build();
	}

	static Option getQueueOption() {
		return Option.builder("q").required(true).hasArg(true).longOpt("queue").desc("IBM MQ Queue").build();
	}

}
