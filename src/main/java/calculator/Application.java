package calculator;
import camp.nextstep.edu.missionutils.Console;

import java.util.Arrays;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현

        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();

        // 빈 입력이면 0 출력
        if (input == null || input.isEmpty()) {
            System.out.println("결과 : 0");
            return;
        }

        String[] numberTokens = DelimiterParser.parse(input);
        int sum = Arrays.stream(numberTokens)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .peek(Application::validateNumeric)     // 숫자 검증
                        .mapToInt(Integer::parseInt)
                        .sum();

        System.out.println("결과 : " + sum);
    }

    private static void validateNumeric(String s) {
        // 양의 정수만 허용
        if(!s.matches("\\d+")) {
            throw new IllegalArgumentException("[ERROR] 숫자가 아닌 값이 포함되어 있습니다: " + s);
        }
    }
}
