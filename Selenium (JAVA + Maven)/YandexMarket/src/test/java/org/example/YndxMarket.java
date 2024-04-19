package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Set;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

public class YndxMarket {
    public static void main(String[] args) {
// Устанавливаем путь к драйверу Chrome
        System.setProperty("webdriver.chrome.driver", "W:\\chromedriver\\chromedriver.exe");

// Инициализируем WebDriver
        WebDriver driver = new ChromeDriver();
// Максимизируем окно браузера
        driver.manage().window().maximize();
// Устанавливаем неявное ожидание
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
// Создаем объект WebDriverWait с таймаутом 10 секунд
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
// Создаем объект JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;


        try{
        driver.get("https://yandex.ru/");

        WebElement yandexSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label=\"Поиск Яндекса\"]")));
        yandexSearch.click();

        driver.switchTo().frame(yandexSearch);
        WebElement yndxMarketLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/form/div[1]/div/div/div/ul/li[2]/a")));
        yndxMarketLink.click();

        String currentWindowHandle = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        handles.remove(currentWindowHandle);
        String newTab = handles.iterator().next();
        driver.switchTo().window(newTab);

        WebElement searchForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#header-search")));
        searchForm.click();
        searchForm.sendKeys("Сотовые телефоны");
        WebElement searchBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span._2bsQ5:nth-child(1)")));
        searchBtn.click();

// Ожидание полной загрузки страницы
        new WebDriverWait(driver, Duration.ofSeconds(30)).until((ExpectedCondition<Boolean>) webDriver -> js.executeScript("return document.readyState").equals("complete"));
// Находим и кликаем кнопку "Еще фильтры"

        WebElement moreFiltersBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/a/button")));
        js.executeScript("arguments[0].scrollIntoView(true);", moreFiltersBtn);
        moreFiltersBtn.click();

// Ожидаем, пока появится поле для ввода максимальной цены и вводим значение
        WebElement priceMaxInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[1]/div[1]/div/div/div/div[2]/input")));
        priceMaxInput.sendKeys("20000");

// Находим и кликаем кнопку выбора диагонали
        WebElement diagonalBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[2]/div[3]/div/button/h4")));
        diagonalBtn.click();

// Вводим значение диагонали
        WebElement diagonalInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div[3]/div/div/div/div[1]/input")));
        diagonalInput.sendKeys("3");

// Находим и кликаем кнопку "Показать"
        WebElement showElementsBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[3]/div/div/a[2]")));
        showElementsBtn.click();

// Ожидаем, чтобы страница обновилась
        new WebDriverWait(driver, Duration.ofSeconds(30)).until((ExpectedCondition<Boolean>) webDriver -> js.executeScript("return document.readyState").equals("complete"));

        List<WebElement> manufacturers = new ArrayList<>();
        int desiredManufacturerCount = 5;
        while (manufacturers.size() < desiredManufacturerCount) {
            // Находим все производителей, которые видны на странице
            List<WebElement> visibleManufacturers = driver.findElements(By.cssSelector("div.vyAUx:nth-child(9) > div:nth-child(1) > fieldset:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div"));
            // Добавляем найденные производителей в список
            manufacturers.addAll(visibleManufacturers);
        }
        System.out.println("Выбрано производителей: " + manufacturers.size());
// Когда найдено достаточно производителей, кликаем на них
        for (int i = 0; i < desiredManufacturerCount; i++) {
            WebElement manufacturer = manufacturers.get(i);
            manufacturer.click();
        }
        js.executeScript("window.scrollTo(0, 0);");


        WebElement first_element_in_list = driver.findElement(By.xpath("//div[2]/div/div/div/article/div/div/div/div/div[2]/div[1]/div/a/h3"));

        String text = first_element_in_list.getText();
        System.out.println("Текст из элемента: " + text);

        int elements_count = driver.findElements(By.xpath("//button[@aria-label='В корзину']/span")).size();

        System.out.println("Обнаружено элементов на странице: " + elements_count);

        WebElement sortByRating = driver.findElement(By.cssSelector("button._3_4D9:nth-child(5)"));
        sortByRating.click();
        WebElement clearForm = driver.findElement(By.cssSelector(".cf3XW>._2pqda"));
        clearForm.click();

        WebElement searchForm2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"header-search\"]")));
        searchForm2.sendKeys(text);

        WebElement btn2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div[1]/button/span")));
        btn2.click();
        Thread.sleep(3000);

        WebElement knownElement = driver.findElement(By.xpath("//div[2]/div/div/div/article/div/div/div/div/div[5]/a"));
        knownElement.click();


        Set<String> allWindowHandles = driver.getWindowHandles();
// Переключаемся на последнюю вкладку в списке, которая считается новой
        for (String handle : allWindowHandles) {driver.switchTo().window(handle);}

        WebElement rating = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[3]/div[2]/div/div[5]/div/div/div/div/div/a/span[1]")));
        String rating_text = rating.getText();
        System.out.println("Оценка товара на маркете: " + rating_text);

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        driver.quit();
    }
    }
}
