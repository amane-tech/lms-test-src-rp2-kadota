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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		WebElement sectionElement = webDriver
				.findElement(By.xpath("//tr[contains(., '関係演算子')]//input[@value='詳細']"));
		sectionElement.click();

		WebElement titleElement = webDriver.findElement(By.cssSelector("h2"));
		assertEquals("関係演算子、条件分岐、繰り返し 2022年10月6日", titleElement.getText(), "「関係演算子、条件分岐、繰り返し」のセクション詳細画面が表示されること");

		getEvidence(new Object() {

		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
		WebElement submitElement = webDriver.findElement(By.xpath("//input[contains(@value, '提出する')]"));
		submitElement.click();

		WebElement titleElement = webDriver.findElement(By.cssSelector("h2"));
		assertTrue(titleElement.getText().contains("日報【デモ】"), "レポート登録画面に遷移すること");

		getEvidence(new Object() {

		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// TODO ここに追加
		WebElement reportElement = webDriver.findElement(By.name("contentArray[0]"));
		reportElement.sendKeys("これはテストです。");
		reportElement.submit();

		WebElement resultElement = webDriver.findElement(By.xpath("//input[contains(@value, '確認する')]"));
		assertTrue(resultElement.getAttribute("value").contains("確認する"),
				"「提出する」ボタンを押下しセクション画面に遷移し、ボタン名が「提出済み日報【デモ】を確認する」に更新されていること");

		getEvidence(new Object() {

		});
	}

}
