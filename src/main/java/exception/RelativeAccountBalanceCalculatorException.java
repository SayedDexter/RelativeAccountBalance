package exception;

/**
 * @author Sayed.Hussain
 *
 */
public class RelativeAccountBalanceCalculatorException extends Exception {

	/**
	 * Serial Version Id
	 */
	private static final long serialVersionUID = 1L;

	public RelativeAccountBalanceCalculatorException(String errorMessage) {
		super(errorMessage);
	}
	
	public RelativeAccountBalanceCalculatorException(String errorMessage, Throwable throwable) {
		super(errorMessage,throwable);
	}
}
