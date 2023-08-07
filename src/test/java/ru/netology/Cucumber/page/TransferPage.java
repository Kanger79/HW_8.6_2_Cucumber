package ru.netology.Cucumber.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.Cucumber.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement depositButton = $("[data-test-id='action-deposit']");
    private final SelenideElement amountInputNew = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement errorMessage = $("[data-test-id='action-cancel']");

    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    private final SelenideElement cancel = $("[data-test-id='action-cancel']");


    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInputNew.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void findErrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText), Duration.ofSeconds(5)).shouldBe(visible);
    }

    public void findCancel() {
        cancel.click();
    }
}

