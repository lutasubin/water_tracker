package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil.ImageLoader
import coil.compose.AsyncImage
import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.core.constants.AssetPaths
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppDimens
import com.weappsinc.watertracker.app.core.theme.AppTypography
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit

@Composable
fun WeighHeightWeightCards(
    heightValue: String,
    weightValue: String,
    massUnit: MassUnit,
    imageLoader: ImageLoader,
    onEditHeight: () -> Unit,
    onEditWeight: () -> Unit,
    modifier: Modifier = Modifier
) {
    val massLabel = if (massUnit == MassUnit.KG) AppText.UNIT_MASS_KG else AppText.UNIT_MASS_LB
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = WeighDimens.ScreenHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(WeighDimens.CardsSpacing)
    ) {
        WeighStatCard(
            iconPath = AssetPaths.HEIGHT_ICON,
            label = AppText.WEIGH_HEIGHT_LABEL,
            primary = heightValue,
            unit = AppText.UNIT_CM,
            imageLoader = imageLoader,
            onEdit = onEditHeight,
            modifier = Modifier.weight(1f)
        )
        WeighStatCard(
            iconPath = AssetPaths.WEIGHT_ICON,
            label = AppText.WEIGH_WEIGHT_LABEL,
            primary = weightValue,
            unit = massLabel,
            imageLoader = imageLoader,
            onEdit = onEditWeight,
            modifier = Modifier.weight(1f)
        )
    }
}
