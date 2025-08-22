package com.attendance.model;  // このファイルが属するパッケージ（フォルダ）

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// 入力チェックをするためのツールをインポートしています。
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserDto {  // ユーザーのデータを扱うためのクラス
	
	@Id  // これが各ユーザを一意に識別するためのIDとなります
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // IDは自動的に増加します
    @Column(name = "user_id")  // データベースに合わせてカラム名を修正
    private Integer id;
	
    @NotEmpty  // ユーザー名は空であってはならないというルール
    private String name;  // ユーザー名を保存するための場所

    @NotEmpty  // パスワードは空であってはならないというルール
    private String password;  // パスワードを保存するための場所
    
    private String role;


}