package calculator;

/**
* <p>
* Title: The Stack Empty Exception class  
* </p>
* 
* <p>
* Description: It is an exception thrown when the stack is empty
* </p>
* 
* <p>
* Copyright: Copyright (c) 2016
* </p>
* 
* @author R.Kasprzyk
* @version 0.9
*/
public class StackEmptyException extends Exception {
	/**
	 * Constructs a new StackEmptyException with a default error message string.
	 */
	public StackEmptyException(){
		super("Exception : Stack is empty");
	}
	/**
	 * Constructs a new StackEmptyException with the parameter as the error message string.
	 * @param msg The string passed as the error message string.
	 */
	public StackEmptyException(String msg){
		super(msg);
	}
}
