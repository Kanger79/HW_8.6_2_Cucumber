package ru.netology.Cucumber.steps;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.Cucumber.data.DataHelper;
import ru.netology.Cucumber.page.DashboardPage;
import ru.netology.Cucumber.page.LoginPage;
import ru.netology.Cucumber.page.VerificationPage;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.Cucumber.data.DataHelper.*;


public class TemplateSteps {

    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement depositButton = $("[data-test-id='action-deposit']");
    private final SelenideElement amountInputNew = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement errorMessage = $("[data-test-id='action-cancel']");
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;


    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPass(String arg0, String arg1) {
        loginPage = open("http://localhost:9999", LoginPage.class);
        DataHelper.AuthInfo authInfo = getAuthInfo();
        verificationPage = loginPage.validLogin(authInfo);
        DataHelper.VerificationCode verificationCode;
        verificationCode = getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }


    @Когда("пользователь переводит {int} рублей с карты с номером {int} {int} {int} {int} на свою первую карту с главной страницы")
    public void transferCards(int arg0, int arg1, int arg2, int arg3, int arg4) {
        depositButton.click();
        amountInputNew.setValue("5000");
        fromInput.setValue(getSecondCardInfo().getCardNumber());
        transferButton.click();
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void assertBalanceOfCards(int arg0, int arg1) {
        var amount = 5000;
        var secondCardBalance = 10000;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var firstCardBalance = 10000;
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var actualBalanceFirstCard = dashboardPage.getCardBalance(getFirstCardInfo());
        var actualBalanceSecondCard = dashboardPage.getCardBalance(getSecondCardInfo());
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);

    }
}
