package calculator;

/**
* <p>
* Title: Calculator
* </p>
* 
* <p>
* Description: Creating a calculator program, which allows the user to input a infix expression.
* Then taking that infix expression and converting it into a postfix expression utilizing stacks and showing the user.
* Finally showing the user the output.
* </p>
* 
* <p>
* Copyright: Copyright (c) 2016
* </p>
* 
* @author R.Kasprzyk, F.Graham
* @version 2.0
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;


@SuppressWarnings("serial")
public class CalculatorFrame extends JFrame implements ActionListener  {
		JTextField jtfInfix = new JTextField(21); // for infix 
		JTextField jtfPostfix = new JTextField();  // for postfix
		JTextField result = new JTextField("0");   // for result
		
		JButton[][] calcButton = new JButton[4][5];
		
		JPanel calcPanel = new JPanel();	
		JPanel topPanel = new JPanel();    

		
		public CalculatorFrame(){
			String[][] buttonText = 
					new String[][]{{"7","8","9","\u00F7","C"},{"4","5","6","\u2217","B"},
					{"1","2","3","-","R"},{"0","(",")","+","="}};
					
			this.setTitle("CSC130 Calculator");
			this.setLayout(new BorderLayout(2,1));

			jtfInfix.setHorizontalAlignment(JTextField.RIGHT);
			jtfPostfix.setHorizontalAlignment(JTextField.RIGHT);
			result.setHorizontalAlignment(JTextField.RIGHT);
			jtfPostfix.setEnabled(false);
			result.setEnabled(false);
			//jtfInfix.setEditable(false); // hide text caret
			
			// set the font size to 34 for the text fields
			Font textFieldFont=new Font(jtfPostfix.getFont().getName(),jtfPostfix.getFont().getStyle(),24);
			jtfInfix.setFont(textFieldFont);
			jtfPostfix.setFont(textFieldFont);
			result.setFont(textFieldFont);
			
			topPanel.setLayout(new GridLayout(3,1));				
			topPanel.add(jtfInfix);		
			topPanel.add(jtfPostfix);
			topPanel.add(result);
			
			calcPanel.setLayout(new GridLayout(4,5,3,3));
			
			for (int i = 0; i < 4; i++) {
				for(int j = 0; j < 5; j++) {
					calcButton[i][j]= new JButton("" + buttonText[i][j]);
					calcButton[i][j].setForeground(Color.blue);
					calcButton[i][j].setFont(new Font("sansserif",Font.BOLD,42));
					calcButton[i][j].addActionListener(this);
					calcButton[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
					calcPanel.add(calcButton[i][j]);
				}
			}
			this.add(topPanel,BorderLayout.NORTH);
			this.add(calcPanel,BorderLayout.CENTER);
		}
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < 4; i++) {
				for(int j = 0; j < 5; j++) {				
					if(e.getSource() == calcButton[i][j]){
						// clear
						if(i==0 && j == 4){
							jtfInfix.setText(null);
							jtfPostfix.setText(null);
							result.setText("0");
						}
						// backspace
						else if(i==1 && j == 4){
							if(jtfInfix.getDocument().getLength()>0)
								try {
									jtfInfix.setText(jtfInfix.getText(0, jtfInfix.getDocument().getLength()-1));
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
							
						}
						// number or operator
						else if(j<4){
							jtfInfix.setText(jtfInfix.getText()
								+ calcButton[i][j].getText());
							}
						// = button pressed
						else if(i==3&&j==4){
							// erase contents of the postfix textfield
							jtfPostfix.setText(null);  
							// update the postfix textfield with the String returned  
							jtfPostfix.setText(infixToPostfix());
							// update the result textfield with the result of the computation
							result.setText("" + calculate());
						}
					}
				}
			}
	    }

		public String infixToPostfix(){	
			
			String token = null; // for the token
			
			String postFix = null; // the returned equation
			
			StackADT<String> stack = new Stack<String>(); 
			
			boolean done = true;
			
			boolean empty = true;
			
			int numele = 0; // number of elements
			
			int numoper = 0 ; // number of operations
			
			int brack = 0;// number of brackets "(" 
			
			int brack2 = 0; // number of brackets ")"
			
			String expression = jtfInfix.getText();
			
			String delims = "+-\u2217\u00F7() ";
			//break the string (in this case the equation) into "tokens"
			StringTokenizer strToken = new StringTokenizer(expression, delims, true);
			
			while(strToken.hasMoreTokens())
			{
				try
				{
				token = strToken.nextToken();
				
				//if the next token is a number, append it to the result
				if(!token.equals("+") && !token.equals("-") && !token.equals("\u2217") && !token.equals("\u00F7") && !token.equals("(") && !token.equals(")") ) 
				{
					numele += 1;// keep count of integers
					//if this is the first element
				
					
					 if (postFix == null)
					{
					postFix = token + " ";
					} 
					else //if there already exists elements 
					{
						postFix += token + " ";
					}
					
				}
				
				//if the next token is a left parenthesis, push it on the stack
				else if (token.equals("("))
				{
					brack += 1;
					
					stack.push(token);
					
				}
				
				//if the next token is a right parenthesis, Pop all the element off the stack and append them to the result without Pop the left parenthesis
				else if(token.equals(")"))
				{
					brack2 += 1;
					
					done = true;
					
					if (stack.isEmpty())
					{
						JOptionPane.showMessageDialog(new JFrame(), "Added one extra )","Dialog", JOptionPane.ERROR_MESSAGE);
						
						jtfInfix.setText(null);
						
						return postFix = null;
					}
					else if (numele == numoper)
					{
						JOptionPane.showMessageDialog(new JFrame(), "To many operations","Dialog", JOptionPane.ERROR_MESSAGE);
						
						jtfInfix.setText(null);
						
						return postFix = null;
					}
					while(done)
					{
						if (!stack.peek().equals("(") && !stack.isEmpty())
						{
							postFix += stack.pop() + " ";
						}
						else
						{
							stack.pop();
							
							done = false;
						}
					}
					if (!stack.isEmpty())
					{
						postFix += stack.pop();
					}
					
				}
				
				//if the next token is a operator
				else
				{	//check to see if the stack is empty
					if (stack.isEmpty() && postFix != null)
					{
						numoper += 1;// keep count of operations
						stack.push(token);
					}
					//check to see if an operation is put before anything 
					else if(stack.isEmpty() && postFix == null || postFix == null)
					{
						if (token.equals("-"))
						{
						
							postFix = token;
						}
						else
						{
						JOptionPane.showMessageDialog(new JFrame(), "Operation is before any integer","Dialog", JOptionPane.ERROR_MESSAGE);
						
						jtfInfix.setText(null);
						
						return postFix = null;
						}
						
					}
					
					else if( token.equals("-") && brack2 > brack || token.equals("-") && brack == 1 && postFix == null || token.equals("-") && numele == numoper)
					{
						postFix += token;
					} 
					//check the precedence of the top to the token
					else if(!token.equals(stack.peek())){
						
						numoper += 1; // keep count of operations
						//check if the top is a "*"
						if (token.equals( "+") && stack.peek().equals("\u2217") || token.equals( "-") && stack.peek().equals("\u2217") || token.equals( "\u00F7") && stack.peek().equals("\u2217"))
						{
							postFix += stack.pop() + " ";
							
							stack.push(token);
						}
						//or a "/"
						else if (token.equals( "+") && stack.peek().equals( "\u00F7") || token.equals( "-") && stack.peek().equals( "\u00F7"))
						{
							postFix += stack.pop() + " ";
							
							stack.push(token);
						}
						//or a "+"
						else if (token.equals( "-") && stack.peek().equals( "+") || token.equals( "-") && stack.peek().equals( "\u2217") || token.equals( "-") && stack.peek().equals( "\u00F7"))
						{
							postFix += stack.pop() + " ";
							
							stack.push(token);
						}
						// else if the precedence is okay 
						else if(token.equals("\u2217")&& stack.peek().equals("\u00F7"))
						{
							postFix += stack.pop() + " ";
							
							stack.push(token);
						}
						// or a "-"
						else
							stack.push(token);
					 }
					//if repeated operations are used then the program will shut down
					else if(token.equals(stack.peek()) && numele - numoper >= 0) 
					{
					
						JOptionPane.showMessageDialog(new JFrame(), "To many operations","Dialog", JOptionPane.ERROR_MESSAGE);
						
						jtfInfix.setText(null);
						
						return postFix = null;
						
					}
					else if(token.equals(stack.peek()))
							{ 
							numoper+=1;
							
							postFix += stack.pop() + " ";
						
							
							stack.push(token);
						
							}
					//if the stack and token match
					else
					{
						stack.push(token);//push it onto the stack
					}
		
			}
				}catch(StackEmptyException see)
				{
					
				}
			}
			
			if (numele - numoper == 0)//check to see if input is good
			{
				JOptionPane.showMessageDialog(new JFrame(), "Equation is not complete","Dialog", JOptionPane.ERROR_MESSAGE);
				
				jtfInfix.setText(null);
				
				return postFix = null;
			}
			
			else if (brack - brack2 != 0)
			{
				JOptionPane.showMessageDialog(new JFrame(), "No enough brackets","Dialog", JOptionPane.ERROR_MESSAGE);
				
				jtfInfix.setText(null);
				
				return postFix = null;
			}
			// remove any remaining elements from the stack 
			while(empty)
			{
				try
				{
					{
						if(!stack.peek().equals("("))
						{
						postFix += stack.pop() + " ";// pop it to the postFix
						}
						else
						{
							JOptionPane.showMessageDialog(new JFrame(), "Missing a )","Dialog", JOptionPane.ERROR_MESSAGE);
							
							jtfInfix.setText(null);
							
							return postFix = null;
						}
					}
				}catch(StackEmptyException see){
					
					empty = false;//if there is nothing in the stack get out of the loop
				}
			}
			
			
			return postFix;
		}
		public String calculate() {
			
			String result = "0";
			
			String token = null;
			
			String hold = null;
			
			StackADT<String> stack = new Stack<String>();
			
			boolean empty = true;
			
			double doub = 0;
			
			double right = 0;
			
			double left= 0;
			
			double ans = 0;
			
			int numele = 0; // number of elements
			
			int numoper = 0 ; // number of operations
			
			
			String postfixstr= jtfPostfix.getText();
			
			String delims = "+-\u2217\u00F7() ";
			
			StringTokenizer strToken = new StringTokenizer(postfixstr, delims, true);
			
			while(strToken.hasMoreTokens())
			{
				try{
				
					token = strToken.nextToken();
					
					
			
				if(token.equals(" "))// removing any spaces added from postFix
					{
					
					}
				//checking if the token is a operand or not	
				else if(!token.equals("+") && !token.equals("-") && !token.equals("\u2217") && !token.equals("\u00F7"))
				{
					numele += 1;// keep count of elements
				
					if (hold != null)
					{
						token = hold + token;
						
						hold = null;
						
					
						
						stack.push(token);
					}
					else
					{
						stack.push(token);//push the number onto the stack
					}	
				} 
				
				else if ((token.equals("-") && stack.isEmpty()) || (token.equals("-") && numele - numoper == 1)) //help with negative numbers
				{
				
					hold = token;
				}
				
				//the token is a operator
				else
				{
					numoper += 1; // keep count of operations
				
					right = Double.parseDouble(stack.pop());//pop the top operand, move it to the right of the equation and change the string to an integer
					
					left = Double.parseDouble(stack.pop());//pop the next top operand, move it to the left of the equation and change the string to an integer
					
					if (token.equals("+"))// if the token is a "+"
					{
						ans = left + right;
					}
					else if (token.equals("-"))//if the token is a "-"
					{
						ans = left - right;
					}
					else if(token.equals("\u2217"))//if the token is a "*"
					{
						ans = left * right;
					}
					else//is the token is a "/"
					{
						ans = left / right;
					}
				stack.push(Double.toString(ans));//push the finished operand back into the stack
					
				}
				}catch(StackEmptyException see)
				{
					
				}
			} 
			while(empty)//pop any remaining element in the stack and store it to the results
			{
				try
				{ 
					 doub = Double.parseDouble(stack.pop());
					 
					if(doub % 1 == 0)
					{ 
						result = Integer.toString((int)doub);
					}
					
					else
						result = Double.toString(doub);
					
				}catch(StackEmptyException see)
				{
					empty = false;
				}
			}
			return result;
		}
		
		static final int MAX_WIDTH = 398, MAX_HEIGHT = 440;
		
		public static void main(String arg[]){
			JFrame frame = new CalculatorFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(MAX_WIDTH,MAX_HEIGHT);	
			frame.setBackground(Color.white);		
			frame.setResizable(false);				
			frame.setVisible(true);
		}
	}


