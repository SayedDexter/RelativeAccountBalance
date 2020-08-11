package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import constant.Constant;
import exception.RelativeAccountBalanceCalculatorException;
import model.Transaction;
import model.TransactionType;

public class CSVReader {

	public List<Transaction> readCSV(String path) throws RelativeAccountBalanceCalculatorException {
		List<Transaction> transactionList = null;
		InputStreamReader input;
		try {
			System.out.println("File location received as:"+path);
			File file = new File(path);
			input = new InputStreamReader(new FileInputStream(file));
		
			
			CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(input);
			transactionList = new ArrayList<Transaction>();
			for (CSVRecord record : csvParser) {
				Transaction transaction = new Transaction();
				transaction.setTransactionId(record.get(0));
				transaction.setFromAccountId(record.get(1));
				transaction.setToAccountId(record.get(2));

				Date createAt = new SimpleDateFormat(Constant.DATE_FORMAT).parse(record.get(3));
				transaction.setCreateAt(createAt);
				BigDecimal amount = new BigDecimal(record.get(4));
				transaction.setAmount(amount);
				TransactionType transactionType = TransactionType.valueOf(record.get(5));
				transaction.setTransactionType(transactionType);
				transaction.setRelatedTransaction(record.get(6));
				transactionList.add(transaction);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RelativeAccountBalanceCalculatorException("Error while getting input file ", e);
		} catch (ParseException e) {
			throw new RelativeAccountBalanceCalculatorException("Error while doing date conversion ", e);
		}
		return transactionList;
	}

}
