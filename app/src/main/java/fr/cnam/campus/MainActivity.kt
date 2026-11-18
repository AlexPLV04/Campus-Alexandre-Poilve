
package fr.cnam.campus

import android.os.Bundle
import android.util.Log
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.cnam.campus.ui.theme.CampusTheme

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CampusTheme {
//        Greeting("Android")
//    }
//}

data class Event(
    val id: Int,
    val title: String,
    val category: String,
    val location: String?,
    val capacity: Int,
    val description: String
)

val events = listOf(
    Event(1, "Event truc", "Blague nulle", "31.1.71", 20, "BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA"),
    Event(2, "Event machin", "Blague nulle", "31.1.67", 20, "BLA BLA BLA BLA"),
    Event(3, "Event bidule", "Blague nulle", "17.2.3", 10, "BLA BLA BLA BLA     BLA BLA BLA BLA"),
    Event(4, "Event jsp", "Plus nulle", null, 20, "BLA BLA BLA BLABLA BLA BLA BLA"),
    Event(5, "Event complet", "Plus nulle", "Hall", 0, "COIN COIN COIN")
)

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(onClick = onClick, modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(event.category)
            }

            Text(
                event.location
                    ?: stringResource(R.string.location_unknown)
            )

            Text(
                pluralStringResource(
                    R.plurals.available_seats,
                    event.capacity,
                    event.capacity
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onClick) {
                    Text(stringResource(R.string.view))
                }

                Button(
                    onClick = onBook,
                    enabled = event.capacity > 0
                ) {
                    Text(stringResource(R.string.book))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    CampusTheme {
        EventCard(
            event = events[4],
            onClick = {},
            onBook = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(events: List<Event>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            item {
                LazyRow {
                    items(events.map { it.category }.distinct().mapIndexed { index, category ->
                        if (index == 0) category else ", $category"
                    }) { category ->
                        Text(category)
                    }
                }
            }

            items(events, key = { it.id }) { event ->
                EventCard(
                    event = event,
                    onClick = {
                        Log.d("Campus", "Clic sur ${event.id}")
                    },
                    onBook = {
                        Log.d("Campus", "Réservation ${event.id}")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

//test
@Composable
fun EventDetailScreen(event: Event) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {

        Image(
            painter = painterResource(R.drawable.images),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )


        Text(
            text = event.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(event.category)

        Text(
            event.location
                ?: stringResource(R.string.location_unknown)
        )

        Text(event.description)

        Text(
            pluralStringResource(
                R.plurals.available_seats,
                event.capacity,
                event.capacity
            )
        )

        Button(
            onClick = {},
            enabled = event.capacity > 0
        ) {
            Text(stringResource(R.string.book))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventListLightPreview() {
    CampusTheme {
        EventListScreen(events)
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun EventListDarkPreview() {
    CampusTheme(darkTheme = true) {
        EventListScreen(events)
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailLightPreview() {
    CampusTheme {
        EventDetailScreen(events[0])
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun EventDetailDarkPreview() {
    CampusTheme(darkTheme = true) {
        EventDetailScreen(events[0])
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusTheme {
                EventListScreen(events)
//                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
////                    Greeting(
////                        name = "Android",
////                        modifier = Modifier.padding(innerPadding)
////                    )
//                    EventCard(
//                        event = Event(1, "Conférence Kotlin", "TECH", null, 30),
//                        onClick = {},
//                        modifier = Modifier.padding(innerPadding)
//                    )
            }
        }
    }
}
