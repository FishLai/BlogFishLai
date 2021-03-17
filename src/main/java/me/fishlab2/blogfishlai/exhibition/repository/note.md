># Oracle regular expression
> ### 想要去除字串空白與特殊符號後，
> ### 比較是否符合字串
> 使用REGEXP_REPLACE(column, regex, replaceWith)  
>> SELECT ts.name  
>> FROM test_string ts  
> > WHERE REGEXP_REPLACE(ts.name, '[[:space:][:punct:]]', '')  
>
> 找出是否有重複的名稱
 