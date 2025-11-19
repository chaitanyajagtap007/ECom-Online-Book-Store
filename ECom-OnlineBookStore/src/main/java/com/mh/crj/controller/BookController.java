package com.mh.crj.controller;

import java.net.HttpURLConnection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mh.crj.entity.Book;
import com.mh.crj.exception.InternalServerException;
import com.mh.crj.model.BookDto;
import com.mh.crj.model.ResponseMessage;
import com.mh.crj.service.BookService;
import com.mh.crj.utility.Constants;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;




@Tag(name = "BookController" , description = "Book CRUD Operation")
@RestController
public class BookController {

	private static final Logger logger = LoggerFactory.getLogger(BookController.class); 
	
	@Autowired
	private BookService bookService;
	
	
	@Operation(summary = "Create Book ",description = "e commerece online books store  register the Book ")
    @ApiResponses({
     @ApiResponse(responseCode = "201",description = "Book register successfully"),
     @ApiResponse(responseCode = "400",description = "Book register failure"),
     @ApiResponse(responseCode = "500",description = "Internal server error")
     })
	@PostMapping("/saveBook")
	public ResponseEntity<ResponseMessage> insertBook(@RequestBody BookDto bookDto) {
		logger.info("Book Registration controller layer calling or started");
		try {
			if(bookDto.getTitle() == null || bookDto.getTitle().isEmpty() ||  bookDto.getAuthor() == null || bookDto.getAuthor().isEmpty()  || bookDto.getPrice()==null || bookDto.getPrice().isNaN()) {				
				logger.debug("Recived Book Data:", bookDto);
				logger.warn("missing Title ,author and price Book request");
				logger.error("Book title , author and price missing : Bad reg data ");
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Title , Author and price cannot be empty"));
			}
			
			Book book = bookService.insertBook(bookDto);
			if(book!=null) {
				 logger.info("Message return eco-system = \"BOOKSTORE_ONLINE_REGISTRATION_CREATION_SUCCESS\" .");  
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "Book Inserted ",book));
			}else {
				 logger.info("Message return eco-system = \"BOOKSTORE_ONLINE_REGISTRATION_CREATION_FAILED\" ."); 
	             logger.info("Book Registration controller layer calling completed");  
	             logger.warn("Book Registration service return null : Book registration failed");
				throw new InternalServerException("Failed to store Book");
			}
		}catch (Exception e) {
			 logger.error("New Book creation process failed in Bookstore-DB . Exception:" +e.getMessage());    
			 throw new InternalServerException("Internal server error !");
		}
	}

	@GetMapping("/getBook/{id}")
	public ResponseEntity<ResponseMessage> getBookById(@PathVariable Long id) {
		Book byId = bookService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS, "Books List fetch successfully ", byId));
	}
	
	@GetMapping("/getAllBooks")
	public ResponseEntity<ResponseMessage> getAllBooks() {
		
		List<Book> allBooks = bookService.getAllBooks();
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS, "Books List fetch successfully ", allBooks));
		
	}
	
//	@DeleteMapping("/deleteBook/{id}")
//	public ResponseEntity<ResponseMessage> deleteBookById(@PathVariable Long id) {
//		String result = bookService.deleteBookById(id);
//		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,result));
//	}
	
	
	
	
	
	
	@DeleteMapping("/deleteBook/{id}")
	@CircuitBreaker(name = "deleteBookById",fallbackMethod = "fallbackDeleteBookById")
	public ResponseEntity<ResponseMessage> deleteBookById(@PathVariable Long id) {
		String result = bookService.deleteBookById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,result));
	}
	
	
	public ResponseEntity<ResponseMessage> fallbackDeleteBookById(Long id,Throwable th) {
		
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(new ResponseMessage(HttpURLConnection.HTTP_UNAVAILABLE, Constants.FAILURE, "Book service temporarily unavailable, please try again later."));
	}
	
	@GetMapping("/getData")
	@CircuitBreaker(name="showData",fallbackMethod ="fallBackgetData")
	public String showData() throws Exception {
		throw new Exception();
	}
	
	//fall back method for circuit breaker
	public String fallBackgetData(Throwable th) {
		return "Book service tempoarly unable to access....";
	}
	
}
