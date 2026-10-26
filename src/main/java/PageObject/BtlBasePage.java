package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BtlBasePage extends BasePages{
    @FindBy(css = ".breadcrumbs, #ctl00_SiteMapPath") // ה-Selector עשוי להשתנות לפי מבנה האתר
    private WebElement breadcrumbs;


    @FindBy(id = "TopQuestions")
    private WebElement inputSearch;

    @FindBy(id = "ctl00_SiteHeader_reserve_btnSearch")
    private WebElement clickSearch;

    @FindBy(id = "ctl00_Topmneu_BranchesHyperLink")
    private WebElement bankBranches;

    private static final Duration DEFAULT_WAIT = Duration.ofSeconds(10);

    public BtlBasePage(WebDriver driver) {

        super(driver);
    }

    public void acceptEnum(Menu menu)
    {
        String menuId = menu.getId();
        String menuText = menu.getMenuItem();
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);

        try {
            WebElement menuItem = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id(menuId))
            );

            menuItem.click();

        } catch (Exception e) {
            throw new RuntimeException("לא הצלחתי ללחוץ על תפריט: " + menuText + " עם מזהה: " + menuId, e);
        }
    }

    public void acceptSubMenu(String subMenuLocator) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);
        WebElement subMenuItem;

        try {
            if (subMenuLocator.startsWith("/") || subMenuLocator.startsWith("(") ) {
                // אם זה נראה כמו XPath
                subMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(subMenuLocator)));
            } else {
                // אם זה טקסט חופשי
                subMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(subMenuLocator)));
            }

            subMenuItem.click();

        } catch (Exception e) {
            throw new RuntimeException("לא הצלחתי ללחוץ על תת תפריט באמצעות המזהה: " + subMenuLocator, e);
        }
    }

    public void search(String searchName)
    {
        inputSearch.clear();
        inputSearch.sendKeys(searchName);
        clickSearch.click();
    }

    public BranchesAndServiceChannelsPage BankBranches()
    {
        bankBranches.click();
        return new BranchesAndServiceChannelsPage(driver);
    }


    public InsuranceFeesPage goToInsuranceFees() {
        acceptEnum(Menu.Insurance_Contributions);
        acceptSubMenu("//*[@id=\"ctl00_Topmneu_Insurance\"]/ul/li[1]/a");
        return new InsuranceFeesPage(driver);
    }

    public UnemploymentPage goToUnemployment() {
        acceptEnum(Menu.Allowances_and_Benefits); // לחיצה על קצבאות והטבות
        acceptSubMenu("//*[@id=\"ctl00_Topmneu_Benefits\"]/div/div[1]/div[5]/ul/li[1]/a");
        return new UnemploymentPage(driver);
    }

    public String getBreadcrumbsText() {
        return wait.until(ExpectedConditions.visibilityOf(breadcrumbs)).getText();
    }
}
