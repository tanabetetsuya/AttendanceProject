package com.attendance.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.attendance.model.MissStampingApply;
import com.attendance.model.TimeRecord;
import com.attendance.model.User;
import com.attendance.model.UserDto;
import com.attendance.service.UserService;

@Controller
public class AdminController {
	
	private final UserService service;
	
	
	public AdminController(UserService service) {
        this.service = service;
    }
	
	// ユーザ一覧画面
    @GetMapping("/admin/users")
    public String userList(Model model, Principal principal) {
    	// ログインユーザの名前を取得
    	String username = principal.getName();
        // DBからユーザ情報を取得
        User user = service.findByUsername(username);
        // modelに渡す
        model.addAttribute("user", user);
        List<User> users = service.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }
    
    // 指定ユーザの勤怠一覧
    @GetMapping("/admin/user/{id}/attendance")
    public String userAttendance(@PathVariable Integer id, Model model) {
        User user = service.getUserById(id);
        List<TimeRecord> records = service.getTimeRecordsByUserId(id);
        model.addAttribute("user", user);
        model.addAttribute("records", records);
        return "admin/userDetail";
    }

    // 打刻修正フォームの表示
	@GetMapping("/admin/StampingCorrect/{recordid}/{userid}/edit")
	public String showAllAttendance(@PathVariable Integer recordid, @PathVariable Integer userid, Model model) {
		
		TimeRecord record = service.getTimeRecordById(recordid);
		User user = service.getUserById(userid);
	    model.addAttribute("record", record);
	    model.addAttribute("user", user);

	    return "admin/StampingCorrect";
	}
	
	// 打刻修正後、保存
    @PostMapping("/admin/StampingCorrect/{recordId}/{userId}/edit")
    public String saveStampingCorrection(@PathVariable int recordId, @PathVariable int userId, @ModelAttribute TimeRecord recordForm) {
        TimeRecord record = service.getTimeRecordById(recordId);

        // 必要なフィールドだけ更新
        record.setStartTime(recordForm.getStartTime());
        record.setFinishTime(recordForm.getFinishTime());
        record.setStartBreakTime(recordForm.getStartBreakTime());
        record.setFinishBreakTime(recordForm.getFinishBreakTime());
        record.setBikou(recordForm.getBikou());

        service.saveTimeRecord(record);
        return "redirect:/admin/user/" + userId + "/attendance";
    }

    // 誤打刻修正申請一覧の表示
    @GetMapping("/admin/StampingApplyIndex")
    public String showAllStampingApply(Model model) {
    	List<MissStampingApply> applyList = service.getAllMissStampingApply();
    	model.addAttribute("applyList", applyList);
    	return "admin/StampingApplyIndex";
    	
    }
    
    // 管理者ユーザ情報編集画面を表示
    @GetMapping("/admin/edit/{id}")
    public String editAdminUserForm(@PathVariable int id, Model model) {
        User user = service.getUserById(id);
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        // パスワードは空のまま（表示しない）
        model.addAttribute("userDto", dto);
        return "admin/userEdit";
    }

    // 管理者ユーザ情報更新処理
    @PostMapping("/admin/edit")
    public String updateUser(@ModelAttribute UserDto userDto) {
        service.updateAdminUser(userDto);
        return "redirect:/admin/users"; // 更新後はユーザ一覧に戻る
    }

}
