#SQL Table for "collection" project

> ## 表格欄位規劃
> 需求：
> 作品與所使用之技術
> <table><tr><td>

Table My_COLLECTION|
 ---|
coll_no 表格ID<br> name 作品名稱<br> start_date 開始時間<br> stop_date 停止時間<br> abstract 摘要<br> cover_path 封面路徑|

</td>
<td>

Table coll_tech|
---|
coll_no 技術ID<br> tech 技術名稱 |

</td></tr></table>


> coll_tech 兩個欄位做為主鍵，以防同一作品中出現一樣的技術。  
> 實作方法有
>  + @idClass 可以走訪個別欄位
>  + @EmbeddedId 可以比較簡短