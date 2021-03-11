package com.knoldus

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, Wait}
import org.scalatestplus.testng.TestNGSuite
import org.testng.Reporter
import org.testng.annotations.Test

class AjaxSpec extends TestNGSuite {

  System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_linux64/chromedriver")

  val driver = new ChromeDriver()
  val URL = "http://demo.guru99.com/test/ajax.html"
  val EMAIL = "sparshbhardwaj10@gmail.com"

  val fluentWait: Wait[WebDriver] = new FluentWait[WebDriver](driver)
  //.withTimeout(20)
  // .pollingEvery(100,TimeUnit.MILLISECONDS)


  @Test(enabled = true)
  def ajaxFirstTest(): Unit = {

    driver.manage().window().maximize()
    driver.get(URL)
    driver.findElementByCssSelector("#yes").click()
    driver.findElementByCssSelector("#buttoncheck").click()
    fluentWait.until(ExpectedConditions.visibilityOf(driver.findElementByCssSelector("body > div.container > form > p.radiobutton")))
    Reporter.log("ajax tested")
    Thread.sleep(1000)

  }


  @Test(enabled = true)
  def ajaxSecondTest(): Unit = {

    driver.manage().window().maximize()
    driver.get(URL)
    val textBefore = driver.findElementByCssSelector("body > div.container > form > p.radiobutton")
    val before = textBefore.getText.trim
    driver.findElementByCssSelector("#no").click()
    driver.findElementByCssSelector("#buttoncheck").click()
    fluentWait.until(ExpectedConditions.visibilityOf(driver.findElementByCssSelector("body > div.container > form > p.radiobutton")))
    val textAfter = driver.findElementByCssSelector("body > div.container > form > p.radiobutton")
    val after = textAfter.getText.trim
    assert(before != after)
    Thread.sleep(2000)

  }

  @Test(enabled = true)
  def child_Window_Or_PopUP(): Unit = {

    driver.manage().window().maximize()
    driver.get("http://demo.guru99.com/popup.php")
    driver.findElementByCssSelector("body > p > a").click()
    Thread.sleep(5000)

    val mainWindow = driver.getWindowHandle
    val allWindows = driver.getWindowHandles
    val i = allWindows.iterator()


    while (i.hasNext) {

      val childWindow: String = i.next()

      if (!mainWindow.equalsIgnoreCase(childWindow)) {
        driver.switchTo().window(childWindow)

        driver.findElementByName("emailid").sendKeys(EMAIL)
        Reporter.log("email entered")
        driver.findElementByCssSelector("body > form > table > tbody > tr:nth-child(6) > td:nth-child(2) > input[type=submit]").click()
        Reporter.log("button clicked")
        driver.close()
      }
    }
driver.switchTo().window(mainWindow)
  }

  @Test(enabled = true)
  def iframes(): Unit = {
    driver.manage().window().maximize()
    driver.get("http://demo.guru99.com/test/guru99home/")
    driver.switchTo().frame("a077aa5e")
    driver.findElementByCssSelector("body > a:nth-child(1) > img:nth-child(1)").click()

    val mainWindow = driver.getWindowHandle

    val allWindows = driver.getWindowHandles
    val i = allWindows.iterator()

    while (i.hasNext) {
      val childWindow = i.next()

      if (!mainWindow.equalsIgnoreCase(childWindow)) {
        driver.switchTo().window(childWindow)
        driver.findElementByCssSelector(".item-page > div:nth-child(4) > p:nth-child(16) > a:nth-child(1)").click()
      }
    }
    driver.close()

    driver.switchTo().window(mainWindow)
    driver.quit()
  }


}
