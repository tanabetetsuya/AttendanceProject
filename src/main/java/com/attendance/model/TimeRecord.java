package com.attendance.model;

import java.time.Duration;
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
    
    // 勤務開始時間を50分以上は切り上げ
    public LocalTime getRoundedStartTime() {
    	if (startTime == null) return null;
    	int minutes = startTime.getMinute();
    	if (minutes >= 50) {
    			return LocalTime.of(startTime.getHour() + 1, 0);
    	}
    	return LocalTime.of(startTime.getHour(), minutes);
    }
    
    // 勤務終了時間を10分以内は切り捨て
    public LocalTime getRoundedFinishTime() {
    	if (finishTime == null) return null;
    	int minutes = finishTime.getMinute();
    	if (minutes <= 10) {
    		minutes = 0;
    	}
    	return LocalTime.of(finishTime.getHour(), minutes);
    }
    
    // 休憩開始時刻を10分以内は切り捨て
    public LocalTime getRoundedStartBreakTime() {
    	if (startBreakTime == null) return null;
    	int minutes = startBreakTime.getMinute();
    	if (minutes <= 10) {
    		minutes = 0;
    	}
    	return LocalTime.of(startBreakTime.getHour(), minutes);
    }
    
    // 休憩終了時刻を50分以上は切り上げ
    public LocalTime getRoundedFinishBreakTime() {
    	if (finishBreakTime == null) return null;
    	int minutes = finishBreakTime.getMinute();
        
    	if (minutes >= 50) { 
        		return LocalTime.of(finishBreakTime.getHour() + 1, 0);
    	}
    	
    	return LocalTime.of(finishBreakTime.getHour(), minutes);
    }
    
    // 勤務時間（休憩時間を引いた実働時間）を取得
    public Duration getWorkDuration() {
        if (startTime == null || finishTime == null || startBreakTime == null || finishBreakTime == null) {
            return Duration.ZERO; // または null を返して、呼び出し側で処理
        }
        Duration total = Duration.between(getRoundedStartTime(), getRoundedFinishTime())
                             .minus(Duration.between(getRoundedStartBreakTime(), getRoundedFinishBreakTime()));
        return total.isNegative() ? Duration.ZERO : total; // 負にならないように
    }


    // 残業時間（8時間以上の時間を残業とする）
    public Duration getOvertime() {
        Duration workDuration = getWorkDuration();
        Duration normalWork = Duration.ofHours(8);
        if (workDuration.compareTo(normalWork) > 0) {
            return workDuration.minus(normalWork);
        } else {
            return Duration.ZERO;
        }
    }

    // 勤務時間を「HH:mm」形式で返す
    public String getWorkDurationString() {
        Duration duration = getWorkDuration();
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%02d:%02d", hours, minutes);
    }


    // 残業時間を「HH:mm」形式で返す
    public String getOvertimeString() {
        Duration d = getOvertime();
        long hours = d.toHours();
        long minutes = d.toMinutesPart();
        return String.format("%02d:%02d", hours, minutes);
    }

}