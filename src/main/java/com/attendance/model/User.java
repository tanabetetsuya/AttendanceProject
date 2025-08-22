package com.attendance.model;  // このファイルが属するパッケージ（フォルダ）

// 以下の部分はデータベースとのやり取りに必要な情報を持つためのものです。
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity  // これはデータベースのテーブルを表しています
@Table(name = "userinfo")  // このクラスが対応するテーブルの名前は "userinfo" です
public class User {

    @Id  // これが各ユーザを一意に識別するためのIDとなります
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // IDは自動的に増加します
    @Column(name = "user_id")  // データベースに合わせてカラム名を修正
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)  // "username" カラム。各ユーザーのユーザー名を表します。同じ名前のユーザーは存在できません
    private String name;

    @Column(name = "password", nullable = false)  // "password" カラム。ユーザーのパスワードを表します
    private String password;
    
    @Column(name = "role")
    private String role;

    
}
