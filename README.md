# Equation Browser Application
## COMP242 project - Data Structers
### Using Stack, LinkedList, and Cursor Array
## Project Summary
This project implements an equation file browser application using linked stack and cursor array implementations of a linked list. The application allows users to load and navigate special .242 files, which contain mathematical equations in infix format. The application validates the file structure, converts valid equations to postfix format, and evaluates them. It also supports browsing through a history of loaded files.

## Project Details
File Structure
The equation files have a .242 extension and must adhere to a specific structure:

The file begins with a <242> tag and ends with a </242> tag.
Within these tags, there are two optional sections:
Files Section: Encapsulated by <files> and </files> tags, containing nested <file> tags.
Equations Section: Encapsulated by <equations> and </equations> tags, containing nested <equation> tags.

## Key Features
### 1. File Loading and Validation

Users can load a .242 file using a file chooser interface. The selected file path is displayed, and its contents are loaded if the file is valid.
The application checks if the tags in the file are balanced and properly nested using a stack-based approach. If the file structure is invalid, an error message is displayed.

### 2. Equation Handling

The application loads infix equations from the file's equations section, ensuring they are valid and balanced.
Valid infix equations are converted to postfix notation using a stack.
The application evaluates the postfix equations and displays the results.

### 3. File Browsing

Users can browse files listed in the files section. Clicking on a file name loads its contents.
A "Back" button allows users to navigate through the history of loaded files, similar to the back button functionality in web browsers.
The back button is disabled when the user reaches the first loaded file.

## Technical Implementation
* Linked Stack: Used for validating and processing the file tags and equations.
* Cursor Array Linked List: Implemented to manage and store the file and equation data.
* JavaFX FileChooser: Utilized for file selection and user interface interaction.
