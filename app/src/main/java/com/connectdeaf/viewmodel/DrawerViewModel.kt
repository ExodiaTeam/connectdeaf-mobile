package com.connectdeaf.viewmodel

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.lifecycle.ViewModel

class DrawerViewModel : ViewModel() {
    val drawerStateMenu = DrawerState(DrawerValue.Closed)
    val drawerStateNotifications = DrawerState(DrawerValue.Closed)


    suspend fun openMenuDrawer(): DrawerState {
        drawerStateMenu.open()
        return drawerStateMenu
    }

    suspend fun closeMenuDrawer() : DrawerState {
        drawerStateMenu.close()
        return drawerStateMenu
    }

    suspend fun openNotificationsDrawer(): DrawerState {
        drawerStateNotifications.open()
        return drawerStateNotifications
    }

    suspend fun closeNotificationsDrawer(): DrawerState {
        drawerStateNotifications.close()
        return drawerStateNotifications
    }
}
