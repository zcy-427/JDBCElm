package ynu.edu.view;

public class MainView {
    private final AdminView adminView = new AdminView();
    private final BusinessView businessView = new BusinessView();

    /**
     * 启动系统主菜单
     */
    public void start() {
        while (true) {
            ConsolePrinter.printTitle("饿了么 JDBC 命令行管理系统");
            System.out.println("1. 管理员登录");
            System.out.println("2. 商家登录");
            System.out.println("0. 退出系统");

            String choice = ConsoleInput.readLine("请选择：");
            switch (choice) {
                case "1":
                    adminView.start();
                    break;
                case "2":
                    businessView.start();
                    break;
                case "0":
                    ConsolePrinter.printMessage("感谢使用饿了么 JDBC 命令行管理系统，再见！");
                    return;
                default:
                    ConsolePrinter.printError("无效选项，请重新选择。");
                    ConsoleInput.pause();
            }
        }
    }
}
