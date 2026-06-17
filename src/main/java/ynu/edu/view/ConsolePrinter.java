package ynu.edu.view;

import ynu.edu.entity.Business;
import ynu.edu.entity.Food;

import java.math.BigDecimal;
import java.util.List;

public class ConsolePrinter {
    private ConsolePrinter() {
    }

    /**
     * 打印控制台标题
     * @param title 标题内容
     */
    public static void printTitle(String title) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("        " + title);
        System.out.println("========================================");
    }

    /**
     * 打印普通提示信息
     * @param message 提示信息
     */
    public static void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * 打印错误提示信息
     * @param message 错误信息
     */
    public static void printError(String message) {
        System.out.println("错误：" + message);
    }

    /**
     * 打印商家列表
     * @param businesses 商家列表
     */
    public static void printBusinessList(List<Business> businesses) {
        if (businesses == null || businesses.isEmpty()) {
            System.out.println("暂无商家数据。");
            return;
        }

        System.out.printf("%-6s %-18s %-24s %-24s %-10s %-10s%n",
                "编号", "名称", "地址", "介绍", "起送费", "配送费");
        System.out.println("--------------------------------------------------------------------------------");
        for (Business business : businesses) {
            System.out.printf("%-6s %-18s %-24s %-24s %-10s %-10s%n",
                    valueOf(business.getBusinessId()),
                    limit(business.getBusinessName(), 16),
                    limit(business.getBusinessAddress(), 22),
                    limit(business.getBusinessExplain(), 22),
                    moneyOf(business.getStarPrice()),
                    moneyOf(business.getDeliveryPrice()));
        }
    }

    /**
     * 打印商家详细信息
     * @param business 商家信息
     */
    public static void printBusinessDetail(Business business) {
        if (business == null) {
            System.out.println("未查询到商家信息。");
            return;
        }

        printTitle("商家信息");
        System.out.println("编号：" + valueOf(business.getBusinessId()));
        System.out.println("名称：" + valueOf(business.getBusinessName()));
        System.out.println("地址：" + valueOf(business.getBusinessAddress()));
        System.out.println("介绍：" + valueOf(business.getBusinessExplain()));
        System.out.println("起送费：" + moneyOf(business.getStarPrice()));
        System.out.println("配送费：" + moneyOf(business.getDeliveryPrice()));
    }

    /**
     * 打印食品列表
     * @param foods 食品列表
     */
    public static void printFoodList(List<Food> foods) {
        if (foods == null || foods.isEmpty()) {
            System.out.println("暂无食品数据。");
            return;
        }

        System.out.printf("%-6s %-18s %-30s %-10s%n", "编号", "名称", "介绍", "价格");
        System.out.println("----------------------------------------------------------------");
        for (Food food : foods) {
            System.out.printf("%-6s %-18s %-30s %-10s%n",
                    valueOf(food.getFoodId()),
                    limit(food.getFoodName(), 16),
                    limit(food.getFoodExplain(), 28),
                    moneyOf(food.getFoodPrice()));
        }
    }

    /**
     * 将对象转换为字符串，空值转换为空字符串
     * @param value 待转换对象
     * @return 字符串结果
     */
    private static String valueOf(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    /**
     * 将金额转换为字符串
     * @param value 金额
     * @return 金额字符串
     */
    private static String moneyOf(BigDecimal value) {
        return value == null ? "0.00" : value.toPlainString();
    }

    /**
     * 限制字符串最大长度
     * @param value 原字符串
     * @param maxLength 最大长度
     * @return 截断后的字符串
     */
    private static String limit(String value, int maxLength) {
        if (value == null) {
            return "";
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 1) + "…";
    }
}
