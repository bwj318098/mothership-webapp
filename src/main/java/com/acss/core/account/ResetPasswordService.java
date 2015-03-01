package com.acss.core.account;

import com.acss.core.forgotpwd.PasswordResetToken;
import com.acss.core.forgotpwd.UpdatePasswordForm;
import com.acss.kaizen.jooq.poc.account.Account;

public interface ResetPasswordService {
	public Account loadUserByEmailAddress(String emailAddress);
	public PasswordResetToken createPasswordTokenForUser(Account user);
	public boolean deleteResetToken(String userId);
	public PasswordResetToken getPasswordResetToken(String token);
	public Account changePassword(String username,UpdatePasswordForm newPassword);
	public boolean sendResetLinkToUser(Account user,String contextPath, String token);
}
