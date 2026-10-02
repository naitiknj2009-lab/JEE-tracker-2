package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AmbientBackground
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.MistakeNotebookScreen
import com.example.ui.screens.MockTestScreen
import com.example.ui.screens.RevisionPlannerScreen
import com.example.ui.screens.StudyHabitScreen
import com.example.ui.screens.SyllabusScreen
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.JeeTheme
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.JeeViewModel

enum class JeeScreen(val title: String, val icon: ImageVector) {
    DASHBOARD("Dashboard", Icons.Default.Dashboard),
    SYLLABUS("Syllabus", Icons.Default.MenuBook),
    TESTS("Mock Tests", Icons.Default.TrendingUp),
    MISTAKES("Mistakes", Icons.Default.Psychology),
    HABIT("Habits", Icons.Default.LocalFireDepartment),
    REVISION("Revision", Icons.Default.Schedule)
}

class MainActivity : ComponentActivity() {

    private val viewModel: JeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
            MyApplicationTheme(themeMode = themeMode) {
                MainApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainApp(viewModel: JeeViewModel) {
    val colors = JeeTheme.colors
    var currentScreen by remember { mutableStateOf(JeeScreen.DASHBOARD) }
    val revisionQueue by viewModel.revisionQueue.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    var showThemeDialog by remember { mutableStateOf(false) }

    // Handle back button: return to dashboard if on another screen
    BackHandler(enabled = currentScreen != JeeScreen.DASHBOARD) {
        currentScreen = JeeScreen.DASHBOARD
    }

    AmbientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                // Top Action Bar with Mission Branding & Quick Theme Toggle
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(colors.violetPrimary, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "JEE TRACKER OS",
                                color = colors.textPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                        }

                        // Theme Quick Switch Button
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, colors.chipBorder, RoundedCornerShape(12.dp))
                                .background(colors.chipBg)
                                .clickable { showThemeDialog = true }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("theme_toggle_button")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = when (themeMode) {
                                        AppThemeMode.DARK -> Icons.Default.DarkMode
                                        AppThemeMode.LIGHT -> Icons.Default.LightMode
                                        AppThemeMode.SYSTEM -> Icons.Default.BrightnessAuto
                                    },
                                    contentDescription = "Switch Theme",
                                    tint = colors.violetPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = when (themeMode) {
                                        AppThemeMode.DARK -> "Dark"
                                        AppThemeMode.LIGHT -> "Light"
                                        AppThemeMode.SYSTEM -> "Auto"
                                    },
                                    color = colors.textPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            },
            bottomBar = {
                GlassBottomNavigationBar(
                    currentScreen = currentScreen,
                    onScreenSelected = { currentScreen = it },
                    revisionBadgeCount = revisionQueue.size
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())
            ) {
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "screen_transition"
                ) { target ->
                    when (target) {
                        JeeScreen.DASHBOARD -> DashboardScreen(
                            viewModel = viewModel,
                            onNavigateToSyllabus = { subject ->
                                viewModel.setSubject(subject)
                                currentScreen = JeeScreen.SYLLABUS
                            },
                            onNavigateToMockTests = { currentScreen = JeeScreen.TESTS },
                            onNavigateToMistakes = { currentScreen = JeeScreen.MISTAKES },
                            onNavigateToHabit = { currentScreen = JeeScreen.HABIT }
                        )
                        JeeScreen.SYLLABUS -> SyllabusScreen(viewModel = viewModel)
                        JeeScreen.TESTS -> MockTestScreen(viewModel = viewModel)
                        JeeScreen.MISTAKES -> MistakeNotebookScreen(viewModel = viewModel)
                        JeeScreen.HABIT -> StudyHabitScreen(viewModel = viewModel)
                        JeeScreen.REVISION -> RevisionPlannerScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }

    // Theme Picker Dialog
    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = {
                Text(
                    text = "Select Appearance Theme",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ThemeRadioOption(
                        label = "🌙 Dark Obsidian Glass (Default)",
                        description = "Deep slate canvas with radiant specular glows",
                        selected = themeMode == AppThemeMode.DARK,
                        onClick = {
                            viewModel.setThemeMode(AppThemeMode.DARK)
                            showThemeDialog = false
                        }
                    )
                    ThemeRadioOption(
                        label = "☀️ Light Frost Crystal",
                        description = "Crisp off-white canvas with frosted glass cards",
                        selected = themeMode == AppThemeMode.LIGHT,
                        onClick = {
                            viewModel.setThemeMode(AppThemeMode.LIGHT)
                            showThemeDialog = false
                        }
                    )
                    ThemeRadioOption(
                        label = "⚙️ System Default",
                        description = "Matches your Android device system setting",
                        selected = themeMode == AppThemeMode.SYSTEM,
                        onClick = {
                            viewModel.setThemeMode(AppThemeMode.SYSTEM)
                            showThemeDialog = false
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showThemeDialog = false }) {
                    Text("Close", color = colors.violetPrimary)
                }
            },
            containerColor = colors.surface
        )
    }
}

@Composable
private fun ThemeRadioOption(
    label: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = JeeTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) colors.violetPrimary.copy(alpha = if (colors.isDark) 0.2f else 0.12f) else colors.chipBg)
            .border(1.dp, if (selected) colors.violetPrimary else colors.chipBorder, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = colors.violetPrimary,
                unselectedColor = colors.textMuted
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                color = colors.textPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                color = colors.textSecondary,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun GlassBottomNavigationBar(
    currentScreen: JeeScreen,
    onScreenSelected: (JeeScreen) -> Unit,
    revisionBadgeCount: Int
) {
    val colors = JeeTheme.colors
    val isDark = colors.isDark

    val items = listOf(
        JeeScreen.DASHBOARD,
        JeeScreen.SYLLABUS,
        JeeScreen.TESTS,
        JeeScreen.MISTAKES,
        JeeScreen.HABIT
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(26.dp))
            .border(
                BorderStroke(1.dp, Brush.verticalGradient(listOf(colors.glassBorderTop, colors.glassBorderBottom))),
                RoundedCornerShape(26.dp)
            )
            .background(
                brush = Brush.verticalGradient(
                    if (isDark) {
                        listOf(
                            Color(0xD90D1424),
                            Color(0xF2070A11)
                        )
                    } else {
                        listOf(
                            Color(0xF5FFFFFF),
                            Color(0xEBFFFFFF)
                        )
                    }
                )
            )
            .padding(vertical = 6.dp, horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { screen ->
                val isSelected = currentScreen == screen
                val activeColor = when (screen) {
                    JeeScreen.DASHBOARD -> colors.violetPrimary
                    JeeScreen.SYLLABUS -> colors.physicsPrimary
                    JeeScreen.TESTS -> colors.mathPrimary
                    JeeScreen.MISTAKES -> colors.chemistryPrimary
                    JeeScreen.HABIT -> colors.physicsPrimary
                    JeeScreen.REVISION -> colors.chemistryPrimary
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onScreenSelected(screen) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("nav_item_${screen.name.lowercase()}"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (screen == JeeScreen.SYLLABUS && revisionBadgeCount > 0) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = colors.chemistryPrimary,
                                        contentColor = Color.White
                                    ) {
                                        Text("$revisionBadgeCount", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.title,
                                    tint = if (isSelected) activeColor else colors.textMuted,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title,
                                tint = if (isSelected) activeColor else colors.textMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = screen.title,
                            color = if (isSelected) colors.textPrimary else colors.textMuted,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )

                        if (isSelected) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Box(
                                modifier = Modifier
                                    .size(width = 12.dp, height = 2.dp)
                                    .background(activeColor, RoundedCornerShape(1.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}
