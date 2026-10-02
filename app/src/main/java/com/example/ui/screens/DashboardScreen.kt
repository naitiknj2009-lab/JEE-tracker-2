package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Subject
import com.example.ui.components.CircularProgressWithLabel
import com.example.ui.components.GlassCard
import com.example.ui.components.StatCard
import com.example.ui.components.SubjectBadge
import com.example.ui.theme.JeeTheme
import com.example.ui.viewmodel.JeeViewModel

@Composable
fun DashboardScreen(
    viewModel: JeeViewModel,
    onNavigateToSyllabus: (Subject?) -> Unit,
    onNavigateToMockTests: () -> Unit,
    onNavigateToMistakes: () -> Unit,
    onNavigateToHabit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = JeeTheme.colors
    val chapters by viewModel.allChapters.collectAsStateWithLifecycle()
    val mockTests by viewModel.allMockTests.collectAsStateWithLifecycle()
    val attemptedTests by viewModel.attemptedMockTests.collectAsStateWithLifecycle()
    val todayLog by viewModel.todayLog.collectAsStateWithLifecycle()
    val revisionQueue by viewModel.revisionQueue.collectAsStateWithLifecycle()
    val mistakes by viewModel.allMistakes.collectAsStateWithLifecycle()

    // Aggregate statistics
    val totalCheckpointsPossible = chapters.size * 7
    val totalCheckpointsDone = chapters.sumOf { it.completedStagesCount }
    val overallProgress = if (totalCheckpointsPossible > 0) totalCheckpointsDone.toFloat() / totalCheckpointsPossible.toFloat() else 0f

    val physicsChapters = chapters.filter { it.subject == Subject.PHYSICS }
    val chemistryChapters = chapters.filter { it.subject == Subject.CHEMISTRY }
    val mathChapters = chapters.filter { it.subject == Subject.MATHEMATICS }

    val physicsProgress = if (physicsChapters.isNotEmpty()) physicsChapters.sumOf { it.completedStagesCount }.toFloat() / (physicsChapters.size * 7f) else 0f
    val chemistryProgress = if (chemistryChapters.isNotEmpty()) chemistryChapters.sumOf { it.completedStagesCount }.toFloat() / (chemistryChapters.size * 7f) else 0f
    val mathProgress = if (mathChapters.isNotEmpty()) mathChapters.sumOf { it.completedStagesCount }.toFloat() / (mathChapters.size * 7f) else 0f

    val avgTestScore = if (attemptedTests.isNotEmpty()) attemptedTests.map { it.totalScore }.average().toInt() else 0
    val bestScore = attemptedTests.maxOfOrNull { it.totalScore } ?: 0
    val totalNegativeMarksLost = attemptedTests.sumOf { it.negativeMarks }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("dashboard_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Glass Card
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowAccentColor = colors.violetPrimary
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(colors.violetPrimary, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "MISSION 100 • JEE 2027",
                                    color = colors.violetPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Preparation OS",
                                color = colors.textPrimary,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        CircularProgressWithLabel(
                            progress = overallProgress,
                            percentageText = "${(overallProgress * 100).toInt()}%",
                            caption = "Overall",
                            accentColor = colors.violetPrimary,
                            size = 80.dp,
                            strokeWidth = 7.dp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "$totalCheckpointsDone of $totalCheckpointsPossible total syllabus checkpoints mastered across Physics, Chemistry, & Mathematics.",
                        color = colors.textSecondary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Quick Stats Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "Avg Score",
                    value = if (attemptedTests.isNotEmpty()) "$avgTestScore/300" else "--",
                    subtitle = "Tests: ${attemptedTests.size}/${mockTests.size}",
                    accentColor = colors.violetPrimary,
                    modifier = Modifier.weight(1f),
                    testTag = "stat_avg_score"
                )
                StatCard(
                    title = "Negative Loss",
                    value = "-$totalNegativeMarksLost",
                    subtitle = "Silly mistakes",
                    accentColor = colors.crimsonPrimary,
                    modifier = Modifier.weight(1f),
                    testTag = "stat_negative_loss"
                )
                StatCard(
                    title = "Today Study",
                    value = "${(todayLog?.studyDurationMinutes ?: 0) / 60}h ${(todayLog?.studyDurationMinutes ?: 0) % 60}m",
                    subtitle = "Target: 8-10h",
                    accentColor = colors.physicsPrimary,
                    modifier = Modifier.weight(1f),
                    testTag = "stat_today_study"
                )
            }
        }

        // Subject Breakdown Cards
        item {
            Text(
                text = "SUBJECT SYLLABUS TRACKER",
                color = colors.textSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SubjectProgressCard(
                    subject = Subject.PHYSICS,
                    totalChapters = physicsChapters.size,
                    completedChapters = physicsChapters.count { it.isFullyCompleted },
                    progress = physicsProgress,
                    onClick = { onNavigateToSyllabus(Subject.PHYSICS) }
                )
                SubjectProgressCard(
                    subject = Subject.CHEMISTRY,
                    totalChapters = chemistryChapters.size,
                    completedChapters = chemistryChapters.count { it.isFullyCompleted },
                    progress = chemistryProgress,
                    onClick = { onNavigateToSyllabus(Subject.CHEMISTRY) }
                )
                SubjectProgressCard(
                    subject = Subject.MATHEMATICS,
                    totalChapters = mathChapters.size,
                    completedChapters = mathChapters.count { it.isFullyCompleted },
                    progress = mathProgress,
                    onClick = { onNavigateToSyllabus(Subject.MATHEMATICS) }
                )
            }
        }

        // Revision Queue Card (Spaced Repetition)
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowAccentColor = colors.chemistryPrimary
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = colors.chemistryPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Revision Queue (Spaced Repetition)",
                                color = colors.textPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "${revisionQueue.size} due",
                            color = colors.chemistryPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    if (revisionQueue.isEmpty()) {
                        Text(
                            text = "No chapters currently queued for revision. Complete lecture, DPP, & PYQ stages to trigger automated spaced repetition schedules.",
                            color = colors.textMuted,
                            fontSize = 12.sp
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            revisionQueue.take(3).forEach { chapter ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(colors.chipBg)
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = chapter.name,
                                            color = colors.textPrimary,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "${chapter.subject.displayName} • Rev #${chapter.revisionCount + 1}",
                                            color = colors.chemistryPrimary,
                                            fontSize = 11.sp
                                        )
                                    }
                                    ElevatedButton(
                                        onClick = { viewModel.scheduleRevision(chapter) },
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = colors.chemistryPrimary,
                                            contentColor = Color.White
                                        ),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Text("Mark Revised", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Daily Check-In & Habit Snapshot
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowAccentColor = colors.physicsPrimary,
                onClick = onNavigateToHabit
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = colors.physicsPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Today's Study & Reflection",
                                color = colors.textPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Open Habit Tracker",
                            tint = colors.textSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Confidence: ${todayLog?.confidenceScore ?: 8}/10 • Energy: ${todayLog?.energyScore ?: 8}/10",
                            color = colors.textSecondary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Sleep: ${todayLog?.sleepHours ?: 7f}h",
                            color = colors.textSecondary,
                            fontSize = 12.sp
                        )
                    }

                    if (todayLog?.completedText?.isNotBlank() == true) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Done: ${todayLog?.completedText}",
                            color = colors.textPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Mistake Notebook & Mock Tests Quick Links
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GlassCard(
                    modifier = Modifier.weight(1f),
                    glowAccentColor = colors.crimsonPrimary,
                    onClick = onNavigateToMistakes
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = colors.crimsonPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Mistake Log",
                                color = colors.textPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${mistakes.size} Logged",
                            color = colors.crimsonPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${mistakes.count { it.flaggedForReattempt }} flagged",
                            color = colors.textMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                GlassCard(
                    modifier = Modifier.weight(1f),
                    glowAccentColor = colors.mathPrimary,
                    onClick = onNavigateToMockTests
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = null,
                                tint = colors.mathPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Mock Tests",
                                color = colors.textPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (bestScore > 0) "$bestScore/300" else "0/300",
                            color = colors.mathPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Best Score",
                            color = colors.textMuted,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
private fun SubjectProgressCard(
    subject: Subject,
    totalChapters: Int,
    completedChapters: Int,
    progress: Float,
    onClick: () -> Unit
) {
    val colors = JeeTheme.colors
    val subjectPrimary = when (subject) {
        Subject.PHYSICS -> colors.physicsPrimary
        Subject.CHEMISTRY -> colors.chemistryPrimary
        Subject.MATHEMATICS -> colors.mathPrimary
    }

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        borderColor = subjectPrimary,
        glowAccentColor = subjectPrimary
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubjectBadge(subject = subject)

                Text(
                    text = "${(progress * 100).toInt()}% Done",
                    color = subjectPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = subjectPrimary,
                trackColor = if (colors.isDark) Color(0x22FFFFFF) else Color(0x15000000),
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$completedChapters of $totalChapters Chapters Mastered",
                    color = colors.textSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "View Chapters →",
                    color = subjectPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
