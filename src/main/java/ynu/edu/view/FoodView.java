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
        String foodName = ConsoleInput.readRequiredString("请输入食品名称：");
        String foodExplain = ConsoleInput.readLine("请输入食品介绍：");
        BigDecimal foodPrice = ConsoleInput.readBigDecimal("请输入食品价格：");

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
        int foodId = ConsoleInput.readPositiveInt("请输入要修改的食品编号：");
        Food food = foodService.getFoodById(foodId, businessId);
        if (food == null) {
            ConsolePrinter.printError("未查询到该食品，或 Service 尚未实现。");
            ConsoleInput.pause();
            return;
        }

        System.out.println("当前食品名称：" + valueOf(food.getFoodName()));
        if (ConsoleInput.readYesNo("是否修改食品名称？（y/n）：")) {
            food.setFoodName(ConsoleInput.readRequiredString("请输入新的食品名称："));
        }

        System.out.println("当前食品介绍：" + valueOf(food.getFoodExplain()));
        if (ConsoleInput.readYesNo("是否修改食品介绍？（y/n）：")) {
            food.setFoodExplain(ConsoleInput.readLine("请输入新的食品介绍："));
        }

        System.out.println("当前食品价格：" + valueOf(food.getFoodPrice()));
        if (ConsoleInput.readYesNo("是否修改食品价格？（y/n）：")) {
            food.setFoodPrice(ConsoleInput.readBigDecimal("请输入新的食品价格："));
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
        int foodId = ConsoleInput.readPositiveInt("请输入要删除的食品编号：");
        boolean confirmed = ConsoleInput.readYesNo("确认删除该食品吗？删除后不可恢复（y/n）：");
        if (!confirmed) {
            ConsolePrinter.printMessage("已取消删除。");
            ConsoleInput.pause();
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
     * 将对象转换为字符串，空值转换为空字符串
     * @param value 待转换对象
     * @return 字符串结果
     */
    private String valueOf(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}
