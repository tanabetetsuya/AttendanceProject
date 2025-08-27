package com.attendance.service; // このファイルが属するパッケージ（フォルダ）


import java.util.List;

// 必要なクラスをインポートします
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.attendance.model.MissStampingApply;
import com.attendance.model.TimeRecord;
import com.attendance.model.User;
import com.attendance.model.UserDto;
import com.attendance.repository.MissStampingApplyRepository;
import com.attendance.repository.TimeRecordRepository;
import com.attendance.repository.UserRepository;

@Service // このクラスがサービス層のクラスであることを示します
public class UserService implements UserDetailsService { // UserDetailsServiceインターフェースを実装しています

    
	@Autowired // Springが自動的にUserRepositoryの実装を注入します
    private UserRepository userRepository;
	
	@Autowired // Springが自動的にTimeRecordRepositoryの実装を注入します
	private TimeRecordRepository timeRecordRepository;
	
	@Autowired // Springが自動的にMissStampingApplyRepositoryの実装を注入します
	private MissStampingApplyRepository missStampingApplyRepository;

    @Autowired // Springが自動的にPasswordEncoderの実装を注入します
    private PasswordEncoder passwordEncoder;
    
    
    // 誤打刻申請一覧取得
    public List<MissStampingApply> getAllMissStampingApply() {
        return missStampingApplyRepository.findAllByOrderByMissstampingIdAsc();
    }
    
    // ユーザ単位で誤打刻申請を取得
    public List<MissStampingApply> getMissStampingApplyByUserId(int userId) {
        return missStampingApplyRepository.findByUserIdOrderByMissstampingIdAsc(userId);
    }

    @Override // UserDetailsServiceインターフェースのメソッドを上書きします
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username); // ユーザー名でユーザーを検索します
        if (user == null) {
            throw new UsernameNotFoundException("User not found"); // ユーザーが見つからない場合、例外をスローします
        }
        return new UserPrincipal(user); // ユーザーが見つかった場合、UserPrincipalを作成し返します
    }
    
    public List<User> findAll(){
    	return userRepository.findAllByOrderByIdAsc();
    
    }
    
    // 新たにメソッドを追加します
    public TimeRecord getTimeRecordById(int id) {
    	return timeRecordRepository.findById(id).orElseThrow(() -> new RuntimeException("TimeRecord not found"));
    }

    //新たにメソッドを追加します
    public User findByUsername(String username) {
        User user = userRepository.findByName(username); // ユーザー名でユーザーを検索し返します
        if (user == null) {
        	throw new RuntimeException("User not found" + username);
        }
        return user;
    }
    
    public User getUserById(int id) {
    	return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ユーザが見つかりません: " + id));
    }
    
    // timerecordテーブル内のuser_idを検索するメソッドを追加します。
    public List<TimeRecord> getTimeRecordsByUserId(int userId){
    	return timeRecordRepository.findByUserIdOrderByTimerecordIdAsc(userId);
    }
    
    @Transactional
    public void saveTimeRecord(TimeRecord record) {
    	timeRecordRepository.save(record);
    }

    @Transactional // トランザクションを開始します。メソッドが終了したらトランザクションがコミットされます。
    public void save(UserDto userDto) {
        // UserDtoからUserへの変換
        User user = new User();
        user.setName(userDto.getName());
        // パスワードをハッシュ化してから保存
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole("USER");

        // データベースへの保存
        userRepository.save(user); // UserRepositoryを使ってユーザーをデータベースに保存します
    }
    
    @Transactional // トランザクションを開始します。メソッドが終了したらトランザクションがコミットされます。
    public void saveAdmin(UserDto userDto) {
    	// UserDtoからUserへの変換
    	User user = new User();
    	user.setName(userDto.getName());
    	// パスワードをハッシュ化してから保存
    	user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    	user.setRole("ADMIN");
    	
    	// データベースへの保存
    	userRepository.save(user);
    }
    
}