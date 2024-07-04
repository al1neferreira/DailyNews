package com.example.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailynews.ui.theme.BlackTextColor
import com.example.dailynews.ui.theme.Blue
import com.example.dailynews.ui.theme.DailyNewsTheme
import com.example.dailynews.ui.theme.Gray
import com.example.dailynews.ui.theme.GrayTextColor
import com.example.dailynews.ui.theme.White
import com.example.dailynews.viewModels.NewsViewModel

data class Suggestion(val title: String = "", var isFocused: Boolean = false)
object SuggestionList {
    val list: List<Suggestion> = listOf(
        Suggestion("All"),
        Suggestion("Politic"),
        Suggestion("Sport"),
        Suggestion("Education"),
        Suggestion("Economy"),
        Suggestion("Technology")
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyNewsTheme {
            }
        }
    }
}

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Header()
        Spacer(modifier = modifier.height(15.dp))
        SearchBar()
        SuggestionRow(suggestion = SuggestionList.list, viewModel = viewModel)
    }
}

@Composable
fun Header(modifier: Modifier = Modifier) {
    Text(
        text = "Discover",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = BlackTextColor
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "News from all aroun the world",
        fontSize = 14.sp,
        color = GrayTextColor
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    var text by remember {
        mutableStateOf("")
    }
    Row(
        modifier
            .fillMaxWidth()
            .background(color = Gray)
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        OutlinedTextField(value = text,
            onValueChange = {
                text = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = White
            ),
            modifier = modifier.weight(1f),
            placeholder = {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                    Spacer(modifier = modifier.width(10.dp))
                    Text(text = "Search")
                }
            }
        )
    }
}

var selectedSuggestionIndex by mutableStateOf(0)

@Composable
fun SuggestionRow(
    suggestion: List<Suggestion>,
    viewModel: NewsViewModel
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(suggestion) { index, sug ->
            SuggestionItem(
                suggestion = sug,
                isFocused = index == selectedSuggestionIndex,
                onClick = {
                    selectedSuggestionIndex = index
                    viewModel.fetchNews()
                }
            )
        }
    }
}

@Composable
fun SuggestionItem(
    suggestion: Suggestion,
    isFocused: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFocused) Blue else Gray
        )
    ) {
        Text(
            text = suggestion.title,
            color = if (isFocused) White else GrayTextColor
        )
    }
}




