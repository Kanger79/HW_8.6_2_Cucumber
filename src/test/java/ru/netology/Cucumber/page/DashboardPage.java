package ru.netology.Cucumber.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.Cucumber.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.Cucumber.data.DataHelper.*;

public class DashboardPage {

    private static final String balanceStart = "баланс: ";
    private static final String balanceFinish = " р.";
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private static final ElementsCollection cards = $$(".list__item div");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public static int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = cards.findBy(Condition.text(cardInfo.getCardNumber().substring(15))).getText();
        return extractBalance(text);
    }


    private static int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void selectCard(int index) {
        cards.get(index);
        $("button").click();
        new TransferPage();

    }

    public void verifyIsDashboardPage() {
    }

    public static void verifyBalance(int remains) {
        var firstCardInfo = getFirstCardInfo();
        var actualBalanceFirstCard = getCardBalance(firstCardInfo);
        assertEquals(remains, actualBalanceFirstCard);

    }

}

