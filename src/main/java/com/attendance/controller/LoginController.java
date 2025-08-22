package com.attendance.controller; // このファイルが属するパッケージ（フォルダ）

// 必要なクラスをインポートします
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // このクラスがWebコントローラーであることを示します
public class LoginController {
	
	
    @GetMapping("/user/login") // "/login"というURLに対するGETリクエストを処理します
    public String login() {
        return "user/login";  // login.htmlを表示します
    }
    
    @GetMapping("/") // ルートURL ("/") に対するGETリクエストを処理します
    public String redirectToIndex() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 現在のユーザーの認証情報を取得します
        if (authentication != null && authentication.isAuthenticated()) { // ユーザーがログインしている場合
            return "redirect:/user/index";  // "/user/index"にリダイレクトします
        }
        return "redirect:/user/login"; // ユーザーがログインしていない場合、"/login"にリダイレクトします
    }
    
    @GetMapping("/user/index") // "/index"というURLに対するGETリクエストを処理します
    public String index() {
        return "user/index"; // index.htmlを表示します
    }
}
