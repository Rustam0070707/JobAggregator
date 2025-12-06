package com.RusGruz.JobAggregator.Scraper;

import com.RusGruz.JobAggregator.Models.Job;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class LinkedInJobScraper implements JobScraperLinkedin {

    public List<Job> scrapeJobs(String keyword, String location) {
        List<Job> jobs = new ArrayList<>();

        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless");
        opts.addArguments("--disable-blink-features=AutomationControlled");
        opts.addArguments("--no-sandbox");
        opts.addArguments("--disable-dev-shm-usage");
        opts.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(opts);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            String url = String.format(
                    "https://www.linkedin.com/jobs/search/?keywords=%s&location=%s",
                    keyword.replace(" ", "%20"),
                    location.replace(" ", "%20")
            );

            driver.get(url);

            // Scroll to load more jobs
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(2000);

            // Wait for job cards to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("ul.jobs-search__results-list li")
            ));

            List<WebElement> jobCards = driver.findElements(
                    By.cssSelector("ul.jobs-search__results-list li")
            );

            System.out.println("Found " + jobCards.size() + " job cards");

            for (int i = 0; i < Math.min(jobCards.size(), 25); i++) {
                try {
                    WebElement card = jobCards.get(i);

                    // Scroll element into view
                    js.executeScript("arguments[0].scrollIntoView(true);", card);
                    Thread.sleep(500);

                    Job job = new Job();

                    // Try multiple selectors for title
                    String title = extractText(card, new String[]{
                            "h3.base-search-card__title",
                            ".base-card__full-link",
                            "a[data-tracking-control-name*='public_jobs']"
                    });
                    job.setTitle(title);

                    // Try multiple selectors for company
                    String company = extractText(card, new String[]{
                            "h4.base-search-card__subtitle",
                            "a.hidden-nested-link",
                            ".base-search-card__subtitle a"
                    });
                    job.setCompany(company);

                    // Try multiple selectors for location
                    String loc = extractText(card, new String[]{
                            "span.job-search-card__location",
                            ".base-search-card__metadata span",
                            "span.base-search-card__metadata"
                    });
                    job.setLocation(loc);

                    // Get job URL
                    String jobUrl = extractAttribute(card, "href", new String[]{
                            "a.base-card__full-link",
                            "a[data-tracking-control-name*='public_jobs']",
                            ".base-card a"
                    });
                    job.setUrl(jobUrl);

                    // Try to get posted date
                    String posted = extractText(card, new String[]{
                            "time",
                            ".job-search-card__listdate",
                            "time.job-search-card__listdate"
                    });
                    job.setPostDate(parseRelativeDate(posted));
                    job.setSource("Linkedin");

                    // Only add if we got at least title and URL
                    if (title != null && !title.isEmpty() && jobUrl != null && !jobUrl.isEmpty()) {
                        jobs.add(job);
                        System.out.println("Scraped: " + title + " at " + company);
                    }

                } catch (Exception e) {
                    System.err.println("Error processing job card: " + e.getMessage());
                    continue;
                }
            }

        } catch (Exception e) {
            System.err.println("Error during scraping: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return jobs;
    }

    private String extractText(WebElement parent, String[] selectors) {
        for (String selector : selectors) {
            try {
                WebElement elem = parent.findElement(By.cssSelector(selector));
                String text = elem.getText().trim();
                if (!text.isEmpty()) {
                    return text;
                }
            } catch (Exception e) {
            }
        }
        return "";
    }

    private String extractAttribute(WebElement parent, String attr, String[] selectors) {
        for (String selector : selectors) {
            try {
                WebElement elem = parent.findElement(By.cssSelector(selector));
                String value = elem.getAttribute(attr);
                if (value != null && !value.isEmpty()) {
                    return value;
                }
            } catch (Exception e) {
                // Try next selector
            }
        }
        return "";
    }
    public LocalDate parseRelativeDate(String relativeDate) {
        if (relativeDate == null || relativeDate.isEmpty()) {
            return null;
        }

        LocalDate now = LocalDate.now();
        relativeDate = relativeDate.toLowerCase().trim();

        try {
            if (relativeDate.contains("day")) {
                int days = Integer.parseInt(relativeDate.replaceAll("[^0-9]", ""));
                return now.minus(days, ChronoUnit.DAYS);
            } else if (relativeDate.contains("week")) {
                int weeks = Integer.parseInt(relativeDate.replaceAll("[^0-9]", ""));
                return now.minus(weeks, ChronoUnit.WEEKS);
            } else if (relativeDate.contains("month")) {
                int months = Integer.parseInt(relativeDate.replaceAll("[^0-9]", ""));
                return now.minus(months, ChronoUnit.MONTHS);
            } else if (relativeDate.contains("year")) {
                int years = Integer.parseInt(relativeDate.replaceAll("[^0-9]", ""));
                return now.minus(years, ChronoUnit.YEARS);
            }
        } catch (Exception e) {
            System.err.println("Could not parse date: " + relativeDate);
        }

        return null;
    }
}