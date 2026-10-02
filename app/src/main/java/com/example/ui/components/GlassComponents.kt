package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Subject
import com.example.ui.theme.JeeTheme
import kotlin.math.sin

@Composable
fun AmbientBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val colors = JeeTheme.colors
    val isDark = colors.isDark

    // Infinite transition for continuous liquid ambient orb motion
    val infiniteTransition = rememberInfiniteTransition(label = "liquid_ambient_transition")

    val orb1X by infiniteTransition.animateFloat(
        initialValue = -70f,
        targetValue = -30f,
        animationSpec = infiniteRepeatable(
            animation = tween(7000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1_x"
    )
    val orb1Y by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(8500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1_y"
    )

    val orb2X by infiniteTransition.animateFloat(
        initialValue = 80f,
        targetValue = 120f,
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2_x"
    )
    val orb2Y by infiniteTransition.animateFloat(
        initialValue = 120f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(7500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2_y"
    )

    val orb3X by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb3_x"
    )
    val orb3Y by infiniteTransition.animateFloat(
        initialValue = 100f,
        targetValue = 60f,
        animationSpec = infiniteRepeatable(
            animation = tween(9500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb3_y"
    )

    val orbAlpha1 = if (isDark) 0.18f else 0.09f
    val orbAlpha2 = if (isDark) 0.20f else 0.10f
    val orbAlpha3 = if (isDark) 0.16f else 0.08f

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.backgroundCanvas)
    ) {
        // Liquid Orb 1 (Top Left Emerald / Theme accent)
        Box(
            modifier = Modifier
                .size(340.dp)
                .offset(x = orb1X.dp, y = orb1Y.dp)
                .blur(95.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors.physicsPrimary.copy(alpha = orbAlpha1),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Liquid Orb 2 (Top Right Electric Violet)
        Box(
            modifier = Modifier
                .size(360.dp)
                .align(Alignment.TopEnd)
                .offset(x = orb2X.dp, y = orb2Y.dp)
                .blur(100.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors.violetPrimary.copy(alpha = orbAlpha2),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Liquid Orb 3 (Bottom Left Math Cyan)
        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.BottomStart)
                .offset(x = orb3X.dp, y = orb3Y.dp)
                .blur(90.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            colors.mathPrimary.copy(alpha = orbAlpha3),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        content()
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(22.dp),
    backgroundColor: Color? = null,
    borderColor: Color? = null,
    glowAccentColor: Color? = null,
    enableShimmer: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val colors = JeeTheme.colors
    val isDark = colors.isDark

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.985f else 1f,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = 400f),
        label = "card_spring_press"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "liquid_shimmer")
    val shimmerPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_phase"
    )

    val finalBg = backgroundColor ?: colors.glassSurface

    // Specular border with subtle refraction shimmer
    val borderBrush = if (borderColor != null) {
        Brush.verticalGradient(
            listOf(
                borderColor.copy(alpha = if (isDark) 0.6f else 0.7f),
                borderColor.copy(alpha = if (isDark) 0.15f else 0.25f)
            )
        )
    } else {
        if (enableShimmer && isDark) {
            val shimmerAlpha = 0.25f + 0.15f * sin(shimmerPhase * 2f * Math.PI.toFloat())
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = shimmerAlpha),
                    colors.glassBorderTop,
                    colors.glassBorderBottom
                ),
                start = Offset(0f, 0f),
                end = Offset(400f * shimmerPhase, 400f)
            )
        } else {
            Brush.verticalGradient(listOf(colors.glassBorderTop, colors.glassBorderBottom))
        }
    }

    val glowBrush = glowAccentColor?.let {
        Brush.radialGradient(
            colors = listOf(
                it.copy(alpha = if (isDark) 0.16f else 0.08f),
                Color.Transparent
            ),
            radius = 380f
        )
    }

    var baseModifier = modifier
        .scale(scale)
        .clip(shape)
        .border(BorderStroke(1.dp, borderBrush), shape)
        .background(
            brush = if (isDark) {
                Brush.verticalGradient(
                    colors = listOf(
                        finalBg.copy(alpha = 0.20f),
                        finalBg.copy(alpha = 0.09f)
                    )
                )
            } else {
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.94f),
                        Color.White.copy(alpha = 0.82f)
                    )
                )
            }
        )

    if (glowBrush != null) {
        baseModifier = baseModifier.background(glowBrush)
    }

    if (onClick != null) {
        baseModifier = baseModifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
    }

    Box(
        modifier = baseModifier.padding(16.dp),
        content = content
    )
}

@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    accentColor: Color,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    testTag: String = "stat_card"
) {
    val colors = JeeTheme.colors
    GlassCard(
        modifier = modifier.testTag(testTag),
        glowAccentColor = accentColor
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(accentColor, CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title.uppercase(),
                    color = colors.textSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = value,
                color = colors.textPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = colors.textMuted,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun CheckpointChip(
    label: String,
    isDone: Boolean,
    onToggle: () -> Unit,
    activeColor: Color,
    modifier: Modifier = Modifier,
    testTag: String = "checkpoint_chip"
) {
    val colors = JeeTheme.colors
    val isDark = colors.isDark

    val doneBgAlpha = if (isDark) 0.24f else 0.18f
    val doneBorderAlpha = if (isDark) 0.85f else 0.95f

    val scaleAnim = remember { Animatable(1f) }

    LaunchedEffect(isDone) {
        if (isDone) {
            scaleAnim.animateTo(1.15f, animationSpec = tween(120))
            scaleAnim.animateTo(1f, animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f))
        }
    }

    val bgColor by animateColorAsState(
        targetValue = if (isDone) activeColor.copy(alpha = doneBgAlpha) else colors.chipBg,
        animationSpec = tween(220),
        label = "chip_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isDone) activeColor.copy(alpha = doneBorderAlpha) else colors.chipBorder,
        animationSpec = tween(220),
        label = "chip_border"
    )

    Box(
        modifier = modifier
            .scale(scaleAnim.value)
            .testTag(testTag)
            .clip(RoundedCornerShape(11.dp))
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(11.dp))
            .background(bgColor)
            .clickable(onClick = onToggle)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isDone) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Done",
                    tint = activeColor,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            } else {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(colors.textMuted, CircleShape)
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
            Text(
                text = label,
                color = if (isDone) colors.textPrimary else colors.textSecondary,
                fontSize = 11.sp,
                fontWeight = if (isDone) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun SubjectBadge(
    subject: Subject,
    modifier: Modifier = Modifier
) {
    val colors = JeeTheme.colors
    val subjectPrimary = when (subject) {
        Subject.PHYSICS -> colors.physicsPrimary
        Subject.CHEMISTRY -> colors.chemistryPrimary
        Subject.MATHEMATICS -> colors.mathPrimary
    }
    val subjectSurface = when (subject) {
        Subject.PHYSICS -> colors.physicsSurface
        Subject.CHEMISTRY -> colors.chemistrySurface
        Subject.MATHEMATICS -> colors.mathSurface
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(9.dp))
            .background(subjectSurface)
            .border(BorderStroke(1.dp, subjectPrimary.copy(alpha = 0.45f)), RoundedCornerShape(9.dp))
            .padding(horizontal = 9.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(subjectPrimary, CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = subject.displayName,
                color = subjectPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun CircularProgressWithLabel(
    progress: Float, // 0..1f
    percentageText: String,
    caption: String,
    accentColor: Color,
    size: Dp = 100.dp,
    strokeWidth: Dp = 8.dp,
    modifier: Modifier = Modifier
) {
    val colors = JeeTheme.colors
    val trackColor = if (colors.isDark) Color(0x1AFFFFFF) else Color(0x15000000)

    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 100f),
        label = "circular_progress_spring"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        // Track
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = trackColor,
            strokeWidth = strokeWidth,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Round
        )
        // Fluid Animated Indicator
        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxSize(),
            color = accentColor,
            strokeWidth = strokeWidth,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Round
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = percentageText,
                color = colors.textPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = caption,
                color = colors.textMuted,
                fontSize = 10.sp
            )
        }
    }
}

/**
 * Liquid Wave Animated Level Indicator
 * Generates an undulating liquid surface reflecting progress inside a frosted container.
 */
@Composable
fun LiquidWaveProgress(
    progress: Float, // 0..1f
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave_oscillation")
    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_phase"
    )

    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0.05f, 1f),
        animationSpec = spring(dampingRatio = 0.85f, stiffness = 80f),
        label = "wave_level_spring"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    ) {
        val width = size.width
        val height = size.height
        val waveHeight = 8.dp.toPx()
        val fillHeight = height * (1f - animatedProgress)

        val path = Path()
        path.moveTo(0f, height)
        path.lineTo(0f, fillHeight)

        val step = 10f
        var x = 0f
        while (x <= width) {
            val y = fillHeight + sin((x / width * 3f * Math.PI.toFloat()) + wavePhase) * waveHeight
            path.lineTo(x, y)
            x += step
        }

        path.lineTo(width, height)
        path.close()

        // Draw Liquid Body
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(
                    accentColor.copy(alpha = 0.55f),
                    accentColor.copy(alpha = 0.25f)
                ),
                startY = fillHeight,
                endY = height
            )
        )

        // Draw Liquid Surface Glow Crest
        drawPath(
            path = path,
            color = accentColor.copy(alpha = 0.8f),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}
