package com.hugo.comermelhor.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.hugo.comermelhor.android.widgets.RecipeListHandlers
import com.hugo.comermelhor.data.model.Recipe

@Composable
fun RecipeCard(modifier: Modifier = Modifier, recipe: Recipe, onClick: RecipeListHandlers) {
    Card(modifier = modifier, shape = RoundedCornerShape(12)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(300.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = "https://placehold.co/900x700/png",
                contentDescription = "",
                modifier.clickable {
                    onClick.onRecipeImageClick(recipe)
                })
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clickable {
                            onClick.onRecipeDescriptionClick(recipe)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        recipe.description,
                        modifier = Modifier.padding(start = 8.dp),
                        textAlign = TextAlign.Center,
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
    })
}