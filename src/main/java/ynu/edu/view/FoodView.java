package ynu.edu.view;

import ynu.edu.entity.Food;
import ynu.edu.service.FoodService;
import ynu.edu.service.Impl.FoodServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class FoodView {
    private final Integer businessId;
    private final FoodService foodService = new FoodServiceImpl();

    /**
     * 创建食品管理界面
     * @param businessId 当前登录商家编号
     */
    public FoodView(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 启动食品管理菜单
     */
    public void start() {
        while (true) {
            ConsolePrinter.printTitle("食品管理");
            System.out.println("1. 查看食品列表");
            System.out.println("2. 新增食品");
            System.out.println("3. 修改食品");
            System.out.println("4. 删除食品");
            System.out.println("0. 返回商家菜单");

            String choice = ConsoleInput.readLine("请选择：");
            switch (choice) {
                case "1":
                    showFoodList();
                    break;
                case "2":
                    addFood();
                    break;
                case "3":
                    updateFood();
                    break;
                case "4":
                    deleteFood();
                    break;
                case "0":
                    return;
                default:
                    ConsolePrinter.printError("无效选项，请重新选择。");
                    ConsoleInput.pause();
            }
        }
    }

    /**
     * 显示当前商家的食品列表
     */
    private void showFoodList() {
        ConsolePrinter.printTitle("食品列表");
        List<Food> foods = foodService.listFoodsByBusinessId(businessId);
        ConsolePrinter.printFoodList(foods);
        ConsoleInput.pause();
    }

    /**
     * 收集食品信息并提交新增食品请求
     */
    private void addFood() {
        ConsolePrinter.printTitle("新增食品");
        printCancelTip();

        String foodName = ConsoleInput.readRequiredStringOrCancel("请输入食品名称：");
        if (foodName == null) {
            cancelOperation("新增食品");
            return;
        }

        String foodExplain = ConsoleInput.readLineOrCancel("请输入食品介绍：");
        if (foodExplain == null) {
            cancelOperation("新增食品");
            return;
        }

        BigDecimal foodPrice = ConsoleInput.readBigDecimalOrCancel("请输入食品价格：");
        if (foodPrice == null) {
            cancelOperation("新增食品");
            return;
        }

        Boolean confirmed = ConsoleInput.readYesNoOrCancel("确认新增该食品吗？（y/n，q取消）：");
        if (!Boolean.TRUE.equals(confirmed)) {
            cancelOperation("新增食品");
            return;
        }

        Food food = new Food();
        food.setBusinessId(businessId);
        food.setFoodName(foodName);
        food.setFoodExplain(foodExplain);
        food.setFoodPrice(foodPrice);

        Integer foodId = foodService.addFood(food);
        if (foodId == null) {
            ConsolePrinter.printError("新增食品失败，Service 尚未实现或数据保存失败。");
        } else {
            ConsolePrinter.printMessage("食品新增成功，食品编号为：" + foodId);
        }
        ConsoleInput.pause();
    }

    /**
     * 按食品编号逐项确认并修改食品信息
     */
    private void updateFood() {
        ConsolePrinter.printTitle("修改食品");
        printCancelTip();

        Integer foodId = ConsoleInput.readPositiveIntOrCancel("请输入要修改的食品编号：");
        if (foodId == null) {
            cancelOperation("修改食品");
            return;
        }

        Food food = foodService.getFoodById(foodId, businessId);
        if (food == null) {
            ConsolePrinter.printError("未查询到该食品，无法修改。");
            ConsoleInput.pause();
            return;
        }

        System.out.println("当前食品名称：" + valueOf(food.getFoodName()));
        Boolean updateName = ConsoleInput.readYesNoOrCancel("是否修改食品名称？（y/n，q取消）：");
        if (updateName == null) {
            cancelOperation("修改食品");
            return;
        }
        if (updateName) {
            String foodName = ConsoleInput.readRequiredStringOrCancel("请输入新的食品名称：");
            if (foodName == null) {
                cancelOperation("修改食品");
                return;
            }
            food.setFoodName(foodName);
        }

        System.out.println("当前食品介绍：" + valueOf(food.getFoodExplain()));
        Boolean updateExplain = ConsoleInput.readYesNoOrCancel("是否修改食品介绍？（y/n，q取消）：");
        if (updateExplain == null) {
            cancelOperation("修改食品");
            return;
        }
        if (updateExplain) {
            String foodExplain = ConsoleInput.readLineOrCancel("请输入新的食品介绍：");
            if (foodExplain == null) {
                cancelOperation("修改食品");
                return;
            }
            food.setFoodExplain(foodExplain);
        }

        System.out.println("当前食品价格：" + valueOf(food.getFoodPrice()));
        Boolean updatePrice = ConsoleInput.readYesNoOrCancel("是否修改食品价格？（y/n，q取消）：");
        if (updatePrice == null) {
            cancelOperation("修改食品");
            return;
        }
        if (updatePrice) {
            BigDecimal foodPrice = ConsoleInput.readBigDecimalOrCancel("请输入新的食品价格：");
            if (foodPrice == null) {
                cancelOperation("修改食品");
                return;
            }
            food.setFoodPrice(foodPrice);
        }

        Boolean confirmed = ConsoleInput.readYesNoOrCancel("确认保存修改吗？（y/n，q取消）：");
        if (!Boolean.TRUE.equals(confirmed)) {
            cancelOperation("修改食品");
            return;
        }

        boolean success = foodService.updateFood(food);
        if (success) {
            ConsolePrinter.printMessage("食品修改成功。");
        } else {
            ConsolePrinter.printError("食品修改失败，Service 尚未实现或数据不存在。");
        }
        ConsoleInput.pause();
    }

    /**
     * 按食品编号删除食品
     */
    private void deleteFood() {
        ConsolePrinter.printTitle("删除食品");
        printCancelTip();

        Integer foodId = ConsoleInput.readPositiveIntOrCancel("请输入要删除的食品编号：");
        if (foodId == null) {
            cancelOperation("删除食品");
            return;
        }

        Food food = foodService.getFoodById(foodId, businessId);
        if (food == null) {
            ConsolePrinter.printError("该食品不存在，无法删除。");
            ConsoleInput.pause();
            return;
        }

        System.out.println("即将删除食品：" + valueOf(food.getFoodName()));
        Boolean confirmed = ConsoleInput.readYesNoOrCancel("确认删除该食品吗？删除后不可恢复（y/n，q取消）：");
        if (!Boolean.TRUE.equals(confirmed)) {
            cancelOperation("删除食品");
            return;
        }

        boolean success = foodService.deleteFood(foodId, businessId);
        if (success) {
            ConsolePrinter.printMessage("食品删除成功。");
        } else {
            ConsolePrinter.printError("食品删除失败，Service 尚未实现或数据不存在。");
        }
        ConsoleInput.pause();
    }

    /**
     * 打印取消操作提示
     */
    private void printCancelTip() {
        System.out.println("提示：输入 q 可取消当前操作。");
    }

    /**
     * 取消操作并暂停
     * @param operation 操作名称
     */
    private void cancelOperation(String operation) {
        ConsolePrinter.printMessage("已取消" + operation + "。");
        ConsoleInput.pause();
    }

    /**
     * 将对象转换为字符串，空值转换为空字符串
     * @param value 待转换对象
     * @return 字符串结果
     */
    private String valueOf(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}
