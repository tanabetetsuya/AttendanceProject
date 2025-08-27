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
import com.attendance.repository.MissStampingApplyRepository;
import com.attendance.repository.UserRepository;
import com.attendance.service.UserService;

@Controller
public class UserController {

    private final UserService service;
    private final UserRepository userRepository;
    private final MissStampingApplyRepository missStampingApplyRepository;

    public UserController(UserService service, UserRepository userRepository, MissStampingApplyRepository missStampingApplyRepository) {
        this.service = service;
        this.userRepository = userRepository;
        this.missStampingApplyRepository = missStampingApplyRepository;
    }
    
    
    @GetMapping("/user/StampingCorrectionForm/{recordId}/{userId}")
    public String showStampingCorrectionForm(@PathVariable int recordId, @PathVariable int userId, Model model) {
    	TimeRecord record = service.getTimeRecordById(recordId);
    	User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    	model.addAttribute("user", user);
    	model.addAttribute("record", record);
    	model.addAttribute("userId", userId);
    	return "user/StampingCorrectForm";
    }
    
    @PostMapping("/user/StampingCorrectionForm/{recordId}/{userId}")
    public String saveStampingCorrectionForm(@PathVariable int recordId, @PathVariable int userId, MissStampingApply miss) {
    	TimeRecord record = service.getTimeRecordById(recordId);
    	User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    	miss.setTimerecord(record);
    	miss.setUser(user);
    	missStampingApplyRepository.save(miss);
    	return "redirect:/user/AttendanceHandling";
    }
    
    @GetMapping("/user/{id}")
    public String showTimeRecords(@PathVariable int id, Model model) {
        model.addAttribute("user", service.getUserById(id));
        model.addAttribute("records", service.getTimeRecordsByUserId(id));
        model.addAttribute("recordForm", new TimeRecord());
        return "time_record";
    }

    @PostMapping("/user/{id}/save")
    public String saveTimeRecord(@PathVariable int id, @ModelAttribute TimeRecord record) {
        record.setUserId(id);
        service.saveTimeRecord(record);
        return "redirect:/user/AttendanceHandling";
    }
    
    @GetMapping("/user/AttendanceHandling")
    public String showMyPage(Model model, Principal principal) {
		// ログインユーザの名前を取得
    	String username = principal.getName();

        // DBからユーザ情報を取得
        User user = service.findByUsername(username);
        // modelに渡す
        model.addAttribute("user", user);
        // 勤怠記録もmodelに渡す
        List<TimeRecord> records = service.getTimeRecordsByUserId(user.getId());
        model.addAttribute("records", records);
        
        // 登録フォーム用
        model.addAttribute("recordForm", new TimeRecord());
            
        
        return "user/AttendanceHandling";
    }
}