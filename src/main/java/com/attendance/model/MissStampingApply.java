package com.attendance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Data

@Entity //これはデータベースのテーブルを表しています
@Table(name="missstampingapply") // このクラスが対応するテーブルは"missstampingapply"です
public class MissStampingApply {
	
	 
	@Id  // これが各ユーザを一意に識別するためのIDとなります
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // IDは自動的に増加します
    @Column(name = "missstampingapply_id")  // データベースに合わせてカラム名を修正
    private Long missstampingId;
	
	@ManyToOne
	@JoinColumn(name = "timerecord_id", nullable = false)
	private TimeRecord timerecord;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@Column(name = "reason", nullable = false, unique = true)
    private String reason;
	
	@Column(name = "correct_time", nullable = false, unique = true)
    private String correctTime;

}
