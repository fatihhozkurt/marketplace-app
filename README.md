# 🚀 Marketplace

Marketplace is a **Spring Boot**-based web application designed for shopping platform. Users can create their accounts, add products to their carts, load balance to their wallets and bought the cart as they needed. 🛠️

# 🤖 Used Tools and Technologies 
- Java & Spring Boot
- JPA
- Maven
- PostgreSQL
- Redis
- RESTful API
- MapStruct
- Jasypt
- IText
- Git Version Control
- To be honest ChatGPT sometimes made me proud. 

## 🏠 Project Structure

`com.fatih.marketplace_app`

### 🔘 annotation
  - 🔧 `OptionalFieldValidation`               # Custom annotations for update dto's validations

### 🔘 config
  - 🔧 `RedisConfig`                           # Redis-cache configuration class for serializing configurations

### 🔘 consts
  - 🔧 `RecordStatus`                          # Record Status Constants for soft delete mechanism
  - 🔧 `UrlConst`                              # URL Constants for API layer interfaces and methods

### 🔘 api (api layer which includes enpoints)
  - 🔧 `AddressApi`         
  - 🔧 `CampaignApi`        
  - 🔧 `CartApi`            
  - 🔧 `CartItemApi`        
  - 🔧 `InvoiceApi`         
  - 🔧 `OrderApi`           
  - 🔧 `ProductApi`         
  - 🔧 `UserApi`            
  - 🔧 `WalletApi`          

### 🔘 controller (controller layer which implements api layer interfaces')
  - 🔧 `AddressController`         
  - 🔧 `CampaignController`        
  - 🔧 `CartController`            
  - 🔧 `CartItemController`        
  - 🔧 `InvoiceController`         
  - 🔧 `OrderController`           
  - 🔧 `ProductController`         
  - 🔧 `UserController`            
  - 🔧 `WalletController`          

### 🔘 converter
  - 🔧 `JasyptAttributeConverter`              # JPA attribute converter that automatically encrypts and decrypts database fields
  - 🔧 `JasyptConfig`                          # Configures the encryption settings for Jasypt
  - 🔧 `SpringContextHolder`                   # Image DTO

### 🔘 dao/data access objects (provide an abstraction layer for database operations (CRUD), separating persistence logic from the business layer.)
  - 🔧 `AddressDao`             
  - 🔧 `CampaignDao`              
  - 🔧 `CartDao`                
  - 🔧 `CartItemDao`                 
  - 🔧 `InvoiceDao`
  - 🔧 `OrderDao`
  - 🔧 `ProductDao`
  - 🔧 `UserDao`
  - 🔧 `WalletDao`

### 🔘 dto/data transformation objects (record objects which used for transforms entities to theirselves)
  - 🔘 `requestDTOs`               
  - 🔘 `responseDTOs`                

### 🔘 listener (sets entities' before create or delete values)
  - 🔧 `CartListener`
  - 🔧 `OrderListener`
  - 🔧 `WalletListener`    

### 🔘 entity
  - 🔧 `AddressEntity`                  
  - 🔧 `BaseEntity`                   
  - 🔧 `CampaignEntity`               
  - 🔧 `CartEntity`                   
  - 🔧 `CartItemEntity`                        # transition table between cart and product 
  - 🔧 `InvoiceEntity`
  - 🔧 `OrderEntity`
  - 🔧 `ProductEntity`
  - 🔧 `UserEntity`
  - 🔧 `WalletEntity`                                                                           

### 🔘 enums
  - 🔧 `CampaignType`                          # Stores a campaign's type as fixed or percentage (discount value changes depends to campaign type)
  - 🔧 `OrderStatus`                           # Stores an order's current status as finalized or cancelled 

### 🔘 exception
  - 🔧 `GlobalExceptionHandler`                # Global exception handler
  - 🔧 `ErrorResponse`                         # Error response object for returning only required fields
  - 🔧 `BusinessException`
  - 🔧 `DataAlreadyExistException`
  - 🔧 `ResourceNotFoundException`

### 🔘 service (service interfaces for managers' implementations)
### 🔘 manager
  - 🔧 `AddressManager`                                     
  - 🔧 `CampaignManager`
  - 🔧 `CartManager`                   
  - 🔧 `CartItemManager`                       # transition table between cart and product 
  - 🔧 `InvoiceExportManager`
  - 🔧 `InvoiceManager`
  - 🔧 `OrderManager`
  - 🔧 `ProductManager`
  - 🔧 `UserManager`
  - 🔧 `WalletManager`    

### 🔘 mapper (used MapStruct to generated entit-dto transformations)
  - 🔧 `AddressMapper`         
  - 🔧 `CampaignMapper`          
  - 🔧 `CartItemMapper`
  - 🔧 `CartMapper`    
  - 🔧 `InvoiceMapper`
  - 🔧 `OrderMapper`
  - 🔧 `ProductMapper`
  - 🔧 `UserMapper`
  - 🔧 `WalletMapper`

### 🔘 repository
  - 🔧 `AddressRepository`         
  - 🔧 `CampaignRepository`          
  - 🔧 `CartItemRepository`
  - 🔧 `CartRepository`    
  - 🔧 `InvoiceRepository`
  - 🔧 `OrderRepository`
  - 🔧 `ProductRepository`
  - 🔧 `UserRepository`
  - 🔧 `WalletRepository`

### 🔘 strategy
  - 🔧 `DiscountStrategyFactory`                # Determines which discount strategy to use based on the campaign type.           
  - 🔧 `DiscountStrategyService`                # Defines the contract for applying discounts.         
  - 🔧 `FixedDiscountStrategyManager`           # Applies a fixed discount amount to the cart price.
  - 🔧 `PercentageDiscountStrategyManager`      # Applies a percentage-based discount to the cart price.

### 🔘 validation
  - 🔧 `DiscountStrategyFactory`                # Determines which discount strategy to use based on the campaign type.           

### 🔘 MarketplaceAppApplication
- 🔧 `MarketplaceAppApplication`                # Main application        


## ⚙️ Setup and Run

### 1️⃣ Requirements
- 🖥️ **Java 17+**
- 🌐 **Spring Boot 3+**
- 🗄️ **PostgreSQL**
- 🖥️ **Redis-cli**

### 2️⃣ Clone the Project
```sh
git clone https://github.com/fatihhozkurt/marketplace_app.git
cd marketplace_app
```

### 3️⃣ Install Dependencies
```sh
mvn clean install
```

### 4️⃣ Start redis-cli
```sh
wsl (windows subsystem for linux to use redis on windows)
redis-cli
```
### finally Run the Application



## 🔥 Features
✅ **User, Product, Cart, Order and Wallet Management** 🏷️
✅ **Consistent and Well-Structed Project** 💬
✅ **Caching System With Redis** ❤️
✅ **Exporting Invoice System with IText (it exports project directory)** 🏷️
✅ **Self-Clears the Inactive Carts in Every 15 minutes** 🏷️
✅ **Soft Delete Mechanism** 🔐
✅ **Spring Boot REST API** 🛠️
✅ **Dev and Prod Profiles** 🛠️
✅ **User Password Encrption with Jasypt** 🔐
✅ **Easy Mapping With MapStruct** ❤️
✅ **Stategy Pattern With CampaignType** ❤️
✅ **Layered Architecture and Loose Coupling With Dependency Injection** ❤️
✅ **Global Exception Hangling and Well-Defined Validations** 🛠️
✅ **PostgreSQL and no comment ❤️**

## 📌 Example API Usage

### ➕ Create an User
```http
POST /user
```
👥 **Request Body**
```json
{
  "firstName": "Fatih",
  "lastName": "Özkurt",
  "email": "fatih.ozkurt21@gmail.com",
  "phone": "+905340422922",
  "password": "123456Fö"
}
```
🔄 **Response**
```json
{
  "userId": *generatedUserId*
  "firstName": "Fatih",
  "lastName": "Özkurt",
  "email": "fatih.ozkurt21@gmail.com",
  "phone": "+905340422922",
  "password": "123456Fö"
}
```

## 🚀 Upcoming Features

I am currently working on integrating the following technologies into the project:
- **JWT & Spring Security** for enhanced authentication 🔐
- **Elasticsearch** for efficient search capabilities 🔍
- **AWS S3** Image Management 📸

These enhancements will improve the performance, security, and scalability of KnitShop. Stay tuned for updates! 🚀

## 🤝 Contributing

Feel free to open a **pull request** if you’d like to contribute.

## 📝 License

This project is licensed under the **MIT License**.

📌 Developed by **@fatihhozkurt**. 😊
