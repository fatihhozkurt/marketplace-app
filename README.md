# 🚀 Marketplace

Marketplace is a **Spring Boot**-based web application designed as a shopping platform. Users can create their accounts, add products to their carts, load balance to their wallets and bought the cart as they wished. 🛠️

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
- No one's writing ChatGPT but to be honest it's being a game changer sometimes. 

## 🏠 Project Structure

`com.fatih.marketplace_app`

### 🔘 annotation
  - 🔧 `OptionalFieldValidation`               # Custom annotations for update dto's validations

### 🔘 config
  - 🔧 `RedisConfig`                           # Redis-cache configuration class about serializing

### 🔘 consts
  - 🔧 `RecordStatus`                          # Record Status constants for soft delete mechanism
  - 🔧 `UrlConst`                              # URL constants for API layer interfaces' methods

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

### 🔘 dao/data access objects (provides an abstraction layer for database operations)
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

### 🔘 listener (sets necessary fields before an entity persist or delete to database)
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
  - 🔧 `CampaignType`                          # Stores a campaign's type as fixed or percentage (discount value depends on campaign type)
  - 🔧 `OrderStatus`                           # Stores an order's current status as finalized or cancelled 

### 🔘 exception
  - 🔧 `GlobalExceptionHandler`                # Global exception handler
  - 🔧 `ErrorResponse`                         # Error response object to returning only required error message fields
  - 🔧 `BusinessException`
  - 🔧 `DataAlreadyExistException`
  - 🔧 `ResourceNotFoundException`

### 🔘 service (service interfaces for managers' implementations)
### 🔘 manager
  - 🔧 `AddressManager`                                     
  - 🔧 `CampaignManager`
  - 🔧 `CartManager`                   
  - 🔧 `CartItemManager`                       # Transition table between cart and product 
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
  - 🔧 `OptionalFieldValidator`                 # Defines custom annotation's field.           

### 🔘 MarketplaceAppApplication
- 🔧 `MarketplaceAppApplication`                # Main application        


## ⚙️ Setup and Run

### 1️⃣ Requirements
- 🖥️ **Java 17+**
- 🌐 **Spring Boot 3+**
- 🗄️ **PostgreSQL**
- 🖥️ **Redis CLI**

### 2️⃣ Clone the Project
```sh
git clone https://github.com/fatihhozkurt/marketplace-app.git
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
- ✅ **User, Product, Cart, Order and Wallet Management** 🏷️
- ✅ **Consistent and Well-Structed Project** 💬
- ✅ **Caching System With Redis** ❤️
- ✅ **Exporting Invoice System with IText (it exports to project directory)** 🏷️
- ✅ **Self-Clearing the Inactive Carts in Every 15 minutes** 🏷️
- ✅ **Soft Delete Mechanism** 🔐
- ✅ **Dev and Prod Profiles** 🛠️
- ✅ **Log Into a File Mechanism (it creates a logs folder in the project folder and an application.txt file in it)** 🛠️
- ✅ **Javadocs in Methods For Improving Readability of the Code** 💬
- ✅ **User Password Encrption with Jasypt** 🔐
- ✅ **Easy Mapping with MapStruct** ❤️
- ✅ **Strategy Pattern with CampaignType** ❤️
- ✅ **Layered Architecture and Loose Coupling with Dependency Injection** ❤️
- ✅ **Global Exception Handling and Well-Defined Validations** 🛠️
- ✅ **PostgreSQL Relational Database ❤️**

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
- **Elasticsearch** for full-text search capabilities 🔍
- **AWS S3** for image management 📸
  
## 🤝 Contributing

Feel free to open a **pull request** if you’d like to contribute.

## 📝 License

This project is licensed under the **MIT License**.

📌 Developed by **@fatihhozkurt**. 😊
