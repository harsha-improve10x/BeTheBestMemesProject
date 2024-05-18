package com.example.bethebestmemesproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.location.GnssStatusCompat
import androidx.wear.compose.material.Chip
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.bethebestmemesproject.ui.theme.BeTheBestMemesProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeTheBestMemesProjectTheme {
                IntegrateAllComponents()
            }
        }
    }
}

@Composable
fun SingleChipBlock(
    statusCode: Int,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .padding(8.dp)
        .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
        .clickable {
            onClick()
        }
    ) {
        Text(
            text = "HTTP$statusCode",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ChipsBlock(
    statusCode: List<Int>,
    onChipClick: (Int) -> Unit
) {
    Row(modifier = Modifier.padding(8.dp)) {
        statusCode.forEach { code ->
            SingleChipBlock(
                statusCode = code,
                onClick = {
                    onChipClick(code)
                }
            )
        }
    }
}

@Composable
fun ImageBlock(url: String) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp),
        error = painterResource(R.drawable.ic_launcher_background),
        placeholder = painterResource(R.drawable.ic_launcher_background)
    )
}

@Composable
fun BlockLayout() {
    Column(modifier = Modifier.padding(16.dp)) {
        SingleChipBlock(statusCode = 200, onClick = {})
        ChipsBlock(statusCode = listOf(200, 404, 500), onChipClick = {})
        ImageBlock(url = "https://picsum.photos/200/300")
    }
}

@Composable
fun SingleChipBlock1(statusCode: Int, onClick: () -> Unit) {
//Chip("HTTP$statusCode", onClick = {onClick(statusCode)}, modifier = Modifier.padding(8.dp))
}

@Composable
fun ChipsBlock1(statusCode: List<Int>, onChipClick: (Int) -> Unit) {
    Row(modifier = Modifier.padding(8.dp)) {
        statusCode.forEach { code ->
            SingleChipBlock1(statusCode = code, onClick = { onChipClick(code) })
        }
    }
}

@Composable
fun ImageBlock1(url: String) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun BlockLayout1(statusCode: List<Int>, imageUrl: String, onChipClick: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        ChipsBlock1(statusCode = statusCode, onChipClick = onChipClick)
        Spacer(modifier = Modifier.height(16.dp))
        ImageBlock1(url = imageUrl)
    }
}

@Composable
fun HandleChipClick(statusCode: Int, updateImageUrl: (String) -> Unit) {
    val imageUrl = mapStatusCodeToImageUrl(statusCode)
    updateImageUrl(imageUrl)
}

@Composable
fun mapStatusCodeToImageUrl(statusCode: Int): String {
    val statusCodeToImageUrl = mapOf(
        200 to "https://picsum.photos/200",
        404 to "https://picsum.photos/404",
        500 to "https://picsum.photos/500"
    )
    return statusCodeToImageUrl[statusCode] ?: "https://picsum.photos/default"
}

@Composable
fun TestChipClickHandling() {
    val statusCode = 404
    HandleChipClick(statusCode, updateImageUrl = {})
}

@Composable
fun AppLayout(statusCode: List<Int>, imageUrl: String, onChipClick: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        //TopBar()
        Spacer(modifier = Modifier.height(16.dp))
        BlockLayout1(statusCode = statusCode, imageUrl = imageUrl, onChipClick = onChipClick)
    }
}

@Composable
fun generateImageUrl(statusCode: Int): String {
    val statusCodeToImageUrl = mapOf(
        200 to "https://picsum.photos/200",
        404 to "https://picsum.photos/404",
        500 to "https://picsum.photos/500"
    )
    return statusCodeToImageUrl[statusCode] ?: "https://picsum.photos/default"
}

@Composable
fun DisplayImage(imageUrl: String) {
    val painter = rememberImagePainter(imageUrl)
    Image(
        painter = painter,
        contentDescription = "Meme Image",
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun HandleImage(statusCode: Int) {
    val imageUrl = generateImageUrl(statusCode)
    DisplayImage(imageUrl = imageUrl)
}

@Composable
fun TestImageHandling() {
    val statusCode = 404
    HandleImage(statusCode)
}

@Composable
fun OnChipClick(statusCode: Int) {
    HandleImage(statusCode)
}

@Composable
fun TestOverAllImplementation() {
    OnChipClick(200)
    OnChipClick(404)
    OnChipClick(500)
}

@Composable
fun IntegrateAllComponents() {
    Column(modifier = Modifier.fillMaxSize()) {
        BlockLayout()
        AppLayout(
            statusCode = listOf(200, 404, 500),
            imageUrl = "https://picsum.photos/200",
            onChipClick = {})
        TestChipClickHandling()
        TestImageHandling()
        TestOverAllImplementation()
    }
}

@Composable
fun TestAppFunctionality() {
    TestImageHandling()
    TestOverAllImplementation()
    TestChipClickHandling()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BeTheBestMemesProjectTheme {
        IntegrateAllComponents()
    }
}