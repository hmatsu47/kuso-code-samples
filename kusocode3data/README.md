# クソコード 3 用データ・テーブル定義

- クソコード 3 を試すためのデータは、クソコード 1 用データを以下のとおり変更して生成します。

```sql:
INSERT INTO `picture`.`count_log` SELECT * FROM `picture`.`access_count` WHERE access_count.id < 1001;
```

※クソコード 1 用データの `access_count` テーブルの 1,000 行目までを `count_log` テーブルにコピー。