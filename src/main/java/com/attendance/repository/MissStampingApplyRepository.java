package com.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//TimeRecordクラスを使うためにインポートしています
import com.attendance.model.MissStampingApply;

// MissStampingApplyRepositoryというインターフェースを作成します。JpaRepositoryを拡張して、MissStampingApplyオブジェクトとそれらのIDをInteger型で扱えるようにします。
public interface MissStampingApplyRepository extends JpaRepository<MissStampingApply, Integer>{
	// ID昇順で取得
    List<MissStampingApply> findAllByOrderByMissstampingIdAsc();
    
    // 任意：特定ユーザの申請だけ取得したい場合
    List<MissStampingApply> findByUserIdOrderByMissstampingIdAsc(int userId);

}
