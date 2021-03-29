# 實作Spring MVC 網站
> ## 目標  
> 提供大家一個能夠展現自我創作的作品上架平台，
> 以作品為核心，  
> 以部落格的方式紀錄且深入探討作品中使用的技術。  
> 
### start from：2021/02/23 
> ## 目前已實現
> 已完成上架作品的 CRUD 功能，包含 hibernate 驗證表單。
> ### 使用技術與工具
>> Spring boot、SpringMVC、Spring Data JPA、Java、  
>> Thymeleaf、JavaScript、VueJS、CSS、HTML、  
>> Oracle SQL
>> 
> ## 操作畫面 Demo
> ### 首頁
> ![image] https://github.com/FishLai/BlogFishLai/blob/master/src/main/resources/static/img-app-intro/collection_homePage.png  
> ### 新增
> ![image] https://github.com/FishLai/BlogFishLai/blob/master/src/main/resources/static/img-app-intro/collection_addPage.png  
> ### 較驗
> ![image] https://github.com/FishLai/BlogFishLai/blob/master/src/main/resources/static/img-app-intro/collection_add_showViolation.png  
> ### 完成新增
> ![image] https://github.com/FishLai/BlogFishLai/blob/master/src/main/resources/static/img-app-intro/collection_afterAdd.png
> 


## 想法：
> 先透過自製作品展示櫃，  
> 在剛開始的階段，在能解決別人的問題之前，我應該要能解決自己的問題。  
>

##待實現功能：
> 訪客與會員功能  
> 技術文部落格功能


## 參閱
> `【急速開發 Java 大型系統 Spring Boot】`  
>> `ISBN 978-986-5501-64-8`

## 目前套件清單
`spring-boot-starter-data-jpa 負責處理資料庫溝通`  
`spring-boot-starter-thymeleaf html模板工具`  
`spring-boot-starter-validation 資料驗證器`  
`spring-boot-starter-web spring MVC 網站開發框架`  
`ojdbc oracle 資料庫驅動`  
`lombok 簡化程式碼工具`  
`spring-boot-starter-test 開發測試工具`  
`spring-boot-devtools 熱部屬工具`


# <i>P.S.</i>
##Live Server setup
###maven
spring-boot-devtools  
\<optional true />
### IDEA setting 
(ctrl+alt+s) setting > build...> compiler turn on auto build  
(ctrl+shift+alt+/) compile..auto.. turn on
### properties setting
spring.thymeleaf.cache=false



