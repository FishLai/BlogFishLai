##要確認
儲存RTF資料進去varchar2欄位，長度問題(目前先調到對大位元數4000)，  
假如有問題可考慮更大的欄位。  

ConstraintTarget.IMPLICIT 的使用。

## 進度
>表單驗證邏輯  
>> @interface MyDateConstraint 日期驗證註譯  
>>
>>>@interface, the keyword to define annotation type  
>>
>> 
# BlogFishLai
> develop my blog
### start from：2021/02/23 
## 想法：
> 想要一個展示作品的展示櫃，沒有一個自己設計規畫的作品，我都很難說服雇用自己  
> 在剛開始的階段，在解決別人的問題之前，我應該要能解決自己的問題。  
> 練習撰寫測試  
> 與VueJS 整合，想想作品展示似乎不需要 SEO，
> 所以暫不考慮JS 生成的頁面，
> 對搜尋引擎不友善的問題
> 

## 目的：
> 以 spring MVC 實作自己的部落格網站  
>> ###功能
>> 公開與私人展示分區
>> CRUD 文章、留言區  


## 預計使用：
> VueJS  
> `thymeleaf、html、css、javascript`  
> `java、spring-boot、jpa`  
> `oracle database 18c`

## 參閱
> `【急速開發 Java 大型系統 Spring Boot】`  
>> `ISBN 978-986-5501-64-8`

## 目前套件清單
`spring-boot-starter-data-jpa 負責處理資料庫溝通`  
`spring-boot-starter-thymeleaf html模板工具`  
`spring-boot-starter-validation ??驗證器??`  
`spring-boot-starter-web spring MVC 網站開發模板`  
`ojdbc oracle 資料庫驅動`  
`lombok 簡化程式碼工具`  
`spring-boot-starter-test 測試工具`  
`spring-boot-devtools 熱部屬工具`

##Live Server setup
###maven
spring-boot-devtools  
\<optional true />
### IDEA setting 
(ctrl+alt+s) setting > build...> compiler turn on auto build  
(ctrl+shift+alt+/) compile..auto.. turn on
### properties setting
spring.thymeleaf.cache=false



