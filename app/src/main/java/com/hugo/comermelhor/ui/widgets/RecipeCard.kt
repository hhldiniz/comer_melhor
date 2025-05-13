package com.hugo.comermelhor.ui.widgets

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.hugo.comermelhor.R
import com.hugo.comermelhor.data.model.Recipe

@Composable
fun RecipeCard(modifier: Modifier = Modifier, recipe: Recipe, onClick: RecipeListHandlers) {
    Card(modifier = modifier, shape = RoundedCornerShape(12)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                contentAlignment = Alignment.TopEnd
            ) {
                AsyncImage(
                    model = recipe.imageUri?.toUri(),
                    placeholder = painterResource(android.R.drawable.ic_menu_camera),
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    error = painterResource(android.R.drawable.stat_notify_error),
                    fallback = painterResource(android.R.drawable.ic_menu_camera),
                    onError = {
                        Log.e("RecipeCard", "Error loading image: ${recipe.imageUri}")
                    },
                    modifier = modifier
                        .clickable {
                            onClick.onRecipeImageClick(recipe)
                        }
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))

                )
                val dropdownOpenState = remember { mutableStateOf(false) }
                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 8.dp),
                    items = listOf {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.recipe_share_label)) },
                            onClick = {
                                onClick.onShareRecipe(recipe)
                            },
                            trailingIcon = {
                                Icon(imageVector = Icons.Default.Share, contentDescription = "")
                            })
                        DropdownMenuItem(
                            colors = MenuDefaults.itemColors(
                                textColor = Color.Red,
                                trailingIconColor = Color.Red
                            ),
                            text = { Text(stringResource(R.string.recipe_delete_label)) },
                            onClick = { onClick.onItemDelete(recipe) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = ""
                                )
                            })
                    },
                    expanded = dropdownOpenState.value,
                    onDismissRequest = { dropdownOpenState.value = false }) {
                    IconButton(
                        modifier = Modifier.align(Alignment.TopEnd),
                        onClick = { dropdownOpenState.value = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onClick.onRecipeDescriptionClick(recipe)
                    },
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        recipe.description,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth(0.9f),
                        fontSize = 20.sp
                    )
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FoodCardPreview() {
    RecipeCard(recipe = Recipe(0, "", "", 0), onClick = object : RecipeListHandlers {
        override fun onRecipeDescriptionClick(recipe: Recipe) {

        }

        override fun onRecipeImageClick(recipe: Recipe) {

        }

        override fun onItemDelete(recipe: Recipe) {

        }

        override fun onShareRecipe(recipe: Recipe) {

        }
    })
}