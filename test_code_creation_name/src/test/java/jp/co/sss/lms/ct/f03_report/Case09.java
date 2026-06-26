package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		WebElement userElement = webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん"));
		userElement.click();

		WebElement titleElement = webDriver.findElement(By.cssSelector("h2"));
		assertTrue(titleElement.getText().contains("ユーザー詳細"), "ユーザー詳細画面に遷移すること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
		WebElement reportElement = webDriver
				.findElement(By.xpath("//tr[contains(., '2022年10月2日')]//input[@value='修正する']"));

		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript("arguments[0].scrollIntoView(true);", reportElement);
		jse.executeScript("arguments[0].click();", reportElement);

		WebElement resultElement = webDriver.findElement(By.cssSelector("h2"));
		assertTrue(resultElement.getText().contains("2022年10月2日"), "レポート登録画面に遷移すること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// TODO ここに追加
		WebElement learningElement = webDriver.findElement(By.name("intFieldNameArray[0]"));
		learningElement.clear();

		WebElement submitElement = webDriver.findElement(By.xpath("//button[contains(text(), '提出する')]"));

		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript("arguments[0].scrollIntoView(true);", submitElement);
		jse.executeScript("arguments[0].click();", submitElement);

		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement errorLearningElement = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("input[name='intFieldNameArray[0]'].errorInput")));

		String classAttribute = errorLearningElement.getAttribute("class");
		assertTrue(classAttribute.contains("errorInput"), "未入力時にエラーになっていること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// TODO ここに追加
		WebElement comprehensionElement = webDriver.findElement(By.name("intFieldValueArray[0]"));
		Select selectComprehensionSelect = new Select(comprehensionElement);
		selectComprehensionSelect.selectByIndex(0);

		WebElement submitElement = webDriver.findElement(By.xpath("//button[contains(text(), '提出する')]"));

		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript("arguments[0].scrollIntoView(true);", submitElement);
		jse.executeScript("arguments[0].click();", submitElement);

		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		comprehensionElement = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.name("intFieldValueArray[0]")));

		String classAttribute = comprehensionElement.getAttribute("class");
		assertTrue(classAttribute.contains("errorInput"), "未入力時にエラーになっていること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// TODO ここに追加 
		WebElement progressElement = webDriver.findElement(By.name("contentArray[0]"));
		progressElement.sendKeys("abc");

		WebElement submitElement = webDriver.findElement(By.xpath("//button[contains(text(), '提出する')]"));

		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript("arguments[0].scrollIntoView(true);", submitElement);
		jse.executeScript("arguments[0].click();", submitElement);

		progressElement = webDriver.findElement(By.name("contentArray[0]"));

		String classAttribute = progressElement.getAttribute("class");
		assertTrue(classAttribute.contains("errorInput"), "未入力時にエラーになっていること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// TODO ここに追加
		WebElement progressElement = webDriver.findElement(By.name("contentArray[0]"));
		progressElement.clear();
		progressElement.sendKeys("11");

		WebElement submitElement = webDriver.findElement(By.xpath("//button[contains(text(), '提出する')]"));

		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript("arguments[0].scrollIntoView(true);", submitElement);
		jse.executeScript("arguments[0].click();", submitElement);

		progressElement = webDriver.findElement(By.name("contentArray[0]"));

		String classAttribute = progressElement.getAttribute("class");
		assertTrue(classAttribute.contains("errorInput"), "未入力時にエラーになっていること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// TODO ここに追加
		WebElement progressElement = webDriver.findElement(By.name("contentArray[0]"));
		progressElement.clear();

		WebElement commentElement = webDriver.findElement(By.name("contentArray[1]"));
		commentElement.clear();

		WebElement submitElement = webDriver.findElement(By.xpath("//button[contains(text(), '提出する')]"));

		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript("arguments[0].scrollIntoView(true);", submitElement);
		jse.executeScript("arguments[0].click();", submitElement);

		progressElement = webDriver.findElement(By.name("contentArray[0]"));
		commentElement = webDriver.findElement(By.name("contentArray[1]"));

		String progressAttribute = progressElement.getAttribute("class");
		assertTrue(progressAttribute.contains("errorInput"), "未入力時にエラーになっていること");

		String commentAttribute = progressElement.getAttribute("class");
		assertTrue(commentAttribute.contains("errorInput"), "未入力時にエラーになっていること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// TODO ここに追加
	}

}
