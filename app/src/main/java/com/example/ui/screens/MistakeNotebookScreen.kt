package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.data.model.Mistake
import com.example.data.model.Subject
import com.example.ui.components.GlassCard
import com.example.ui.components.SubjectBadge
import com.example.ui.theme.JeeTheme
import com.example.ui.viewmodel.JeeViewModel

@Composable
fun MistakeNotebookScreen(
    viewModel: JeeViewModel,
    modifier: Modifier = Modifier
) {
    val colors = JeeTheme.colors
    val allMistakes by viewModel.allMistakes.collectAsStateWithLifecycle()

    var selectedSubjectFilter by remember { mutableStateOf<Subject?>(null) }
    var selectedReasonFilter by remember { mutableStateOf<String?>(null) }
    var showOnlyFlagged by remember { mutableStateOf(false) }

    var showAddDialog by remember { mutableStateOf(false) }

    val errorReasons = listOf(
        "Calculation Error",
        "Concept Gap",
        "Misread Question",
        "Time Pressure",
        "Formula Forgotten",
        "Silly Guesswork"
    )

    val filteredMistakes = allMistakes.filter { m ->
        val matchSub = selectedSubjectFilter == null || m.subject == selectedSubjectFilter
        val matchReason = selectedReasonFilter == null || m.errorReason == selectedReasonFilter
        val matchFlagged = !showOnlyFlagged || m.flaggedForReattempt
        matchSub && matchReason && matchFlagged
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("mistake_notebook_screen")
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
                            text = "ERROR LOG & LEARNING OS",
                            color = colors.crimsonPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Mistake Notebook",
                            color = colors.textPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    ElevatedButton(
                        onClick = { showAddDialog = true },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = colors.crimsonPrimary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.testTag("log_mistake_button")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Log Mistake", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            // Summary Banner
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    glowAccentColor = colors.crimsonPrimary
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${allMistakes.size} Total Errors Documented",
                                color = colors.textPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${allMistakes.count { it.flaggedForReattempt }} Flagged for Re-attempt • ${allMistakes.count { it.isMastered }} Mastered",
                                color = colors.textSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Filters
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Subject filters
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ReasonChip(
                            label = "All Subjects",
                            isSelected = selectedSubjectFilter == null,
                            onClick = { selectedSubjectFilter = null }
                        )
                        ReasonChip(
                            label = "Physics",
                            isSelected = selectedSubjectFilter == Subject.PHYSICS,
                            activeColor = colors.physicsPrimary,
                            onClick = { selectedSubjectFilter = if (selectedSubjectFilter == Subject.PHYSICS) null else Subject.PHYSICS }
                        )
                        ReasonChip(
                            label = "Chemistry",
                            isSelected = selectedSubjectFilter == Subject.CHEMISTRY,
                            activeColor = colors.chemistryPrimary,
                            onClick = { selectedSubjectFilter = if (selectedSubjectFilter == Subject.CHEMISTRY) null else Subject.CHEMISTRY }
                        )
                        ReasonChip(
                            label = "Mathematics",
                            isSelected = selectedSubjectFilter == Subject.MATHEMATICS,
                            activeColor = colors.mathPrimary,
                            onClick = { selectedSubjectFilter = if (selectedSubjectFilter == Subject.MATHEMATICS) null else Subject.MATHEMATICS }
                        )
                    }

                    // Reason filters
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ReasonChip(
                            label = "Flagged Only",
                            isSelected = showOnlyFlagged,
                            activeColor = colors.crimsonPrimary,
                            onClick = { showOnlyFlagged = !showOnlyFlagged }
                        )
                        errorReasons.forEach { reason ->
                            ReasonChip(
                                label = reason,
                                isSelected = selectedReasonFilter == reason,
                                onClick = {
                                    selectedReasonFilter = if (selectedReasonFilter == reason) null else reason
                                }
                            )
                        }
                    }
                }
            }

            // Empty state or list
            if (filteredMistakes.isEmpty()) {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = colors.textMuted,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No Mistakes Logged Yet",
                                color = colors.textPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Every wrong answer is a rank waiting to be saved. Log mistakes from mock tests and DPPs here to re-attempt before D-Day.",
                                color = colors.textMuted,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            } else {
                items(filteredMistakes, key = { it.id }) { mistake ->
                    MistakeItemCard(
                        mistake = mistake,
                        onToggleFlag = { viewModel.toggleMistakeFlag(mistake) },
                        onToggleMastered = { viewModel.toggleMistakeResolved(mistake) },
                        onDelete = { viewModel.deleteMistake(mistake) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }

    if (showAddDialog) {
        AddMistakeDialog(
            errorReasons = errorReasons,
            onDismiss = { showAddDialog = false },
            onSave = { title, desc, subject, chapter, reason, resolution ->
                viewModel.addMistake(title, desc, subject, chapter, reason, resolution)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun ReasonChip(
    label: String,
    isSelected: Boolean,
    activeColor: Color? = null,
    onClick: () -> Unit
) {
    val colors = JeeTheme.colors
    val finalActive = activeColor ?: colors.violetPrimary
    val isDark = colors.isDark

    val bg = if (isSelected) finalActive.copy(alpha = if (isDark) 0.2f else 0.15f) else colors.chipBg
    val border = if (isSelected) finalActive else colors.chipBorder

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, border, RoundedCornerShape(8.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) colors.textPrimary else colors.textSecondary,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun MistakeItemCard(
    mistake: Mistake,
    onToggleFlag: () -> Unit,
    onToggleMastered: () -> Unit,
    onDelete: () -> Unit
) {
    val colors = JeeTheme.colors
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = if (mistake.isMastered) colors.physicsPrimary else colors.crimsonPrimary.copy(alpha = 0.5f),
        glowAccentColor = if (mistake.isMastered) colors.physicsPrimary else colors.crimsonPrimary
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        SubjectBadge(subject = mistake.subject)
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(colors.crimsonPrimary.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = mistake.errorReason,
                                color = colors.crimsonPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = mistake.questionTitle,
                        color = colors.textPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Chapter: ${mistake.chapterName}",
                        color = colors.textSecondary,
                        fontSize = 11.sp
                    )
                }

                Row {
                    IconButton(onClick = onToggleFlag) {
                        Icon(
                            imageVector = if (mistake.flaggedForReattempt) Icons.Filled.Flag else Icons.Outlined.Flag,
                            contentDescription = "Flag for Re-attempt",
                            tint = if (mistake.flaggedForReattempt) colors.crimsonPrimary else colors.textMuted
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = colors.textMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            if (mistake.questionDescription.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mistake.questionDescription,
                    color = colors.textSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            // Key Takeaway Note
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(colors.physicsPrimary.copy(alpha = 0.12f))
                    .border(1.dp, colors.physicsPrimary.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        text = "KEY TAKEAWAY / CORRECTION",
                        color = colors.physicsPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = mistake.resolutionNote,
                        color = colors.textPrimary,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (mistake.flaggedForReattempt) "Flagged for Revision" else "Normal",
                    color = if (mistake.flaggedForReattempt) colors.crimsonPrimary else colors.textMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )

                ElevatedButton(
                    onClick = onToggleMastered,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = if (mistake.isMastered) colors.physicsPrimary else colors.chipBg,
                        contentColor = if (mistake.isMastered) Color.White else colors.textPrimary
                    ),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text(
                        text = if (mistake.isMastered) "Mastered ✓" else "Mark Mastered",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun AddMistakeDialog(
    errorReasons: List<String>,
    onDismiss: () -> Unit,
    onSave: (
        title: String,
        desc: String,
        subject: Subject,
        chapter: String,
        reason: String,
        resolution: String
    ) -> Unit
) {
    val colors = JeeTheme.colors
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf(Subject.PHYSICS) }
    var chapterName by remember { mutableStateOf("") }
    var selectedReason by remember { mutableStateOf(errorReasons.first()) }
    var resolution by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Log Mistake in Notebook",
                color = colors.textPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Problem Summary / Question Title", color = colors.textMuted) },
                    placeholder = { Text("e.g. Rolling motion cylinder acceleration on incline", color = colors.textMuted, fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary
                    )
                )

                // Subject Select
                Text("Subject", color = colors.textSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Subject.entries.forEach { sub ->
                        val subColor = when (sub) {
                            Subject.PHYSICS -> colors.physicsPrimary
                            Subject.CHEMISTRY -> colors.chemistryPrimary
                            Subject.MATHEMATICS -> colors.mathPrimary
                        }
                        ReasonChip(
                            label = sub.displayName,
                            isSelected = selectedSubject == sub,
                            activeColor = subColor,
                            onClick = { selectedSubject = sub }
                        )
                    }
                }

                OutlinedTextField(
                    value = chapterName,
                    onValueChange = { chapterName = it },
                    label = { Text("Chapter / Topic", color = colors.textMuted) },
                    placeholder = { Text("e.g. Rotational Motion", color = colors.textMuted, fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary
                    )
                )

                Text("Reason for Mistake", color = colors.textSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    errorReasons.forEach { r ->
                        ReasonChip(
                            label = r,
                            isSelected = selectedReason == r,
                            activeColor = colors.crimsonPrimary,
                            onClick = { selectedReason = r }
                        )
                    }
                }

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Question Details / What Went Wrong", color = colors.textMuted) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary
                    )
                )

                OutlinedTextField(
                    value = resolution,
                    onValueChange = { resolution = it },
                    label = { Text("Resolution / Key Takeaway Note", color = colors.textMuted) },
                    placeholder = { Text("e.g. Remember to take torque about instantaneous center of rotation", color = colors.textMuted, fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && chapterName.isNotBlank() && resolution.isNotBlank()) {
                        onSave(title, description, selectedSubject, chapterName, selectedReason, resolution)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.crimsonPrimary),
                enabled = title.isNotBlank() && chapterName.isNotBlank()
            ) {
                Text("Save to Notebook", color = Color.White)
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
