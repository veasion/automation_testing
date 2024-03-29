package cn.veasion.auto.util;

/**
 * Constants
 *
 * @author luozhuowei
 * @date 2020/12/15
 */
public class Constants {

    private Constants() {
    }

    /**
     * Web Socket Path
     */
    public static final String WEBSOCKET_PATH = "/js";

    /**
     * Web Socket PORT
     */
    public static final int PORT = 11630;

    /**
     * Web Socket Max Length
     */
    public static final int MAX_PACK_LENGTH = 10 * 1024 * 1024;

    /**
     * js file path
     */
    public static final String ENV_GLOBAL_FILE_PATH = "filePath";

    /**
     * js file name
     */
    public static final String ENV_GLOBAL_FILE_NAME = "fileName";

    /**
     * desktop dir
     */
    public static final String DESKTOP_DIR = "DESKTOP_DIR";

    /**
     * 驱动选项
     */
    public static final String DRIVER_OPTIONS = "DRIVER_OPTIONS";

    /**
     * 图片旋转统计范围
     */
    public static final int HOUGH_RANGE_VALUE = 3;

    /**
     * ocr key
     */
    public static final String CONFIG_OCR = "OCR";

    /**
     * ocr default
     */
    public static final String OCR_DEFAULT = "default";

    /**
     * ocr captcha
     */
    public static final String OCR_CAPTCHA = "captcha";

}
