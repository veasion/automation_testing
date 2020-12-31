package cn.veasion.auto.captcha.ocr;

import java.io.Serializable;
import java.util.List;

/**
 * OcrResult
 *
 * @author luozhuowei
 * @date 2020/12/31
 */
public class OcrResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Words> wordsList;

    public List<Words> getWordsList() {
        return wordsList;
    }

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
    public String toString() {
        final StringBuffer sb = new StringBuffer("OcrResult{");
        sb.append("wordsList=").append(wordsList);
        sb.append('}');
        return sb.toString();
    }

    public static class Location implements Serializable {

        private static final long serialVersionUID = 1L;

        private Integer top;
        private Integer left;
        private Integer width;
        private Integer height;

        public Integer getTop() {
            return top;
        }

        public void setTop(Integer top) {
            this.top = top;
        }

        public Integer getLeft() {
            return left;
        }

        public void setLeft(Integer left) {
            this.left = left;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        @Override
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

    public static class Words implements Serializable {

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

        public void setWords(String words) {
            this.words = words;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Words{");
            sb.append("words='").append(words).append('\'');
            sb.append(", location=").append(location);
            sb.append('}');
            return sb.toString();
        }
    }
}
