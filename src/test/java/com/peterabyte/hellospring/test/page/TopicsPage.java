package com.peterabyte.hellospring.test.page;

import com.peterabyte.hellospring.test.SmokeTestConfig;
import com.peterabyte.hellospring.test.service.WebDriverService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertEquals;

@Component
public class TopicsPage extends BasePage {
    
    @Autowired
    public TopicsPage(WebDriverService webDriverService, SmokeTestConfig smokeTestConfig) {
        super(webDriverService, smokeTestConfig);
    }

    @Override
    public void navigateTo() {
        WebDriver webDriver = webDriverService.getWebDriver();
        webDriver.navigate().to(smokeTestConfig.getUrl() + "/topics");
        assertEquals("Hello Spring Boot - Topics", webDriver.getTitle());
        PageFactory.initElements(webDriver, this);
    }

    public int getNumberOfComments() {
        return webDriverService.getWebDriver().findElements(By.className("comment-card")).size();
    }
}
