package com.jiji.ATM;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account> accounts = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    //用户登录界面设计
    public void start(){
        while (true) {
            System.out.println("===========欢迎来到小坤坤银行=============");
            System.out.println("请选择操作指令");
            System.out.println("1.账户开户");
            System.out.println("2.账户登录");
            System.out.println("3.退出系统");
            String command = sc.next();
            switch (command){
                case ("1"):
                        //用户开户
                    createAccount();
                    break;
                case ("2"):
                        //用户登录
                    login();
                    break;
                case ("3"):
                    //退出系统
                    System.out.println("再见，欢迎再来办理业务！");
                    return;
                default:
                    System.out.println("您的输入有误，请重新输入！");
            }
        }
    }

// 创建开户方法
    private void createAccount(){
        Account account = new Account();
        System.out.println("=====进入开户操作界面=====");
        System.out.println("请输入开户用户名：");
        String userName = sc.next();
        account.setUserName(userName);

        while (true) {
            System.out.println("请输入性别：");
            char sex = sc.next().charAt(0);
            if (sex == '男' || sex == '女'){
                account.setSex(sex);
                break;
            }else {
                System.out.println("你的输入有误，请输入 男 或 女 ");
            }
        }

        while (true) {
            System.out.println("请设置用户登录密码：");
            String passWord = sc.next();
            System.out.println("请输入用户登录确认密码：");
            String okPassWord = sc.next();
            if (okPassWord.equals(passWord)){
                account.setPassWord(passWord);
                break;
            }else {
                System.out.println("你的密码设置错误，请重新设置。");
            }
        }

        System.out.println("请设置你的取现额度：");
        double limit = sc.nextDouble();
        account.setLimit(limit);

        //随机生成一个账户，且该账户不能和已有的账户一样。
        String cardId = createCardId();
        account.setCardId(cardId);

        //存储账户对象信息到集合中
        accounts.add(account);
        System.out.println("恭喜，" + account.getUserName() +  "开户成功，你的卡号是：" + account.getCardId());
        System.out.println("=========================================");

    }

//用于创建返回一个8位数的卡号，且该卡号不能和已有的账号重复
    private String createCardId(){
        StringBuilder id = new StringBuilder();
        Random r = new Random();
        while (true) {
            for (int i = 0; i < 8; i++){
                int data = r.nextInt(10);
                id.append(data);
            }
            String cardId = id.toString();
            Account account = getAccountByCardId(cardId);
            if (account == null){
                return cardId;
            }
        }

    }

//    用于根据 cardId 返回集合中已有的Account对象，如无则返回null
    private Account getAccountByCardId(String cardId){
        for (Account account : accounts) {
            if (account.getCardId().equals(cardId)) {
                return account;
            }
        }
        return null;
    }

//    用户登录操作设计
    private void login(){
        System.out.println("====登录操作====");
//        判断用户账号是否存在，存在则登录，不存在则失败
        if (accounts.isEmpty()){
            System.out.println("系统中不存在账户，请先创建账户。");
            return;
        }

        while (true) {
            System.out.println("请输入卡号：");
            String loginCardId = sc.next();
            Account account = getAccountByCardId(loginCardId);
            if (account == null){
                System.out.println("您输入的卡号错误或不存在，请确认后重新输入！");
            }else {
                while (true) {
                    System.out.println("请输入密码：");
                    String loginPassWord = sc.next();
                    if (account.getPassWord().equals(loginPassWord)){
                        System.out.println("密码正确，欢迎您。");
                        System.out.println("===========================");
                        showUserCommand(account);
                        return;
                    }else {
                        System.out.println("密码错误，请确认后重新输入。");
                    }
                }
            }
        }
    }

//    展示页面的功能设计实现
    private void showUserCommand(Account account){
        while (true) {
            System.out.println("请选择业务操作：");
            System.out.println("1.查询账户信息。");
            System.out.println("2.存款。");
            System.out.println("3.取款。");
            System.out.println("4.转账。");
            System.out.println("5.密码修改。");
            System.out.println("6.退出系统。");
            System.out.println("7.注销账户。");
            String command = sc.next();
            switch (command){
                case ("1"):
//                    查询账户信息
                    showLoginAccount(account);
                    break;
                case ("2"):
//                    存款
                    depositMoney(account);
                    break;
                case ("3"):
//                    取款
                    drawMoney(account);
                    break;
                case ("4"):
//                    转账
                    transferMoney(account);
                    break;
                case ("5"):
//                    修改账户密码
                    updatePassWord(account);
                    return;
                case ("6"):
//                    退出系统
                    System.out.println("退出账户，欢迎您再次使用ATM系统。");
                    System.out.println("===========================");
                    return;
                case ("7"):
//                    注销账户
                    if (deleteAccount(account)){
                        return;
                    }
                    break;
                default:
                    System.out.println("你的输入有误，请重新输入。");
            }
        }
    }

//    密码修改
    private void updatePassWord(Account account) {
        System.out.println("===修改账户密码操作===");
        System.out.println("请输入当前账户密码:");
        String userPassWord = sc.next();
        if (account.getPassWord().equals(userPassWord)){
            System.out.println("密码验证正确，请输入新密码：");
            while (true) {
                String newPassWord = sc.next();
                System.out.println("请再次输入新密码：");
                String againNewPassWord = sc.next();
                if (againNewPassWord.equals(newPassWord)){
                    account.setPassWord(newPassWord);
                    System.out.println("密码修改成功~~");
                    return;
                }else {
                    System.out.println("两次密码输入不一致，请重新输入~~");
                }
            }
        }
        System.out.println("密码错误，请重新验证~~");
    }

    //    删除账户
    private boolean deleteAccount(Account account) {
        System.out.println("===销户操作===");
        System.out.println("请确认是否销户，确认销户请输入【y】,否则请输入【n】");
        String command = sc.next();
        if (command.equals("y")) {
            if (account.getMoney() == 0) {
                accounts.remove(account);
                System.out.println("销户成功，江湖再见~~");
                return true;
            } else {
                System.out.println("账户中仍有存款，禁止销户，请先取出存款，再进行销户操作");
                return false;
            }
        }
        System.out.println("销户取消~~");
        return false;
    }

    //    转账操作
    private void transferMoney(Account account) {
        System.out.println("===转账操作===");
//        判断系统中是否存在两个及以上账户
        if (accounts.size() < 2){
            System.out.println("当前系统中只有一个账户，无法转账。");
            return;
        }

        if (account.getMoney() <= 0){
            System.out.println("当前账户中无存款，无法转账。");
            return;
        }

        while (true) {
            System.out.println("请输入转入账号卡号：");
            String transferCardId = sc.next();
            Account acc = getAccountByCardId(transferCardId);
            if (acc == null){
                System.out.println("您输入的卡号不存在，请确认后重新输入：");
            }else {
                String name = "*" + acc.getUserName().substring(1);
                System.out.println("请输入【" + name + "】的姓氏，以确认对方账户。");
                String preName = sc.next();
                if (acc.getUserName().startsWith(preName)){
                    while (true) {
                        System.out.println("请输入转账金额：");
                        double money = sc.nextDouble();
                        if (money > account.getMoney()){
                            System.out.println("您输入的转账金额超过您的存款，您当前存款为：" + account.getMoney() + ",请重新输入转账金额~~");
                        }else {
                            acc.setMoney(acc.getMoney() + money);
                            account.setMoney(account.getMoney() - money);
                            System.out.println("转账成功，您当前存款为：" + account.getMoney());
                            return;
                        }
                    }
                }else {
                    System.out.println("你输入的姓氏有误，请重新确认。");
                }
            }
        }
    }

    //    取款操作
    private void drawMoney(Account account) {
        System.out.println("==取款操作==");
        if (account.getMoney() < 100){
            System.out.println("当前账户余额小于100元，无法取款。");
            System.out.println("===============================");
            return;
        }

        while (true) {
            System.out.println("请输入取款金额：");
            double money = sc.nextDouble();
            if (money <= account.getLimit()){
                if (money > account.getMoney()){
                    System.out.println("取款金额大于余额，当前账户余额是：" + account.getMoney());
                    System.out.println("===============================");
                } else {
                    account.setMoney(account.getMoney() - money);
                    System.out.println("取款操作已完成，当前账户余额为：" + account.getMoney());
                    return;
                }
            }else {
                System.out.println("取款金额超过账户取款限额，当前账户取款限额为：" + account.getLimit() + "当前账户余额是:" + account.getMoney());
                System.out.println("===============================");
            }
        }

    }

    //存钱操作
    private void depositMoney(Account account) {
        System.out.println("=====存款操作=====");
        System.out.println("请输入存款金额：");
        double money = sc.nextDouble();
        account.setMoney(account.getMoney() + money);
        System.out.println("已存入" + money + ",当前余额为" + account.getMoney());
        System.out.println("===============================");
    }

    //    展示用户信息
    private void showLoginAccount(Account account){
        System.out.println("=====用户信息展示=====");
        System.out.println("用户名：" + account.getUserName());
        System.out.println("用户卡号：" + account.getCardId());
        System.out.println("用户性别：" + account.getSex());
        System.out.println("用户余额：" + account.getMoney());
        System.out.println("用户取款限额：" + account.getLimit());
        System.out.println("===========================");
    }
}
