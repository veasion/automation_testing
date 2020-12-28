package cn.veasion.auto.util;

/**
 * AssertException
 *
 * @author luozhuowei
 * @date 2020/12/28
 */
public class AssertException extends AutomationException {

	private static final long serialVersionUID = 1L;

	public AssertException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        // super.printStackTrace();
    }

}
