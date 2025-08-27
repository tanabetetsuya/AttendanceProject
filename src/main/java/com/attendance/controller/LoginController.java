package com.attendance.controller; // このファイルが属するパッケージ（フォルダ）

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 必要なクラスをインポートします
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // このクラスがWebコントローラーであることを示します
public class LoginController {
	
	@GetMapping("/admin/index")  // "/admin/login"というURLに対するGETリクエストを処理します
	public String adminIndex() {
		return "admin/index";
	}
	
	
    @GetMapping("/user/login") // "/user/login"というURLに対するGETリクエストを処理します
    public String userLogin() {
        return "user/login";  // login.htmlを表示します
    }
    
    @GetMapping("/") // ルートURL ("/") に対するGETリクエストを処理します
    public String redirectToIndex() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 現在のユーザーの認証情報を取得します
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) { //ユーザがログインしている場合かつ権限によって表示する画面を決定

            // 権限をチェック
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                if ("ROLE_ADMIN".equals(role)) {
                    return "redirect:/admin/users";
                } else if ("ROLE_USER".equals(role)) {
                    return "redirect:/user/AttendanceHandling";
                }
            }
        }
        return "redirect:/user/login"; // ユーザーがログインしていない場合、"/login"にリダイレクトします
    }
    
    @GetMapping("/user/index") // "/index"というURLに対するGETリクエストを処理します
    public String index() {
        return "user/index"; // index.htmlを表示します
    }
    
    @GetMapping("/user/logout")
    public String UserLogout(HttpServletRequest request, HttpServletResponse response) {
        // セッションを無効にする
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // ログアウト処理が完了したらトップページにリダイレクトする
        return "redirect:/";
    }
    
    @GetMapping("/admin/logout")
    public String AdminLogout(HttpServletRequest request, HttpServletResponse response) {
        // セッションを無効にする
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // ログアウト処理が完了したらトップページにリダイレクトする
        return "redirect:/";
    }
}
