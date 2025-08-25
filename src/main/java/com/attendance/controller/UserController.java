package com.attendance.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.attendance.model.TimeRecord;
import com.attendance.model.User;
import com.attendance.service.UserService;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/user/{id}")
    public String viewTimeRecords(@PathVariable int id, Model model) {
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