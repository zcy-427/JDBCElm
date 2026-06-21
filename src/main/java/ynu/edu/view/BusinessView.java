package ynu.edu.view;

import ynu.edu.entity.Business;
import ynu.edu.service.BusinessService;
import ynu.edu.service.Impl.BusinessServiceImpl;

import java.math.BigDecimal;

public class BusinessView {
    private final BusinessService businessService = new BusinessServiceImpl();

    /**
     * 启动商家登录界面
     */
    public void start() {
        ConsolePrinter.printTitle("商家登录");
        int businessId = ConsoleInput.readPositiveInt("请输入商家编号：");
        String password = ConsoleInput.readRequiredString("请输入商家密码：");

        Business business = businessService.login(businessId, password);
        if (business == null) {
            ConsolePrinter.printError("商家登录失败，编号或者密码错误。");
            ConsoleInput.pause();
            return;
        }

        ConsolePrinter.printMessage("商家登录成功，欢迎：" + business.getBusinessName());
        showBusinessMenu(business);
    }

    /**
     * 显示商家功能菜单
     * @param currentBusiness 当前登录商家
     */
    private void showBusinessMenu(Business currentBusiness) {
        while (true) {
            ConsolePrinter.printTitle("商家菜单");
            System.out.println("1. 查看商家信息");
            System.out.println("2. 修改商家信息");
            System.out.println("3. 修改登录密码");
            System.out.println("4. 食品管理");
            System.out.println("0. 返回主菜单");

            String choice = ConsoleInput.readLine("请选择：");
            switch (choice) {
                case "1":
                    showBusinessInfo(currentBusiness);
                    break;
                case "2":
                    updateBusinessInfo(currentBusiness);
                    break;
                case "3":
                    updatePassword(currentBusiness.getBusinessId());
                    break;
                case "4":
                    new FoodView(currentBusiness.getBusinessId()).start();
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
     * 显示当前商家信息
     * @param currentBusiness 当前登录商家
     */
    private void showBusinessInfo(Business currentBusiness) {
        Business business = businessService.getBusinessById(currentBusiness.getBusinessId());
        if (business == null) {
            business = currentBusiness;
        }
        ConsolePrinter.printBusinessDetail(business);
        ConsoleInput.pause();
    }

    /**
     * 逐项确认并修改商家信息
     * @param currentBusiness 当前登录商家
     */
    private void updateBusinessInfo(Business currentBusiness) {
        ConsolePrinter.printTitle("修改商家信息");
        printCancelTip();

        Business business = businessService.getBusinessById(currentBusiness.getBusinessId());
        if (business == null) {
            business = currentBusiness;
        }

        System.out.println("当前商家名称：" + valueOf(business.getBusinessName()));
        Boolean updateName = ConsoleInput.readYesNoOrCancel("是否修改商家名称？（y/n，q取消）：");
        if (updateName == null) {
            cancelOperation("修改商家信息");
            return;
        }
        if (updateName) {
            String businessName = ConsoleInput.readRequiredStringOrCancel("请输入新的商家名称：");
            if (businessName == null) {
                cancelOperation("修改商家信息");
                return;
            }
            business.setBusinessName(businessName);
        }

        System.out.println("当前商家地址：" + valueOf(business.getBusinessAddress()));
        Boolean updateAddress = ConsoleInput.readYesNoOrCancel("是否修改商家地址？（y/n，q取消）：");
        if (updateAddress == null) {
            cancelOperation("修改商家信息");
            return;
        }
        if (updateAddress) {
            String businessAddress = ConsoleInput.readRequiredStringOrCancel("请输入新的商家地址：");
            if (businessAddress == null) {
                cancelOperation("修改商家信息");
                return;
            }
            business.setBusinessAddress(businessAddress);
        }

        System.out.println("当前商家介绍：" + valueOf(business.getBusinessExplain()));
        Boolean updateExplain = ConsoleInput.readYesNoOrCancel("是否修改商家介绍？（y/n，q取消）：");
        if (updateExplain == null) {
            cancelOperation("修改商家信息");
            return;
        }
        if (updateExplain) {
            String businessExplain = ConsoleInput.readLineOrCancel("请输入新的商家介绍：");
            if (businessExplain == null) {
                cancelOperation("修改商家信息");
                return;
            }
            business.setBusinessExplain(businessExplain);
        }

        System.out.println("当前起送费：" + valueOf(business.getStarPrice()));
        Boolean updateStarPrice = ConsoleInput.readYesNoOrCancel("是否修改起送费？（y/n，q取消）：");
        if (updateStarPrice == null) {
            cancelOperation("修改商家信息");
            return;
        }
        if (updateStarPrice) {
            BigDecimal starPrice = ConsoleInput.readBigDecimalOrCancel("请输入新的起送费：");
            if (starPrice == null) {
                cancelOperation("修改商家信息");
                return;
            }
            business.setStarPrice(starPrice);
        }

        System.out.println("当前配送费：" + valueOf(business.getDeliveryPrice()));
        Boolean updateDeliveryPrice = ConsoleInput.readYesNoOrCancel("是否修改配送费？（y/n，q取消）：");
        if (updateDeliveryPrice == null) {
            cancelOperation("修改商家信息");
            return;
        }
        if (updateDeliveryPrice) {
            BigDecimal deliveryPrice = ConsoleInput.readBigDecimalOrCancel("请输入新的配送费：");
            if (deliveryPrice == null) {
                cancelOperation("修改商家信息");
                return;
            }
            business.setDeliveryPrice(deliveryPrice);
        }

        Boolean confirmed = ConsoleInput.readYesNoOrCancel("确认保存商家信息修改吗？（y/n，q取消）：");
        if (!Boolean.TRUE.equals(confirmed)) {
            cancelOperation("修改商家信息");
            return;
        }

        boolean success = businessService.updateBusiness(business);
        if (success) {
            refreshCurrentBusiness(currentBusiness, business);
            ConsolePrinter.printMessage("商家信息修改成功。");
        } else {
            ConsolePrinter.printError("商家信息修改失败，Service 尚未实现或数据不存在。");
        }
        ConsoleInput.pause();
    }

    /**
     * 修改商家登录密码
     * @param businessId 商家编号
     */
    private void updatePassword(Integer businessId) {
        ConsolePrinter.printTitle("修改登录密码");
        printCancelTip();

        String oldPassword = ConsoleInput.readRequiredStringOrCancel("请输入旧密码：");
        if (oldPassword == null) {
            cancelOperation("修改登录密码");
            return;
        }

        String newPassword = ConsoleInput.readRequiredStringOrCancel("请输入新密码：");
        if (newPassword == null) {
            cancelOperation("修改登录密码");
            return;
        }

        String confirmPassword = ConsoleInput.readRequiredStringOrCancel("请再次输入新密码：");
        if (confirmPassword == null) {
            cancelOperation("修改登录密码");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            ConsolePrinter.printError("两次输入的新密码不一致。");
            ConsoleInput.pause();
            return;
        }

        if (oldPassword.equals(newPassword)) {
            ConsolePrinter.printError("新密码不能与旧密码相同。");
            ConsoleInput.pause();
            return;
        }

        Boolean confirmed = ConsoleInput.readYesNoOrCancel("确认修改登录密码吗？（y/n，q取消）：");
        if (!Boolean.TRUE.equals(confirmed)) {
            cancelOperation("修改登录密码");
            return;
        }

        boolean success = businessService.updatePassword(businessId, oldPassword, newPassword);
        if (success) {
            ConsolePrinter.printMessage("密码修改成功，请牢记新密码。");
        } else {
            ConsolePrinter.printError("密码修改失败");
        }
        ConsoleInput.pause();
    }

    /**
     * 刷新当前登录商家缓存信息
     * @param currentBusiness 当前登录商家
     * @param business 修改后的商家信息
     */
    private void refreshCurrentBusiness(Business currentBusiness, Business business) {
        currentBusiness.setBusinessName(business.getBusinessName());
        currentBusiness.setBusinessAddress(business.getBusinessAddress());
        currentBusiness.setBusinessExplain(business.getBusinessExplain());
        currentBusiness.setStarPrice(business.getStarPrice());
        currentBusiness.setDeliveryPrice(business.getDeliveryPrice());
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
