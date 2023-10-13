@file:OptIn(ExperimentalMaterial3Api::class)

package org.kuittaan.voicediary

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kuittaan.voicediary.ui.theme.VoiceDiaryTheme

class EntryView {

    @Composable
    fun createMainView() {

        VoiceDiaryTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

            }
        }


        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        {
            items(5) {
                item ->
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = item.toString())
                }
            }
        }

    }

}