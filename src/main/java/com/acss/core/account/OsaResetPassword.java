package com.acss.core.account;

import static com.acss.kaizen.jooq.poc.db.tables.MAccount.M_ACCOUNT;
import static com.acss.kaizen.jooq.poc.db.tables.TPasswordresettoken.T_PASSWORDRESETTOKEN;

import java.math.BigDecimal;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.acss.core.forgotpwd.PasswordResetToken;
import com.acss.core.forgotpwd.UpdatePasswordForm;
import com.acss.core.model.ACSSDateUtil;
import com.acss.kaizen.jooq.poc.account.Account;
import com.acss.kaizen.jooq.poc.base.UpdateableRepository;
import com.acss.kaizen.jooq.poc.passwordreset.ResetToken;


@Service
public class OsaResetPassword implements ResetPasswordService{
	
	@Autowired
	private UpdateableRepository<Account,Long> accountRepo;
	@Autowired
	private UpdateableRepository<ResetToken,Integer> resetTokenRepo;
	
	
	@Autowired
    private JavaMailSender mailSender;
	
	public Account loadUserByEmailAddress(String emailAddress) {
		return accountRepo.findUsingCondition(M_ACCOUNT.EMAIL.equal(emailAddress)).get(0);
	}
	
	/**
	 * Creates a new token for the user
	 */
	public PasswordResetToken createPasswordTokenForUser(Account user) {
		String sToken = UUID.randomUUID().toString();
		PasswordResetToken pToken = new PasswordResetToken(sToken);
		BigDecimal expiryDate = ACSSDateUtil.getDateAsYYYYMMDDHHmmssFromDateTime(pToken.getExpiryDate());
		
		ResetToken rToken = new ResetToken(sToken,
				expiryDate, new BigDecimal(user.getId()));
		rToken.setCreDate(ACSSDateUtil.getDateAsYYYYMMDDFromDateTime());
		rToken.setCreTime(ACSSDateUtil.getTimeAsHHMMSSFromDateTime());
		
		resetTokenRepo.add(rToken);
		return pToken;
	}
	/**
	 * Gets the generated token for the user
	 */
	public PasswordResetToken getPasswordResetToken(String token) {
		ResetToken rToken = resetTokenRepo.findUsingCondition(T_PASSWORDRESETTOKEN.TOKEN.eq(token)).get(0);
		DateTime expiryDate = ACSSDateUtil.getDateAsDateTimeFromBigDecimalDateTime(rToken.getExpiryDate());
		PasswordResetToken pToken = new PasswordResetToken();
		pToken.setExpiryDate(expiryDate);
		pToken.setToken(rToken.getToken());
		pToken.setUser(accountRepo.findById(rToken.getUserId().longValue()));
		
		return pToken;
	}

	public Account changePassword(String user,UpdatePasswordForm newPassword) {
		Account updateUser = accountRepo.findUsingCondition(M_ACCOUNT.USERNAME.eq(user)).get(0);
		updateUser.setPassword(newPassword.getPassword());
		return accountRepo.update(updateUser);
	}
	
	/**
	 * Sends a link which contain the password reset form.
	 */
	public boolean sendResetLinkToUser(Account user,String contextPath, String token) {
		SimpleMailMessage email = constructResetTokenEmail(user,contextPath,token);
		mailSender.send(email);
		return true;
	}
	
	private SimpleMailMessage constructResetTokenEmail(Account user,String contextPath, String token) {
		String url = contextPath + "/changePassword?id=" + user.getId() + "&token=" + token;
		String message = "To reset the Password please follow the link. ";
		SimpleMailMessage email = new SimpleMailMessage();
		//TODO please use the user's email here Gail after the UAT. make sure email is added to whitelist.
		email.setTo(user.getEmail());
		email.setSubject("Reset Password");
		email.setText(message + " rn" + url);
		email.setFrom("osa@aeonphilippines.com.ph");
		return email;
	}
	/**
	 * Deletes the token after successful update of password.
	 */
	public void deleteTokenAfterSuccessfulUpdate(String userId) {
		ResetToken rToken = resetTokenRepo.findUsingCondition(T_PASSWORDRESETTOKEN.USER_ID.eq(new Integer(userId))).get(0);
		resetTokenRepo.delete(rToken.getId());
	}

}
