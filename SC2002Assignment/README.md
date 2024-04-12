# Folder Structure

|   branch_list.txt
|   branch_list.xlsx
|   Fastfood ordering and management System (FOMS) SC2002 Assignment 2023S2.pdf
|   LICENSE
|   menu_list.txt
|   menu_list.xlsx
|   orders_serialize.txt
|   payments.txt
|   README.md
|   staff_list.xlsx
|   staff_list_with_pw.txt
|
+---.vscode
|       settings.json
|
\---SC2002Assignment
    |   README.md
    |
    +---bin
    |   |   App.class
    |   |   Main.class
    |   |
    |   +---controller
    |   |       AuthenticationController.class
    |   |       LoginController.class
    |   |
    |   +---model
    |   |   |   Staff.class
    |   |   |
    |   |   \---abstracts
    |   |           AUser.class
    |   |
    |   \---view
    |       |   LoginView.class
    |       |   OrderView.class
    |       |
    |       \---interfaces
    |               ViewInterface.class
    |
    \---src
        |   App$DataFiles.class
        |   App.java
        |   Main.java
        |
        +---controller
        |   |   AdminController.java
        |   |   AuthenticationController.java
        |   |   CustomerController.java
        |   |   LoginController.java
        |   |   ManagerController.java
        |   |   OrderMenuController.java
        |   |   StaffController.java
        |   |
        |   \---abstracts
        |           AController.java
        |
        +---model
        |   |   AdminRole.java
        |   |   Branch.java
        |   |   BranchDataManager.java
        |   |   EmployeeDataManager.java
        |   |   EmployeeFilter.java
        |   |   EmployeeHandler.java
        |   |   ManagerRole.java
        |   |   Order$OrderStatus.class
        |   |   Order.java
        |   |   ResetPassword.java
        |   |   SerializationManager.java
        |   |   StaffRole.java
        |   |   UpdateMenu.java
        |   |
        |   +---abstracts
        |   |       AEmployee.java
        |   |
        |   +---interfaces
        |   |       IMenuHandler.java
        |   |
        |   +---menus
        |   |       MenuHandler.java
        |   |       MenuItem.java
        |   |       SetMealCategory.java
        |   |
        |   \---payments
        |           Alipay.class
        |           Alipay.java
        |           CreditCardPayment.java
        |           DebitCardPayment.java
        |           GenerateNewPayment.java
        |           IPaymentProcessor.java
        |           OCBC.class
        |           OCBC.java
        |           PaymentMethodFactory.java
        |           PayNowPayment.java
        |
        \---view
            |   AdminHomePageView.java
            |   BranchView.java
            |   CustomerHomePageView.java
            |   LoginView.java
            |   ManagerHomePageView.java
            |   MenuView.java
            |   OrderMenuView.java
            |   OrderView.java
            |   ReceiptView.java
            |   StaffHomePageView.java
            |
            +---abstracts
            |       ARenderPayment.java
            |       ARenderView.java
            |
            +---interfaces
            |       ViewInterface.java
            |
            \---payments
                    PaymentView.java
