package com.keshorecluster.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.keshorecluster.test.model.ResponseModel;

@RestController(value = "/numerology")
public class NumberologyController {

	Map<String, Object> greetingMessage = new HashMap<>();
	private static int count = 0;

	private ResponseModel resp(Map<String, Object> greetingMessage) throws Exception {
		if (count++ % 2 == 0) {
			throw new Exception("Oops, got exception:  " + greetingMessage.get("message"));
		}
		return ResponseModel.getResponse(greetingMessage);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseModel greetingText() throws Exception {
		greetingMessage.put("message", "Hello World!");
		return resp(greetingMessage);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseModel greetingText(@PathVariable String name) throws Exception {
		greetingMessage.put("message", "Hello " + name + " !");
		return resp(greetingMessage);
	}

}
