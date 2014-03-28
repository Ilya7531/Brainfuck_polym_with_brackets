package javaapplication8;
import java.io.IOException;

class Symbol {
		
	public final static char NEXT = '>';
	public final static char PREVIOUS = '<';
	public final static char PLUS = '+';
	public final static char MINUS = '-';
	public final static char OUTPUT = '.';
	public final static char INPUT = ',';
	public final static char BRACKET_LEFT = '[';
	public final static char BRACKET_RIGHT = ']';
}

public class Interpreter {
	
        private String str;
	private int charPointer = -1;
	
	public Interpreter(String s) {
		str = s;
	}
	
	public boolean hasNextTerm() {
		return charPointer + 1 < str.length();
	}
	
	public Term nextTerm() {
		Term t = null;
		charPointer++;
		switch (str.charAt(charPointer)) {
			case Symbol.NEXT:
				t = new Next();
				break;
			case Symbol.PREVIOUS:
				t = new Previous();
				break;
			case Symbol.PLUS:
				t = new Plus();
				break;
			case Symbol.MINUS:
				t = new Minus();
				break;
			case Symbol.OUTPUT:
				t = new Output();
				break;
			case Symbol.INPUT:
				t = new Input();
				break;
			case Symbol.BRACKET_LEFT:
				t = new BracketLeft();
				break;
			case Symbol.BRACKET_RIGHT:
				t = new BracketRight();
				break;
		}
		return t;
	}
	
	
	abstract public static class Term {
		
		protected static int data[];
		protected static int dataPointer;
		
		static {
			dataPointer = 0;
			data = new int [30];
			for (int i = 0; i < data.length; ++i) data[i] = 0;
		}
		
		abstract public void doProcess();
	}

	class Plus extends Term {
		
		public void doProcess() {
			++data[dataPointer];
		}
	}

	class Minus extends Term {
		
		public void doProcess() {
			--data[dataPointer];
		}
	}

	class Next extends Term {

		public void doProcess() {
			++dataPointer;
		}
	}

	class Previous extends Term {

		public void doProcess() {
			--dataPointer;
		}
	}

	class Input extends Term {
		
		public void doProcess() {
			try {
				data[dataPointer] = (int) System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Output extends Term {
		
		public void doProcess() {
			System.out.print((char) data[dataPointer]);
		}
	}
	
	class BracketLeft extends Term {
		
		public void doProcess() {
			if (data[dataPointer] == 0) {
				int i = 1;
				while (i > 0) {
					char c2 = str.charAt(++charPointer);
					if (c2 == Symbol.BRACKET_LEFT) i++;
					else if (c2 == Symbol.BRACKET_RIGHT) i--;
				}
			}
		}
	}

	class BracketRight extends Term {
		
		public void doProcess() {
			int i = 1;
			while (i > 0) {
				char c2 = str.charAt(--charPointer);
				if (c2 == Symbol.BRACKET_LEFT) i--;
				else if (c2 == Symbol.BRACKET_RIGHT) i++;
			}
			charPointer--;
		}
	}

}


