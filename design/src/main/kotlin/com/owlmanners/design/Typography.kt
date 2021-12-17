package com.owlmanners.design

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val typography = Typography(
    // header
    h1 = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
    ),
    // title
    h2 = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
    ),
    // subtitle
    subtitle1 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
    ),
    // info
    subtitle2 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    ),
    button = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
    ),
    // editable label
    h3 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
    ),
    // editable input
    body1 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
    ),
    // editable assistive
    overline = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    ),
    // clickable text large
    h4 = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
    ),
    // clickable text small
    h5 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
    ),
)
