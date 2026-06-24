package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		goTo("http://localhost:8080/lms");
		WebElement titleElement = webDriver.findElement(By.cssSelector("h2"));
		assertEquals("ログイン", titleElement.getText(), "遷移した画面のタイトルが「ログイン」であること");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加

		WebElement loginIdElement = webDriver.findElement(By.id("loginId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("StudentAA01");

		WebElement passwordElement = webDriver.findElement(By.name("password"));
		passwordElement.clear();
		passwordElement.sendKeys("AA01Student");

		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

		WebElement titleElement = webDriver.findElement(By.className("active"));
		assertEquals("コース詳細", titleElement.getText(), "「コース詳細」の画面が表示されること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		WebElement sectionElement = webDriver
				.findElement(By.xpath("//tr[contains(., 'アルゴリズム')]//input[@value='詳細']"));
		sectionElement.click();

		WebElement titleElement = webDriver.findElement(By.cssSelector("h2"));
		assertEquals("アルゴリズム、フローチャート 2022年10月2日", titleElement.getText(), "「アルゴリズム、フローチャート」のセクション詳細画面が表示されること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
		WebElement submitElement = webDriver.findElement(By.xpath("//input[contains(@value, '週報')]"));
		submitElement.click();

		WebElement titleElement = webDriver.findElement(By.cssSelector("h2"));
		assertTrue(titleElement.getText().contains("週報【デモ】"), "レポート登録画面に遷移すること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// TODO ここに追加
		WebElement progressElement = webDriver.findElement(By.name("contentArray[0]"));
		progressElement.clear();
		progressElement.sendKeys("4");
		WebElement commentElement = webDriver.findElement(By.name("contentArray[1]"));
		commentElement.clear();
		commentElement.sendKeys("修正テスト");
		WebElement reportElement = webDriver.findElement(By.name("contentArray[2]"));
		reportElement.clear();
		reportElement.sendKeys("報告内容を修正するテストです。");
		reportElement.submit();

		WebElement resultElement = webDriver.findElement(By.xpath("//input[contains(@value, '確認する')]"));
		assertTrue(resultElement.getAttribute("value").contains("確認する"), "「提出する」ボタンを押下しセクション画面に遷移すること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// TODO ここに追加
		WebElement userElement = webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん"));
		userElement.click();

		WebElement titleElement = webDriver.findElement(By.cssSelector("h2"));
		assertTrue(titleElement.getText().contains("ユーザー詳細"), "ユーザー詳細画面に遷移すること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// TODO ここに追加
		WebElement reportElement = webDriver
				.findElement(By.xpath("//tr[contains(., '2022年10月2日')]//input[@value='詳細']"));

		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript("arguments[0].scrollIntoView(true);", reportElement);
		jse.executeScript("arguments[0].click();", reportElement);

		WebElement resultElement = webDriver.findElement(By.cssSelector("h2"));
		assertTrue(resultElement.getText().contains("2022年10月2日"), "レポート詳細画面に遷移すること");

		WebElement updateElement = webDriver.findElement(By.xpath("//*[contains(text(), '報告内容を修正するテストです。')]"));
		assertTrue(updateElement.getText().contains("報告内容を修正するテストです。"), "レポート詳細画面で修正内容が反映されていること");

		getEvidence(new Object() {

		});
	}

}
