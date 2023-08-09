package ru.netology.Cucumber.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.Cucumber.data.DataHelper;
import ru.netology.Cucumber.page.DashboardPage;
import ru.netology.Cucumber.page.LoginPage;
import ru.netology.Cucumber.page.TransferPage;
import ru.netology.Cucumber.page.VerificationPage;


public class TemplateSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;


    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @Когда("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("пользователь вводит проверочный код 'из смс' {string}")
    public void setValidCode(String verificationCode) {
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Тогда("происходит успешная авторизация и пользователь попадает на страницу 'Личный кабинет'")
    public void verifyDashboardPage() {
        dashboardPage.verifyIsDashboardPage();
    }

    @Тогда("появляется ошибка о неверном коде проверки")
    public void verifyCodeIsInvalid() {
        verificationPage.verifyCodeIsInvalid();
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою первую карту")
    public void makeTransferTo(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        transferPage.transferFromSecondToFirst(amountToTransfer, DataHelper.getSecondCardInfo());
//        DataHelper.getFirstCardInfo();
    }

    @Пусть("пользователь заходит на страницу пополнения карты {string}")
    public void goTransferPage(DataHelper.CardInfo cardInfo) {
        dashboardPage.selectCardToTransfer(cardInfo);
    }
}
