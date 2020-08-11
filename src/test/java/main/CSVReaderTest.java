package main;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Transaction;
import utility.CSVReader;

public class CSVReaderTest {

	private CSVReader csvReader = null;

	@Before
	public void setUp() {
		csvReader = new CSVReader();
	}

	@Test
	public void testCSVFileRead() throws Exception {
		List<Transaction> listOfTransaction = csvReader.readCSV(this.getClass().getResource("/input.csv").getPath());
		Assert.assertEquals(5, listOfTransaction.size());
	}

}
