package com.pogrom.kotlinlvl1_2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import coil.decode.ImageDecoderDecoder
import coil.imageLoader
import coil.request.ImageRequest
import com.pogrom.kotlinlvl1_2.data.model.ImageItem


@Composable
fun ImageList(
    modifier: Modifier,
    mainViewModel: MainViewModel = viewModel(
        factory = MainViewModel.provideFactory(LocalContext.current)
    )
) {
    val state = mainViewModel.state
    Column(

    ){
        LazyVerticalStaggeredGrid(
            modifier = modifier,
            columns = StaggeredGridCells.Adaptive(150.dp),
            horizontalArrangement = Arrangement.Center

        ) {
            items(state.items.size) { i ->
                val item = state.items[i]
                if (i >= state.items.size - 1 && !state.endReached && !state.isLoading && state.error == null)
                    mainViewModel.loadNextItems()
                ImageCell(
                    item = item,
                    onLoadImg = { },
                    modifier = Modifier
                        .aspectRatio(item.aspectRatio)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            if (state.error != null && !state.isLoading)
                item {
                    IconButton(
                        onClick = { mainViewModel.loadNextItems() }, modifier = Modifier
                            .padding(30.dp)
                            .size(150.dp)


                    ) {
                        Icon(
                            Icons.Outlined.Refresh,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            else
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(30.dp)
                            .size(150.dp)
                    )
                }
        }

    }
}

@Composable
fun ImageCell(
    item: ImageItem,
    onLoadImg: (ImageItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current


    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary
    ){

        SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(item.data)
                        .size(141)
                        .decoderFactory(ImageDecoderDecoder.Factory())
                        .build(),
                    loading = {
                        LinearProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                    },
            error = {
                    Icon(Icons.Filled.Clear, contentDescription = null)
            },
                    imageLoader = LocalContext.current.imageLoader,
                    contentDescription = item.title,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .padding(4.dp)
                )

    }
}