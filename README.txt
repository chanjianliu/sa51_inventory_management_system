TEAM 9 Java CA


1. Configure file [application.properties]
   - replaced in your password for Mysql database [spring.datasource.password=password]
   - the database/schema name is "team9"


//Only needed if your computer does not have a (D:) drive
2. Configure the pathway to download RerorderReport and UsageReport
   - Access the "com.team9.motors.service" folder
     => Open [CatalogueImplementation.java] and edit line #193
     => Open [ProductImplementation.java] and edit line #130
   - replace "D:\\reorderreport.dat" with "C:\\Users\\{your username}\\Team9Report.dat"


3. Run the project as Spring Boot App.


4. Open the [CreateUser.sql] located in "src\main\resources" 
   (you should be able to see it when you alongside the [application.properties] file)
   - copy and run the sql statements in your Mysql Workbench or HeidiSQL
   - note all the users passwords: password123

5. Run JUnit test on [createSupplierProductInventory.java] to generate supplier,
   product and inventory data



Delivered By:
AFRIN Rukaya
CHAN Jian Liu
LIU Lei
RAMAKRISHNAN Niveditha
XIAO Changwei
ZHANG Hongduo
ZHOU Yanjun
ZHU Haokun
