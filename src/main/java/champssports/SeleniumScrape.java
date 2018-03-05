package champssports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumScrape {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("user.dir"));
		String pwd = System.getProperty("user.dir");
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "src/main/resources/chromedriver"); ChromeOptions option = new
		 * ChromeOptions(); option.addExtensions(new File(pwd +
		 * "/Block-image_v1.0.crx")); WebDriver driver = new ChromeDriver(option);
		 */
		/// FirefoxOptions options = new FirefoxOptions();
		// options.addPreference("permissions.default.image", 2);
		// System.setProperty("webdriver.gecko.driver",
		/// "src/main/resources/geckodriver-v0.19.1-linux64/geckodriver");
		// WebDriver driver = new FirefoxDriver(options);

		List<String> urls = new ArrayList<String>();
		urls.add("https://www.champssports.com/_-_/keyword-air+jordan+shoes?Rpp=180&cm_SORT=Initial%20Results");
		urls.add(
				"https://www.champssports.com/_-_/keyword-air+jordan+shoes?Rpp=180&Ns=P_AverageOverallRating%7C1&cm_SORT=Product%20Rating%20%28High%20to%20Low%29");
		urls.add(
				"https://www.champssports.com/_-_/keyword-air+jordan+shoes?Rpp=180&Ns=P_ModelName%7C0&cm_SORT=Alphabetical%20%28A%20to%20Z%29");
		urls.add(
				"https://www.champssports.com/_-_/keyword-air+jordan+shoes?Rpp=180&Ns=P_NewArrivalDateEpoch%7C1&cm_SORT=New%20Arrivals");
		urls.add(
				"https://www.champssports.com/_-_/keyword-air+jordan+shoes?Rpp=180&Ns=P_StyleSalePrice%7C0&cm_SORT=Price%20%28Low%20to%20High%29");
		urls.add(
				"https://www.champssports.com/_-_/keyword-air+jordan+shoes?Rpp=180&Ns=P_StyleSalePrice%7C1&cm_SORT=Price%20%28High%20to%20Low%29");
		urls.add(
				"https://www.champssports.com/_-_/keyword-air+jordan+shoes?Rpp=180&Ns=P_TopSalesAmount%7C1&cm_SORT=Top%20Sellers");
		urls.add(
				"https://www.champssports.com/_-_/keyword-air+jordan+shoes?Rpp=180&Ns=P_BrandName%7C0&cm_SORT=Brand%20Name%20A-Z");
		for (String url : urls) {

			/*System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			ChromeOptions option = new ChromeOptions();
			option.addExtensions(new File(pwd + "/Block-image_v1.0.crx"));
			WebDriver driver = new ChromeDriver(option);*/
			FirefoxOptions options = new FirefoxOptions();
			options.addPreference("permissions.default.image", 2);
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver-v0.19.1-linux64/geckodriver");
			WebDriver driver = new FirefoxDriver(options);

			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.get(
					url);
			Map<String, String> multiHref = new HashMap<String, String>();
			List<WebElement> shoelis = driver.findElements(By.cssSelector("#endeca_search_results > ul > li"));
			int lisize = shoelis.size();
			for (int i = 1; i <= lisize; i++) {
				try {

					// check for li
					String liClassValue = driver
							.findElement(By.cssSelector("#endeca_search_results > ul > li:nth-child(" + i + ")"))
							.getAttribute("class");
					if (liClassValue.equals("clearRow")) {
						continue;
					} else {

						try {
							String href = driver
									.findElement(
											By.cssSelector("#endeca_search_results > ul > li:nth-child(" + i + ") > a"))
									.getAttribute("href");
							String img_url = driver
									.findElement(By.cssSelector("#endeca_search_results > ul > li:nth-child(" + i
											+ ") > a:nth-child(1) > span.product_image > img"))
									.getAttribute("src");
							//System.out.println("New href--- " + href + "  for li " + i);
							multiHref.put(href, img_url);// .add(img_url,href);
						} catch (NoSuchElementException ex) {
							System.out.println("Something went wrong");
						}
					}

				} catch (Exception ex) {

					ex.printStackTrace();
					break;
				}
			}
			System.out.println("Total no of shoes : " + multiHref.size()+ " for the url : "+url);
			
			for (Map.Entry m : multiHref.entrySet()) {
				// driver1.get((String) itr.next());
				// Thread.sleep(500);
				System.out.println("shoe url---" + m.getKey());
				String shoeurl = m.getKey().toString();
				String shoeimg = m.getValue().toString();
				System.out.println("image url--" + shoeimg);
				driver.get(shoeurl);
				String title = driver.findElement(By.cssSelector(".product_info > div.title")).getText();
				System.out.println("title---" + title);
				String price = null;
				try {
					price = driver.findElement(By.cssSelector(
							"#product_form > div.pdp_wrapper > div.top_wrapper > div.product_content > span.right_column > div.product_info > div.product_price > div.sale > span.value"))
							.getText();
				} catch (Exception ex) {
					try {
						price = driver.findElement(By.cssSelector(
								"#product_form > div.pdp_wrapper > div.top_wrapper > div.product_content > span.right_column > div.product_info > div.product_price > div > span.value"))
								.getText();
					} catch (Exception ex1) {
						System.out.println("No price found");
						ex1.printStackTrace();
					}
				}

				System.out.println("price--" + price);
				List<String> shoesize = new ArrayList<String>();
				driver.findElement(By.cssSelector("#pdp_size_select")).click();
				List<WebElement> sizes = driver.findElements(By.cssSelector(
						"#product_form > div.pdp_wrapper > div.top_wrapper > div.product_content > span.right_column > div.product_info > div.add_section > div.product_sizes_content > span > a.button"));
				int s = sizes.size();
				System.out.println("s" + s);
				for (int j = 1; j <= s; j++) {
					String sz = null;
					try {
						String notneeded = driver.findElement(By.cssSelector(
								"#product_form > div.pdp_wrapper > div.top_wrapper > div.product_content > span.right_column > div.product_info > div.add_section > div.product_sizes_content > span > a.disabled:nth-child("
										+ j + ")"))
								.getText();
						continue;
						// shoesize.add(sz);
					} catch (Exception ex) {
						sz = driver.findElement(By.cssSelector(
								"#product_form > div.pdp_wrapper > div.top_wrapper > div.product_content > span.right_column > div.product_info > div.add_section > div.product_sizes_content > span > a.button:nth-child("
										+ j + ")"))
								.getText();
						shoesize.add(sz);
						// continue;
					}

				}
				System.out.println("Shoe sizes in stock are : " + shoesize.toString());
				// Thread.sleep(500);

			}
			driver.close();
		}
	}

}
