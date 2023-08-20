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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.Cucumber.data.DataHelper.getFirstCardInfo;
import static ru.netology.Cucumber.data.DataHelper.getSecondCardInfo;


public class TemplateSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;

    private int firstCardBalanceBefore;
    private int secondCardBalanceBefore;

    public void setFirstCardBalanceBefore(int firstCardBalanceBefore) {
        this.firstCardBalanceBefore = dashboardPage.getCardBalance(getFirstCardInfo());
    }

    public int getFirstCardBalanceBefore() {
        return firstCardBalanceBefore;
    }

    public void setSecondCardBalanceBefore(int secondCardBalanceBefore) {
        this.secondCardBalanceBefore = dashboardPage.getCardBalance(getSecondCardInfo());
    }

    public int getSecondCardBalanceBefore() {
        return secondCardBalanceBefore;
    }


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
        dashboardPage = new DashboardPage();
        setFirstCardBalanceBefore(getFirstCardBalanceBefore());
        setSecondCardBalanceBefore(getSecondCardBalanceBefore());
        System.out.println("firstCardBalanceBefore = " + getFirstCardBalanceBefore());
        System.out.println("secondCardBalanceBefore = " + getSecondCardBalanceBefore());
    }

    @Тогда("появляется ошибка о неверном коде проверки")
    public void verifyCodeIsInvalid() {
        verificationPage.verifyCodeIsInvalid();
    }


    @Пусть("пользователь заходит на страницу пополнения карты с номером {int}")
    public void goTransferPage(int index) {
        dashboardPage.selectCard(index);

    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на выранную ранее карту")
    public void makeTransfer(int amountToTransfer, String cardNumber) {
        TransferPage transferPage = new TransferPage();
        transferPage.makeTransfer(amountToTransfer, cardNumber);
    }


    @Тогда("баланс пополняемой карты должен стать больше на {int} рублей")
    public void verifyBalance(int amount) {
        dashboardPage = new DashboardPage();
        int firstCardBalance = dashboardPage.getCardBalance(getFirstCardInfo());
        int secondCardBalance = dashboardPage.getCardBalance(getSecondCardInfo());
        assertEquals(firstCardBalanceBefore + amount, firstCardBalance);
        assertEquals(secondCardBalanceBefore - amount, secondCardBalance);
        System.out.println("firstCardBalance = " + dashboardPage.getCardBalance(getFirstCardInfo()));
        System.out.println("secondCardBalance = " + dashboardPage.getCardBalance(getSecondCardInfo()));
    }
}
