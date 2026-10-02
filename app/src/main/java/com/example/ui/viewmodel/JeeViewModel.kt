package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.Chapter
import com.example.data.model.DailyLog
import com.example.data.model.Mistake
import com.example.data.model.MockTest
import com.example.data.model.Subject
import com.example.data.model.Weightage
import com.example.data.repository.JeeRepository
import com.example.ui.theme.AppThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JeeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: JeeRepository
    private val prefs = application.getSharedPreferences("jee_theme_prefs", Context.MODE_PRIVATE)

    // Theme Mode
    private val _themeMode = MutableStateFlow(
        try {
            AppThemeMode.valueOf(prefs.getString("theme_mode", AppThemeMode.DARK.name) ?: AppThemeMode.DARK.name)
        } catch (e: Exception) {
            AppThemeMode.DARK
        }
    )
    val themeMode = _themeMode.asStateFlow()

    fun setThemeMode(mode: AppThemeMode) {
        _themeMode.value = mode
        prefs.edit().putString("theme_mode", mode.name).apply()
    }

    fun toggleTheme() {
        val next = when (_themeMode.value) {
            AppThemeMode.DARK -> AppThemeMode.LIGHT
            AppThemeMode.LIGHT -> AppThemeMode.SYSTEM
            AppThemeMode.SYSTEM -> AppThemeMode.DARK
        }
        setThemeMode(next)
    }

    // Search and filter states
    private val _selectedSubject = MutableStateFlow<Subject?>(null)
    val selectedSubject = _selectedSubject.asStateFlow()

    private val _selectedWeightage = MutableStateFlow<Weightage?>(null)
    val selectedWeightage = _selectedWeightage.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _filterStatus = MutableStateFlow("ALL") // "ALL", "COMPLETED", "IN_PROGRESS", "UNSTARTED"
    val filterStatus = _filterStatus.asStateFlow()

    // Study Timer State (Pomodoro / Stopwatch)
    private val _timerSeconds = MutableStateFlow(0)
    val timerSeconds = _timerSeconds.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning = _isTimerRunning.asStateFlow()

    private var timerJob: Job? = null

    // Date
    val todayDateString: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = JeeRepository(
            chapterDao = database.chapterDao(),
            mockTestDao = database.mockTestDao(),
            mistakeDao = database.mistakeDao(),
            dailyLogDao = database.dailyLogDao()
        )
        // Ensure initial data seeded on background thread
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.populateDatabase(database)
        }
    }

    val allChapters: StateFlow<List<Chapter>> = repository.allChapters
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredChapters: StateFlow<List<Chapter>> = combine(
        allChapters,
        _selectedSubject,
        _selectedWeightage,
        _searchQuery,
        _filterStatus
    ) { chapters, subject, weightage, query, status ->
        chapters.filter { ch ->
            val matchSubject = subject == null || ch.subject == subject
            val matchWeightage = weightage == null || ch.weightage == weightage
            val matchQuery = query.isBlank() || ch.name.contains(query, ignoreCase = true) || ch.category.contains(query, ignoreCase = true)
            val matchStatus = when (status) {
                "COMPLETED" -> ch.isFullyCompleted
                "IN_PROGRESS" -> ch.completedStagesCount in 1..6
                "UNSTARTED" -> ch.completedStagesCount == 0
                else -> true
            }
            matchSubject && matchWeightage && matchQuery && matchStatus
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allMockTests: StateFlow<List<MockTest>> = repository.allMockTests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val attemptedMockTests: StateFlow<List<MockTest>> = repository.attemptedMockTests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allMistakes: StateFlow<List<Mistake>> = repository.allMistakes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayLog: StateFlow<DailyLog?> = repository.getLogForDate(todayDateString)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allLogs: StateFlow<List<DailyLog>> = repository.allLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val revisionQueue: StateFlow<List<Chapter>> = repository.allChapters
        .combine(MutableStateFlow(System.currentTimeMillis())) { chapters, _ ->
            chapters.filter { it.nextRevisionTimestamp > 0 || (it.completedStagesCount >= 3 && it.revisionCount == 0) }
                .sortedBy { it.nextRevisionTimestamp }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filter Setters
    fun setSubject(subject: Subject?) { _selectedSubject.value = subject }
    fun setWeightage(weightage: Weightage?) { _selectedWeightage.value = weightage }
    fun setSearchQuery(query: String) { _searchQuery.value = query }
    fun setFilterStatus(status: String) { _filterStatus.value = status }

    // Checkpoint Actions
    fun toggleCheckpoint(chapter: Chapter, stage: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = when (stage) {
                "LECTURE" -> chapter.copy(lectureDone = !chapter.lectureDone)
                "DPP" -> chapter.copy(dppDone = !chapter.dppDone)
                "PYQ" -> chapter.copy(pyqDone = !chapter.pyqDone)
                "SHEET" -> chapter.copy(sheetDone = !chapter.sheetDone)
                "NOTES" -> chapter.copy(notesDone = !chapter.notesDone)
                "TEST" -> chapter.copy(testDone = !chapter.testDone)
                "ANALYSIS" -> chapter.copy(analysisDone = !chapter.analysisDone)
                else -> chapter
            }
            repository.updateChapter(updated)
        }
    }

    fun updateChapterRemarks(chapterId: Long, remarks: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRemarks(chapterId, remarks)
        }
    }

    fun scheduleRevision(chapter: Chapter) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.scheduleNextRevision(chapter)
        }
    }

    // Mock Test Actions
    fun saveMockTestResult(
        testId: Long,
        testName: String,
        testType: String,
        platform: String,
        pattern: String,
        scheduledDate: String,
        pCorrect: Int,
        pIncorrect: Int,
        pUnattempted: Int,
        cCorrect: Int,
        cIncorrect: Int,
        cUnattempted: Int,
        mCorrect: Int,
        mIncorrect: Int,
        mUnattempted: Int,
        mistakeTopics: String,
        isAnalysisDone: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val totalCorrect = pCorrect + cCorrect + mCorrect
            val totalIncorrect = pIncorrect + cIncorrect + mIncorrect
            val totalAttempted = totalCorrect + totalIncorrect
            val totalScore = (totalCorrect * 4) - (totalIncorrect * 1)
            val negativeMarks = totalIncorrect * 1
            val accuracy = if (totalAttempted > 0) (totalCorrect.toFloat() / totalAttempted.toFloat()) * 100f else 0f

            val test = MockTest(
                id = testId,
                testName = testName,
                testType = testType,
                platform = platform,
                pattern = pattern,
                scheduledDate = scheduledDate,
                isAttempted = true,
                attemptTimestamp = System.currentTimeMillis(),
                physicsCorrect = pCorrect,
                physicsIncorrect = pIncorrect,
                physicsUnattempted = pUnattempted,
                chemistryCorrect = cCorrect,
                chemistryIncorrect = cIncorrect,
                chemistryUnattempted = cUnattempted,
                mathCorrect = mCorrect,
                mathIncorrect = mIncorrect,
                mathUnattempted = mUnattempted,
                totalScore = totalScore,
                maxScore = 300,
                accuracy = accuracy,
                negativeMarks = negativeMarks,
                mistakeTopics = mistakeTopics,
                isAnalysisDone = isAnalysisDone
            )
            repository.saveMockTest(test)
        }
    }

    fun deleteMockTest(test: MockTest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMockTest(test)
        }
    }

    // Mistake Actions
    fun addMistake(
        title: String,
        description: String,
        subject: Subject,
        chapter: String,
        reason: String,
        resolution: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val mistake = Mistake(
                questionTitle = title,
                questionDescription = description,
                subject = subject,
                chapterName = chapter,
                errorReason = reason,
                resolutionNote = resolution,
                flaggedForReattempt = true,
                isMastered = false
            )
            repository.saveMistake(mistake)
        }
    }

    fun toggleMistakeResolved(mistake: Mistake) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveMistake(mistake.copy(isMastered = !mistake.isMastered))
        }
    }

    fun toggleMistakeFlag(mistake: Mistake) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveMistake(mistake.copy(flaggedForReattempt = !mistake.flaggedForReattempt))
        }
    }

    fun deleteMistake(mistake: Mistake) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMistake(mistake)
        }
    }

    // Daily Log Actions
    fun saveDailyLog(log: DailyLog) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveDailyLog(log)
        }
    }

    // Timer Controls
    fun startTimer() {
        if (_isTimerRunning.value) return
        _isTimerRunning.value = true
        timerJob = viewModelScope.launch {
            while (_isTimerRunning.value) {
                delay(1000)
                _timerSeconds.value += 1
            }
        }
    }

    fun pauseTimer() {
        _isTimerRunning.value = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        _isTimerRunning.value = false
        timerJob?.cancel()
        _timerSeconds.value = 0
    }

    fun saveTimerToTodayLog() {
        val minutesToAdd = _timerSeconds.value / 60
        if (minutesToAdd > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addStudyMinutes(todayDateString, minutesToAdd)
                _timerSeconds.value = 0
                _isTimerRunning.value = false
                timerJob?.cancel()
            }
        }
    }
}
