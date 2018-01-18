import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail_163Client {
	public static void main(String[] args) throws MessagingException {
		String result = Mail_163Client.sendEmail("cyang198906@163.com", 
				new String[]{
				"610039525@qq.com",
				"2406352526@qq.com"
				}, 
				"yc535689", 
				"JMail邮件服务学习资料",
				"JMail学习地址："+"http://blog.csdn.net/jiangtao7913/article/details/50492947");
		System.out.println("邮件发送结果："+result);
	}
	
	public static String sendEmail(final String fromAddr,final String[] toAddrs,final String thirdPwd,String title,String content){
		Properties props = new Properties();  
        props.setProperty("mail.smtp.auth", "true");  
        props.setProperty("mail.transport.protocol", "smtp");  
        props.setProperty("mail.host", "smtp.163.com");  
        Session session = Session.getInstance(props, new Authenticator() {  
            protected PasswordAuthentication getPasswordAuthentication() { 
            	//第三方客户端授权密码和登录密码不一样
            	final String fromAddr_prefixx = fromAddr.split("@")[0];
                return new PasswordAuthentication(fromAddr_prefixx, thirdPwd);  
            }  
        });  
        session.setDebug(true);  
        try {
        	Message msg = new MimeMessage(session);
        	msg.setFrom(new InternetAddress(fromAddr)); 
			msg.setSubject(title);
			msg.setContent("<span style='color:red;margin:0 auto'>"+content+"</span>", "text/html;charset=utf-8");
			if(toAddrs!=null&&toAddrs.length==1){
				msg.setRecipient(RecipientType.TO, new InternetAddress(toAddrs[0])); 
			}else if(toAddrs!=null&&toAddrs.length>1){
				msg.setRecipients(RecipientType.TO, InternetAddress.parse(Mail_163Client.join(toAddrs))); 
			}else{
				throw new IllegalArgumentException("目标邮件地址不可为空");
			}
	        Transport.send(msg); 
	        return MailStatus.SUCCESS;
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}  
        return MailStatus.FAILED;
	}
	static class MailStatus {
		public static final String SUCCESS ="SUCCESS";
		public static final String FAILED ="FAILED";
		public static final String Email_suffix ="@163.com";
	}
	static String join(String[] arr){
		if(arr==null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<arr.length;i++){
			if (i ==0) {
				sb.append(arr[0]);
			} else {
				sb.append(",").append(arr[i]);
			}
			
		}
		return sb.toString();
	}
}
