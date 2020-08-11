package main;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import constant.Constant;
import exception.RelativeAccountBalanceCalculatorException;
import model.InputCriteria;
import model.Output;
import model.Transaction;
import model.TransactionType;
import utility.CSVReader;

public class RelativeAccountBalanceCalculator {

	public static void main(String ar[]) throws RelativeAccountBalanceCalculatorException {
		RelativeAccountBalanceCalculator relativeAccountBalanceCalculator = new RelativeAccountBalanceCalculator();
		CSVReader csvReader = new CSVReader();
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter input file location");
		String inputFileLocation = in.nextLine();
		System.out.println("Please enter account number ");
		String accountNumber = in.nextLine();
		System.out.println("Please enter start date and time");
		String fromDuration = in.nextLine();
		System.out.println("Please enter end date and time");
		String toDuration = in.nextLine();

		InputCriteria inputCritea = new InputCriteria();
		inputCritea.setAccountId(accountNumber);
		try {
			inputCritea.setFromStartingDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse(fromDuration));
			inputCritea.setToEndingDate(new SimpleDateFormat(Constant.DATE_FORMAT).parse(toDuration));
			Output balance = relativeAccountBalanceCalculator.calculateRelativeBalance(inputCritea,
					csvReader.readCSV(inputFileLocation));
			System.out.println("Relative balance for the period is : " + balance.getRelativeAmount());
			System.out.println("Number of transactions included is : " + balance.getCount());
		} catch (ParseException parseException) {
			parseException.printStackTrace();
			throw new RelativeAccountBalanceCalculatorException("Error while date parsing :", parseException);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RelativeAccountBalanceCalculatorException("Invalid date provided:", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RelativeAccountBalanceCalculatorException(e.getMessage());
		} finally {
			in.close();
		}
	}

	public Output calculateRelativeBalance(InputCriteria inputCriteria, List<Transaction> data) throws Exception {
		Map<String, List<Transaction>> groupedMap = data.stream()
				.collect(Collectors.groupingBy(Transaction::getFromAccountId));

		if (groupedMap.containsKey(inputCriteria.getAccountId())) {
			// List contains all account detail
			List<Transaction> filteredList = groupedMap.get(inputCriteria.getAccountId());

			// Map consist of Key - Transaction Id and list of all accounts
			Map<String, List<Transaction>> filterdMap = groupedMap.get(inputCriteria.getAccountId()).stream()
					.collect(Collectors.groupingBy(Transaction::getTransactionId));

			// Map consist of Key - Transaction Id of reversal transaction and
			// list of accounts
			Map<String, List<Transaction>> reversalList = filteredList.stream()
					.filter(predicate -> TransactionType.REVERSAL.equals(predicate.getTransactionType()))
					.collect(Collectors.groupingBy(Transaction::getRelatedTransaction));

			if (!reversalList.isEmpty()) {
				// transactionId,List<transaction>
				filterdMap.entrySet().removeIf(predicate -> reversalList.containsKey(predicate.getKey()));
				List<Transaction> finalList = new ArrayList<>();
				filterdMap.values().forEach(e -> {
					finalList.add(e.get(0));
				});
				return returnOutPut(finalList, inputCriteria);
			} else {
				// Condition will work when no reversal is passed and will give
				// output
				return returnOutPut(filteredList, inputCriteria);
			}
		} else {
			throw new RelativeAccountBalanceCalculatorException("Provided account id does not exist");
		}
	}

	/**
	 * Received input and apply date filter over it and returns Output
	 * 
	 * @param finalList
	 * @param inputCriteria
	 * @return {@link Output}
	 */
	private Output returnOutPut(List<Transaction> finalList, InputCriteria inputCriteria) {
		List<Transaction> dateFilteredList = finalList.stream()
				.filter(transaction -> transaction.getCreateAt().after(inputCriteria.getFromStartingDate())
						&& transaction.getCreateAt().before(inputCriteria.getToEndingDate()))
				.collect(Collectors.toList());
		BigDecimal total = new BigDecimal(0.0);
		for (Transaction value : dateFilteredList) {
			total = total.subtract(value.getAmount());
		}
		Output out = new Output();
		out.setCount(dateFilteredList.size());
		out.setRelativeAmount(total);
		return out;
	}

}
