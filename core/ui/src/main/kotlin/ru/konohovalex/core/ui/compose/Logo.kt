package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = stringResource(id = R.string.logo),
        modifier = Modifier
            .padding(Theme.paddings.contentMedium),
    )
}
