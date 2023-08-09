package ru.netology.Cucumber.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.Before;
import ru.netology.Cucumber.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.Cucumber.data.DataHelper.getAuthInfo;
import static ru.netology.Cucumber.data.DataHelper.getVerificationCode;

public class LoginPage {

    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");

    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashboardPage dashboardPage;


//    public void login() {
//        var loginPage = open("http://localhost:9999", LoginPage.class);
//        var authInfo = getAuthInfo();
//        var verificationPage = loginPage.validLogin(authInfo);
//        var verificationCode = getVerificationCode();
//        dashboardPage = verificationPage.validVerify(verificationCode);
//    }

//    public VerificationPage validLogin(DataHelper.AuthInfo info) {
//        loginField.setValue(info.getLogin());
//        passwordField.setValue(info.getPassword());
//        loginButton.click();
//        return new VerificationPage();
//    }

    public VerificationPage validLogin(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        return new VerificationPage();
    }

//    public VerificationPage verifyLogin(DataHelper.AuthInfo info) {
//        validLogin(DataHelper.AuthInfo info);
//        verificationPage = loginPage.validLogin(authInfo);
//        DataHelper.VerificationCode verificationCode;
//        verificationCode = getVerificationCode();
//        dashboardPage = verificationPage.validVerify(verificationCode);
//        return new VerificationPage();
//    }
}
