package com.pocketly.app.ui.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pocketly.app.data.TransactionRepository
import com.pocketly.app.ui.theme.InriaSerifFontFamily
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily
import com.pocketly.app.ui.theme.TextPrimary
import com.pocketly.app.ui.theme.TextSecondary

@Composable
fun AddTransactionDialog(
    onDismiss: () -> Unit,
    onTransactionAdded: () -> Unit
) {
    val context = LocalContext.current
    val repository = remember { TransactionRepository(context) }

    var title by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var isCredit by remember { mutableStateOf(false) } // false = Expense, true = Income
    var selectedCategory by remember { mutableStateOf("Food") }
    var selectedPaymentMode by remember { mutableStateOf("UPI") } // UPI, Cash, Cheque
    var isRecurring by remember { mutableStateOf(false) }
    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }

    var titleError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }

    val categories = listOf(
        "Food",
        "Shopping",
        "Transport",
        "Mobile Bills",
        "Entertainment",
        "Bills",
        "Income",
        "Other"
    )

    val paymentModes = listOf("UPI", "Cash", "Cheque")

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0xFFF9FAFB)
                        )
                    )
                )
                .border(
                    BorderStroke(1.dp, Color(0x33000000)),
                    RoundedCornerShape(24.dp)
                )
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add Transaction",
                        fontFamily = InriaSerifFontFamily,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // RadioButton selection: Expense vs Income
                Text(
                    text = "Transaction Type",
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { isCredit = false }
                            .padding(end = 16.dp)
                    ) {
                        RadioButton(
                            selected = !isCredit,
                            onClick = { isCredit = false },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFE23744))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Expense",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (!isCredit) Color(0xFFE23744) else TextPrimary
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { isCredit = true }
                    ) {
                        RadioButton(
                            selected = isCredit,
                            onClick = { isCredit = true },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF10B981))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Income",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isCredit) Color(0xFF10B981) else TextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Payment Mode — minimal pill toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pay via",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(end = 2.dp)
                    )
                    paymentModes.forEach { mode ->
                        val isSelected = selectedPaymentMode == mode
                        Text(
                            text = mode,
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 12.5.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) Color(0xFF0F172A) else TextSecondary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .border(
                                    BorderStroke(
                                        if (isSelected) 1.2.dp else 0.8.dp,
                                        if (isSelected) Color(0xFF1E293B) else Color(0x22000000)
                                    ),
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { selectedPaymentMode = mode }
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Title Input
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = false
                    },
                    label = { Text("Title / Description", fontFamily = PlusJakartaSansFontFamily, fontSize = 13.sp) },
                    placeholder = { Text("e.g. Swiggy, Netflix, Salary", color = Color.Gray, fontSize = 13.sp) },
                    singleLine = true,
                    isError = titleError,
                    supportingText = if (titleError) {
                        { Text("Title cannot be empty", color = Color(0xFFDC2626), fontSize = 11.sp) }
                    } else null,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1E293B),
                        unfocusedBorderColor = Color(0x33000000)
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Amount Input
                OutlinedTextField(
                    value = amountText,
                    onValueChange = {
                        amountText = it
                        amountError = false
                    },
                    label = { Text("Amount (₹)", fontFamily = PlusJakartaSansFontFamily, fontSize = 13.sp) },
                    placeholder = { Text("0.00", color = Color.Gray, fontSize = 13.sp) },
                    singleLine = true,
                    isError = amountError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    supportingText = if (amountError) {
                        { Text("Enter a valid amount (> 0)", color = Color(0xFFDC2626), fontSize = 11.sp) }
                    } else null,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1E293B),
                        unfocusedBorderColor = Color(0x33000000)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Category Dropdown / Spinner
                Text(
                    text = "Category",
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(1.dp, Color(0x33000000)), RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { isCategoryDropdownExpanded = true }
                            .padding(horizontal = 14.dp, vertical = 11.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedCategory,
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextPrimary
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Select Category",
                                tint = TextSecondary
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = isCategoryDropdownExpanded,
                        onDismissRequest = { isCategoryDropdownExpanded = false }
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat, fontFamily = PlusJakartaSansFontFamily) },
                                onClick = {
                                    selectedCategory = cat
                                    isCategoryDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Recurring Checkbox
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isRecurring = !isRecurring }
                        .padding(vertical = 2.dp)
                ) {
                    Checkbox(
                        checked = isRecurring,
                        onCheckedChange = { isRecurring = it },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1E293B))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Mark as recurring monthly transaction",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 12.5.sp,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Actions: Cancel and Save
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0x33000000))
                    ) {
                        Text(
                            text = "Cancel",
                            fontFamily = PlusJakartaSansFontFamily,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = {
                            var hasError = false
                            if (title.isBlank()) {
                                titleError = true
                                hasError = true
                            }
                            val parsedAmount = amountText.toDoubleOrNull()
                            if (parsedAmount == null || parsedAmount <= 0.0) {
                                amountError = true
                                hasError = true
                            }

                            if (hasError) {
                                Toast.makeText(
                                    context,
                                    "Please fill in all fields with valid data",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                repository.insertTransaction(
                                    title = title,
                                    amount = parsedAmount!!,
                                    isCredit = isCredit,
                                    category = selectedCategory,
                                    isRecurring = isRecurring,
                                    paymentMode = selectedPaymentMode
                                )

                                Toast.makeText(
                                    context,
                                    "Transaction saved to database!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                onTransactionAdded()
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B))
                    ) {
                        Text(
                            text = "Save",
                            fontFamily = PlusJakartaSansFontFamily,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
