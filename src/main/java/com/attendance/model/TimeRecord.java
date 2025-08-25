package com.attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data

@Entity //これはデータベースのテーブルを表しています
@Table(name="timerecord") // このクラスが対応するテーブルは"timerecord"です
public class TimeRecord {
	
	 
	@Id  // これが各ユーザを一意に識別するためのIDとなります
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // IDは自動的に増加します
    @Column(name = "timerecord_id")  // データベースに合わせてカラム名を修正
    private Long timerecordId;
	
	@Column(name = "user_id", nullable = false, unique = true)
    private int userId;
	
	@Column(name = "date", nullable = false, unique = true)
    private LocalDate date;
	
	@Column(name = "start_time", nullable = false, unique = true)
    private LocalTime startTime;
	
	@Column(name = "finish_time", nullable = false, unique = true)
    private LocalTime finishTime;
	
	@Column(name = "start_break_time", nullable = false, unique = true)
	private LocalTime startBreakTime;
	
	@Column(name = "finish_break_time", nullable = false, unique = true)
	private LocalTime finishBreakTime;
	
	@Column(name = "bikou", nullable = false, unique = true)
    private String bikou;

    public TimeRecord() {

    }
}