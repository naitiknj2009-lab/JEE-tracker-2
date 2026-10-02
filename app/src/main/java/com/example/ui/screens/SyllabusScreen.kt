package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Chapter
import com.example.data.model.Subject
import com.example.data.model.Weightage
import com.example.ui.components.CheckpointChip
import com.example.ui.components.GlassCard
import com.example.ui.components.SubjectBadge
import com.example.ui.theme.JeeTheme
import com.example.ui.viewmodel.JeeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SyllabusScreen(
    viewModel: JeeViewModel,
    modifier: Modifier = Modifier
) {
    val colors = JeeTheme.colors
    val chapters by viewModel.filteredChapters.collectAsStateWithLifecycle()
    val allChapters by viewModel.allChapters.collectAsStateWithLifecycle()
    val selectedSubject by viewModel.selectedSubject.collectAsStateWithLifecycle()
    val selectedWeightage by viewModel.selectedWeightage.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filterStatus by viewModel.filterStatus.collectAsStateWithLifecycle()

    var editingRemarksChapter by remember { mutableStateOf<Chapter?>(null) }
    var remarksInputText by remember { mutableStateOf("") }

    val subjectCounts = remember(allChapters) {
        mapOf(
            null to allChapters.size,
            Subject.PHYSICS to allChapters.count { it.subject == Subject.PHYSICS },
            Subject.CHEMISTRY to allChapters.count { it.subject == Subject.CHEMISTRY },
            Subject.MATHEMATICS to allChapters.count { it.subject == Subject.MATHEMATICS }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("syllabus_screen")
    ) {
        // Top Search & Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "SYLLABUS TRACKER",
                        color = colors.violetPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = if (selectedSubject != null) selectedSubject!!.displayName else "All 79 Chapters",
                        color = colors.textPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "${chapters.size} Showing",
                    color = colors.textSecondary,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                placeholder = { Text("Search chapter or category (e.g. Optics, Calculus)...", color = colors.textMuted, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = colors.textMuted) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setSearchQuery("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = colors.textMuted)
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("chapter_search_input"),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.violetPrimary,
                    unfocusedBorderColor = colors.chipBorder,
                    focusedContainerColor = colors.chipBg,
                    unfocusedContainerColor = colors.chipBg,
                    focusedTextColor = colors.textPrimary,
                    unfocusedTextColor = colors.textPrimary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Subject Filter Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SubjectFilterTab(
                    title = "All (${subjectCounts[null] ?: 0})",
                    isSelected = selectedSubject == null,
                    activeColor = colors.violetPrimary,
                    onClick = { viewModel.setSubject(null) }
                )
                SubjectFilterTab(
                    title = "Physics (${subjectCounts[Subject.PHYSICS] ?: 0})",
                    isSelected = selectedSubject == Subject.PHYSICS,
                    activeColor = colors.physicsPrimary,
                    onClick = { viewModel.setSubject(Subject.PHYSICS) }
                )
                SubjectFilterTab(
                    title = "Chemistry (${subjectCounts[Subject.CHEMISTRY] ?: 0})",
                    isSelected = selectedSubject == Subject.CHEMISTRY,
                    activeColor = colors.chemistryPrimary,
                    onClick = { viewModel.setSubject(Subject.CHEMISTRY) }
                )
                SubjectFilterTab(
                    title = "Mathematics (${subjectCounts[Subject.MATHEMATICS] ?: 0})",
                    isSelected = selectedSubject == Subject.MATHEMATICS,
                    activeColor = colors.mathPrimary,
                    onClick = { viewModel.setSubject(Subject.MATHEMATICS) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Secondary Filter Pills (Weightage & Status)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterPill(
                    label = "All Weightage",
                    isSelected = selectedWeightage == null,
                    onClick = { viewModel.setWeightage(null) }
                )
                FilterPill(
                    label = "🔥 High / Do-or-Die",
                    isSelected = selectedWeightage == Weightage.HIGH,
                    accentColor = colors.crimsonPrimary,
                    onClick = { viewModel.setWeightage(if (selectedWeightage == Weightage.HIGH) null else Weightage.HIGH) }
                )
                FilterPill(
                    label = "⚡ Medium",
                    isSelected = selectedWeightage == Weightage.MEDIUM,
                    accentColor = colors.chemistryPrimary,
                    onClick = { viewModel.setWeightage(if (selectedWeightage == Weightage.MEDIUM) null else Weightage.MEDIUM) }
                )
                FilterPill(
                    label = "Standard",
                    isSelected = selectedWeightage == Weightage.LOW,
                    accentColor = colors.mathPrimary,
                    onClick = { viewModel.setWeightage(if (selectedWeightage == Weightage.LOW) null else Weightage.LOW) }
                )

                Box(modifier = Modifier.width(1.dp).height(18.dp).background(colors.chipBorder))

                FilterPill(
                    label = "Completed",
                    isSelected = filterStatus == "COMPLETED",
                    accentColor = colors.physicsPrimary,
                    onClick = { viewModel.setFilterStatus(if (filterStatus == "COMPLETED") "ALL" else "COMPLETED") }
                )
                FilterPill(
                    label = "In Progress",
                    isSelected = filterStatus == "IN_PROGRESS",
                    accentColor = colors.violetPrimary,
                    onClick = { viewModel.setFilterStatus(if (filterStatus == "IN_PROGRESS") "ALL" else "IN_PROGRESS") }
                )
            }
        }

        // Chapters List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(chapters, key = { it.id }) { chapter ->
                ChapterItemCard(
                    chapter = chapter,
                    onToggleStage = { stage -> viewModel.toggleCheckpoint(chapter, stage) },
                    onEditRemarks = {
                        editingRemarksChapter = chapter
                        remarksInputText = chapter.remarks
                    },
                    onScheduleRevision = { viewModel.scheduleRevision(chapter) }
                )
            }
        }
    }

    // Edit Remarks Dialog
    if (editingRemarksChapter != null) {
        val chapterSubjectColor = when (editingRemarksChapter!!.subject) {
            Subject.PHYSICS -> colors.physicsPrimary
            Subject.CHEMISTRY -> colors.chemistryPrimary
            Subject.MATHEMATICS -> colors.mathPrimary
        }

        AlertDialog(
            onDismissRequest = { editingRemarksChapter = null },
            title = {
                Text(
                    text = "Remarks & Status Note",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = editingRemarksChapter!!.name,
                        color = chapterSubjectColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = remarksInputText,
                        onValueChange = { remarksInputText = it },
                        placeholder = { Text("e.g. Focus on collision formulas; practice 2024 shift PYQs", color = colors.textMuted) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.violetPrimary,
                            unfocusedBorderColor = colors.chipBorder,
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateChapterRemarks(editingRemarksChapter!!.id, remarksInputText)
                        editingRemarksChapter = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colors.violetPrimary)
                ) {
                    Text("Save Note")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingRemarksChapter = null }) {
                    Text("Cancel", color = colors.textSecondary)
                }
            },
            containerColor = colors.surface
        )
    }
}

@Composable
private fun SubjectFilterTab(
    title: String,
    isSelected: Boolean,
    activeColor: Color,
    onClick: () -> Unit
) {
    val colors = JeeTheme.colors
    val isDark = colors.isDark

    val bgColor = if (isSelected) activeColor.copy(alpha = if (isDark) 0.2f else 0.15f) else colors.chipBg
    val borderColor = if (isSelected) activeColor else colors.chipBorder

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            color = if (isSelected) colors.textPrimary else colors.textSecondary,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun FilterPill(
    label: String,
    isSelected: Boolean,
    accentColor: Color? = null,
    onClick: () -> Unit
) {
    val colors = JeeTheme.colors
    val finalAccent = accentColor ?: colors.violetPrimary
    val isDark = colors.isDark

    val bgColor = if (isSelected) finalAccent.copy(alpha = if (isDark) 0.2f else 0.15f) else Color.Transparent
    val borderColor = if (isSelected) finalAccent else colors.chipBorder

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) colors.textPrimary else colors.textSecondary,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChapterItemCard(
    chapter: Chapter,
    onToggleStage: (String) -> Unit,
    onEditRemarks: () -> Unit,
    onScheduleRevision: () -> Unit
) {
    val colors = JeeTheme.colors
    var isExpanded by remember { mutableStateOf(false) }

    val subjectPrimary = when (chapter.subject) {
        Subject.PHYSICS -> colors.physicsPrimary
        Subject.CHEMISTRY -> colors.chemistryPrimary
        Subject.MATHEMATICS -> colors.mathPrimary
    }
    val subjectSurface = when (chapter.subject) {
        Subject.PHYSICS -> colors.physicsSurface
        Subject.CHEMISTRY -> colors.chemistrySurface
        Subject.MATHEMATICS -> colors.mathSurface
    }

    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = if (chapter.isFullyCompleted) colors.physicsPrimary else null,
        glowAccentColor = subjectPrimary
    ) {
        Column {
            // Header Row: SNo + Chapter Name + Weightage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(subjectSurface)
                            .border(1.dp, subjectPrimary.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${chapter.sNo}",
                            color = subjectPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = chapter.name,
                            color = colors.textPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = chapter.category,
                                color = colors.textMuted,
                                fontSize = 11.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(chapter.weightage.color.copy(alpha = 0.15f))
                                    .padding(horizontal = 5.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = chapter.weightage.label,
                                    color = chapter.weightage.color,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${chapter.completedStagesCount}/7",
                        color = if (chapter.isFullyCompleted) colors.physicsPrimary else subjectPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${chapter.progressPercent.toInt()}%",
                        color = colors.textMuted,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = { chapter.progressPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = if (chapter.isFullyCompleted) colors.physicsPrimary else subjectPrimary,
                trackColor = if (colors.isDark) Color(0x1AFFFFFF) else Color(0x15000000),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 7 Checkpoints FlowRow directly from Mission 100 Tracker
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                CheckpointChip(
                    label = "Lecture",
                    isDone = chapter.lectureDone,
                    onToggle = { onToggleStage("LECTURE") },
                    activeColor = subjectPrimary,
                    testTag = "chk_lecture_${chapter.id}"
                )
                CheckpointChip(
                    label = "DPP",
                    isDone = chapter.dppDone,
                    onToggle = { onToggleStage("DPP") },
                    activeColor = subjectPrimary,
                    testTag = "chk_dpp_${chapter.id}"
                )
                CheckpointChip(
                    label = "PYQ's",
                    isDone = chapter.pyqDone,
                    onToggle = { onToggleStage("PYQ") },
                    activeColor = subjectPrimary,
                    testTag = "chk_pyq_${chapter.id}"
                )
                CheckpointChip(
                    label = "Replica Sheet",
                    isDone = chapter.sheetDone,
                    onToggle = { onToggleStage("SHEET") },
                    activeColor = subjectPrimary,
                    testTag = "chk_sheet_${chapter.id}"
                )
                CheckpointChip(
                    label = "Notes / Rev",
                    isDone = chapter.notesDone,
                    onToggle = { onToggleStage("NOTES") },
                    activeColor = subjectPrimary,
                    testTag = "chk_notes_${chapter.id}"
                )
                CheckpointChip(
                    label = "Test",
                    isDone = chapter.testDone,
                    onToggle = { onToggleStage("TEST") },
                    activeColor = subjectPrimary,
                    testTag = "chk_test_${chapter.id}"
                )
                CheckpointChip(
                    label = "Analysis",
                    isDone = chapter.analysisDone,
                    onToggle = { onToggleStage("ANALYSIS") },
                    activeColor = subjectPrimary,
                    testTag = "chk_analysis_${chapter.id}"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Expandable Footer: Remarks & Revision Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { isExpanded = !isExpanded }
                ) {
                    Text(
                        text = if (chapter.remarks.isNotBlank()) "Note: ${chapter.remarks.take(24)}..." else "Add Remarks",
                        color = if (chapter.remarks.isNotBlank()) colors.textSecondary else colors.textMuted,
                        fontSize = 11.sp
                    )
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = colors.textMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (chapter.revisionCount > 0) {
                        Text(
                            text = "Rev #${chapter.revisionCount}",
                            color = colors.chemistryPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(end = 6.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(colors.chipBg)
                            .clickable(onClick = onScheduleRevision)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = colors.chemistryPrimary,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Revise",
                                color = colors.chemistryPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.chipBg)
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "STATUS / REMARKS",
                            color = colors.textSecondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        IconButton(
                            onClick = onEditRemarks,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit remarks", tint = colors.violetPrimary, modifier = Modifier.size(14.dp))
                        }
                    }
                    Text(
                        text = if (chapter.remarks.isNotBlank()) chapter.remarks else "No remarks added yet. Tap edit to write study notes or key weak points.",
                        color = if (chapter.remarks.isNotBlank()) colors.textPrimary else colors.textMuted,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
