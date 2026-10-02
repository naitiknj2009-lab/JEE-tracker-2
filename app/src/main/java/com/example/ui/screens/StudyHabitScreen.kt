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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.DailyLog
import com.example.ui.components.GlassCard
import com.example.ui.components.LiquidWaveProgress
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.JeeTheme
import com.example.ui.viewmodel.JeeViewModel
import java.util.Locale

@Composable
fun StudyHabitScreen(
    viewModel: JeeViewModel,
    modifier: Modifier = Modifier
) {
    val colors = JeeTheme.colors
    val todayLog by viewModel.todayLog.collectAsStateWithLifecycle()
    val timerSeconds by viewModel.timerSeconds.collectAsStateWithLifecycle()
    val isTimerRunning by viewModel.isTimerRunning.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    var completedText by remember(todayLog) { mutableStateOf(todayLog?.completedText ?: "") }
    var uncompletedText by remember(todayLog) { mutableStateOf(todayLog?.uncompletedText ?: "") }
    var distractionText by remember(todayLog) { mutableStateOf(todayLog?.distractionReason ?: "") }
    var task1 by remember(todayLog) { mutableStateOf(todayLog?.tomorrowTask1 ?: "") }
    var task2 by remember(todayLog) { mutableStateOf(todayLog?.tomorrowTask2 ?: "") }
    var task3 by remember(todayLog) { mutableStateOf(todayLog?.tomorrowTask3 ?: "") }
    var confidenceScore by remember(todayLog) { mutableFloatStateOf((todayLog?.confidenceScore ?: 8).toFloat()) }
    var energyScore by remember(todayLog) { mutableFloatStateOf((todayLog?.energyScore ?: 8).toFloat()) }
    var sleepHoursStr by remember(todayLog) { mutableStateOf((todayLog?.sleepHours ?: 7.5f).toString()) }
    var backlogNotes by remember(todayLog) { mutableStateOf(todayLog?.backlogNotes ?: "") }
    var saveSuccessMsg by remember { mutableStateOf(false) }

    val totalStudyMinutesToday = (todayLog?.studyDurationMinutes ?: 0)
    val targetMinutes = 9 * 60 // 9 hours target
    val progress = totalStudyMinutesToday.toFloat() / targetMinutes.toFloat()

    val formattedTimer = remember(timerSeconds) {
        val hours = timerSeconds / 3600
        val mins = (timerSeconds % 3600) / 60
        val secs = timerSeconds % 60
        String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, mins, secs)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("study_habit_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "HABIT & SELF-REFLECTION OS",
                    color = colors.physicsPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Daily Tracker & Study Timer",
                    color = colors.textPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Theme Switcher Card (Dark / Light / System)
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowAccentColor = colors.violetPrimary
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                tint = colors.violetPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "App Theme Mode",
                                color = colors.textPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = when (themeMode) {
                                AppThemeMode.DARK -> "Dark (Obsidian)"
                                AppThemeMode.LIGHT -> "Light (Clean Frost)"
                                AppThemeMode.SYSTEM -> "System Auto"
                            },
                            color = colors.violetPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ThemeOptionButton(
                            title = "Dark Glass",
                            icon = Icons.Default.DarkMode,
                            isSelected = themeMode == AppThemeMode.DARK,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.setThemeMode(AppThemeMode.DARK) }
                        )
                        ThemeOptionButton(
                            title = "Light Frost",
                            icon = Icons.Default.LightMode,
                            isSelected = themeMode == AppThemeMode.LIGHT,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.setThemeMode(AppThemeMode.LIGHT) }
                        )
                        ThemeOptionButton(
                            title = "Auto",
                            icon = Icons.Default.BrightnessAuto,
                            isSelected = themeMode == AppThemeMode.SYSTEM,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.setThemeMode(AppThemeMode.SYSTEM) }
                        )
                    }
                }
            }
        }

        // Pomodoro / Study Timer
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowAccentColor = colors.violetPrimary
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "FOCUS STOPWATCH & STUDY LOG",
                        color = colors.violetPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = formattedTimer,
                        color = colors.textPrimary,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        ElevatedButton(
                            onClick = {
                                if (isTimerRunning) viewModel.pauseTimer() else viewModel.startTimer()
                            },
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = if (isTimerRunning) colors.chemistryPrimary else colors.physicsPrimary,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.testTag("timer_toggle_button")
                        ) {
                            Icon(
                                imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isTimerRunning) "Pause" else "Start Study Session", fontWeight = FontWeight.Bold)
                        }

                        if (timerSeconds > 0) {
                            ElevatedButton(
                                onClick = { viewModel.saveTimerToTodayLog() },
                                colors = ButtonDefaults.elevatedButtonColors(
                                    containerColor = colors.violetPrimary,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Log Session", fontSize = 12.sp)
                            }

                            ElevatedButton(
                                onClick = { viewModel.resetTimer() },
                                colors = ButtonDefaults.elevatedButtonColors(
                                    containerColor = colors.chipBg,
                                    contentColor = colors.textSecondary
                                )
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    // Liquid Wave Progress Indicator
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(28.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (colors.isDark) Color(0x18FFFFFF) else Color(0x10000000))
                            .border(1.dp, colors.chipBorder, RoundedCornerShape(10.dp))
                    ) {
                        LiquidWaveProgress(
                            progress = progress,
                            accentColor = colors.physicsPrimary,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Logged Today: ${totalStudyMinutesToday / 60}h ${totalStudyMinutesToday % 60}m",
                            color = colors.textSecondary,
                            fontSize = 11.sp
                        )
                        Text(
                            text = "Target: 8–10h (${(progress * 100).toInt()}%)",
                            color = colors.physicsPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Daily Check-in Form directly from PDF Page 5
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowAccentColor = colors.chemistryPrimary
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(colors.chemistryPrimary, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "TODAY'S CHECK-IN • WRITE ONE LINE ONLY",
                            color = colors.chemistryPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    OutlinedTextField(
                        value = completedText,
                        onValueChange = { completedText = it },
                        label = { Text("What I completed", color = colors.textMuted) },
                        placeholder = { Text("e.g. 2 Physics Lectures + 30 Mechanics PYQs", color = colors.textMuted, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )

                    OutlinedTextField(
                        value = uncompletedText,
                        onValueChange = { uncompletedText = it },
                        label = { Text("What I did not complete", color = colors.textMuted) },
                        placeholder = { Text("e.g. Chemistry Coordination DPP sheet", color = colors.textMuted, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )

                    OutlinedTextField(
                        value = distractionText,
                        onValueChange = { distractionText = it },
                        label = { Text("Why / distraction", color = colors.textMuted) },
                        placeholder = { Text("e.g. Spent 45 mins scrolling YouTube shorts / low afternoon energy", color = colors.textMuted, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "TOMORROW'S TOP 3 TASKS",
                        color = colors.textSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    OutlinedTextField(
                        value = task1,
                        onValueChange = { task1 = it },
                        label = { Text("1) High Priority Task", color = colors.textMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )
                    OutlinedTextField(
                        value = task2,
                        onValueChange = { task2 = it },
                        label = { Text("2) Medium Priority Task", color = colors.textMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )
                    OutlinedTextField(
                        value = task3,
                        onValueChange = { task3 = it },
                        label = { Text("3) Revision / Test Analysis", color = colors.textMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )
                }
            }
        }

        // PDF Page 6: Confidence, Energy, Sleep & Backlog
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowAccentColor = colors.mathPrimary
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "VITALITY & MINDSET METRICS",
                        color = colors.mathPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    // Confidence Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Current Confidence", color = colors.textPrimary, fontSize = 13.sp)
                        Text("${confidenceScore.toInt()} / 10", color = colors.mathPrimary, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = confidenceScore,
                        onValueChange = { confidenceScore = it },
                        valueRange = 1f..10f,
                        steps = 8,
                        colors = SliderDefaults.colors(
                            thumbColor = colors.mathPrimary,
                            activeTrackColor = colors.mathPrimary,
                            inactiveTrackColor = colors.chipBorder
                        )
                    )

                    // Energy Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Energy Level", color = colors.textPrimary, fontSize = 13.sp)
                        Text("${energyScore.toInt()} / 10", color = colors.physicsPrimary, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = energyScore,
                        onValueChange = { energyScore = it },
                        valueRange = 1f..10f,
                        steps = 8,
                        colors = SliderDefaults.colors(
                            thumbColor = colors.physicsPrimary,
                            activeTrackColor = colors.physicsPrimary,
                            inactiveTrackColor = colors.chipBorder
                        )
                    )

                    // Sleep Hours
                    OutlinedTextField(
                        value = sleepHoursStr,
                        onValueChange = { sleepHoursStr = it },
                        label = { Text("Sleep Duration (Hours)", color = colors.textMuted) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )

                    // Backlog to Clear & PDF Golden Rule
                    OutlinedTextField(
                        value = backlogNotes,
                        onValueChange = { backlogNotes = it },
                        label = { Text("Backlog to Clear", color = colors.textMuted) },
                        placeholder = { Text("e.g. Fluid mechanics lectures 4 & 5", color = colors.textMuted, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(colors.chemistryPrimary.copy(alpha = 0.12f))
                            .border(1.dp, colors.chemistryPrimary.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "💡 Rule: Track the process, not just marks. If a box is unfinished, move it forward instead of hiding it.",
                            color = colors.chemistryPrimary,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = {
                            val sleepHours = sleepHoursStr.toFloatOrNull() ?: 7.5f
                            val log = DailyLog(
                                dateString = viewModel.todayDateString,
                                studyDurationMinutes = totalStudyMinutesToday,
                                completedText = completedText,
                                uncompletedText = uncompletedText,
                                distractionReason = distractionText,
                                tomorrowTask1 = task1,
                                tomorrowTask2 = task2,
                                tomorrowTask3 = task3,
                                confidenceScore = confidenceScore.toInt(),
                                energyScore = energyScore.toInt(),
                                sleepHours = sleepHours,
                                backlogNotes = backlogNotes
                            )
                            viewModel.saveDailyLog(log)
                            saveSuccessMsg = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.physicsPrimary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("save_daily_log_button")
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save Today's Reflection", fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    if (saveSuccessMsg) {
                        Text(
                            text = "✓ Today's check-in saved to local storage!",
                            color = colors.physicsPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}

@Composable
private fun ThemeOptionButton(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = JeeTheme.colors
    val isDark = colors.isDark

    val bg = if (isSelected) colors.violetPrimary.copy(alpha = if (isDark) 0.25f else 0.15f) else colors.chipBg
    val border = if (isSelected) colors.violetPrimary else colors.chipBorder

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, border, RoundedCornerShape(12.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) colors.violetPrimary else colors.textSecondary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                color = if (isSelected) colors.textPrimary else colors.textSecondary,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}
