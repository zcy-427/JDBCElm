package ynu.edu.view;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleInput {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String CANCEL_INPUT = "q";

    private ConsoleInput() {
    }

    /**
     * 读取一行控制台输入并去除首尾空格
     * @param prompt 输入提示文本
     * @return 用户输入的字符串
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    /**
     * 读取非空字符串
     * @param prompt 输入提示文本
     * @return 非空字符串
     */
    public static String readRequiredString(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("输入不能为空，请重新输入。");
        }
    }

    /**
     * 读取正整数
     * @param prompt 输入提示文本
     * @return 大于 0 的整数
     */
    public static int readPositiveInt(String prompt) {
        while (true) {
            String input = readLine(prompt);
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("请输入大于 0 的整数。");
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入整数。");
            }
        }
    }

    /**
     * 读取非负金额
     * @param prompt 输入提示文本
     * @return 大于等于 0 的 BigDecimal 金额
     */
    public static BigDecimal readBigDecimal(String prompt) {
        while (true) {
            String input = readLine(prompt);
            try {
                BigDecimal value = new BigDecimal(input);
                if (value.compareTo(BigDecimal.ZERO) >= 0) {
                    return value;
                }
                System.out.println("金额不能为负数，请重新输入。");
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入合法金额。");
            }
        }
    }

    /**
     * 读取 y/n 确认输入
     * @param prompt 输入提示文本
     * @return 输入 y 返回 true，输入 n 返回 false
     */
    public static boolean readYesNo(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if ("y".equalsIgnoreCase(input)) {
                return true;
            }
            if ("n".equalsIgnoreCase(input)) {
                return false;
            }
            System.out.println("请输入 y 或 n。");
        }
    }

    /**
     * 读取可取消的字符串
     * @param prompt 输入提示文本
     * @return 输入内容，输入 q 返回 null
     */
    public static String readLineOrCancel(String prompt) {
        String input = readLine(prompt);
        if (isCancel(input)) {
            return null;
        }
        return input;
    }

    /**
     * 读取可取消的非空字符串
     * @param prompt 输入提示文本
     * @return 非空输入内容，输入 q 返回 null
     */
    public static String readRequiredStringOrCancel(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (isCancel(input)) {
                return null;
            }
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("输入不能为空，请重新输入。");
        }
    }

    /**
     * 读取可取消的正整数
     * @param prompt 输入提示文本
     * @return 大于 0 的整数，输入 q 返回 null
     */
    public static Integer readPositiveIntOrCancel(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (isCancel(input)) {
                return null;
            }
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("请输入大于 0 的整数。");
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入整数。");
            }
        }
    }

    /**
     * 读取可取消的非负金额
     * @param prompt 输入提示文本
     * @return 大于等于 0 的金额，输入 q 返回 null
     */
    public static BigDecimal readBigDecimalOrCancel(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (isCancel(input)) {
                return null;
            }
            try {
                BigDecimal value = new BigDecimal(input);
                if (value.compareTo(BigDecimal.ZERO) >= 0) {
                    return value;
                }
                System.out.println("金额不能为负数，请重新输入。");
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入合法金额。");
            }
        }
    }

    /**
     * 读取可取消的 y/n 确认输入
     * @param prompt 输入提示文本
     * @return 输入 y 返回 true，输入 n 返回 false，输入 q 返回 null
     */
    public static Boolean readYesNoOrCancel(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (isCancel(input)) {
                return null;
            }
            if ("y".equalsIgnoreCase(input)) {
                return true;
            }
            if ("n".equalsIgnoreCase(input)) {
                return false;
            }
            System.out.println("请输入 y、n 或 q。");
        }
    }

    /**
     * 判断是否为取消输入
     * @param input 用户输入
     * @return 是否取消
     */
    public static boolean isCancel(String input) {
        return CANCEL_INPUT.equalsIgnoreCase(input);
    }

    /**
     * 暂停控制台，等待用户按回车继续
     */
    public static void pause() {
        System.out.print("按回车键继续...");
        SCANNER.nextLine();
    }
}
