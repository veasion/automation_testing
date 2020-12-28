package cn.veasion.auto.util;

import javax.script.ScriptException;

/**
 * JavascriptException
 *
 * @author luozhuowei
 * @date 2020/8/1
 */
public class JavascriptException extends AutomationException {

	private static final long serialVersionUID = 1L;
	
	private String fileName;
    private Integer lineNumber;

    public JavascriptException(String fileName, String message) {
        super(message);
        this.fileName = fileName;
    }

    public JavascriptException(String fileName, Integer lineNumber, String message) {
        this(fileName, message);
        this.lineNumber = lineNumber;
    }

    public JavascriptException(String fileName, Integer lineNumber, String message, Throwable e) {
        this(fileName, message, e);
        if (lineNumber != null) {
            this.lineNumber = lineNumber;
        }
    }

    public JavascriptException(String fileName, Throwable e) {
        this(fileName, e != null ? e.getMessage() : null, e);
    }

    public JavascriptException(String fileName, String message, Throwable e) {
        super(message != null ? message : (e != null ? e.getMessage() : null), e);
        this.fileName = fileName;
        if (e instanceof ScriptException) {
            if (fileName == null) {
                this.fileName = ((ScriptException) e).getFileName();
            }
            this.lineNumber = ((ScriptException) e).getLineNumber();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    @Override
    public void printStackTrace() {
        // super.printStackTrace();
    }

}
