package com.attendance.service; // このファイルが属するパッケージ（フォルダ）

// 必要なツールをインポートしています
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Userクラスを使うためにインポートしています
import com.attendance.model.User;

// UserDetailsインターフェースを実装したUserPrincipalというクラスを作成します。これはSpring Securityでユーザー情報を扱うためのクラスです。
public class UserPrincipal implements UserDetails {

    private User user;  // Userオブジェクトを保持します。

    // コンストラクタでUserオブジェクトを受け取り、それをこのクラスのuserにセットします。
    public UserPrincipal(User user) {
        this.user = user;
    }

    // ユーザーに与えられる権限を返します。ここでは全てのユーザーに"USER"という権限を与えています。
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    // Userオブジェクトのパスワードを返します。
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Userオブジェクトのユーザー名を返します。
    @Override
    public String getUsername() {
        return user.getName();
    }

    // アカウントが有効期限切れでないことを示すために、常にtrueを返します。
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // アカウントがロックされていないことを示すために、常にtrueを返します。
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 資格情報（ここではパスワード）が有効期限切れでないことを示すために、常にtrueを返します。
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // アカウントが有効であることを示すために、常にtrueを返します。
    @Override
    public boolean isEnabled() {
        return true;
    }
}