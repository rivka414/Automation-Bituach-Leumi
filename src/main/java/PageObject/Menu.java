package PageObject;

public enum Menu {
    Exhaustion_of_Rights("מיצוי זכויות", "ctl00_Topmneu_BenefitsHyperLink"),
    Allowances_and_Benefits("קצבאות והטבות", "ctl00_Topmneu_HyperLink3"),
    Insurance_Contributions("דמי ביטוח", "ctl00_Topmneu_InsuranceHyperLink"),
    Contact_Us("יצירת קשר", "ctl00_Topmneu_ContactsHyperLink");

    private final String menuItem;
    private final String id; // המשתנה החדש עבור ה-ID

    // עדכון הקונסטרקטור שיקבל שני פרמטרים
    Menu(String menuItem, String id) {
        this.menuItem = menuItem;
        this.id = id;
    }

    public String getMenuItem() {
        return this.menuItem;
    }

    public String getId() {
        return this.id;
    }

}
