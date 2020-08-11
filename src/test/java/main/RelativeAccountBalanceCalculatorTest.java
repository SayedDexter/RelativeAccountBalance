package main;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import constant.Constant;
import exception.RelativeAccountBalanceCalculatorException;
import model.InputCriteria;
import utility.CSVReader;

public class RelativeAccountBalanceCalculatorTest {

	private RelativeAccountBalanceCalculator relativeAccountBalanceCalculator;
	private CSVReader csvReader;
	
	@Before
	public void setUp() {
		relativeAccountBalanceCalculator = new RelativeAccountBalanceCalculator();
		csvReader = new CSVReader();
	}

	@Test
	public void testRelativeBalanceWhenReversalIsAvaialble() throws Exception {
		InputCriteria inputCriteria = new InputCriteria();
		inputCriteria.setAccountId("ACC334455");
		inputCriteria.setFromStartingDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse("20/10/2018 12:00:00"));
		inputCriteria.setToEndingDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse("20/10/2018 19:00:00"));
		BigDecimal result = new BigDecimal(-25);
		Assert.assertEquals(result, relativeAccountBalanceCalculator.calculateRelativeBalance(inputCriteria, csvReader.readCSV()).getRelativeAmount());
		Assert.assertEquals(1, relativeAccountBalanceCalculator.calculateRelativeBalance(inputCriteria, csvReader.readCSV()).getCount());
	}
	
	@Test(expected=RelativeAccountBalanceCalculatorException.class)
	public void testRelativeBalanceWhenInvalidAccountIsProvided() throws Exception {
		InputCriteria inputCriteria = new InputCriteria();
		inputCriteria.setAccountId("ACC3344551");
		inputCriteria.setFromStartingDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse("20/10/2018 12:00:00"));
		inputCriteria.setToEndingDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse("20/10/2018 19:00:00"));
		relativeAccountBalanceCalculator.calculateRelativeBalance(inputCriteria, csvReader.readCSV());
	}
	
	@Test
	public void testRelativeAccountBalanceCalculatorWhenRevarsalIsNotAvailable() throws Exception {
		InputCriteria inputCriteria = new InputCriteria();
		inputCriteria.setAccountId("ACC998877");
		BigDecimal result = new BigDecimal(-5);
		inputCriteria.setFromStartingDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse("20/10/2018 12:00:00"));
		inputCriteria.setToEndingDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse("20/10/2018 19:00:00"));
		Assert.assertEquals(1, relativeAccountBalanceCalculator.calculateRelativeBalance(inputCriteria, csvReader.readCSV()).getCount());
		Assert.assertEquals(result, relativeAccountBalanceCalculator.calculateRelativeBalance(inputCriteria, csvReader.readCSV()).getRelativeAmount());
	}
	
	@Test
	public void testRelativeAccountBalanceCalculatorWhenOutOfDateRangeInputProvided() throws Exception {
		InputCriteria inputCriteria = new InputCriteria();
		inputCriteria.setAccountId("ACC998877");
		BigDecimal result = new BigDecimal(0);
		inputCriteria.setFromStartingDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse("21/10/2018 12:00:00"));
		inputCriteria.setToEndingDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse("21/1/2018 19:00:00"));
		Assert.assertEquals(result, relativeAccountBalanceCalculator.calculateRelativeBalance(inputCriteria, csvReader.readCSV()).getRelativeAmount());
		Assert.assertEquals(0, relativeAccountBalanceCalculator.calculateRelativeBalance(inputCriteria, csvReader.readCSV()).getCount());
	}
	
}
