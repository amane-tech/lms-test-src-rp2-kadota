package jp.co.sss.lms.ct.f02_faq;

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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
		goTo("http://localhost:8080/lms");

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加
		WebElement menuElement = webDriver.findElement(By.linkText("機能"));
		menuElement.click();

		WebElement helpElement = webDriver.findElement(By.linkText("ヘルプ"));
		helpElement.click();

		WebElement helpTitleElement = webDriver.findElement(By.cssSelector("h2"));
		assertEquals("ヘルプ", helpTitleElement.getText(), "「ヘルプ」の画面が表示されること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加
		WebElement menuElement = webDriver.findElement(By.linkText("機能"));
		menuElement.click();

		WebElement faqElement = webDriver.findElement(By.linkText("よくある質問"));
		faqElement.click();

		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		WebElement faqTitleElement = webDriver.findElement(By.cssSelector("h2"));

		assertEquals("よくある質問", faqTitleElement.getText(), "「よくある質問」の画面が表示されること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// TODO ここに追加
		WebElement categoryElement = webDriver.findElement(By.partialLinkText("研修関係"));
		categoryElement.click();

		WebElement categoryTitleElement = webDriver.findElement(By.cssSelector("th"));
		assertEquals("検索結果", categoryTitleElement.getText(), "カテゴリ検索結果が表示されること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// TODO ここに追加
		WebElement categoryQuestionElement = webDriver.findElement(By.xpath("//span[contains(text(), '研修の申し込み')]"));
		categoryQuestionElement.click();

		WebElement categoryAnswerElement = webDriver.findElement(By.xpath("//span[contains(text(), '申し込み方法')]"));

		String answer = "申し込み方法";
		assertTrue(categoryAnswerElement.getText().contains(answer), "カテゴリ検索結果の質問の回答が表示されること");

		getEvidence(new Object() {

		});
	}

}
