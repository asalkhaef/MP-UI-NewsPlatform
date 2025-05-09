package com.example.myapplication.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isRememberMeChecked by remember { mutableStateOf(false) }




    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal =  (16 + (16*1f)).dp , vertical = 16.dp)
                .background(Color.White),

        ) {

        Spacer(modifier = Modifier.fillMaxHeight(0.1f))

        Text(text = "Hello", fontSize = 48.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        Text(text = "Again!", fontSize = 48.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Welcome back you’ve\n" +
                "been missed", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.fillMaxHeight(0.06f))

        Text(text = "Username", fontSize = 16.sp, color =  MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Password", fontSize = 16.sp, color =  MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 8.dp) ,
            verticalAlignment = CenterVertically
           , horizontalArrangement = Arrangement.SpaceBetween


        ) {
            Checkbox(
                modifier = Modifier
                    .padding(0.dp),
                checked = isRememberMeChecked,
                onCheckedChange = { isRememberMeChecked = it }
            )

            Text(
                text = "Remember me",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 0.dp).clickable { isRememberMeChecked = !isRememberMeChecked }
            )
            Text(
                text = "Forgot the password ?",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable {  },

            )

        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val success = login(username, password)
                    withContext(Dispatchers.Main) {
                        if (success) {
                            onLoginSuccess()
                        } else {
                            // TODO: Show an error message (e.g., Toast, Snackbar)
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Login", fontSize = 16.sp, fontWeight = FontWeight.W700)
        }


        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.align(CenterHorizontally)) {
            Text(
                text = "don’t have an account ? ",
                fontSize = 14.sp,
                fontWeight = FontWeight.W300,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "Sign Up",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable {

                    }

            )

        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}
