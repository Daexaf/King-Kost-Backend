    package com.enigma.kingkost.controllers;

    import com.enigma.kingkost.constant.AppPath;
    import com.enigma.kingkost.services.CustomerService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping(AppPath.RESET_PASSWORD)
    public class ResetPasswordController {
        private final CustomerService customerService;

        @PostMapping("/request")
        public ResponseEntity<String> requestPasswordReset(@RequestParam String usernameOrEmail) {
            try {
                customerService.resetPassword(usernameOrEmail);
                return ResponseEntity.ok("Password reset request has been sent. Check your email.");
            } catch (NullPointerException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    }
