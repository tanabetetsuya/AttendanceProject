package com.attendance.repository;


import org.springframework.data.jpa.repository.JpaRepository;

//TimeRecordクラスを使うためにインポートしています
import com.attendance.model.MissStampingApply;

// MissStampingApplyRepositoryというインターフェースを作成します。JpaRepositoryを拡張して、MissStampingApplyオブジェクトとそれらのIDをInteger型で扱えるようにします。
public interface MissStampingApplyRepository extends JpaRepository<MissStampingApply, Integer>{
	
}
