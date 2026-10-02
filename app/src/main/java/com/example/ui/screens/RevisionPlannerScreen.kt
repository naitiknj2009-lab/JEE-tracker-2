package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
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
import com.example.data.model.Chapter
import com.example.ui.components.GlassCard
import com.example.ui.components.SubjectBadge
import com.example.ui.theme.JeeTheme
import com.example.ui.viewmodel.JeeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RevisionPlannerScreen(
    viewModel: JeeViewModel,
    modifier: Modifier = Modifier
) {
    val colors = JeeTheme.colors
    val revisionQueue by viewModel.revisionQueue.collectAsStateWithLifecycle()
    val allChapters by viewModel.allChapters.collectAsStateWithLifecycle()

    val completedRevisions = allChapters.filter { it.revisionCount > 0 }
    val dateFormatter = SimpleDateFormat("dd MMM", Locale.getDefault())

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("revision_planner_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "SPACED REPETITION ENGINE",
                    color = colors.chemistryPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Revision Queue",
                    color = colors.textPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Automated forgetting-curve intervals: Day 3 → Day 7 → Day 21 → Day 45.",
                    color = colors.textSecondary,
                    fontSize = 12.sp
                )
            }
        }

        // Active Queue Count
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowAccentColor = colors.chemistryPrimary
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${revisionQueue.size} Chapters Due For Revision",
                            color = colors.textPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${completedRevisions.size} Total Chapters with active spaced revision cycles",
                            color = colors.textSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        if (revisionQueue.isEmpty()) {
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = colors.textMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "All Caught Up!",
                            color = colors.textPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "No revisions are currently overdue. As you complete chapters on the syllabus tab, they will queue here automatically.",
                            color = colors.textMuted,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        } else {
            items(revisionQueue, key = { it.id }) { chapter ->
                RevisionChapterCard(
                    chapter = chapter,
                    dateFormatter = dateFormatter,
                    onMarkRevised = { viewModel.scheduleRevision(chapter) }
                )
            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}

@Composable
private fun RevisionChapterCard(
    chapter: Chapter,
    dateFormatter: SimpleDateFormat,
    onMarkRevised: () -> Unit
) {
    val colors = JeeTheme.colors
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = colors.chemistryPrimary.copy(alpha = 0.5f),
        glowAccentColor = colors.chemistryPrimary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SubjectBadge(subject = chapter.subject)
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(colors.chemistryPrimary.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Next: Rev #${chapter.revisionCount + 1}",
                            color = colors.chemistryPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = chapter.name,
                    color = colors.textPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                if (chapter.nextRevisionTimestamp > 0) {
                    Text(
                        text = "Scheduled: ${dateFormatter.format(Date(chapter.nextRevisionTimestamp))}",
                        color = colors.textMuted,
                        fontSize = 11.sp
                    )
                } else {
                    Text(
                        text = "Initial Revision Recommended",
                        color = colors.textMuted,
                        fontSize = 11.sp
                    )
                }
            }

            ElevatedButton(
                onClick = onMarkRevised,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = colors.chemistryPrimary,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Mark Done", fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }
        }
    }
}
