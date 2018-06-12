package com.spring.restapi;

import com.spring.restapi.models.Product;
import com.spring.restapi.repositories.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestapiApplicationTests {
	
	private ProductRepository productRepository;
	static String id_value;
    public Product savedProduct;
	
    @Autowired
    public void setRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

	/*@Test
	public void contextLoads() {
	}*/
	
	@Before
	public void init() {
		productRepository.deleteAll();
        Product product = new Product("Dummy Product 1", "The First Product in The world part 1", 100.0, "https://dummyimage.com/600x400/000/aaa");
		Product savedProduct = productRepository.save(product);
		id_value=savedProduct.getId();
	}
		
	@Test
    public void createProduct() {
		System.out.println("Test1");
		
        Product product = new Product("Dummy Product 2", "The First Product in The world part 2", 200.0, "https://dummyimage.com/600x400/000/bbb");
		Product savedProduct = productRepository.save(product);
		System.out.println(savedProduct.getId());
    }

    @Test
    public void findAllProducts() {
		System.out.println("Test2");
        Iterable<Product> products = productRepository.findAll();
        assertNotNull(products);
        //assertTrue(!products.isEmpty());
    }

    @Test
    public void findProductById() {
        Product product = productRepository.findOne(id_value);
        assertNotNull(product);
		assertEquals("Dummy Product 1", product.getProdName());
        assertEquals("The First Product in The world part 1", product.getProdDesc());
		assertEquals(100.0, product.getProdPrice(), 0.0);
		assertEquals("https://dummyimage.com/600x400/000/aaa", product.getProdImage());
    }
	
	@Test
    public void updateProduct() {
		Product product = productRepository.findOne(id_value);
		product.setProdName("Spring Product1");
		product.setProdDesc("Spring related Product");
		product.setProdPrice(300.0);
		product.setProdImage("https://dummyimage.com/600x400/000/spring");
		
		assertEquals("Spring Product1", product.getProdName());
        assertEquals("Spring related Product", product.getProdDesc());
		assertEquals(300.0, product.getProdPrice(), 0.0);
		assertEquals("https://dummyimage.com/600x400/000/spring", product.getProdImage());
    }

    @Test
    public void deleteProductWithId() {
        Product product = productRepository.findOne(id_value);
        productRepository.delete(product);
        assertNotNull(product);
    }
}