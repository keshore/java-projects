package com.keshorecluster.test.model;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ResponseModel {

	private UUID reponseID;
	private Object response;

	public ResponseModel(Object response) {
		this.reponseID = UUID.randomUUID();
		this.response = response;
	}

	public static ResponseModel getResponse(Object response) {
		return new ResponseModel(response);
	}
}
