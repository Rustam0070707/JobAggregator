package com.RusGruz.JobAggregator.Scraper;

import com.RusGruz.JobAggregator.Models.Job;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RemoteScraper implements JobScraper {
    private static final String URL = "https://remoteok.com/remote-java-jobs";

    @Override
    public List<Job> scrapeJobs() {
        List<Job> jobs = new ArrayList<>();
        WebDriver driver = null;

        try {
            // Setup Chrome options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless"); // Run without opening browser window
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            // Initialize driver
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Navigate to URL
            driver.get(URL);

            // Wait for job listings to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr.job")));

            // Small delay to ensure all content loads
            Thread.sleep(2000);

            // Find all job rows
            List<WebElement> jobRows = driver.findElements(By.cssSelector("tr.job"));

            System.out.println("Found " + jobRows.size() + " jobs");

            for (WebElement jobRow : jobRows) {
                try {
                    // Extract job details
                    String title = jobRow.findElement(By.cssSelector("h2[itemprop='title']")).getText();
                    String company = "";
                    String location = "";
                    String link = "";

                    // Try to get company name
                    try {
                        company = jobRow.findElement(By.cssSelector("h3[itemprop='name']")).getText();
                    } catch (Exception e) {
                        company = "N/A";
                    }

                    // Try to get location
                    try {
                        location = jobRow.findElement(By.cssSelector("div.location")).getText();
                    } catch (Exception e) {
                        location = "Remote";
                    }

                    // Try to get link
                    try {
                        String href = jobRow.findElement(By.cssSelector("a.preventLink")).getAttribute("href");
                        link = href.startsWith("http") ? href : "https://remoteok.com" + href;
                    } catch (Exception e) {
                        link = URL;
                    }

                    if (!title.isEmpty()) {
                        Job job = new Job();
                        job.setTitle(title);
                        job.setCompany(company);
                        job.setLocation(location);
                        job.setSource("RemoteOk");
                        job.setUrl(link);
                        jobs.add(job);

                        System.out.println("Scraped: " + title + " at " + company);
                    }

                } catch (Exception e) {
                    System.err.println("Error parsing individual job: " + e.getMessage());
                    continue;
                }
            }

        } catch (Exception e) {
            System.err.println("Error during scraping: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Always close the browser
            if (driver != null) {
                driver.quit();
            }
        }

        System.out.println("Total jobs scraped: " + jobs.size());
        return jobs;
    }
}