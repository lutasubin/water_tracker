package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.weappsinc.watertracker.R
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.AppTypography

/** Popup chúc mừng đạt đúng mục tiêu cân (giống flow tab Water, khác chữ). */
@Composable
fun WeighTargetMetDialog(
    targetWeightDisplay: String,
    onDismiss: () -> Unit,
) {
    val line2Text = buildAnnotatedString {
        append(stringResource(R.string.weigh_target_met_dialog_line2_prefix))
        withStyle(style = SpanStyle(color = AppColors.HomePrimary)) {
            append(targetWeightDisplay)
        }
    }
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = AppColors.HomeCard,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.HomeCard)
                    .padding(horizontal = 26.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "🎉",
                    style = TextStyle(fontSize = 52.sp, lineHeight = 58.sp),
                )
                Spacer(Modifier.height(18.dp))
                Text(
                    text = stringResource(R.string.weigh_target_met_dialog_line1),
                    color = AppColors.HomeTitle,
                    style = AppTypography.Title3,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )
                Text(
                    text = line2Text,
                    color = AppColors.HomeTitle,
                    style = AppTypography.Title3,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.HomePrimary,
                        contentColor = AppColors.HomeCard,
                    ),
                    modifier = Modifier.height(42.dp),
                ) {
                    Text(
                        text = stringResource(R.string.close),
                        style = AppTypography.BodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
            }
        }
    }
}
