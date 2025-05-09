package com.example.myapplication.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    // متغیرهای حالت برای ایمیل و رمز عبور
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // برای نمایش Snackbar
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // تنظیمات Google Sign-In
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("YOUR_WEB_CLIENT_ID") // از Firebase Console بگیر و جایگزین کن
        .requestEmail()
        .build()
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    // لانچر برای دریافت نتیجه Google Sign-In
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onLoginSuccess() // تغییر وضعیت لاگین
                } else {
                    errorMessage = "Google Sign-In failed: ${task.exception?.message}"
                }
            }
        } catch (e: ApiException) {
            errorMessage = "Google Sign-In error: ${e.message}"
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))

            Text(text = "Hello", fontSize = 48.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text(text = "Again!", fontSize = 48.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Welcome back you’ve\nbeen missed",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.06f))

            // فیلد ایمیل
            Text(
                text = "Email",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // فیلد رمز عبور
            Text(
                text = "Password",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // دکمه لاگین با ایمیل و رمز عبور
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    onLoginSuccess() // تغییر وضعیت لاگین
                                    errorMessage = null
                                } else {
                                    errorMessage = "Login failed: ${task.exception?.message}"
                                }
                            }
                    } else {
                        errorMessage = "Please fill in both email and password"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Login with Email", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // دکمه Google Sign-In
            Button(
                onClick = {
                    val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Sign in with Google", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = "don’t have an account? ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W300,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Sign Up",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { /* منطق Sign Up */ }
                )
            }

            // نمایش خطاها با Snackbar
            LaunchedEffect(errorMessage) {
                errorMessage?.let {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                    errorMessage = null
                }
            }
        }
    }
}