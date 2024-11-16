package com.iadv.freshdesk;

public class BoldTextMail {
   public static String getEmailBody(String companyName2, String licenseCategoryName, String apptypeDesc, String displayRefId, String statusDesc, String addressPremises, String delayeddays, String appSubmissionDate) {
      String mailb = "";

      try {
         String companyName = companyName2.trim();
         String licenseCategory = licenseCategoryName.trim();
         String formattedText = "<html><body><p>Dear <strong>" + companyName + "</strong>,</p>" + "</br>" + "<p>I trust this message finds you in good health and high spirits.</p>" + "<p>We would like to extend our sincere gratitude for choosing India Advocacy for your licensing needs. We understand the importance of a smooth and efficient registration process and are committed to providing you with the best service.</p>" + "<p>As part of our ongoing efforts to keep you informed, we are pleased to provide you with an update on the status of your license application:</p>" + "</br>" + "<p><strong>Days of Update:</strong><strong> Day " + delayeddays + " </strong>(as of the last update on " + appSubmissionDate + ")</p>" + "<p><strong>Company Name:</strong><strong> " + companyName + "</strong></p>" + "<p><strong>License Category:</strong><strong> " + licenseCategory + "</strong></p>" + "<p><strong>Application Type:</strong><strong> " + apptypeDesc + "</strong></p>" + "<p><strong>Application Reference ID:</strong><strong> " + displayRefId + "</strong></p>" + "<p><strong>Status Description:</strong><strong> " + statusDesc + "</strong></p>" + "<p><strong>Registered Address:</strong><strong>" + addressPremises + "</strong></p>" + "</br>" + "<p>We understand the significance of a timely registration process for your business, and we want to assure you that our team is diligently working towards expediting the completion of your license registration.</p>" + "<p>Your patience and cooperation during this period are greatly appreciated. If you have any inquiries or require further assistance, please do not hesitate to reach out to us.</p>" + "<p>Thank you for choosing India Advocacy. We remain committed to providing you with exceptional service, and we look forward to the successful completion of your registration.</p>" + "</br>" + "</br>" + "<p><strong>Best Regards,</strong><br/><strong>Team India Advocacy</strong></p>" + "</body></html>";
         mailb = mailb + formattedText;
         System.out.println(mailb);
      } catch (Exception var16) {
         var16.printStackTrace();
      }

      return mailb;
   }
}
