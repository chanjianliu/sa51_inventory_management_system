package com.team9.motors.mail;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.team9.motors.model.Inventory;
import com.team9.motors.model.ProductState;

public class JavaMailUtil {
	
	public static void sendEmail(Inventory inventory,String[] mailAddressList) {
		// Create a mail sender gmail
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setPort(587);
		javaMailSender.setUsername("zhuhaokun3@gmail.com");
		javaMailSender.setPassword("springmail123");
		// Set authentication and TLS
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		javaMailSender.setJavaMailProperties(properties);
		// Set the email address we want to send to
		//String[] mailAddressList = mailAddressList;
		// Create an SimpleMailMessage
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setFrom("ManagementSystem");
		smm.setTo(mailAddressList);
		smm.setSubject("Product Reorder Reminder");
		String mailText = "This is a message from system. \nThe number of product ["
		+inventory.getProduct().getName()
		+"] has gone down to ["
		+inventory.getQuantity()
		+"]. The Supplier is ["
		+inventory.getProduct().getSupplier().getName()
		+"], and its email is ["
		+inventory.getProduct().getSupplier().getEmail()
		+"]. \nPlease reoder the products soon. \n\nThank you.\n\nFrom: \nTeam 9 Inventory Management System";
		
		smm.setText(mailText);
		try {
			javaMailSender.send(smm);
			System.out.println("Sended successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Send failure");
		}

	}

	public static void changeProductState(Inventory inventory) {

		if (inventory.getQuantity() <= inventory.getProduct().getReorderLevel()
				&& inventory.getProductState() == ProductState.InStock) {
			//sendEmail(inventory);
			inventory.setProductState(ProductState.BelowReorderLevel);
		}
//		else if (inventory.getQuantity() <= inventory.getProduct().getReorderLevel()
//				&& inventory.getProductState() == ProductState.BelowReorderLevel) {
//			// do nothing
//		} else if (inventory.getQuantity() <= inventory.getProduct().getReorderLevel()
//				&& inventory.getProductState() == ProductState.ReorderPlaced) {
//			// inventory.setProductState(ProductState.InStock);
//		}
	}
}