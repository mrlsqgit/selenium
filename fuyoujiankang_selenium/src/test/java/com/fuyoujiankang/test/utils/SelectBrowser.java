package com.fuyoujiankang.test.utils;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;

/**
 * @author Jone
 * @decription 在不同的平台上选择对应的浏览器,系统平台程序自动判断是什么平台
 * */
public class SelectBrowser {
	static Logger logger = Logger.getLogger(SelectBrowser.class.getName());

	public WebDriver selectExplorerByName(String browser, ITestContext context) {
		Properties props = System.getProperties(); // 获得系统属性集
		String currentPlatform = props.getProperty("os.name"); // 操作系统名称
		logger.info("当前操作系统是:[" + currentPlatform + "]");
		logger.info("启动测试浏览器：[" + browser + "]");
		//从testNG的配置文件读取参数driverConfgFilePath的值
		String driverConfgFilePath = context.getCurrentXmlTest().getParameter("driverConfgFilePath");
		/** 声明好驱动的路径 */
		String chromedriver_win = PropertiesDataProvider.getTestData(driverConfgFilePath, "chromedriver_win");
		String chromedriver_linux = PropertiesDataProvider.getTestData(driverConfgFilePath, "chromedriver_linux");
		String chromedriver_mac = PropertiesDataProvider.getTestData(driverConfgFilePath, "chromedriver_mac");
		String ghostdriver_win = PropertiesDataProvider.getTestData(driverConfgFilePath, "ghostdriver_win");
		String iedriver = PropertiesDataProvider.getTestData(driverConfgFilePath, "iedriver");
		if (currentPlatform.toLowerCase().contains("win")) { //如果是windows平台
			if (browser.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", iedriver);
				//IE的常规设置，便于执行自动化测试
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				//返回ie浏览器对象
				return new InternetExplorerDriver(ieCapabilities);
			} else if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", chromedriver_win);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("disable-infobars");
				//返回谷歌浏览器对象
				 return new ChromeDriver(options);
			} else if (browser.equalsIgnoreCase("firefox")) {
				//返回火狐浏览器对象
				return new FirefoxDriver();

			} else if(browser.equalsIgnoreCase("ghost")){
				DesiredCapabilities ghostCapabilities = new DesiredCapabilities();
				ghostCapabilities.setJavascriptEnabled(true);                       
				ghostCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, ghostdriver_win);
				//返回ghost对象
			    return new PhantomJSDriver(ghostCapabilities);
				
			}else {
				logger.error("The [" + browser + "]" + " explorer is not applicable  for  [" + currentPlatform + "] OS");
				Assert.fail("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
			}

		} else if (currentPlatform.toLowerCase().contains("linux")) { //如果是linux平台
			if (browser.equalsIgnoreCase("chrome")) {
//				System.setProperty("webdriver.chrome.driver",  chromedriver_linux);
                //设置chromedriver在linux中的绝对路径   要在/usr/bin目录执行   sudo chmod a+x chromedriver
                //给chromedriver设置执行权限
                System.setProperty("webdriver.chrome.driver",  "/usr/bin/chromedriver");
				ChromeOptions chromeOptions = new ChromeOptions();
				//设置为 headless 模式 （必须）
				chromeOptions.addArguments("--headless");
				//设置浏览器窗口打开大小  （非必须）
				chromeOptions.addArguments("--window-size=1920,1080");
				return new ChromeDriver(chromeOptions);
			} else if (browser.equalsIgnoreCase("firefox")) {
				return new FirefoxDriver();
			} else {
				logger.error("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
				Assert.fail("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
			}
		} else if (currentPlatform.toLowerCase().contains("mac")) { //如果是mac平台
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", chromedriver_mac);
				return new ChromeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				return new FirefoxDriver();
			} else {
				logger.error("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
				Assert.fail("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
			}
		} else {
            logger.error("The [" + currentPlatform + "] is not supported for this automation frame,please change the OS(Windows,MAC or LINUX)");
            Assert.fail("The [" + currentPlatform + "] is not supported for this automation frame,please change the OS(Windows,MAC or LINUX)");
		}
            return null;
	}
}
