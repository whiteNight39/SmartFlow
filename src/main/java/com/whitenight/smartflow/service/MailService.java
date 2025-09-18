package com.whitenight.smartflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(String toEmail, String token) {
        String subject = "Activate your SmartFlow account";
        String activationLink = "https://your-frontend.com/activate?token=" + token;

        String text = String.format(
                """
                        Hi there,
                        
                        Please use the token below to activate your account:
                        
                        Token: %s
                        
                        Or click the link below:
                        %s
                        
                        This token will expire in 2 days.
                        
                        Best regards,
                        SmartFlow Team""",
                token, activationLink
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("smartflow.whitenight@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public void sendStaffUpdateConfirmationEmail(String toEmail) {
        String subject = "SmartFlow profile update submitted";
        String text = """
                Hi there,
                
                An update has been submitted for your SmartFlow account. \
                Your department head will review it shortly, and you will be notified once it is approved or rejected.
                
                Best regards,
                SmartFlow Team""";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("smartflow.whitenight@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public void sendStaffUpdateApprovalRequestEmail(String toEmail) {
        String subject = "Approval required: Staff profile update request";
        String text = """
                Hello,
                
                A staff member in your department has submitted a profile update request. \
                Please log in to SmartFlow to review and approve/reject the request.
                
                Best regards,
                SmartFlow Team""";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("smartflow.whitenight@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public void sendActivationSuccessEmail(String staffEmail) {
        String subject = "Your SmartFlow account is now active!";
        String text = """
                Hi there,
                
                Congratulations! Your SmartFlow account has been successfully activated. \
                You can now log in and access all the features available to your role.
                
                If you did not perform this activation, please contact your administrator immediately.
                
                Best regards,
                SmartFlow Team""";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("smartflow.whitenight@gmail.com");
        message.setTo(staffEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public void sendStaffUpdateApprovalEmail(String staffEmail) {
        String subject = "SmartFlow profile update approved";
        String text = """
                Hi there,
                
                Your requested profile update has been approved by your department head. \
                You can now log in and see the updated information in your account.
                
                Best regards,
                SmartFlow Team""";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("smartflow.whitenight@gmail.com");
        message.setTo(staffEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public void sendStaffUpdateRejectionEmail(String staffEmail) {
        String subject = "SmartFlow profile update rejected";
        String text = """
                Hi there,
                
                Your requested profile update has been reviewed by your department head, \
                and it has been rejected. Please contact your department head for further details.
                
                Best regards,
                SmartFlow Team""";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("smartflow.whitenight@gmail.com");
        message.setTo(staffEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

}