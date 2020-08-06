package com.bny.eru.mq;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

public class QueueMessages {

	private MQQueue queue;
	private static CommandLine commandLine = null;
	private static Logger log = LoggerFactory.getLogger(QueueMessages.class);

	public QueueMessages(MQQueueManager queueManager, String queueName) throws MQException {
		this.queue = queueManager.accessQueue(queueName, CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_OUTPUT);
	}

	static Option getInputFileOption() {
		return Option.builder("i").required(true).hasArg(true).longOpt("inputfile").desc("InputFile").build();
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
		options.addOption(getInputFileOption());

		try {
			commandLine = new DefaultParser().parse(options, args);
		} catch (ParseException parseException) {
			new HelpFormatter().printHelp("java -jar -cp com.bny.eru.mq.DrainMessages [options]", options);
			throw new Exception(parseException.getMessage());
		}

		new QueueMessages(MQOptions.getConnection(commandLine), commandLine.getOptionValue("queue"))
				.Queue(commandLine.getOptionValue("outputfile"));
	}

	public void Queue(String inputFileLocation) {
		try (BufferedReader br = new BufferedReader(new FileReader(inputFileLocation))) {
			MQPutMessageOptions options = new MQPutMessageOptions();
			MQMessage message = new MQMessage();
			String str = br.readLine();
			int count = 0;
			while (str != null) {
				count++;
				message.writeUTF(str);
				this.queue.put(message, options);
				str = br.readLine();
			}
			log.info("Queued {} messages", count);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (MQException e) {
			e.printStackTrace();
		}
	}

}
