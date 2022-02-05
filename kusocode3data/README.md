# クソコード 3 用データ・テーブル定義

- クソコード 3 を試すためのデータは、クソコード 1 用データを以下のとおり変更して生成したものです

```sql:
INSERT INTO picture.count_log SELECT * FROM `picture`.`access_count` WHERE access_count.id < 1001;
```

※クソコード 1 用データの `access_count` テーブルの 1,000 行目までを `count_log` テーブルにコピー

- 以下の方法で書き出したデータファイルもあります
  - `LOAD DATA INFILE` でロードします
  - DB の設定で、 `max_allowed_packet` を大きな値にしておくことと、 `character_set_XXX` （特に `character_set_database` ）を `utf8mb4` にしておくのを忘れずに
  - 非圧縮です

```sql:
SELECT * FROM picture.count_log INTO OUTFILE 'count_log.csv' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n';
```

- クソコード 1 用の `picture` テーブルも使用します
