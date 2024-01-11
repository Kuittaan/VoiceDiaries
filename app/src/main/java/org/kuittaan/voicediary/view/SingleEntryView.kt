@file:OptIn(ExperimentalMaterial3Api::class)

package org.kuittaan.voicediary.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class SingleEntryView {

    @Composable
    fun createSingleEntryView() {

        Row {

            Card(
                modifier = Modifier
                    .padding(50.dp)

            ){

                IconButton(
                    onClick = {
                        // todo: play entry using TTSManager
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Yellow)
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Close")
                }
            }
        }
    }
}