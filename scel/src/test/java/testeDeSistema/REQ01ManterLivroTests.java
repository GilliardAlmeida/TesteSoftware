package testeDeSistema;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class REQ01ManterLivroTests {

	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://ts-scel-web.herokuapp.com/login");
		driver.manage().window().maximize();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void CT01CadastrarLivroComSucesso() {
		
		FazerLogin();
		
		InserirLivro();
	    
	    assertEquals("0001", driver.findElement(By.cssSelector("td:nth-child(2)")).getText());
		assertEquals("Clube da Luta", driver.findElement(By.cssSelector("td:nth-child(3)")).getText());
		assertEquals("Chuck", driver.findElement(By.cssSelector("td:nth-child(4)")).getText());
		ExcluirDados();
	}
	
	@Test
	public void CT02AlterarAutorTituloLivroComSucesso() {
		FazerLogin();
		
		InserirLivro();
		
		driver.findElement(By.linkText("Editar")).click();
		
	    driver.findElement(By.id("autor")).click();
	    driver.findElement(By.id("autor")).clear();
	    driver.findElement(By.id("autor")).sendKeys("Chuck P.");
	    
	    driver.findElement(By.id("titulo")).click();
	    driver.findElement(By.id("titulo")).clear();
	    driver.findElement(By.id("titulo")).sendKeys("Clube da luta 2");
	    
	    driver.findElement(By.cssSelector(".btn")).click();
	    
	    assertEquals("Clube da luta 2", driver.findElement(By.cssSelector("td:nth-child(3)")).getText());
	    assertEquals("Chuck P.", driver.findElement(By.cssSelector("td:nth-child(4)")).getText());
	    ExcluirDados();
	    
	}
	
	@Test
	public void CT03ConsultarLivroComSucesso() {
		FazerLogin();
		InserirLivro();
		
		driver.findElement(By.linkText("Voltar")).click();
		driver.findElement(By.linkText("Livros")).click();
		driver.findElement(By.linkText("Lista de Livros")).click();
		
		assertEquals("Clube da Luta", driver.findElement(By.cssSelector("td:nth-child(3)")).getText());
		assertEquals("Chuck", driver.findElement(By.cssSelector("td:nth-child(4)")).getText());
		ExcluirDados();
	}
	
	@Test
	public void CT04ExcluirLivroComSucesso() {
		FazerLogin();
		InserirLivro();
		
		List<WebElement> quantidadeLinhasNaTabelaAposInserir = driver.findElements(By.xpath("/html/body/div/div[2]/div/table/tbody/tr/td[1]"));
		
		System.out.println(quantidadeLinhasNaTabelaAposInserir.size());
		
		ExcluirDados();
		
		List<WebElement> quantidadeLinhasNaTabelaAposExcluir = driver.findElements(By.xpath("/html/body/div/div[2]/div/table/tbody/tr/td[1]"));
		
		System.out.println(quantidadeLinhasNaTabelaAposExcluir.size());
		
		//System.out.println("No of rows are : " +rows.size()); 
		assertNotEquals(quantidadeLinhasNaTabelaAposExcluir.size(), quantidadeLinhasNaTabelaAposInserir.size());
	}
	
	// Método de espera de 2 segundos.
	public void Delay() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Método deFazer Login na página
	private void FazerLogin() {
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		Delay();
	}
	
	//Método de Inserir Livros
	private void InserirLivro()	{
		driver.findElement(By.id("isbn")).click();
	    driver.findElement(By.id("isbn")).sendKeys("0001");
	    driver.findElement(By.id("autor")).click();
	    driver.findElement(By.id("autor")).sendKeys("Chuck");
	    driver.findElement(By.id("titulo")).click();
	    driver.findElement(By.id("titulo")).sendKeys("Clube da Luta");
	    driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
	    Delay();
	}
	
	//Método de Excluir
	private void ExcluirDados() {
		driver.findElement(By.linkText("Excluir")).click();
	}

	

}
