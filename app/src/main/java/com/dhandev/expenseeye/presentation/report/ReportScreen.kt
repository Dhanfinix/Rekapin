package com.dhandev.expenseeye.presentation.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.navigation.NavigationDestination

object ReportDestination : NavigationDestination{
    override val route: String = "report"
    override val titleRes: Int = R.string.report
}
@Composable
fun ReportScreen(
    modifier : Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "This is Report Screen")
    }
}