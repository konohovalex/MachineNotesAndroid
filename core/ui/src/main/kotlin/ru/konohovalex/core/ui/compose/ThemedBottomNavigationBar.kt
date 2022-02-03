package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import kotlinx.android.parcel.Parcelize
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.extension.unwrap
import ru.konohovalex.core.ui.model.BottomNavigationItem
import ru.konohovalex.core.ui.model.ButtonData
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.TextWrapper

@Composable
fun ThemedBottomNavigationBar(
    bottomNavigationItems: List<BottomNavigationItem>,
    selectedBottomNavigationItem: BottomNavigationItem,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = Theme.palette.backgroundColor,
        elevation = Theme.materialElevations.bottomNavigationBar,
    ) {
        Row(
            modifier = Modifier
                .padding(PaddingValues(vertical = Theme.paddings.bottomNavigationBarVertical)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val selectedBottomNavigationItemState = rememberSaveable {
                mutableStateOf(selectedBottomNavigationItem)
            }

            bottomNavigationItems.forEach { bottomNavigationItem ->
                BottomNavigationBarPosition(
                    bottomNavigationItem = bottomNavigationItem,
                    selectedBottomNavigationItem = selectedBottomNavigationItemState.value,
                    onBottomNavigationItemSelected = {
                        val isSelectedPosition = it == selectedBottomNavigationItemState.value
                        selectedBottomNavigationItemState.value = it
                        if (!isSelectedPosition) navController.performBottomNavigation(it)
                    },
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBarPosition(
    bottomNavigationItem: BottomNavigationItem,
    selectedBottomNavigationItem: BottomNavigationItem?,
    onBottomNavigationItemSelected: (BottomNavigationItem) -> Unit,
) {
    val content = mutableListOf<ButtonData.Content>(
        ButtonData.Content.Image(imageWrapper = bottomNavigationItem.imageWrapper)
    ).apply {
        val contentDescription = bottomNavigationItem.imageWrapper.contentDescription
        contentDescription
            ?.unwrap()
            ?.takeIf { it.isNotBlank() }
            ?.let {
                add(ButtonData.Content.Text(textWrapper = contentDescription))
            }
    }

    ThemedButton(
        buttonData = ButtonData.Outlined(
            onClick = {
                onBottomNavigationItemSelected.invoke(bottomNavigationItem)
            },
            content = content,
            contentArrangement = ButtonData.ContentArrangement.VERTICAL,
            contentSpacing = Theme.paddings.contentExtraSmall,
            selected = bottomNavigationItem == selectedBottomNavigationItem,
        )
    )
}

private fun NavController.performBottomNavigation(bottomNavigationItem: BottomNavigationItem) {
    navigate(bottomNavigationItem.navigationRoute) {
        launchSingleTop = true
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        restoreState = true
    }
}

private sealed class PreviewBottomNavigationItem(
    override val navigationRoute: String = "",
    override val imageWrapper: ImageWrapper,
) : BottomNavigationItem {
    @Parcelize
    object NoteList : PreviewBottomNavigationItem(
        imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_notes),
    )

    @Parcelize
    object Preferences : PreviewBottomNavigationItem(
        imageWrapper = ImageWrapper.ImageResource(
            resourceId = R.drawable.ic_preferences,
            contentDescription = TextWrapper.PlainText(value = "Настройки"),
        ),
    )

    @Parcelize
    object Profile : PreviewBottomNavigationItem(
        imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_profile),
    )
}

@Preview
@Composable
private fun ThemedBottomAppBarPreview() {
    Theme {
        ThemedBottomNavigationBar(
            bottomNavigationItems = listOf(
                PreviewBottomNavigationItem.NoteList,
                PreviewBottomNavigationItem.Preferences,
                PreviewBottomNavigationItem.Profile,
            ),
            selectedBottomNavigationItem = PreviewBottomNavigationItem.NoteList,
            navController = rememberNavController(),
        )
    }
}
