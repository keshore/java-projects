package com.keshorecluster.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.keshorecluster.test.model.MyException;
import com.keshorecluster.test.model.ResponseModel;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class ExcelptionHandlerController {

	private static ResponseModel logExceptionAndReturnResponseModel(Exception e) {
		ResponseModel model = ResponseModel.getResponse(e.getMessage());
		log.error(e);
		return model;
	}

	@ExceptionHandler(MyException.class)
	public ResponseEntity<ResponseModel> globalExceptionHandler(final MyException e) {
		ResponseModel resp = logExceptionAndReturnResponseModel(e);
		return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseModel> globalExceptionHandler(final Exception e) {
		ResponseModel resp = logExceptionAndReturnResponseModel(e);
		return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
