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
        Chapter(sNo = 4, name = "Motion in a Plane", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 5, name = "Laws of Motion", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 6, name = "Work, Energy & Power", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 7, name = "Centre of Mass & System of Particles", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 8, name = "Rotational Motion", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 9, name = "Rotational Motion & Rotational Dynamics", subject = Subject.PHYSICS, category = "Mechanics", weightage = Weightage.HIGH),
        Chapter(sNo = 10, name = "Mechanical Properties of Solids", subject = Subject.PHYSICS, category = "Properties of Matter", weightage = Weightage.MEDIUM),
        Chapter(sNo = 11, name = "Thermal Properties of Matter", subject = Subject.PHYSICS, category = "Thermal Physics", weightage = Weightage.MEDIUM),
        Chapter(sNo = 12, name = "Kinetic Theory of Thermodynamics", subject = Subject.PHYSICS, category = "Thermal Physics", weightage = Weightage.HIGH),
        Chapter(sNo = 13, name = "Mechanical Properties of Fluids", subject = Subject.PHYSICS, category = "Properties of Matter", weightage = Weightage.HIGH),
        Chapter(sNo = 14, name = "Oscillations", subject = Subject.PHYSICS, category = "Waves & SHM", weightage = Weightage.HIGH),
        Chapter(sNo = 15, name = "Waves", subject = Subject.PHYSICS, category = "Waves & SHM", weightage = Weightage.MEDIUM),
        Chapter(sNo = 16, name = "Electric Charges & Fields", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 17, name = "Electrostatic Potential & Capacitance", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 18, name = "Current Electricity", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 19, name = "Moving Charges & Magnetism", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 20, name = "Magnetism & Matter", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.MEDIUM),
        Chapter(sNo = 21, name = "Electromagnetic Induction", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 22, name = "Alternating Current", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.HIGH),
        Chapter(sNo = 23, name = "Electromagnetic Waves", subject = Subject.PHYSICS, category = "Electrodynamics", weightage = Weightage.MEDIUM),
        Chapter(sNo = 24, name = "Ray Optics & Optical Instruments", subject = Subject.PHYSICS, category = "Optics", weightage = Weightage.HIGH),
        Chapter(sNo = 25, name = "Wave Optics", subject = Subject.PHYSICS, category = "Optics", weightage = Weightage.HIGH),
        Chapter(sNo = 26, name = "Dual Nature of Radiation & Matter", subject = Subject.PHYSICS, category = "Modern Physics", weightage = Weightage.HIGH),
        Chapter(sNo = 27, name = "Atoms", subject = Subject.PHYSICS, category = "Modern Physics", weightage = Weightage.HIGH),
        Chapter(sNo = 28, name = "Nuclei", subject = Subject.PHYSICS, category = "Modern Physics", weightage = Weightage.HIGH),
        Chapter(sNo = 29, name = "Semiconductor Electronics: Materials, Devices & Simple Circuits", subject = Subject.PHYSICS, category = "Modern Physics", weightage = Weightage.HIGH)
    )

    fun getChemistryChapters(): List<Chapter> = listOf(
        Chapter(sNo = 1, name = "Some Basic Concepts of Chemistry", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 2, name = "Structure of Atom", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 3, name = "Thermodynamics & Thermochemistry", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 4, name = "Equilibrium", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 5, name = "Electrochemistry", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 6, name = "Chemical Kinetics", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 7, name = "Surface Chemistry", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.MEDIUM),
        Chapter(sNo = 8, name = "Redox Reaction", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.MEDIUM),
        Chapter(sNo = 9, name = "Solutions", subject = Subject.CHEMISTRY, category = "Physical Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 10, name = "Coordination Compounds", subject = Subject.CHEMISTRY, category = "Inorganic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 11, name = "Principles of Qualitative Analysis", subject = Subject.CHEMISTRY, category = "Inorganic Chemistry", weightage = Weightage.MEDIUM),
        Chapter(sNo = 12, name = "p-Block Elements", subject = Subject.CHEMISTRY, category = "Inorganic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 13, name = "The d- and f-Block Elements", subject = Subject.CHEMISTRY, category = "Inorganic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 14, name = "Some Basic Principles & Techniques (IUPAC Naming)", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 15, name = "Isomerism", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 16, name = "Hydrocarbons", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 17, name = "Haloalkanes & Haloarenes", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 18, name = "Alcohols, Phenols & Ethers", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 19, name = "Aldehydes, Ketones & Carboxylic Acids", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 20, name = "Amines", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 21, name = "Biomolecules", subject = Subject.CHEMISTRY, category = "Organic Chemistry", weightage = Weightage.HIGH),
        Chapter(sNo = 22, name = "Purification Methods", subject = Subject.CHEMISTRY, category = "Practical Chemistry", weightage = Weightage.LOW)
    )

    fun getMathematicsChapters(): List<Chapter> = listOf(
        Chapter(sNo = 1, name = "Sequence & Series", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 2, name = "Quadratic Equations", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 3, name = "Complex Numbers", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 4, name = "Binomial Theorem", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 5, name = "Straight Lines", subject = Subject.MATHEMATICS, category = "Coordinate Geometry", weightage = Weightage.HIGH),
        Chapter(sNo = 6, name = "Circles", subject = Subject.MATHEMATICS, category = "Coordinate Geometry", weightage = Weightage.HIGH),
        Chapter(sNo = 7, name = "Conic Sections: Parabola", subject = Subject.MATHEMATICS, category = "Coordinate Geometry", weightage = Weightage.HIGH),
        Chapter(sNo = 8, name = "Conic Sections: Hyperbola", subject = Subject.MATHEMATICS, category = "Coordinate Geometry", weightage = Weightage.HIGH),
        Chapter(sNo = 9, name = "Conic Sections: Ellipse", subject = Subject.MATHEMATICS, category = "Coordinate Geometry", weightage = Weightage.HIGH),
        Chapter(sNo = 10, name = "Complex Number (Advanced)", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.MEDIUM),
        Chapter(sNo = 11, name = "Statistics", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 12, name = "Trigonometric Functions", subject = Subject.MATHEMATICS, category = "Trigonometry", weightage = Weightage.MEDIUM),
        Chapter(sNo = 13, name = "Trigonometric Equation", subject = Subject.MATHEMATICS, category = "Trigonometry", weightage = Weightage.MEDIUM),
        Chapter(sNo = 14, name = "Solutions of Triangle", subject = Subject.MATHEMATICS, category = "Trigonometry", weightage = Weightage.LOW),
        Chapter(sNo = 15, name = "Determinants", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 16, name = "Matrices", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 17, name = "Vector Algebra", subject = Subject.MATHEMATICS, category = "Vectors & 3D", weightage = Weightage.HIGH),
        Chapter(sNo = 18, name = "Three Dimensional Geometry", subject = Subject.MATHEMATICS, category = "Vectors & 3D", weightage = Weightage.HIGH),
        Chapter(sNo = 19, name = "Sets & Relations", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH),
        Chapter(sNo = 20, name = "Functions", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 21, name = "Inverse Trigonometric Functions", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.MEDIUM),
        Chapter(sNo = 22, name = "Limits, Continuity & Differentiability", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 23, name = "Method of Differentiation", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 24, name = "Application of Derivatives", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 25, name = "Indefinite Integration", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 26, name = "Application of Integrals", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 27, name = "Differential Equation", subject = Subject.MATHEMATICS, category = "Calculus", weightage = Weightage.HIGH),
        Chapter(sNo = 28, name = "Probability", subject = Subject.MATHEMATICS, category = "Algebra", weightage = Weightage.HIGH)
    )

    fun getAllChapters(): List<Chapter> = getPhysicsChapters() + getChemistryChapters() + getMathematicsChapters()

    fun getInitialMockTests(): List<MockTest> = listOf(
        MockTest(sNo = 1, testName = "JEE Mains-1", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "11 Oct 2026"),
        MockTest(sNo = 2, testName = "JEE Mains-2", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "18 Oct 2026"),
        MockTest(sNo = 3, testName = "JEE Mains-3", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "25 Oct 2026"),
        MockTest(sNo = 4, testName = "JEE Mains-4", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "1 Nov 2026"),
        MockTest(sNo = 5, testName = "JEE Mains-5", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "22 Nov 2026"),
        MockTest(sNo = 6, testName = "JEE Mains-6", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "29 Nov 2026"),
        MockTest(sNo = 7, testName = "JEE Mains-7", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "6 Dec 2026"),
        MockTest(sNo = 8, testName = "JEE Mains-8", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "13 Dec 2026"),
        MockTest(sNo = 9, testName = "JEE Mains-9", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "20 Dec 2026"),
        MockTest(sNo = 10, testName = "JEE Mains-10", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "27 Dec 2026"),
        MockTest(sNo = 11, testName = "JEE Mains-11", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "3 Jan 2027"),
        MockTest(sNo = 12, testName = "JEE Mains-12", testType = "Part Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "10 Jan 2027"),
        MockTest(sNo = 13, testName = "AITS-1", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "13 Jan 2027"),
        MockTest(sNo = 14, testName = "AITS-2", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "15 Jan 2027"),
        MockTest(sNo = 15, testName = "AITS-3", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "20 Jan 2027"),
        MockTest(sNo = 16, testName = "AITS-4", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "24 Jan 2027"),
        MockTest(sNo = 17, testName = "AITS-5", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "31 Jan 2027"),
        MockTest(sNo = 18, testName = "AITS-6", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "7 Feb 2027"),
        MockTest(sNo = 19, testName = "AITS-7", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "14 Feb 2027"),
        MockTest(sNo = 20, testName = "AITS-8", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "21 Feb 2027"),
        MockTest(sNo = 21, testName = "AITS-9", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "28 Feb 2027"),
        MockTest(sNo = 22, testName = "AITS-10", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "7 Mar 2027"),
        MockTest(sNo = 23, testName = "AITS-11", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "14 Mar 2027"),
        MockTest(sNo = 24, testName = "AITS-12", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "21 Mar 2027"),
        MockTest(sNo = 25, testName = "AITS-13", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "28 Mar 2027"),
        MockTest(sNo = 26, testName = "AITS-14", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "4 Apr 2027"),
        MockTest(sNo = 27, testName = "AITS-15", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "11 Apr 2027"),
        MockTest(sNo = 28, testName = "AITS-16", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "18 Apr 2027"),
        MockTest(sNo = 29, testName = "AITS-17", testType = "Full Test", pattern = "JEE Main", platform = "Mission 100", scheduledDate = "25 Apr 2027"),
        MockTest(sNo = 30, testName = "AITS-18", testType = "Full Test", pattern = "JEE Advanced", platform = "Mission 100", scheduledDate = "2 May 2027"),
        MockTest(sNo = 31, testName = "AITS-19", testType = "Full Test", pattern = "JEE Advanced", platform = "Mission 100", scheduledDate = "9 May 2027"),
        MockTest(sNo = 32, testName = "AITS-20", testType = "Full Test", pattern = "JEE Advanced", platform = "Mission 100", scheduledDate = "12 May 2027")
    )
}
