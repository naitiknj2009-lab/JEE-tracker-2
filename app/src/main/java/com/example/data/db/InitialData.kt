package com.example.data.db

import com.example.data.model.Chapter
import com.example.data.model.MockTest
import com.example.data.model.Subject
import com.example.data.model.Weightage

object InitialData {

    fun getPhysicsChapters(): List<Chapter> = listOf(
        Chapter(sNo = 1, name = "Units, Dimensions & Measurements", subject = Subject.PHYSICS, category = "General Physics", weightage = Weightage.HIGH),
        Chapter(sNo = 2, name = "Mathematical Tools & Vectors", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.MEDIUM),
        Chapter(sNo = 3, name = "Motion in a Straight Line", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 4, name = "Motion in a Plane & Projectile Motion", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 5, name = "Laws of Motion & Friction", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 6, name = "Work, Energy & Power", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 7, name = "Centre of Mass & System of Particles", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 8, name = "Rotational Motion & Dynamics", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 9, name = "Gravitation", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 10, name = "Mechanical Properties of Solids", subject = Subject.PHYSICS, category = "Properties of Matter", weightage = Weightage.MEDIUM),
        Chapter(sNo = 11, name = "Mechanical Properties of Fluids", subject = Subject.PHYSICS, category = "Properties of Matter", weightage = Weightage.HIGH),
        Chapter(sNo = 12, name = "Thermal Properties of Matter", subject = Subject.PHYSICS, category = "Thermal Physics", weightage = Weightage.MEDIUM),
        Chapter(sNo = 13, name = "Thermodynamics", subject = Subject.PHYSICS, category = "Thermal Physics", weightage = Weightage.HIGH),
        Chapter(sNo = 14, name = "Kinetic Theory of Gases", subject = Subject.PHYSICS, category = "Thermal Physics", weightage = Weightage.HIGH),
        Chapter(sNo = 15, name = "Oscillations (SHM)", subject = Subject.PHYSICS, category = "Waves & SHM", weightage = Weightage.HIGH),
        Chapter(sNo = 16, name = "Waves & Acoustics", subject = Subject.PHYSICS, category = "Waves & SHM", weightage = Weightage.MEDIUM),
        Chapter(sNo = 17, name = "Electric Charges & Fields", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 18, name = "Electrostatic Potential & Capacitance", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 19, name = "Current Electricity", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 20, name = "Moving Charges & Magnetism", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 21, name = "Magnetism & Matter", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.MEDIUM),
        Chapter(sNo = 22, name = "Electromagnetic Induction", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 23, name = "Alternating Current", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 24, name = "Electromagnetic Waves", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.MEDIUM),
        Chapter(sNo = 25, name = "Ray Optics & Optical Instruments", subject = Subject.PHYSICS, category = "Optics", weightage = Weightage.HIGH),
        Chapter(sNo = 26, name = "Wave Optics", subject = Subject.PHYSICS, category = "Optics", weightage = Weightage.HIGH),
        Chapter(sNo = 27, name = "Dual Nature of Radiation & Matter", subject = Subject.PHYSICS, category = "Modern Physics", weightage = Weightage.HIGH),
        Chapter(sNo = 28, name = "Atoms & Nuclei", subject = Subject.PHYSICS, category = "Modern Physics", weightage = Weightage.HIGH),
        Chapter(sNo = 29, name = "Semiconductor Electronics: Devices & Circuits", subject = Subject.PHYSICS, category = "Modern Physics", weightage = Weightage.HIGH)
    )

    fun getChemistryChapters(): List<Chapter> = listOf(
        Chapter(sNo = 1, name = "Some Basic Concepts of Chemistry", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 2, name = "Structure of Atom", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 3, name = "Chemical Bonding & Molecular Structure", subject = Subject.CHEMISTRY, category = "Inorganic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 4, name = "Thermodynamics & Thermochemistry", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 5, name = "Chemical Equilibrium & Ionic Equilibrium", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 6, name = "Solutions & Colligative Properties", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 7, name = "Electrochemistry", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 8, name = "Chemical Kinetics", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 9, name = "Redox Reactions", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.MEDIUM),
        Chapter(sNo = 10, name = "Surface Chemistry & Colloids", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.MEDIUM),
        Chapter(sNo = 11, name = "Classification of Elements & Periodicity", subject = Subject.CHEMISTRY, category = "Inorganic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 12, name = "p-Block Elements", subject = Subject.CHEMISTRY, category = "Inorganic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 13, name = "The d- and f-Block Elements", subject = Subject.CHEMISTRY, category = "Inorganic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 14, name = "Coordination Compounds", subject = Subject.CHEMISTRY, category = "Inorganic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 15, name = "Principles of Qualitative Analysis", subject = Subject.CHEMISTRY, category = "Practical Chemistry", weightage = Weightage.MEDIUM),
        Chapter(sNo = 16, name = "General Organic Chemistry (GOC & IUPAC)", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 17, name = "Hydrocarbons (Alkanes, Alkenes, Alkynes)", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 18, name = "Haloalkanes & Haloarenes", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 19, name = "Alcohols, Phenols & Ethers", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 20, name = "Aldehydes, Ketones & Carboxylic Acids", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 21, name = "Amines & Diazonium Salts", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 22, name = "Biomolecules & Polymers", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH)
    )

    fun getMathematicsChapters(): List<Chapter> = listOf(
        Chapter(sNo = 1, name = "Sets, Relations & Functions", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 2, name = "Complex Numbers", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 3, name = "Quadratic Equations & Expressions", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 4, name = "Matrices & Determinants", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 5, name = "Permutations & Combinations", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 6, name = "Binomial Theorem", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 7, name = "Sequence & Series (AP, GP, AGP)", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 8, name = "Straight Lines", subject = Subject.MATHEMATICS, category = "Coordinate Geometry", weightage = Weightage.HIGH),
        Chapter(sNo = 9, name = "Circles", subject = Subject.MATHEMATICS, category = "Coordinate Geometry", weightage = Weightage.HIGH),
        Chapter(sNo = 10, name = "Conic Sections: Parabola", subject = Subject.MATHEMATICS, category = "Coordinate Geometry", weightage = Weightage.HIGH),
        Chapter(sNo = 11, name = "Conic Sections: Ellipse", subject = Subject.MATHEMATICS, category = "Coordinate Geometry", weightage = Weightage.HIGH),
        Chapter(sNo = 12, name = "Conic Sections: Hyperbola", subject = Subject.MATHEMATICS, category = "Coordinate Geometry", weightage = Weightage.HIGH),
        Chapter(sNo = 13, name = "Trigonometric Functions & Identities", subject = Subject.MATHEMATICS, category = "Trigonometry", weightage = Weightage.MEDIUM),
        Chapter(sNo = 14, name = "Trigonometric Equations & SOT", subject = Subject.MATHEMATICS, category = "Trigonometry", weightage = Weightage.LOW),
        Chapter(sNo = 15, name = "Inverse Trigonometric Functions", subject = Subject.MATHEMATICS, category = "Trigonometry", weightage = Weightage.MEDIUM),
        Chapter(sNo = 16, name = "Limits, Continuity & Differentiability", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 17, name = "Methods of Differentiation", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 18, name = "Application of Derivatives (AOD)", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 19, name = "Indefinite Integration", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 20, name = "Definite Integration", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 21, name = "Area Under Curves (AUC)", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 22, name = "Differential Equations", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 23, name = "Vector Algebra", subject = Subject.MATHEMATICS, category = "Vectors & 3D", weightage = Weightage.HIGH),
        Chapter(sNo = 24, name = "Three Dimensional Geometry (3D)", subject = Subject.MATHEMATICS, category = "Vectors & 3D", weightage = Weightage.HIGH),
        Chapter(sNo = 25, name = "Statistics", subject = Subject.MATHEMATICS, category = "Statistics & Reasoning", weightage = Weightage.HIGH),
        Chapter(sNo = 26, name = "Probability", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 27, name = "Mathematical Reasoning", subject = Subject.MATHEMATICS, category = "Statistics & Reasoning", weightage = Weightage.MEDIUM),
        Chapter(sNo = 28, name = "Linear Inequalities & Logarithms", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.MEDIUM)
    )

    fun getAllChapters(): List<Chapter> = getPhysicsChapters() + getChemistryChapters() + getMathematicsChapters()

    fun getInitialMockTests(): List<MockTest> = listOf(
        MockTest(sNo = 1, testName = "JEE Mains-1", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "11 Oct 2026", isAttempted = true, totalScore = 176, accuracy = 80.6f, negativeMarks = 12, isAnalysisDone = true, mistakeTopics = "Doppler effect signs"),
        MockTest(sNo = 2, testName = "JEE Mains-2", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "18 Oct 2026", isAttempted = true, totalScore = 192, accuracy = 84.2f, negativeMarks = 9, isAnalysisDone = true, mistakeTopics = "Amine basicity order"),
        MockTest(sNo = 3, testName = "JEE Mains-3", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "25 Oct 2026", isAttempted = true, totalScore = 185, accuracy = 81.0f, negativeMarks = 11, isAnalysisDone = true),
        MockTest(sNo = 4, testName = "JEE Mains-4", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "01 Nov 2026", isAttempted = true, totalScore = 208, accuracy = 88.5f, negativeMarks = 7, isAnalysisDone = true),
        MockTest(sNo = 5, testName = "JEE Mains-5", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "08 Nov 2026"),
        MockTest(sNo = 6, testName = "JEE Mains-6", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "15 Nov 2026"),
        MockTest(sNo = 7, testName = "JEE Mains-7", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "22 Nov 2026"),
        MockTest(sNo = 8, testName = "JEE Mains-8", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "29 Nov 2026"),
        MockTest(sNo = 9, testName = "JEE Mains-9", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "06 Dec 2026"),
        MockTest(sNo = 10, testName = "JEE Mains-10", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "13 Dec 2026"),
        MockTest(sNo = 11, testName = "JEE Mains-11", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "20 Dec 2026"),
        MockTest(sNo = 12, testName = "JEE Mains-12", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "27 Dec 2026"),
        MockTest(sNo = 13, testName = "AITS-1", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "03 Jan 2027"),
        MockTest(sNo = 14, testName = "AITS-2", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "10 Jan 2027"),
        MockTest(sNo = 15, testName = "AITS-3", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "17 Jan 2027"),
        MockTest(sNo = 16, testName = "AITS-4", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "24 Jan 2027"),
        MockTest(sNo = 17, testName = "AITS-5", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "31 Jan 2027"),
        MockTest(sNo = 18, testName = "AITS-6", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "07 Feb 2027"),
        MockTest(sNo = 19, testName = "AITS-7", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "14 Feb 2027"),
        MockTest(sNo = 20, testName = "AITS-8", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "21 Feb 2027"),
        MockTest(sNo = 21, testName = "AITS-9", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "28 Feb 2027"),
        MockTest(sNo = 22, testName = "AITS-10", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "07 Mar 2027"),
        MockTest(sNo = 23, testName = "AITS-11", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "14 Mar 2027"),
        MockTest(sNo = 24, testName = "AITS-12", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "21 Mar 2027"),
        MockTest(sNo = 25, testName = "AITS-13", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "28 Mar 2027"),
        MockTest(sNo = 26, testName = "AITS-14", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "04 Apr 2027"),
        MockTest(sNo = 27, testName = "AITS-15", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "11 Apr 2027"),
        MockTest(sNo = 28, testName = "AITS-16", testType = "Full Test", pattern = "JEE Main", platform = "MathonGo", scheduledDate = "18 Apr 2027"),
        MockTest(sNo = 29, testName = "AITS-17", testType = "Full Test", pattern = "JEE Advanced", platform = "Allen", scheduledDate = "25 Apr 2027"),
        MockTest(sNo = 30, testName = "AITS-18", testType = "Full Test", pattern = "JEE Advanced", platform = "Allen", scheduledDate = "02 May 2027"),
        MockTest(sNo = 31, testName = "AITS-19", testType = "Full Test", pattern = "JEE Advanced", platform = "Allen", scheduledDate = "09 May 2027"),
        MockTest(sNo = 32, testName = "AITS-20", testType = "Full Test", pattern = "JEE Advanced", platform = "Allen", scheduledDate = "16 May 2027")
    )
}
