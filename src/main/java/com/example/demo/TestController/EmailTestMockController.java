// package com.example.demo.TestController;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.stereotype.Controller;

// import com.example.demo.service.EmailService;

// @Controller
// class TempMailTestController {

//     private final EmailService emailService;

//     TempMailTestController(EmailService emailService) 
//     {
//         this.emailService = emailService;
//     }

//     @GetMapping("/test/mail")
//     @ResponseBody
//     public String testMail() 
//     {
//         emailService.sendEmail("hmonwabise@gmail.com", "Test Mail", "If you're reading this, Spring Mail works.");
//         return "Sent — check your inbox and or spam folder";
//     }
// }


package com.example.demo.TestController;

import com.example.demo.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("uniqueEmailTestController")
@RequestMapping("/api/email")
public class EmailTestMockController {

    private final EmailService emailService;

    public EmailTestMockController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-html")
    public ResponseEntity<String> testHtmlEmail(@RequestBody Map<String, String> request) {
        String to = request.get("to");
        String subject = request.get("subject");
        String htmlBody = request.get("htmlBody");

        if (to == null || subject == null || htmlBody == null) {
            return ResponseEntity.badRequest().body("Missing required fields: to, subject, or htmlBody");
        }

        emailService.sendHtmlEmail(to, subject, htmlBody);
        
        return ResponseEntity.ok("HTML email processing started via background thread.");
    }
}
