package page;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;

import java.util.List;

public class BasePage extends App {

    private static AndroidDriver driver = getDriver();

    static MobileElement findElementById(String using) {
        return (MobileElement) driver.findElementById("com.caibaopay.cashier:id/" + using);
    }

    static MobileElement findElementByXpath(String using) {
        return (MobileElement) driver.findElementByXPath(using);
    }

    static List<MobileElement> findElementsByXpath(String using) {
        return driver.findElementsByXPath(using);
        //return (List<MobileElement>) driver.findElementsByXPath(using);
    }




    static boolean isElementPresent(String by, String using) {
        try {
            switch (by) {
                case "By.Id":
                    findElementById(using);
                    break;
                case "By.Xpath":
                    findElementByXpath(using);
                    break;
                default:
                    System.out.println("查找类型错误");
                    return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected static void touchKeyboard(int xPoint, int yPoint) {
        (new TouchAction(driver)).tap(PointOption.point(xPoint, yPoint)).perform();
    }

    static void swipeByCoordinateWithElement(String element, double startX, double endX, double startY, double endY) {
        //获取屏幕大小
        Dimension screenSize = driver.manage().window().getSize();

        while (true) {
            try {
                (new TouchAction(driver))
                        .longPress(PointOption.point((int) (screenSize.width * startX), (int) (screenSize.height * startY)))
                        .moveTo(PointOption.point((int) (screenSize.width * endX), (int) (screenSize.height * endY)))
                        .release()
                        .perform();
            } catch (Exception e) {
                if (startX >= 1 || startX <= 0) {
                    System.out.println("超出屏幕边界，x起始坐标必须小于1且大于0");
                } else if (endX >= 1 || endX <= 0) {
                    System.out.println("超出屏幕边界，x终点坐标必须小于1且大于0");
                } else if (startY >= 1 || startY <= 0) {
                    System.out.println("超出屏幕边界，y起始坐标必须小于1且大于0");
                } else if (endY >= 1 || endY <= 0) {
                    System.out.println("超出屏幕边界，y终点坐标必须小于1且大于0");
                }
                return;
            }
            String pageSource = driver.getPageSource();
            //判断元素是否存在，存在则不等于-1,String.indexOf(xxxx)返回包含该字符串在父类字符串中起始位置，不包含则全部返回-1
            if (pageSource.contains(element)) {
                return;
            }
        }
    }
}
