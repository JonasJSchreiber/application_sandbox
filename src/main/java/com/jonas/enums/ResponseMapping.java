package com.jonas.enums;

import com.jonas.gson.GetAppointmentConfirmationResultsInfo;

public class ResponseMapping {
	private boolean applicable;
	private GetAppointmentConfirmationResultsInfo resultInfo;
	private WritebackStatus writebackStatus;
	public boolean isApplicable() {
		return applicable;
	}
	public void setApplicable(boolean applicable) {
		this.applicable = applicable;
	}
	public GetAppointmentConfirmationResultsInfo getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(GetAppointmentConfirmationResultsInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
	public WritebackStatus getWritebackStatus() {
		return writebackStatus;
	}
	public void setWritebackStatus(WritebackStatus writebackStatus) {
		this.writebackStatus = writebackStatus;
	}
}
