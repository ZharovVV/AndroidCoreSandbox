package com.github.zharovvv.compose.sandbox.ui.pager

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerScope

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnimatedPager(
    count: Int,
    contentPadding: PaddingValues,
    content: @Composable PagerScope.(Int) -> Unit
) {
    HorizontalPager(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(vertical = 32.dp),
        count = count,
        contentPadding = contentPadding,
        content = content
    )
}