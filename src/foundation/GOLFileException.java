package foundation;

public class GOLFileException extends RuntimeException {

	private int lineno = -1;
	
	public GOLFileException(String message) {
		super(message);
	}

	public GOLFileException(String message, int lineno) {
		super(message);
		
		this.lineno = lineno;
	}

	public int getLineno() {
		return lineno;
	}
	
	
}
