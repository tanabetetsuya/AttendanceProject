package com.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//TimeRecordクラスを使うためにインポートしています
import com.attendance.model.TimeRecord;

// TimeRecordRepositoryというインターフェースを作成します。JpaRepositoryを拡張して、TimeRecordオブジェクトとそれらのIDをInteger型で扱えるようにします。
public interface TimeRecordRepository extends JpaRepository<TimeRecord, Integer>{
	List<TimeRecord> findByUserId(int userId);
}
