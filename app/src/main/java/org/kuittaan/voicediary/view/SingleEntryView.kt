@file:OptIn(ExperimentalMaterial3Api::class)

package org.kuittaan.voicediary.view

import androidx.compose.foundation.layout.Column
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

class SingleEntryView {

    @Composable
    fun createSingleEntryView() {

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)

        ){
            Text(text = "")
        }

    }
}