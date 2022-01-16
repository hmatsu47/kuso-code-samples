# クソコード 1 用データ・テーブル定義

- クソコード 1 を試すためのデータです
- `picture` テーブルのデータはクソデータではなく「写真 AC」・「ぱくたそ」からダウンロードした真っ当な写真データが入っています
- MySQL または MariaDB 用です
- データは以下の方法で CSV 形式にしたものを ZIP 化してあります
  - `LOAD DATA INFILE` でテーブルにロードします

```sql:
SELECT * FROM picture.access_count INTO OUTFILE 'access_count.csv' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n';
SELECT * FROM picture.picture INTO OUTFILE 'picture.csv' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n';
```