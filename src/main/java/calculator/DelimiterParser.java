package calculator;
import java.util.regex.Pattern;

public class DelimiterParser {
    private static final String DEFAULT_DELIMITER = ",|:";

    public static String[] parse(String input) {

        if(!hasCustomDelimiter(input)) {
            // 기본 구분자 사용
            return input.isEmpty() ? new String[0] : input.split(DEFAULT_DELIMITER);
        }

        // 커스텀 구분자 사용
        String custom = extractCustomDelimiter(input);
        String numbersPart = extractNumbersPart(input);
        String regex = Pattern.quote(custom);

        return numbersPart.isEmpty() ? new String[0] : numbersPart.split(regex);
    }

    private static boolean hasCustomDelimiter(String input) {
        return input.startsWith("//");
    }

    private static String extractNumbersPart(String input) {
        int newlineIndex = input.indexOf("\n");
        if(newlineIndex == -1) {
            newlineIndex = input.indexOf("\\n");
        }

        // 개행 이후 문자열만 반환
        if(newlineIndex == -1 || newlineIndex >= input.length() - 1) {
            return "";
        }

        // 실제 개행(\n)은 +1, 문자열 리터럴(\\n)은 +2
        int offset = input.startsWith("\\n", newlineIndex) ? 2 : 1;
        return input.substring(newlineIndex + offset);
    }

    private static String extractCustomDelimiter(String input) {
        if(!input.startsWith("//")) {
            throw new IllegalArgumentException(("커스텀 구분자 형식이 올바르지 않습니다."));
        }

        // 실제 개행(\n) 또는 문자열 리터럴(\\n)을 모두 인식
        int newlineIndex = input.indexOf("\n");
        if(newlineIndex == -1) {
            newlineIndex = input.indexOf("\\n"); // 콘솔 입력 시 이렇게 들어올 수도 있음
        }

        // 개행이 없으면 예외
        if(newlineIndex == -1) {
            throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다.");
        }

        int startIndex = 2;
        if(newlineIndex <= startIndex) {
            throw new IllegalArgumentException("커스텀 구분자를 찾을 수 없습니다.");
        }

        // 커스텀 구분자 추출
        return input.substring(startIndex, newlineIndex);
    }
}
