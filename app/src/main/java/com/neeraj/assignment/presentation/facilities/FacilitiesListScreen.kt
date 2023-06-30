package com.neeraj.assignment.presentation.facilities;

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.neeraj.assignment.domain.model.Data
import com.neeraj.assignment.presentation.facilities.components.CustomButton
import com.neeraj.assignment.presentation.facilities.components.FacilityListItem
import org.koin.androidx.compose.koinViewModel


@Composable
fun FacilitiesListScreen(
    viewModel: FacilitiesViewModel = koinViewModel()
) {
    Content(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(viewModel: FacilitiesViewModel? = null) {

    fun onOptionSelected(option: Data.Facility.Option) {
        viewModel?.onOptionSelected(option)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            title = {
                Text(text = "Facilities")
            })
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            val state = viewModel?.state?.value
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    if (state?.isLoading == true) {
                        Spacer(modifier = Modifier.height(20.dp))
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(20.dp))
                    } else if (state?.error?.isNotEmpty() == true) {
                        Text(
                            text = state.error,
                            fontSize = 14.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
                items(state?.facilities ?: emptyList()) { facility ->
                    FacilityListItem(
                        facility = facility,
                        selectedOptions = state?.selectedOptions,
                        exclusionPairs = state?.exclusionPairs?.filter {
                            it.first != null && it.second != null
                        }?.map { it.first!! to it.second!! } ?: emptyList()) {
                        onOptionSelected(it)
                    }
                }
                item {
                    viewModel?.let {
                        Buttons(it)
                    }
                }

            }
        }

    }
}

@Composable
fun ButtonLayout(
    enabled: Boolean,
    onApplyClicked: () -> Unit = {},
    onClearClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 10.dp, 10.dp, 30.dp)
    ) {
        CustomButton(
            text = "Clear",
            textColor = Color.White,
            color = Color.Red,
            enabled = enabled,
            modifier = Modifier.weight(1f)
        ) {
            onClearClicked()
        }
        Spacer(modifier = Modifier.width(20.dp))
        CustomButton(
            text = "Apply",
            textColor = Color.White,
            color = Color.Green,
            enabled = enabled,
            modifier = Modifier.weight(1f)
        ) {
            onApplyClicked()
        }
    }
}

@Composable
fun Buttons(viewModel: FacilitiesViewModel) {
    val state = viewModel.state.value
    val enabled = (!state.isLoading && state.selectedOptions.isNotEmpty())
    fun onApplyClicked() = viewModel.applySelection()
    fun onClearClicked() = viewModel.clearSelection()
    ButtonLayout(enabled, ::onApplyClicked, ::onClearClicked)
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ButtonPreview() {
    ButtonLayout(true)
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ButtonPreviewDisabled() {
    ButtonLayout(false)
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun DefaultPreview() {
    Content()
}
