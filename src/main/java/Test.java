import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by yaonengjun on 2017/6/16 下午8:43.
 */
public class Test {

  public static void main(String[] args) throws InterruptedException, IOException {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setJavascriptEnabled(true);
    capabilities.setCapability("takeScreenshot", true);
    capabilities.setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            "/Users/neyao/dev/phtantomjs/phantomjs-2.1.1-macosx/bin/phantomjs");

    long t1 = System.currentTimeMillis();
    PhantomJSDriver driver = new PhantomJSDriver(capabilities);
//    driver.get("https://www.itslaw.com/bj");
    driver.get("http://angularjs.cn");
//    driver.get("http://465.vip");
    driver.executePhantomJS("var page=this;return page.pageResources = {};");

    //第一种等待渲染的方式：等待固定的十秒
//    Thread.sleep(10000);

    //第二种等待渲染的方式：最长等待十秒
    driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

    //第三种等待渲染的方式：等到DOM返回渲染完成的消息
//    new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
//            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

    long t2 = System.currentTimeMillis();


    System.out.println();
    System.out.println("--- title ---");
    System.out.println(driver.getTitle());
    System.out.println("--------");

    System.out.println("--- html ---");
    System.out.println(driver.getPageSource());
    System.out.println("--------");
    System.out.println("===>done, time cost:" + (t2-t1) +" ms");


    // 截图
    byte[] bytes = driver.getScreenshotAs(OutputType.BYTES);
    File file = OutputType.FILE.convertFromPngBytes(bytes);
    boolean created = file.createNewFile();
    System.out.println("created: " + created +"; file: " + file.getAbsolutePath());
    //MAC下，默认生成在/var/folders/7j/9rf_9fnn0pl01c0r7jd7nc2h0000gn/T/screenshot5549327564759194523.png

    driver.close();
  }
}
