package presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.CurrencyCode
import domain.model.CurrencyModel
import domain.model.CurrencyType
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.primaryColor
import ui.theme.surfaceColor
import ui.theme.textColor

@Composable
fun CurrencyPickerDialog(
    listOfCurrency: List<CurrencyModel>,
    currencyType: CurrencyType,
    onPositiveClicked: (CurrencyCode) -> Unit,
    onDismiss: () -> Unit
) {
    val allCurrencies = remember {
        mutableStateListOf<CurrencyModel>().apply {
            this.addAll(listOfCurrency)
        }
    }

    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedCurrencyCode by remember(currencyType) {
        mutableStateOf(currencyType.currencyCode)
    }

    AlertDialog(
        containerColor = surfaceColor,
        title = {
            Text(
                text = "Select a currency",
                color = textColor
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(size = 99.dp)),
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query.uppercase()

                        if(query.isNotEmpty()) {
                            val filteredCurrencies = allCurrencies.filter {
                                it.code.contains(query.uppercase())
                            }
                            allCurrencies.clear()
                            allCurrencies.addAll(filteredCurrencies)
                        }
                        else {
                            allCurrencies.clear()
                            allCurrencies.addAll(listOfCurrency)
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Search here",
                            color = textColor.copy(alpha = 0.38f),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                    },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        color = textColor,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = textColor.copy(alpha = 0.1f),
                        unfocusedContainerColor = textColor.copy(alpha = 0.1f),
                        disabledContainerColor = textColor.copy(alpha = 0.1f),
                        errorContainerColor = textColor.copy(alpha = 0.1f),
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColor
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                AnimatedContent(
                    targetState = allCurrencies
                ) {
                    if (allCurrencies.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            items(
                                items = allCurrencies,
                                key = { currencyModel ->
                                    currencyModel._id.toHexString()
                                }
                            ) { currencyModel ->
                                println("HomeViewModel: Adding to picker ${currencyModel.code}")
                                CurrencyCodePickerView(
                                    currencyCode = CurrencyCode.valueOf(currencyModel.code),
                                    isSelected = selectedCurrencyCode.name == currencyModel.code,
                                    onSelected = { currencyCode ->
                                        selectedCurrencyCode = currencyCode
                                    }
                                )
                            }
                        }
                    }
                    else {
                        ErrorScreen(
                            modifier = Modifier.height(250.dp)
                        )
                    }
                }
            }
        },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.outline
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPositiveClicked(selectedCurrencyCode)
                }) {
                Text(
                    text = "Confirm",
                    color = primaryColor
                )
            }
        }
    )
}

@Composable
@Preview
fun PreviewCurrencyPickerDialog() {
    CurrencyPickerDialog(
        listOfCurrency = emptyList(),
        currencyType = CurrencyType.None,
        onPositiveClicked = {},
        onDismiss = {}
    )
}