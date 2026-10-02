package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.MockTest
import com.example.ui.components.GlassCard
import com.example.ui.components.StatCard
import com.example.ui.theme.JeeTheme
import com.example.ui.viewmodel.JeeViewModel

@Composable
fun MockTestScreen(
    viewModel: JeeViewModel,
    modifier: Modifier = Modifier
) {
    val colors = JeeTheme.colors
    val allTests by viewModel.allMockTests.collectAsStateWithLifecycle()
    val attemptedTests by viewModel.attemptedMockTests.collectAsStateWithLifecycle()

    var showEntryModal by remember { mutableStateOf(false) }
    var selectedTestToEdit by remember { mutableStateOf<MockTest?>(null) }

    val avgScore = if (attemptedTests.isNotEmpty()) attemptedTests.map { it.totalScore }.average().toInt() else 0
    val bestScore = attemptedTests.maxOfOrNull { it.totalScore } ?: 0
    val totalNegativeLost = attemptedTests.sumOf { it.negativeMarks }
    val avgAccuracy = if (attemptedTests.isNotEmpty()) attemptedTests.map { it.accuracy }.average().toFloat() else 0f

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("mock_test_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MOCK TEST ANALYTICS",
                            color = colors.mathPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Scorecard & Analysis",
                            color = colors.textPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    ElevatedButton(
                        onClick = {
                            selectedTestToEdit = null
                            showEntryModal = true
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = colors.violetPrimary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.testTag("record_test_button")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Record Test", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            // Stats Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "Avg Score",
                        value = if (attemptedTests.isNotEmpty()) "$avgScore/300" else "--",
                        subtitle = "Target: 220+",
                        accentColor = colors.mathPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Best Score",
                        value = if (attemptedTests.isNotEmpty()) "$bestScore/300" else "--",
                        subtitle = "Peak achieved",
                        accentColor = colors.physicsPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Accuracy",
                        value = if (attemptedTests.isNotEmpty()) "${avgAccuracy.toInt()}%" else "--",
                        subtitle = "Avg Precision",
                        accentColor = colors.chemistryPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Negative Marks Callout
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = colors.crimsonPrimary,
                    glowAccentColor = colors.crimsonPrimary
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "SILLY MISTAKES REVENUE LEAKAGE",
                                color = colors.crimsonPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "-$totalNegativeLost Marks Lost to Negatives",
                                color = colors.textPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Across ${attemptedTests.size} attempted tests. Every incorrect answer costs you 5 ranks in JEE Main.",
                                color = colors.textMuted,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // Section Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SCHEDULED & CUSTOM TESTS (${attemptedTests.size}/${allTests.size} Attempted)",
                        color = colors.textSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            // Test List
            items(allTests, key = { it.id }) { test ->
                MockTestItemCard(
                    test = test,
                    onRecordScore = {
                        selectedTestToEdit = test
                        showEntryModal = true
                    },
                    onDelete = { viewModel.deleteMockTest(test) }
                )
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }

    // Modal for Test Entry & Auto Calculator
    if (showEntryModal) {
        MockTestEntryDialog(
            existingTest = selectedTestToEdit,
            onDismiss = { showEntryModal = false },
            onSave = { testId, testName, testType, platform, pattern, date, pC, pI, pU, cC, cI, cU, mC, mI, mU, mistakes, isAnalysisDone ->
                viewModel.saveMockTestResult(
                    testId = testId,
                    testName = testName,
                    testType = testType,
                    platform = platform,
                    pattern = pattern,
                    scheduledDate = date,
                    pCorrect = pC,
                    pIncorrect = pI,
                    pUnattempted = pU,
                    cCorrect = cC,
                    cIncorrect = cI,
                    cUnattempted = cU,
                    mCorrect = mC,
                    mIncorrect = mI,
                    mUnattempted = mU,
                    mistakeTopics = mistakes,
                    isAnalysisDone = isAnalysisDone
                )
                showEntryModal = false
            }
        )
    }
}

@Composable
private fun MockTestItemCard(
    test: MockTest,
    onRecordScore: () -> Unit,
    onDelete: () -> Unit
) {
    val colors = JeeTheme.colors
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = if (test.isAttempted) colors.mathPrimary else null,
        glowAccentColor = if (test.isAttempted) colors.mathPrimary else null
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = test.testName,
                            color = colors.textPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (test.pattern == "JEE Advanced") colors.violetPrimary.copy(alpha = 0.2f) else colors.mathPrimary.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = test.pattern,
                                color = if (test.pattern == "JEE Advanced") colors.violetPrimary else colors.mathPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${test.testType} • ${test.platform} • ${test.scheduledDate}",
                        color = colors.textMuted,
                        fontSize = 11.sp
                    )
                }

                if (test.isAttempted) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${test.totalScore}/300",
                            color = colors.mathPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${test.accuracy.toInt()}% Acc • -${test.negativeMarks} neg",
                            color = colors.textSecondary,
                            fontSize = 11.sp
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(colors.chipBg)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Pending",
                            color = colors.textMuted,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            if (test.isAttempted) {
                Spacer(modifier = Modifier.height(10.dp))
                // Subject mini breakdown
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.chipBg)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Physics: +${test.physicsCorrect * 4 - test.physicsIncorrect}",
                        color = colors.physicsPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Chem: +${test.chemistryCorrect * 4 - test.chemistryIncorrect}",
                        color = colors.chemistryPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Math: +${test.mathCorrect * 4 - test.mathIncorrect}",
                        color = colors.mathPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                if (test.mistakeTopics.isNotBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Mistakes/Notes: ${test.mistakeTopics}",
                        color = colors.textSecondary,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (test.isAnalysisDone) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Analysis Done",
                            tint = colors.physicsPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Analysis Done", color = colors.physicsPrimary, fontSize = 11.sp)
                    } else if (test.isAttempted) {
                        Text("Analysis Pending", color = colors.crimsonPrimary, fontSize = 11.sp)
                    }
                }

                ElevatedButton(
                    onClick = onRecordScore,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = if (test.isAttempted) colors.chipBg else colors.mathPrimary,
                        contentColor = if (test.isAttempted) colors.textPrimary else Color.White
                    ),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = if (test.isAttempted) "Edit Score" else "Enter Score",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun MockTestEntryDialog(
    existingTest: MockTest?,
    onDismiss: () -> Unit,
    onSave: (
        testId: Long,
        testName: String,
        testType: String,
        platform: String,
        pattern: String,
        date: String,
        pC: Int, pI: Int, pU: Int,
        cC: Int, cI: Int, cU: Int,
        mC: Int, mI: Int, mU: Int,
        mistakes: String,
        isAnalysisDone: Boolean
    ) -> Unit
) {
    val colors = JeeTheme.colors

    var testName by remember { mutableStateOf(existingTest?.testName ?: "Custom Mock Test") }
    var testType by remember { mutableStateOf(existingTest?.testType ?: "Full Test") }
    var platform by remember { mutableStateOf(existingTest?.platform ?: "MathonGo") }
    var pattern by remember { mutableStateOf(existingTest?.pattern ?: "JEE Main") }
    var date by remember { mutableStateOf(existingTest?.scheduledDate ?: "Today") }

    // Physics
    var pCorrectStr by remember { mutableStateOf(existingTest?.physicsCorrect?.toString() ?: "18") }
    var pIncorrectStr by remember { mutableStateOf(existingTest?.physicsIncorrect?.toString() ?: "4") }
    var pUnattemptedStr by remember { mutableStateOf(existingTest?.physicsUnattempted?.toString() ?: "3") }

    // Chemistry
    var cCorrectStr by remember { mutableStateOf(existingTest?.chemistryCorrect?.toString() ?: "19") }
    var cIncorrectStr by remember { mutableStateOf(existingTest?.chemistryIncorrect?.toString() ?: "3") }
    var cUnattemptedStr by remember { mutableStateOf(existingTest?.chemistryUnattempted?.toString() ?: "3") }

    // Math
    var mCorrectStr by remember { mutableStateOf(existingTest?.mathCorrect?.toString() ?: "15") }
    var mIncorrectStr by remember { mutableStateOf(existingTest?.mathIncorrect?.toString() ?: "5") }
    var mUnattemptedStr by remember { mutableStateOf(existingTest?.mathUnattempted?.toString() ?: "5") }

    var mistakeNotes by remember { mutableStateOf(existingTest?.mistakeTopics ?: "") }
    var isAnalysisDone by remember { mutableStateOf(existingTest?.isAnalysisDone ?: false) }

    val pC = pCorrectStr.toIntOrNull() ?: 0
    val pI = pIncorrectStr.toIntOrNull() ?: 0
    val pU = pUnattemptedStr.toIntOrNull() ?: 0

    val cC = cCorrectStr.toIntOrNull() ?: 0
    val cI = cIncorrectStr.toIntOrNull() ?: 0
    val cU = cUnattemptedStr.toIntOrNull() ?: 0

    val mC = mCorrectStr.toIntOrNull() ?: 0
    val mI = mIncorrectStr.toIntOrNull() ?: 0
    val mU = mUnattemptedStr.toIntOrNull() ?: 0

    val totalCorrect = pC + cC + mC
    val totalIncorrect = pI + cI + mI
    val totalScore = (totalCorrect * 4) - (totalIncorrect * 1)
    val negativeLost = totalIncorrect * 1
    val totalAttempted = totalCorrect + totalIncorrect
    val accuracy = if (totalAttempted > 0) (totalCorrect.toFloat() / totalAttempted.toFloat()) * 100f else 0f

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = if (existingTest != null) "Edit Mock Score: ${existingTest.testName}" else "Record New Mock Test",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Auto calculates +4 / -1 / 0 scoring rules",
                    color = colors.mathPrimary,
                    fontSize = 11.sp
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Live summary score banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colors.mathPrimary.copy(alpha = 0.15f))
                        .border(1.dp, colors.mathPrimary.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "TOTAL SCORE", color = colors.textSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(text = "$totalScore / 300", color = colors.textPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "ACCURACY: ${accuracy.toInt()}%", color = colors.physicsPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Negative marks: -$negativeLost", color = colors.crimsonPrimary, fontSize = 11.sp)
                        }
                    }
                }

                if (existingTest == null) {
                    OutlinedTextField(
                        value = testName,
                        onValueChange = { testName = it },
                        label = { Text("Test Name", color = colors.textMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )
                    OutlinedTextField(
                        value = platform,
                        onValueChange = { platform = it },
                        label = { Text("Platform (MathonGo, Allen, NTA, etc.)", color = colors.textMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )
                }

                // Physics section
                Text(text = "Physics (+4 / -1)", color = colors.physicsPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ScoreInput(value = pCorrectStr, label = "Correct (+4)", color = colors.physicsPrimary, modifier = Modifier.weight(1f)) { pCorrectStr = it }
                    ScoreInput(value = pIncorrectStr, label = "Wrong (-1)", color = colors.crimsonPrimary, modifier = Modifier.weight(1f)) { pIncorrectStr = it }
                    ScoreInput(value = pUnattemptedStr, label = "Left (0)", color = colors.textMuted, modifier = Modifier.weight(1f)) { pUnattemptedStr = it }
                }

                // Chemistry section
                Text(text = "Chemistry (+4 / -1)", color = colors.chemistryPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ScoreInput(value = cCorrectStr, label = "Correct (+4)", color = colors.chemistryPrimary, modifier = Modifier.weight(1f)) { cCorrectStr = it }
                    ScoreInput(value = cIncorrectStr, label = "Wrong (-1)", color = colors.crimsonPrimary, modifier = Modifier.weight(1f)) { cIncorrectStr = it }
                    ScoreInput(value = cUnattemptedStr, label = "Left (0)", color = colors.textMuted, modifier = Modifier.weight(1f)) { cUnattemptedStr = it }
                }

                // Mathematics section
                Text(text = "Mathematics (+4 / -1)", color = colors.mathPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ScoreInput(value = mCorrectStr, label = "Correct (+4)", color = colors.mathPrimary, modifier = Modifier.weight(1f)) { mCorrectStr = it }
                    ScoreInput(value = mIncorrectStr, label = "Wrong (-1)", color = colors.crimsonPrimary, modifier = Modifier.weight(1f)) { mIncorrectStr = it }
                    ScoreInput(value = mUnattemptedStr, label = "Left (0)", color = colors.textMuted, modifier = Modifier.weight(1f)) { mUnattemptedStr = it }
                }

                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = mistakeNotes,
                    onValueChange = { mistakeNotes = it },
                    label = { Text("Mistakes / Weak Topics Identified", color = colors.textMuted) },
                    placeholder = { Text("e.g. Silly sign error in electrostatics; forgot amine basicity order", color = colors.textMuted, fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary
                    )
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isAnalysisDone,
                        onCheckedChange = { isAnalysisDone = it },
                        colors = CheckboxDefaults.colors(checkedColor = colors.physicsPrimary)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Post-test in-depth analysis completed", color = colors.textPrimary, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        existingTest?.id ?: 0L,
                        testName,
                        testType,
                        platform,
                        pattern,
                        date,
                        pC, pI, pU,
                        cC, cI, cU,
                        mC, mI, mU,
                        mistakeNotes,
                        isAnalysisDone
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.violetPrimary),
                modifier = Modifier.testTag("save_score_button")
            ) {
                Text("Save Scorecard", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = colors.textSecondary)
            }
        },
        containerColor = colors.surface
    )
}

@Composable
private fun ScoreInput(
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    val colors = JeeTheme.colors
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = color, fontSize = 10.sp) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = color,
            unfocusedBorderColor = color.copy(alpha = 0.4f),
            focusedTextColor = colors.textPrimary,
            unfocusedTextColor = colors.textPrimary
        )
    )
}
