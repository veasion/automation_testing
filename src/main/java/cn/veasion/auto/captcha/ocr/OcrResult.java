package cn.veasion.auto.captcha.ocr;

import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.ApiDocumentGenerator;

import java.io.Serializable;
import java.util.List;

/**
 * OcrResult
 *
 * @author luozhuowei
 * @date 2020/12/31
 */
@Api.ClassInfo(desc = "识别结果")
public class OcrResult implements Serializable, ApiDocumentGenerator.DocGenerator {

    private static final long serialVersionUID = 1L;

    private List<Words> wordsList;

    @Api(result = Words.class)
    public List<Words> getWordsList() {
        return wordsList;
    }

    @Api(generator = false)
    public void setWordsList(List<Words> wordsList) {
        this.wordsList = wordsList;
    }

    public String getContent() {
        if (wordsList == null || wordsList.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Words words : wordsList) {
            sb.append(words.getWords()).append("\n");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    @Api(generator = false)
    public String toString() {
        final StringBuffer sb = new StringBuffer("OcrResult{");
        sb.append("wordsList=").append(wordsList);
        sb.append('}');
        return sb.toString();
    }

    @Api.ClassInfo(desc = "位置")
    public static class Location implements Serializable, ApiDocumentGenerator.DocGenerator {

        private static final long serialVersionUID = 1L;

        private Integer top;
        private Integer left;
        private Integer width;
        private Integer height;

        public Integer getTop() {
            return top;
        }

        @Api(generator = false)
        public void setTop(Integer top) {
            this.top = top;
        }

        public Integer getLeft() {
            return left;
        }

        @Api(generator = false)
        public void setLeft(Integer left) {
            this.left = left;
        }

        public Integer getWidth() {
            return width;
        }

        @Api(generator = false)
        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        @Api(generator = false)
        public void setHeight(Integer height) {
            this.height = height;
        }

        @Override
        @Api(generator = false)
        public String toString() {
            final StringBuffer sb = new StringBuffer("Location{");
            sb.append("top=").append(top);
            sb.append(", left=").append(left);
            sb.append(", width=").append(width);
            sb.append(", height=").append(height);
            sb.append('}');
            return sb.toString();
        }
    }

    @Api.ClassInfo(desc = "单词")
    public static class Words implements Serializable, ApiDocumentGenerator.DocGenerator {

        private static final long serialVersionUID = 1L;

        private String words;
        private Location location;

        public Words() {
        }

        public Words(String words) {
            this.words = words;
        }

        public String getWords() {
            return words;
        }

        @Api(generator = false)
        public void setWords(String words) {
            this.words = words;
        }

        public Location getLocation() {
            return location;
        }

        @Api(generator = false)
        public void setLocation(Location location) {
            this.location = location;
        }

        @Override
        @Api(generator = false)
        public String toString() {
            final StringBuffer sb = new StringBuffer("Words{");
            sb.append("words='").append(words).append('\'');
            sb.append(", location=").append(location);
            sb.append('}');
            return sb.toString();
        }
    }
}
