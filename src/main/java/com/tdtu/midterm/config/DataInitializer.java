package com.tdtu.midterm.config;

import com.tdtu.midterm.constant.Brand;
import com.tdtu.midterm.constant.Color;
import com.tdtu.midterm.model.Category;
import com.tdtu.midterm.model.Product;
import com.tdtu.midterm.repository.CategoryRepository;
import com.tdtu.midterm.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            // Clear existing data
            productRepository.deleteAll();
            categoryRepository.deleteAll();

            // Create categories
            Category electronics = new Category();
            electronics.setNameCategory("Electronics");
            electronics = categoryRepository.save(electronics);

            Category fashion = new Category();
            fashion.setNameCategory("Fashion");
            fashion = categoryRepository.save(fashion);

            Category home = new Category();
            home.setNameCategory("Home & Living");
            home = categoryRepository.save(home);

            // Create products
            Product laptop = new Product();
            laptop.setProductName("MacBook Pro 14\"");
            laptop.setBrand(Brand.APPLE);
            laptop.setPrice(1999.99);
            laptop.setColor(Color.GRAY);
            laptop.setDescription("Powerful laptop with M2 Pro chip, 16GB RAM, and 512GB SSD");
            laptop.setImageUrl("https://i.pinimg.com/736x/d4/80/2c/d4802ccbbd6fd24cb7324e086ee6dd75.jpg");
            laptop.setCategory(electronics);
            productRepository.save(laptop);

            Product tshirt = new Product();
            tshirt.setProductName("Classic White T-Shirt");
            tshirt.setBrand(Brand.NIKE);
            tshirt.setPrice(29.99);
            tshirt.setColor(Color.WHITE);
            tshirt.setDescription("Comfortable cotton t-shirt with classic fit");
            tshirt.setImageUrl("https://i.pinimg.com/736x/70/c6/1d/70c61d829f558494811a37b4fbff83f4.jpg");
            tshirt.setCategory(fashion);
            productRepository.save(tshirt);

            Product sofa = new Product();
            sofa.setProductName("Modern Sectional Sofa");
            sofa.setBrand(Brand.IKEA);
            sofa.setPrice(899.99);
            sofa.setColor(Color.ORANGE);
            sofa.setDescription("Comfortable 3-seater sectional sofa with modern design");
            sofa.setImageUrl("https://i.pinimg.com/736x/89/93/18/899318a9bc3313bb62e4961688e5ac77.jpg");
            sofa.setCategory(home);
            productRepository.save(sofa);

            //more products
            Product phone = new Product();
            phone.setProductName("iPhone 14 Pro");
            phone.setBrand(Brand.APPLE);
            phone.setPrice(999.99);
            phone.setColor(Color.GOLD);
            phone.setDescription("Latest iPhone with A16 Bionic chip, 128GB storage");
            phone.setImageUrl("https://i.pinimg.com/736x/bf/f4/2d/bff42d2af8e6125ae5973f226144aeb2.jpg");
            phone.setCategory(electronics);
            productRepository.save(phone);

            //more products
            Product chair = new Product();
            chair.setProductName("Ergonomic Office Chair");
            chair.setBrand(Brand.UNDER_ARMOUR);
            chair.setPrice(149.99);
            chair.setColor(Color.BLACK);
            chair.setDescription("Ergonomic office chair with breathable mesh backrest");
            chair.setImageUrl("https://i.pinimg.com/736x/45/8d/7d/458d7d00000000000000000000000000.jpg");
            chair.setCategory(home);
            productRepository.save(chair);

            //more products
            Product desk = new Product();
            desk.setProductName("Adjustable Standing Desk");
            desk.setBrand(Brand.IKEA);
            desk.setPrice(299.99);
            desk.setColor(Color.GRAY);
            desk.setDescription("Adjustable standing desk with ergonomic design");
            desk.setImageUrl("https://i.pinimg.com/736x/45/8d/7d/458d7d00000000000000000000000000.jpg");
            desk.setCategory(home);
            productRepository.save(desk);

            //more products
            Product lamp = new Product();
            lamp.setProductName("Table Lamp");
            lamp.setBrand(Brand.LG);
            lamp.setPrice(49.99);
            lamp.setColor(Color.WHITE);
            lamp.setDescription("Table lamp with adjustable height and light intensity");
            lamp.setImageUrl("https://i.pinimg.com/736x/45/8d/7d/458d7d00000000000000000000000000.jpg");
            lamp.setCategory(home);
            productRepository.save(lamp);

            //more products
            Product speaker = new Product();
            speaker.setProductName("Bluetooth Speaker");
            speaker.setBrand(Brand.TARGET);
            speaker.setPrice(79.99);
            speaker.setColor(Color.BLACK);
            speaker.setDescription("Bluetooth speaker with high-quality sound and long battery life");
            speaker.setImageUrl("https://i.pinimg.com/736x/45/8d/7d/458d7d00000000000000000000000000.jpg");
            speaker.setCategory(home);
            productRepository.save(speaker);

        };
    }
}
