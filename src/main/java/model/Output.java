package model;

import java.math.BigDecimal;

public class Output {

	private BigDecimal relativeAmount;
	public BigDecimal getRelativeAmount() {
		return relativeAmount;
	}
	public void setRelativeAmount(BigDecimal relativeAmount) {
		this.relativeAmount = relativeAmount;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	private int count;
	
}
