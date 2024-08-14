package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//These stacks are used for back button 
			CursorStack<String> topLBStack = new CursorStack<>(100);// Stack for files loaded paths to display the path on [top of GUI]
			CursorStack<String> fileTAStack = new CursorStack<>(100);// Stack for the opening loaded file path to allow you back to the previous file 
			CursorStack<String> equationsTAStack = new CursorStack<>(100);// Stack for the text area that contain equations from the loaded file
			CursorStack<String> filesTAStack = new CursorStack<>(100);// Stack for the text area that contain file paths from the loaded file
			CursorStack<String> hyperLStack = new CursorStack<>(100);// Stack for files paths from the loaded file

			
			// GUI main border
			BorderPane root = new BorderPane();

			//Links to another files 
			Hyperlink hLink = new Hyperlink();

			//Back button allow you to back to the previous file and some styling 
			Button backBT = new Button("Back");
			backBT.setFont(Font.font(15));
			backBT.setDisable(true);
			Label fileLB = new Label();
			Button loadBT = new Button("Load");
			loadBT.setFont(Font.font(15));

			//This HBox contain the top of GUI : back and load button and file of the path 
			HBox b_f_lHB = new HBox();
			b_f_lHB.getChildren().addAll(backBT, fileLB, loadBT);
			b_f_lHB.setAlignment(Pos.TOP_CENTER);
			b_f_lHB.setPadding(new Insets(15));
			b_f_lHB.setSpacing(15);
			root.setTop(b_f_lHB);

			//Text areas that display the equations and file paths in the loaded file  
			TextArea equationsTA = new TextArea();
			TextArea filesTA = new TextArea();

			//Styling
			GridPane gp = new GridPane();
			gp.setAlignment(Pos.TOP_CENTER);
			Label equationsLB = new Label("Equations");
			equationsLB.setFont(Font.font(15));
			Font myFont = Font.font(null, FontWeight.BOLD, equationsLB.getFont().getSize());
			equationsLB.setFont(myFont);
			equationsLB.setPadding(new Insets(15));
			gp.add(equationsLB, 0, 0);
			root.setCenter(gp);
			gp.add(equationsTA, 0, 1);
			equationsTA.setBackground(null);

			Label filesLB = new Label("Files");
			filesLB.setFont(Font.font(15));
			Font myFont2 = Font.font(null, FontWeight.BOLD, filesLB.getFont().getSize());
			filesLB.setFont(myFont);
			filesLB.setPadding(new Insets(15));
			gp.add(filesLB, 0, 3);
			gp.add(filesTA, 0, 4);
			gp.add(hLink, 0, 4);
			filesTA.setBackground(null);
			filesTA.setPadding(new Insets(0, 0, 70, 0));

			//load button , that open file chooser to select any file you want  
			loadBT.setOnAction(e -> {
				backBT.setDisable(false);
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text File", "*242"));
				File f1 = fileChooser.showOpenDialog(primaryStage);
				
				//Read the selected file 
				Scanner sc = null;
				if (f1.exists()) {
					try {
						sc = new Scanner(f1);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					String str = "", equation = "";
					while (sc.hasNext()) {
						str += sc.next() + " ";
					}
					//try the method below, the method are described below  
					fileLB.setText(String.valueOf(f1));
					if (isValidFile(str)) {
						String[] op = result(str).split(",");
						for (int i = 0; i < op.length; i++) {
							if (toPost(op[i]).equals("Un Balanced equation")) {
								equation += op[i] + " [Un Balanced equation]" + "\n";
								continue;
							}
							if (toPost(op[i]).equals("invalid")) {
								equation += op[i] + " [invalid]" + "\n";
								continue;
							}
							if (!op[i].equals(" "))
								if(evaluatePost(toPost(op[i]))!=0) {
								equation += op[i] + " --> " + toPost(op[i]) + " => " + evaluatePost(toPost(op[i]))
										+ "\n";}
								else {
									equation += op[i] + " [missing operator] "+"\n";
								}
						}
						equationsTA.setText(equation);
					} else {
						equationsTA.setText("INVALID File : tags problem");
					}
					//push in stacks the info that used to display the info in GUI and allow the user to get back for previous file 
					hLink.setText(readFiles(str));
					topLBStack.push(fileLB.getText());
					filesTAStack.push(hLink.getText());
					equationsTAStack.push(equationsTA.getText());
					hyperLStack.push(readFiles(str));
				}

			});

			
			//back button : if you press back the info will pop from the stack and dynamically will the GUI update  
			backBT.setOnAction(e -> {
				topLBStack.pop();
				fileTAStack.pop();
				equationsTAStack.pop();
				hyperLStack.pop();
				   
				if (topLBStack.isEmpty() && fileTAStack.isEmpty()) {
					/* 
					 * this if statement check if you press in back and you reach that there is no data in stacks then
					 * the stacks is Empty and will display nothing in the GUI
					*/
					filesTA.clear();
					equationsTA.clear();
					backBT.setDisable(true);
				}
				fileLB.setText(topLBStack.peek());
				equationsTA.setText(equationsTAStack.peek());
				hLink.setText(hyperLStack.peek());
				filesTA.setText(fileTAStack.peek());
			});

			Scene scene = new Scene(root, 600, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Mohammad's Project");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String result(String file) {
		String res = readEquations(file);
		String all = "";
		String[] eqArray = res.split("\n");
		for (int m = 0; m < eqArray.length; m++) {
			all += eqArray[m] + ",";
		}
		return all;
	}

	public static String readEquations(String line) { // method to read Equations from files
		String s = "";
		String[] token = line.split("<equation>|\\</equation>");
		for (int i = 0; i < token.length - 2; i++) {
			s += token[i + 1] + "\n";
		}
		return s;
	}

	public static String readFiles(String line) { // method to read files path from files
		String s = "";
		String[] token = line.split(" ");
		for (int m = 0; m < token.length; m++) {
			if (token[m].equals("<file>")) {
				s += token[m + 1] + "\n";
			}
		}
		return s;
	}

	 public static double evaluatePost(String s) { //this method get the post fix equation from toPost(String s) method and calculate it
	        CursorStack<Integer> stack = new CursorStack<>(10);
	        String[] variable = s.trim().split(" ");
	        int operators = 0, operands=0;
	        for(int i=0; i < variable.length; i++) {

	            if (!(variable[i].equals("+") || variable[i].equals("-") || variable[i].equals("*") || variable[i].equals("/"))) {
	                stack.push(Integer.valueOf(variable[i]));
	                operators++;
	            }

	            else {

	                operands++;
	                if (operands >= operators)
	                    return 0;

	                else {
	                    int operandTwo = stack.pop();
	                    int operandOne = stack.pop();

	                    switch (variable[i]) {
	                        case "+":
	                            stack.push(operandTwo + operandOne);
	                            break;

	                        case "-":
	                            stack.push(operandOne - operandTwo);
	                            break;

	                        case "*":
	                            stack.push(operandTwo * operandOne);
	                            break;

	                        case "/":
	                            stack.push(operandOne / operandTwo);
	                            break;

	                    }
	                }
	            }

	        }
	        if ((operands % 2 != 0 && operators % 2 != 0) || (operands % 2 == 0 && operators % 2 == 0))
	            return 0;

	        return stack.pop();
	    }
	
	
	
	public static String toPost(String s) { // method to convert inFix expression to postFix
		CursorStack<String> postStack = new CursorStack<>(200);
		String[] op = s.split(" ");
		String outPut = "";
		if (isBalancedEq(s)) {
			for (int m = 0; m < op.length; m++) {
				if (op[m].equals("+") || op[m].equals("-")) {
					if (op[m + 1].equals("*") || op[m + 1].equals("/") || op[m + 1].equals("+") || op[m + 1].equals("-")
							|| op[m + 1].equals("^"))
						return "invalid";
					while (!postStack.isEmpty() && !postStack.peek().equals("("))
						outPut += postStack.pop() + " ";
					postStack.push(op[m]);

				} else if (op[m].equals("*") || op[m].equals("/")) {
					if (op[m + 1].equals("*") || op[m + 1].equals("/") || op[m + 1].equals("+") || op[m + 1].equals("-")
							|| op[m + 1].equals("^"))
						return "invalid";
					while (!postStack.isEmpty() && !postStack.peek().equals("(") && !postStack.peek().equals("+")
							&& !postStack.peek().equals("-"))
						outPut += postStack.pop() + " ";
					postStack.push(op[m]);

				} else if (op[m].equals("^")) {
					if (op[m + 1].equals("*") || op[m + 1].equals("/") || op[m + 1].equals("+") || op[m + 1].equals("-")
							|| op[m + 1].equals("^"))
						return "invalid";
					while (!postStack.isEmpty() && !postStack.peek().equals("(") && !postStack.peek().equals("+")
							&& !postStack.peek().equals("-") && !postStack.peek().equals("*")
							&& !postStack.peek().equals("/"))
						outPut += postStack.pop() + " ";
					postStack.push(op[m]);
				} else if (op[m].equals("(")) {
					postStack.push(op[m]);
				} else if (op[m].equals(")")) {
					while (!postStack.isEmpty() && !postStack.peek().equals("("))
						outPut += postStack.pop() + " ";
					postStack.pop();
				} else {
					outPut += op[m] + " ";
				}
			}
		} else {
			return "Un Balanced equation";
		}
		while (!postStack.isEmpty()) {
			outPut += postStack.pop() + " ";
		}

		return outPut;
	}

	public static boolean isBalancedEq(String s) { // method to check that the method is balanced or not
		CursorStack<String> stack = new CursorStack<>(50);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c + "") {
			case "(":
				stack.push(c + "");
				break;
			case ")":
				if (stack.isEmpty())
					return false;
				char o = stack.pop().charAt(0);
				if ((c == ')' && o != '('))
					return false;
			}
		}
		return (stack.isEmpty()) ? true : false;
	}

	public static boolean isValidFile(String s) { // method to check that all opening tags have close in files
		CursorStack<String> CA = new CursorStack<>(50);
		String[] tags = s.split(" ");
		for (int i = 0; i < tags.length; i++) {
			switch (tags[i]) {
			case "<242>":
			case "<equations>":
			case "<equation>":
			case "<files>":
			case "<file>":
				CA.push(tags[i]);
				break;
			case "</242>":
			case "</equations>":
			case "</equation>":
			case "</files>":
			case "</file>":
				if (CA.isEmpty())
					return false;
				String o = CA.pop();
				if (((tags[i] == ("</242>") && o != ("<242>") || tags[i] == ("</equations>") && o != ("<equations>")
						|| tags[i] == ("</equation>") && o != ("<equation>")
						|| tags[i] == ("</files>") && o != ("files") || tags[i] == ("</file>") && o != ("file"))))
					return false;
			}
		}
		return (CA.isEmpty()) ? true : false;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
