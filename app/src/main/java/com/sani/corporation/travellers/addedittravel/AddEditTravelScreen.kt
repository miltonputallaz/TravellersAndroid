package com.sani.corporation.travellers.addedittravel

import android.net.Uri
import android.widget.ImageButton
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sani.corporation.travellers.R
import com.sani.corporation.travellers.TravellersTheme
import com.sani.corporation.travellers.util.AddEditTaskTopAppBar

@Composable
fun AddTravelScreen(
    @StringRes topBarTitle: Int,
    onTravelUpdate: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddTravelScreenViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { AddEditTaskTopAppBar(topBarTitle, onBack) },
        floatingActionButton = {
            SmallFloatingActionButton(onClick = viewModel::saveTravel) {
                Icon(Icons.Filled.Done, stringResource(id = R.string.cd_save_task))
            }
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                viewModel.updateImageUri(uri)
            } else {

            }
        }


        AddTravelScreenContent(
            loading = uiState.isLoading,
            title = uiState.title,
            description = uiState.description,
            imageUri = uiState.imageUri,
            onTitleChanged = viewModel::updateTitle,
            onDescriptionChanged = viewModel::updateDescription,
            onClickEditImage = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            modifier = Modifier.padding(paddingValues)
        )

        // Check if the task is saved and call onTaskUpdate event
        LaunchedEffect(uiState.isTravelSaved) {
            if (uiState.isTravelSaved) {
                onTravelUpdate()
            }
        }

        // Check for user messages to display on the screen
        uiState.userMessage?.let { userMessage ->
            val snackbarText = stringResource(userMessage)
            LaunchedEffect(snackbarHostState, viewModel, userMessage, snackbarText) {
                snackbarHostState.showSnackbar(snackbarText)
                viewModel.snackbarMessageShown()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTravelScreenContent(
    loading: Boolean,
    title: String,
    description: String,
    imageUri: Any?,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onClickEditImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val refreshingState = rememberPullToRefreshState()


    if (loading) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            state = refreshingState,
            onRefresh = { /* DO NOTHING */ },
            content = { }
        )
    } else {
        Column(
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(all = dimensionResource(id = R.dimen.horizontal_margin))
                .verticalScroll(rememberScrollState())
        ) {
            val textFieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onSecondary
            )

            Box(
                modifier =  Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            ) {

                AsyncImage(
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUri)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )

                Button(
                    onClick = onClickEditImage ,
                    shape = RectangleShape,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopStart),
                    contentPadding = PaddingValues(1.dp)
                ) {
                    // Inner content including an icon and a text label
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(20.dp)
                    )
                }

            }


            OutlinedTextField(
                value = title,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onTitleChanged,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.title_hint),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                textStyle = MaterialTheme.typography.headlineSmall
                    .copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                colors = textFieldColors
            )
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChanged,
                placeholder = { Text(stringResource(id = R.string.description_hint)) },
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth(),
                colors = textFieldColors
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    TravellersTheme {
        AddTravelScreen(
            R.string.list_title,
            {},
            {}
        )
    }
}

