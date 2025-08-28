＜システム概要＞<br>
勤怠WEB管理システムで従業員の勤務時間を入力、表示してくれるシステム。誤打刻の修正を管理者に送る事ができ、受け取った管理者は勤怠の情報を編集することができる

＜ツールの使い方＞<br>
・一般ユーザ<br>
最初のログイン画面が表示されたら、新規登録画面に遷移し、ユーザ登録を行う。登録したユーザでログインを行うと、勤怠処理画面へと遷移する。この画面は主に自身の勤怠の記録を行い、DBへの登録を行い、同じ画面で
自身の勤怠情報を確認することができる。もし、誤打刻をしてしまった場合、誤打刻修正申請画面へ遷移し、誤打刻してしまった理由と修正してほしい時間をテキストで入力する。

・管理者ユーザ<br>
最初のログイン画面で管理者権限のユーザでログインを行う。管理者でログイン後、登録ユーザの一覧画面が表示される。誤打刻修正申請フォーム一覧を確認すると、一般ユーザが登録した誤打刻修正申請内容が確認できる。
それをもとに、ユーザの一覧画面のユーザ毎に修正遷移ボタンがあるので押下すると、ユーザ勤怠情報修正画面へと遷移できる。そこで修正申請内容に記載の修正時間へと修正する。

＜使用している環境＞<br>
開発環境: eclipse　Version: 2025-06 (4.36.0)<br>
使用言語: java21<br>
フレームワーク: springboot version3.5.4<br>
セキュリティ: springsecurity<br>
使用DB: PostgreSQL 17.6 on x86_64-windows<br>

＜テーブル構成＞<br>
・userinfo<br>
create table userinfo (<br>
 userid integer serial primary key,<br>
 name varchar(100) not null,<br>
 password varchar(100) not null,<br>
 role varchar(100) not null<br>
);<br>

・timerecord<br>
create table timerecord (<br>
timerecord_id integer serial primary key,<br>
user_id integer,<br>
date date not null,<br>
start_time time,<br>
finish_time time,<br>
start_break_time time,<br>
finish_break_time time,<br>
bikou varchar(10) not null,<br>
foreign key (user_id) references userinfo (user_id)<br>
);<br>

・missstampingapply<br>
create table missstampingapply (<br>
missstampingapply_id integer serial primary key,<br>
timerecord_id integer,<br>
user_id integer,<br>
reason varchar(100) not null,<br>
correct_time varchar(50) not null,<br>
foreign key (timerecord_id) references timerecord (timerecord_id),<br>
foreign key (user_id) references userinfo (user_id)<br>
);<br>
 
