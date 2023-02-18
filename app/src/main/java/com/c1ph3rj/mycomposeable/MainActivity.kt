package com.c1ph3rj.mycomposeable

import android.graphics.Paint.Align
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c1ph3rj.mycomposeable.newsApi.Articles
import com.c1ph3rj.mycomposeable.newsApi.NEWSRequestApi
import com.c1ph3rj.mycomposeable.newsApi.NEWSResponse
import com.c1ph3rj.mycomposeable.ui.theme.MyComposeableTheme
import com.c1ph3rj.mycomposeable.ui.theme.Shapes
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {
    companion object{
        lateinit var NEWS_API_KEY :String
        var listOfArticles = mutableStateListOf<Articles>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeableTheme {
                NEWS_API_KEY = stringResource(id = R.string.NEWS_API_KEY)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen()
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                kotlin.run {
                    getTechNews()
                }
            }, 4000L
        )
    }

    private fun getTechNews(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiCall = retrofit.create(NEWSRequestApi::class.java)

        apiCall.getTopTechNews("technology", "en", MainActivity.NEWS_API_KEY)
            .enqueue(object : Callback<NEWSResponse?>{
                override fun onResponse(
                    call: Call<NEWSResponse?>,
                    response: Response<NEWSResponse?>
                ) {
                    println(response)
                    if(response.isSuccessful){
                        response.body()?.articles?.let { listOfArticles.addAll(it) }
                    }
                }

                override fun onFailure(call: Call<NEWSResponse?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

}


@Composable
fun HomeScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
        ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Column {
                    Text(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp,
                        text = "Hi Jeeva"
                    )
                    Text(
                        text = "34`C", fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp
                    )
                }
            }
            Row {
                Box(modifier = Modifier.size(50.dp)){
                    Image(
                        painter = painterResource(id = R.drawable.img),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        LazyRow (
            contentPadding = PaddingValues(40.dp,0.dp,0.dp,0.dp)
                ) {
            val listOfTitle =
                mutableListOf("India", "Pakistan", "China", "United States")
            items (MainActivity.listOfArticles) {
                Article ->
                Article.title?.let { Headline(it) }
            }
        }
    }
}

@Composable
fun Headline(headlineTitle: String) {
    Box(modifier = Modifier
        .width(200.dp)
        .heightIn(100.dp, 100.dp)
        .padding(5.dp)){
        Card (modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp){
            Box (modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            contentAlignment = Alignment.Center) {
                Text(text = headlineTitle,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .padding(10.dp),)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposeableTheme {
        HomeScreen()
    }
}