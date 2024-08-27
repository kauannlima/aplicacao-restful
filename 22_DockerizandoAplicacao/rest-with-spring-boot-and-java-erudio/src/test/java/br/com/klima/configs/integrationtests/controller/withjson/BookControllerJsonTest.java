package br.com.klima.configs.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.klima.configs.TestConfigs;
import br.com.klima.configs.integrationtests.vo.AccountCredentialsVO;
import br.com.klima.configs.integrationtests.vo.BookVO;
import br.com.klima.configs.integrationtests.vo.TokenVO;
import br.com.klima.configs.integrationtests.vo.wrappers.WrapperBookVO;
import br.com.klima.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerJsonTest extends AbstractIntegrationTest{

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static BookVO book;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		book = new BookVO();
		
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer "+accessToken)
				.setBasePath("/api/book/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockBook();
				
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.body(book)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getLaunchDate());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getTitle());
		
		assertTrue(persistedBook.getId() > 0);
	
		assertEquals("Michael C. Feathers",persistedBook.getAuthor());
		assertEquals(Double.valueOf(55.99),persistedBook.getPrice());
		assertEquals("Working effectively with legacy code",persistedBook.getTitle());
	}
	
	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		book.setAuthor("Kauan de A. Lima");
				
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.body(book)
					.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getLaunchDate());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getTitle());
		
		assertTrue(persistedBook.getId() > 0);
	
		assertEquals("Kauan de A. Lima",persistedBook.getAuthor());
		assertEquals(Double.valueOf(55.99),persistedBook.getPrice());
		assertEquals("Working effectively with legacy code",persistedBook.getTitle());
	}
	

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockBook();	
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
					.pathParam("id", book.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		
		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getLaunchDate());
		assertNotNull(persistedBook.getPrice());
		assertNotNull(persistedBook.getTitle());
		
		assertTrue(persistedBook.getId() > 0);
	
		assertEquals("Kauan de A. Lima",persistedBook.getAuthor());
		assertEquals(Double.valueOf(55.99),persistedBook.getPrice());
		assertEquals("Working effectively with legacy code",persistedBook.getTitle());
	}
	
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
			
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", book.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);	
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
						
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParams("page",0,"size", 10,"direction","asc")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
						//.as(new TypeRef<List<BookVO>>() {});
		WrapperBookVO wrapper = objectMapper.readValue(content, WrapperBookVO.class);
		var book = wrapper.getEmbedded().getBooks();
		
		
		BookVO foundBookOne = book.get(0);
	
		assertNotNull(foundBookOne.getId());
		assertNotNull(foundBookOne.getAuthor());
		assertNotNull(foundBookOne.getPrice());
		assertNotNull(foundBookOne.getTitle());
		
		assertEquals(12, foundBookOne.getId());
		assertEquals("Viktor Mayer-Schonberger e Kenneth Kukier",foundBookOne.getAuthor());
		assertEquals(Double.valueOf(54.0),foundBookOne.getPrice());
		assertEquals("Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana",foundBookOne.getTitle());
		
		
		BookVO foundBookSix = book.get(5);
		
		assertNotNull(foundBookSix.getId());
		assertNotNull(foundBookSix.getAuthor());
		assertNotNull(foundBookSix.getPrice());
		assertNotNull(foundBookSix.getTitle());

		
		assertEquals(11 , foundBookSix.getId());
		assertEquals("Roger S. Pressman",foundBookSix.getAuthor());
		assertEquals(Double.valueOf(56.0),foundBookSix.getPrice());
		assertEquals("Engenharia de Software: uma abordagem profissional",foundBookSix.getTitle());
	}
	
	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
						
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/api/book/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		given().spec(specificationWithoutToken)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.when()
					.get()
				.then()
					.statusCode(403);
	}
	
	@Test
	@Order(7)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException {
						
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParams("page",0,"size", 10,"direction","asc")
					.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();

		assertTrue(content.contains("_links\":{\"self\":{\"href\":\"http://localhost:8888/api/book/v1/12\"}}}"));
		assertTrue(content.contains("_links\":{\"self\":{\"href\":\"http://localhost:8888/api/book/v1/3\"}}}"));
		assertTrue(content.contains("_links\":{\"self\":{\"href\":\"http://localhost:8888/api/book/v1/5\"}}}"));
		
		
		assertTrue(content.contains("\"first\":{\"href\":\"http://localhost:8888/api/book/v1?direction=asc&page=0&size=10&sort=title,asc\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/book/v1?page=0&size=10&direction=asc\"}"));
		assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/api/book/v1?direction=asc&page=1&size=10&sort=title,asc\"}"));
		assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/api/book/v1?direction=asc&page=1&size=10&sort=title,asc\"}"));
		
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":15,\"totalPages\":2,\"number\":0}}"));
	
	}
	
	private void mockBook() {
		book.setAuthor("Michael C. Feathers");
		book.setLaunchDate(new Date());
		book.setPrice(Double.valueOf(55.99));
		book.setTitle("Working effectively with legacy code");

	}

}
