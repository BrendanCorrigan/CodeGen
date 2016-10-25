package ${bean.packageName}.dao;

/**
 * NotFoundException exception. This exception will be thrown from Dao object if
 * load, update or delete for one object fails to find the correct row.
 *
 */
public class NotFoundException extends Exception {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 1694716306477780784L;

	/**
	 * Constructor for NotFoundException.
	 * 
	 * @param msg
	 *            The message to be displayed for the Exception
	 */
	public NotFoundException(String msg) {
		super(msg);
	}

}