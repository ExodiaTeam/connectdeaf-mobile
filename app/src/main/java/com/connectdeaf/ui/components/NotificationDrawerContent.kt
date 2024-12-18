package com.connectdeaf.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.connectdeaf.data.model.notifications
import kotlinx.coroutines.CoroutineScope

@Composable
fun NotificationDrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(380.dp)
            .padding(end = 20.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Notificações",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(notifications) { notification ->
                    NotificationCard(
                        notification = notification,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            println("Notification clicked: ${notification.title}")
                        }
                    )
                }
            }
        }
    }
}
