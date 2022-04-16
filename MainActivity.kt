package m.derakhshan.swipablecards

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import m.derakhshan.swipablecards.ui.theme.SwipableCardsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipableCardsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val items =
                        remember { mutableStateListOf("hello", "this is", "for", "test", "ok") }
                    var zValue by remember { mutableStateOf(1f) }

                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            if (items.size > 1)
                                Card(
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .size(width = 300.dp, 300.dp)
                                        .background(Color.Gray, shape = RoundedCornerShape(15.dp))
                                        .clip(shape = RoundedCornerShape(15.dp)),
                                    sentence = if ((1 - zValue) == 1f) items[0] else items[1],
                                    zIndex = 1 - zValue
                                ) {
                                    items.removeFirst()
                                    zValue = 1 - zValue
                                }
                            if (items.size > 0)
                                Card(
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .size(width = 300.dp, 300.dp)
                                        .background(Color.Blue, shape = RoundedCornerShape(15.dp))
                                        .clip(shape = RoundedCornerShape(15.dp)),
                                    sentence = if (zValue == 1f) items[0] else items[1],
                                    zIndex = zValue
                                ) {
                                    zValue = 1 - zValue
                                    items.removeFirst()
                                }
                        }
                    }
                }
            }
        }
    }
}

