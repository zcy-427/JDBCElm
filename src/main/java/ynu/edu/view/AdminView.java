package ynu.edu.view;

import ynu.edu.entity.Business;
import ynu.edu.service.AdminService;
import ynu.edu.service.Impl.AdminServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class AdminView {
    private final AdminService adminService = new AdminServiceImpl();

    /**
     * 启动管理员登录界面
     */
    public void start() {
        ConsolePrinter.printTitle("管理员登录");
        String username = ConsoleInput.readRequiredString("请输入管理员用户名：");
        String password = ConsoleInput.readRequiredString("请输入管理员密码：");

        if (!adminService.login(username, password)) {
            ConsolePrinter.printError("管理员登录失败，账号密码错误或 Service 尚未实现。");
            ConsoleInput.pause();
            return;
        }

        ConsolePrinter.printMessage("管理员登录成功。");
        showAdminMenu();
    }

    /**
     * 显示管理员功能菜单
     */
    private void showAdminMenu() {
        while (true) {
            ConsolePrinter.printTitle("管理员菜单");
            System.out.println("1. 查看所有商家");
            System.out.println("2. 搜索商家");
            System.out.println("3. 新建商家");
            System.out.println("4. 删除商家");
            System.out.println("0. 返回主菜单");

            String choice = ConsoleInput.readLine("请选择：");
            switch (choice) {
                case "1":
                    showAllBusinesses();
                    break;
                case "2":
                    searchBusinesses();
                    break;
                case "3":
                    addBusiness();
                    break;
                case "4":
                    deleteBusiness();
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
     * 显示所有商家列表
     */
    private void showAllBusinesses() {
        ConsolePrinter.printTitle("所有商家列表");
        List<Business> businesses = adminService.listBusinesses();
        ConsolePrinter.printBusinessList(businesses);
        ConsoleInput.pause();
    }

    /**
     * 根据名称和地址关键词搜索商家
     */
    private void searchBusinesses() {
        ConsolePrinter.printTitle("搜索商家");
        String nameKeyword = ConsoleInput.readLine("请输入商家名称关键词（可直接回车跳过）：");
        String addressKeyword = ConsoleInput.readLine("请输入商家地址关键词（可直接回车跳过）：");

        List<Business> businesses = adminService.searchBusinesses(nameKeyword, addressKeyword);
        ConsolePrinter.printBusinessList(businesses);
        ConsoleInput.pause();
    }

    /**
     * 收集商家信息并提交新增商家请求
     */
    private void addBusiness() {
        ConsolePrinter.printTitle("新建商家");
        String businessName = ConsoleInput.readRequiredString("请输入商家名称：");
        String businessAddress = ConsoleInput.readRequiredString("请输入商家地址：");
        String businessExplain = ConsoleInput.readLine("请输入商家介绍：");
        BigDecimal starPrice = ConsoleInput.readBigDecimal("请输入起送费：");
        BigDecimal deliveryPrice = ConsoleInput.readBigDecimal("请输入配送费：");
        String password = ConsoleInput.readLine("请输入初始密码（直接回车默认 123456）：");
        if (password.isEmpty()) {
            password = "123456";
        }

        Business business = new Business();
        business.setPassword(password);
        business.setBusinessName(businessName);
        business.setBusinessAddress(businessAddress);
        business.setBusinessExplain(businessExplain);
        business.setStarPrice(starPrice);
        business.setDeliveryPrice(deliveryPrice);

        Integer businessId = adminService.addBusiness(business);
        if (businessId == null) {
            ConsolePrinter.printError("新增商家失败，Service 尚未实现或数据保存失败。");
        } else {
            ConsolePrinter.printMessage("新建商家成功，商家编号为：" + businessId);
        }
        ConsoleInput.pause();
    }

    /**
     * 根据商家编号删除商家
     */
    private void deleteBusiness() {
        ConsolePrinter.printTitle("删除商家");
        int businessId = ConsoleInput.readPositiveInt("请输入要删除的商家编号：");
        boolean confirmed = ConsoleInput.readYesNo("确认删除该商家吗？删除后不可恢复（y/n）：");
        if (!confirmed) {
            ConsolePrinter.printMessage("已取消删除。");
            ConsoleInput.pause();
            return;
        }

        boolean success = adminService.deleteBusiness(businessId);
        if (success) {
            ConsolePrinter.printMessage("删除成功。");
        } else {
            ConsolePrinter.printError("删除失败，商家不存在或 Service 尚未实现。");
        }
        ConsoleInput.pause();
    }
}
