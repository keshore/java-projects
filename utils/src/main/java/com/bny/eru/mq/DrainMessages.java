package com.bny.eru.mq;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

public class DrainMessages {
	private MQQueue queue;
	private BufferedWriter writer;
	private MQMessage message;
	private MQGetMessageOptions options;
	private static Logger log = LoggerFactory.getLogger(DrainMessages.class);
	private static CommandLine commandLine = null;

	public DrainMessages(MQQueueManager queueManager, String queueName) throws MQException {
		this.queue = queueManager.accessQueue(queueName,
				CMQC.MQSO_CREATE | CMQC.MQSO_FAIL_IF_QUIESCING | CMQC.MQSO_MANAGED | CMQC.MQSO_NON_DURABLE);
		options = new MQGetMessageOptions();
		options.matchOptions = CMQC.MQMO_NONE;
		options.waitInterval = 10000;
		options.options = CMQC.MQGMO_WAIT + CMQC.MQGMO_FAIL_IF_QUIESCING;
		message = new MQMessage();
	}

	void drain(String OutputFileLocation) throws InterruptedException {
		boolean shouldDrain = true;
		while (shouldDrain) {
			try {
				this.queue.get(message, options);
				String str = message.readLine();
				writer = new BufferedWriter(new FileWriter(OutputFileLocation));
				writer.append("\n" + str);
			} catch (MQException e) {
				if (e.reasonCode == CMQC.MQRC_NO_MSG_AVAILABLE) {
					log.warn("No Messages available in the queue now. Re-trying in 2 seconds");
					Thread.sleep(2000);
				} else {
					e.printStackTrace();
					shouldDrain = false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static Option getOutputFileOption() {
		return Option.builder("o").required(true).hasArg(true).longOpt("outputfile").desc("OutputFile").build();
	}

	public static void main(String[] args) throws Exception {
		Options options = new Options();

		options.addOption(MQOptions.getHostOption());
		options.addOption(MQOptions.getPortOption());
		options.addOption(MQOptions.getUsernameOption());
		options.addOption(MQOptions.getPasswordOption());
		options.addOption(MQOptions.getChannelOption());
		options.addOption(MQOptions.getManagerOption());
		options.addOption(MQOptions.getQueueOption());
		options.addOption(getOutputFileOption());

		try {
			commandLine = new DefaultParser().parse(options, args);
		} catch (ParseException parseException) {
			new HelpFormatter().printHelp("java -jar -cp com.bny.eru.mq.DrainMessages [options]", options);
			throw new Exception(parseException.getMessage());
		}

		new DrainMessages(MQOptions.getConnection(commandLine), commandLine.getOptionValue("queue"))
				.drain(commandLine.getOptionValue("outputfile"));

	}
}
