// package com.schoopy.back.fcm.config;

// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
// import com.google.firebase.messaging.FirebaseMessaging;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.io.IOException;
// import java.io.InputStream;
// import java.io.FileNotFoundException;

// @Configuration
// public class FirebaseConfig {
//     @Bean
//     public FirebaseMessaging firebaseMessaging() throws IOException {
//         System.out.println("▶ FirebaseMessaging Bean 생성 시작");

//         InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-service-account.json");

//         if (serviceAccount == null) {
//             throw new FileNotFoundException("firebase-service-account.json not found in classpath!");
//         }

//         FirebaseOptions options = FirebaseOptions.builder()
//                 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                 .build();

//         if (FirebaseApp.getApps().isEmpty()) {
//             FirebaseApp.initializeApp(options);
//             System.out.println("✅ FirebaseApp 초기화 완료");
//         }

//         return FirebaseMessaging.getInstance();
//     }
// }
