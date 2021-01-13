package com.maids.cc.store.management.backend.seeder;

import com.maids.cc.store.management.backend.entity.*;
import com.maids.cc.store.management.backend.repository.*;
import org.springframework.stereotype.Component;

@Component
public class Seeder {

    private CategoryRepository categoryRepository;
    private ClientRepository clientRepository;
    private LocationRepository locationRepository;
    private ProductRepository productRepository;
    private SaleItemRepository saleItemRepository;
    private SaleRepository saleRepository;
    private SellerRepository sellerRepository;

    private final int CATEGORY_SIZE = 5;
    private final int CLIENT_SIZE = 5;
    private final int LOCATION_SIZE = 4;
    private final int PRODUCT_SIZE = 10;
    private final int SALE_ITEM_SIZE = 20;
    private final int SALE_SIZE = 5;
    private final int SELLER_SIZE = 5;

    private Category[] categories = null;
    private Client[] clients = null;
    private Location[] locations = null;
    private Product[] products = null;
    private SaleItem[] saleItems = null;
    private Sale[] sales = null;
    private Seller[] sellers = null;

    public Seeder(
            CategoryRepository categoryRepository,
            ClientRepository clientRepository,
            LocationRepository locationRepository,
            ProductRepository productRepository,
            SaleItemRepository saleItemRepository,
            SaleRepository saleRepository,
            SellerRepository sellerRepository
    ){
        this.categoryRepository = categoryRepository;
        this.clientRepository = clientRepository;
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
        this.saleItemRepository = saleItemRepository;
        this.saleRepository = saleRepository;
        this.sellerRepository = sellerRepository;

        categories = new Category[CATEGORY_SIZE];
        clients = new Client[CLIENT_SIZE];
        locations = new Location[LOCATION_SIZE];
        products = new Product[PRODUCT_SIZE];
        saleItems = new SaleItem[SALE_ITEM_SIZE];
        sales = new Sale[SALE_SIZE];
        sellers = new Seller[SELLER_SIZE];
    }

    public void seedDb(){
        try{
            seedCategories();
            seedProducts();
            seedClients();
            seedLocations();
            seedSellers();
            seedSales();
            seedSaleItems();
            System.out.println("================== Database initialized successfully ==================");
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("================== Failed to initialize database ==================");
        }
    }

    private void seedCategories(){
        for(int i = 0; i < CATEGORY_SIZE; i++){
            categories[i] = new Category();

            categories[i].setName("category name " + (i+1));
            categories[i].setDescription("category description " + (i+1));

            categoryRepository.save(categories[i]);
        }
    }

    private void seedProducts(){
        for(int i = 0; i < PRODUCT_SIZE; i++){
            products[i] = new Product();

            products[i].setName("name " + (i + 1));
            products[i].setDescription("description " + (i + 1));
            products[i].setPrice(100 * (i + 1) );
            products[i].setQuantity((i + 1));

            products[i].setCategory(categories[ (i % CATEGORY_SIZE)]);
            categories[( i % CATEGORY_SIZE)].addProduct(products[i]);

            productRepository.save(products[i]);
            categoryRepository.save(categories[(i % CATEGORY_SIZE)]);
        }
    }

    private void seedClients(){
        for(int i = 0; i < CLIENT_SIZE; i++){
            clients[i] = new Client();

            clients[i].setFirstName("first name " + (i + 1));
            clients[i].setLastName("last name " + (i + 1));
            clients[i].setMobileNumber("099912325" + (i + 1));

            clientRepository.save(clients[i]);
        }
    }

    private void seedLocations(){
        for(int i = 0; i < LOCATION_SIZE; i++){
            locations[i] = new Location();

            locations[i].setCity("city " + (i + 1));
            locations[i].setZone("zone " + (i + 1));
            locations[i].setStreet("street " + (i + 1));

            locationRepository.save(locations[i]);
        }
    }

    private void seedSellers(){
        for(int i = 0; i < SELLER_SIZE; i++){

            sellers[i] = new Seller();

            sellers[i].setFirstName("first name " + (i + 1));
            sellers[i].setLastName("last name " + (i + 1));
            sellers[i].setMobileNumber("098877441" + (i + 1));

            sellers[i].setLocation(locations[(i % LOCATION_SIZE)]);
            locations[(i % LOCATION_SIZE)].addSeller(sellers[i]);

            sellerRepository.save(sellers[i]);
            locationRepository.save(locations[(i % LOCATION_SIZE)]);
        }
    }

    private void seedSales(){
        for(int i = 0; i < SALE_SIZE; i++){

            sales[i] = new Sale();

            sales[i].setClient(clients[(i % CLIENT_SIZE)]);
            clients[(i % CLIENT_SIZE)].addSale(sales[i]);

            sales[i].setSeller(sellers[i % SELLER_SIZE]);
            sellers[(i % SELLER_SIZE)].addSale(sales[i]);

            saleRepository.save(sales[i]);
            clientRepository.save(clients[(i % CLIENT_SIZE)]);
            sellerRepository.save(sellers[(i % SELLER_SIZE)]);
        }
    }

    private void seedSaleItems(){
        for(int i = 0; i < SALE_ITEM_SIZE; i++){

            saleItems[i] = new SaleItem();

            saleItems[i].setPrice(100* ( i + 1));
            saleItems[i].setQuantity((i + 1));

            saleItems[i].setProduct(products[( i % PRODUCT_SIZE)]);
            products[(i % PRODUCT_SIZE)].addSaleItem(saleItems[i]);

            saleItems[i].setSale(sales[(i % SALE_SIZE)]);
            sales[(i % SALE_SIZE)].addSaleItem(saleItems[i]);

            saleItemRepository.save(saleItems[i]);
            productRepository.save(products[i % PRODUCT_SIZE]);
            saleRepository.save(sales[i % SALE_SIZE]);
        }
    }
}
