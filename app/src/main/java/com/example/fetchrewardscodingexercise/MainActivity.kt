package com.example.fetchrewardscodingexercise

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetchrewardscodingexercise.MainActivity.VisibleGroups
import com.example.fetchrewardscodingexercise.ui.theme.FetchRewardsCodingExerciseTheme
import com.example.fetchrewardscodingexercise.ui.theme.lightBlue
import com.example.fetchrewardscodingexercise.ui.theme.lightGreen
import com.example.fetchrewardscodingexercise.ui.theme.lightRed
import com.example.fetchrewardscodingexercise.ui.theme.lightYellow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    /**
     * Easy way to contain all booleans needed to show visible groups
     */
    data class VisibleGroups(
        var group1: Boolean = true,
        var group2: Boolean = true,
        var group3: Boolean = true,
        var group4: Boolean = true
    )

    /**
     * Main view
     */
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            FetchRewardsCodingExerciseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Blue
                ) {

                    // Hold state for all buttons
                    val visibleGroups = remember { mutableStateOf(VisibleGroups()) }

                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(),
                                title = {
                                    Text(
                                        text = "Fetch Rewards Coding Exercise"
                                    )
                                },
                                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
                            )
                        }
                    ) { paddingValues ->
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight(0.25f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = visibleGroups.value.group1,
                                        onCheckedChange = {
                                            visibleGroups.value =
                                                visibleGroups.value.copy(group1 = it)
                                        }
                                    )
                                    Text(
                                        text = "Show Group 1"
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = visibleGroups.value.group2,
                                        onCheckedChange = {
                                            visibleGroups.value =
                                                visibleGroups.value.copy(group2 = it)
                                        }
                                    )
                                    Text(
                                        text = "Show Group 2"
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = visibleGroups.value.group3,
                                        onCheckedChange = {
                                            visibleGroups.value =
                                                visibleGroups.value.copy(group3 = it)
                                        }
                                    )
                                    Text(
                                        text = "Show Group 3"
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = visibleGroups.value.group4,
                                        onCheckedChange = {
                                            visibleGroups.value =
                                                visibleGroups.value.copy(group4 = it)
                                        }
                                    )
                                    Text(
                                        text = "Show Group 4"
                                    )
                                }
                            }

                            //Spacer(modifier = Modifier.weight(1f))

                            DisplayListView(
                                modifier = Modifier
                                    .fillMaxHeight(1f)
                                    .fillMaxWidth(1f),
                                visibleGroups = visibleGroups.value
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Displays the hiring list as a scrollable column
 *
 * @param modifier the modifiers for the main column
 * @param visibleGroups the group ids that will be visible
 */
@Composable
fun DisplayListView(modifier: Modifier = Modifier, visibleGroups: MainActivity.VisibleGroups) {
    val context = LocalContext.current

    val hiringList = remember { mutableStateListOf<HiringListModal>() }

    // Only need to pull the json data once
    LaunchedEffect(Unit) {
        getJSONData(hiringList, context);
    }

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(hiringList) { data ->
            // Hide the element if its group is not supposed to be visible
            val isVisible = when (data.listId) {
                1 -> visibleGroups.group1
                2 -> visibleGroups.group2
                3 -> visibleGroups.group3
                4 -> visibleGroups.group4
                else -> false
            }
            if (isVisible) {
                Card(
                    shape = AbsoluteCutCornerShape(0),
                    // Change color based on group
                    colors = CardDefaults.cardColors(
                        containerColor = when (data.listId) {
                            1 -> lightRed
                            2 -> lightBlue
                            3 -> lightGreen
                            4 -> lightYellow
                            else -> Color.White
                        }
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "Group ${data.listId}",
                                textAlign = TextAlign.Right
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            // Need to handle if the name is null, should be filtered out
                            data.name?.let {
                                Text(
                                    text = it,
                                    textAlign = TextAlign.Left
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "ID = ${data.id}",
                                textAlign = TextAlign.Left
                            )
                        }
                    }
                    HorizontalDivider(
                        color = Color.Black
                    )
                }
            }
        }
    }
}

/**
 * Previews the display list view
 */
@Preview(showBackground = true)
@Composable
fun DisplayListViewPreview() {
    val visibleGroups: VisibleGroups = VisibleGroups();

    FetchRewardsCodingExerciseTheme {
        DisplayListView(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(1f),
            visibleGroups = visibleGroups
        )
    }
}

/**
 * Gets the JSON data from fetch and formats it according to the project requirements
 *
 * @param hiringList the list of hiring items which we will change
 * @param ctx the context the function is called in
 */
fun getJSONData(hiringList: MutableList<HiringListModal>, ctx: Context) {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    val retrofitAPI = retrofit.create(RetrofitAPI::class.java);

    val call: Call<ArrayList<HiringListModal>> = retrofitAPI.getHiringData();

    call!!.enqueue(object : Callback<ArrayList<HiringListModal>?> {
        override fun onResponse(
            call: Call<ArrayList<HiringListModal>?>,
            response: Response<ArrayList<HiringListModal>?>
        ) {
            if (response.isSuccessful) {
                var dataReceived: List<HiringListModal> = ArrayList()

                // Set the received data to the response body
                dataReceived = response.body()!!;

                // Format the received data and cast it back to ArrayList
                dataReceived = DataFormatter.sortData(DataFormatter.filterData(dataReceived));

                // Add data to hiring list
                for (res in dataReceived) {
                    hiringList.add(res);
                }
            }
        }

        override fun onFailure(call: Call<ArrayList<HiringListModal>?>, t: Throwable) {
            Toast.makeText(ctx, "Failed to get data", Toast.LENGTH_SHORT)
                .show();
        }
    })
}
