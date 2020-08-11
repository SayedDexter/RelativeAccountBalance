package model;

import java.util.Date;

public class InputCriteria {

	private String accountId;
	private Date fromStartingDate;
	private Date toEndingDate;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Date getFromStartingDate() {
		return fromStartingDate;
	}

	public void setFromStartingDate(Date fromStartingDate) {
		this.fromStartingDate = fromStartingDate;
	}

	public Date getToEndingDate() {
		return toEndingDate;
	}

	public void setToEndingDate(Date toEndingDate) {
		this.toEndingDate = toEndingDate;
	}

}
