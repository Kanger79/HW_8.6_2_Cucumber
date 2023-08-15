package ru.netology.Cucumber.page;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private static final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private static final SelenideElement amountInputNew = $("[data-test-id='amount'] input");
    private static final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement errorMessage = $("[data-test-id='action-cancel']");

    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public static void makeTransfer(int amountToTransfer, String cardNumber) {
        amountInputNew.setValue(String.valueOf(amountToTransfer));
        fromInput.setValue(cardNumber);
        transferButton.click();
    }

}

