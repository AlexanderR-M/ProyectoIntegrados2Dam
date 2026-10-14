package com.iesejemplo.llaves

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iesejemplo.llaves.data.KeysViewModel
import com.iesejemplo.llaves.ui.components.AppBottomBar
import com.iesejemplo.llaves.ui.components.Tab
import com.iesejemplo.llaves.ui.screens.*
import com.iesejemplo.llaves.ui.theme.AppLlavesTheme
import com.iesejemplo.llaves.ui.theme.Background

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { AppLlavesTheme { AppRoot() } }
    }
}

@Composable
fun AppRoot(vm: KeysViewModel = viewModel()) {
    if (!vm.loggedIn) {
        LoginScreen(error = vm.loginError, onLogin = vm::login)
        return
    }

    val nav = rememberNavController()
    val route = nav.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        containerColor = Background,
        bottomBar = {
            AppBottomBar(currentRoute = route, onSelect = { tab ->
                nav.navigate(tab.route) {
                    popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Tab.LLAVES.route,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        ) {
            composable(Tab.LLAVES.route) {
                KeysScreen(vm.keys, vm.available, vm.borrowed)
            }
            composable(Tab.PRESTAMOS.route) {
                LoansScreen(vm.loans, onReturn = vm::returnKey)
            }
            composable(Tab.NFC.route) {
                NfcScreen(
                    query = vm.searchQuery,
                    onQueryChange = { vm.searchQuery = it },
                    results = vm.searchResults(),
                    message = vm.nfcMessage,
                    onTagRead = vm::onTagRead,
                    onMessageShown = vm::clearNfcMessage,
                    onCancel = { vm.searchQuery = "" }
                )
            }
            composable(Tab.INCIDENCIAS.route) {
                IncidentsScreen(onSend = vm::sendIncident)
            }
        }
    }
}
