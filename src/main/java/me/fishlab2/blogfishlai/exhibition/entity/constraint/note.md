# 使用註解定義驗證資料
> ## @target 元註解(meta-annotation)
> 定義註解使用時機  
> 參考java.lang.annotation.ElementType  
>> ###Constants  
>> - ANNOTATION_TYPE
>> - CONSTRUCTOR
>> - FIELD
>> - LOCAL_VARIABLE
>> - PACKAGE
>> - PARAMETER
>> - TYPE
>> - TYPE_PARAMETER
>> - TYPE_USE
>>
> ## @Retention
> 註解存在位置
> 參考java.lang.annotation.Retention
> and enum java.lang.annotation.RetentionPolicy  
> > ###Constants  
> > - SOURCE 只留在原始碼不編譯進.class檔案
> > - CLASS 編譯進 .class 但 runtime 不可讀取
> > - RUNTIME runtime 時可讀取
> 
> ## @Constraint
> 主解此註解為 bean 資料驗證器，至少要有三個屬性
> - String message() default [...]; 預設的錯誤提示
> - Class<?>[] groups() default {}; 未知類別的 List
>> - Class<? extends Payload>[] 繼承 Payload 類別子類別的 List  
>> 可宣告要使用哪一個介面實作

## 自定義註解 @interface MyDateConstraint
> 為註解提供設置預設錯誤訊息、註解List與註解payload()方法參數 (必要)，  
> 再加上兩個自定義參數 fromDate and toDate 用於讀取欄位值

## 實作前端輸入日期驗證器 MyDateConstrainValidator
> String strStartDate & strStopDate get frontend date(Month)  
> Implement my date format validator  
>> ### regex note 
>> regex 用 Matcher.matches() 判斷是否完全符合字串  
>> 而 .find() 會一次找出還能匹配的字串
