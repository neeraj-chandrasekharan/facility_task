package com.neeraj.assignment.presentation.facilities.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neeraj.assignment.R
import com.neeraj.assignment.domain.model.Data

@Composable
fun FacilityListItem(
    facility: Data.Facility,
    selectedOptions: Map<String, Data.Facility.Option>? = null,
    exclusionPairs: List<Pair<Data.Facility.Option, Data.Facility.Option>> = emptyList(),
    onOptionSelected: (Data.Facility.Option) -> Unit = {}
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    fun onItemSelected(option: Data.Facility.Option) {
        onOptionSelected(option)
    }

    val arrowIcon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier
        .clickable { expanded = !expanded }
        .fillMaxWidth()
        .drawBehind {
            val width = size.width
            val height = size.height - 2
            drawLine(
                color = Color.Black,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = 2.0F
            )
        }
        .padding(16.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessLow
            )
        )) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = facility.name,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                )
                selectedOptions?.get(facility.facilityId)?.let {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = it.icon),
                        contentDescription = it.name,
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                    Text(
                        text = it.name,
                        fontSize = 12.sp,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
            Image(
                painter = rememberVectorPainter(image = arrowIcon),
                contentDescription = "Arrow",
            )
        }
        if (expanded) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
            facility.options.forEach { option ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    val disabled = exclusionPairs.filter { it.second.id == option.id }.let {
                        it.forEach { pair ->
                            val selOp = selectedOptions?.getOrDefault(pair.first.facilityId, null)
                            if (selOp != null && selOp.id == pair.first.id) return@let true
                        }
                        false
                    }
                    OptionItem(
                        option, selectedOptions ?: mapOf(), disabled = disabled
                    ) {
                        onItemSelected(it)
                    }
                }
            }
        }


    }
}

@Composable
fun OptionItem(
    option: Data.Facility.Option,
    selectedIds: Map<String, Data.Facility.Option>,
    disabled: Boolean = false,
    onItemSelected: (Data.Facility.Option) -> Unit
) {
    val selectedOptionId = selectedIds[option.facilityId]?.id
    val selected = selectedOptionId == option.id
    Box(modifier = Modifier
        .clickable(!disabled) {
            if (!disabled) onItemSelected(option)
        }
        .fillMaxWidth()
        .background(
            when {
                disabled -> Color.White
                selected -> Color(android.graphics.Color.parseColor("#00BCD4"))
                else -> Color.White
            }
        )
        .padding(16.dp, 8.dp)
        .alpha(if (disabled) 0.5F else 1.0F)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = option.icon),
                contentDescription = option.name,
                colorFilter = ColorFilter.tint(
                    when {
                        disabled -> Color.LightGray
                        selected -> Color.White
                        else -> Color.Black
                    }
                )
            )
            Text(
                text = option.name, fontSize = 16.sp, color = when {
                    disabled -> Color.LightGray
                    selected -> Color.White
                    else -> Color.Black
                }, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        }

    }
}

@Preview(
    showBackground = true
)
@Composable
fun Default() {
    FacilityListItem(facility = Data.Facility("1", "Name", emptyList()))
}

@Preview(
    showBackground = true
)
@Composable
fun OptionItemPreview() {
    val option = Data.Facility.Option(
        icon = R.drawable.ic_no_room, id = "1", facilityId = "3", name = "name"
    )
    val selectedOption = Data.Facility.Option(
        icon = R.drawable.ic_no_room, id = "1", facilityId = "3", name = "name"
    )
    OptionItem(
        option = option, selectedIds = mapOf("3" to selectedOption), disabled = false
    ) {}
}