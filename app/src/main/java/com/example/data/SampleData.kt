package com.example.data

import com.example.model.*

object SampleData {
    val sampleQuestions: List<Question> = listOf(
        Question(
            id = "q_neet_phy_24",
            examType = ExamType.NEET,
            subject = Subject.PHYSICS,
            section = "Section A",
            chapter = "Electromagnetic Waves & Wave Optics",
            topic = "Wavelength and Permittivity in Dielectric",
            questionNumber = 24,
            questionText = "An electromagnetic wave of frequency ν = 3.0 MHz passes from vacuum into a dielectric medium with relative permittivity εᵣ = 4.0 and relative permeability μᵣ = 1.0. Then its wavelength in the medium is:",
            formulaHighlight = "ν = 3.0 MHz, εᵣ = 4.0, μᵣ = 1.0",
            conceptHint = "Wave Optics & EM Waves: Refractive index relation n = √(εᵣμᵣ) applies.",
            optionA = "Doubled and frequency remains unchanged",
            optionB = "Halved and frequency remains unchanged",
            optionC = "Doubled and frequency becomes half",
            optionD = "Remains unchanged",
            correctAnswer = 1, // B
            explanation = "The refractive index n is given by n = √(εᵣ·μᵣ) = √(4.0 × 1.0) = 2.0. Frequency ν remains unchanged when passing into a medium. The speed of the wave becomes v = c/n = c/2. Since λ = v/ν, the wavelength becomes λ' = λ/n = λ/2 (Halved).",
            ncertReference = "NCERT Physics Part-II: Ch. 8 (EM Waves), Pg 274",
            marks = 4.0,
            negativeMarks = 1.0,
            difficulty = Difficulty.MEDIUM,
            isBookmarked = true
        ),
        Question(
            id = "q_neet_phy_01",
            examType = ExamType.NEET,
            subject = Subject.PHYSICS,
            section = "Section A",
            chapter = "Electrostatics & Gauss Law",
            topic = "Electric Flux Through Gaussian Surfaces",
            questionNumber = 1,
            questionText = "A point charge +Q is placed at the center of an open cylindrical tube of radius R and length L. What is the total electric flux passing through the curved surface of the cylinder if length L >> R?",
            formulaHighlight = "∮E·dA = Q/ε₀",
            conceptHint = "Electrostatics: Apply Gauss's law with symmetry considerations for closed surfaces.",
            optionA = "Q / ε₀",
            optionB = "Q / (2ε₀)",
            optionC = "Q / (4ε₀)",
            optionD = "Zero",
            correctAnswer = 0, // A
            explanation = "When L >> R, the solid angles subtended by the two circular openings at the center approach zero. Therefore, almost all the field lines emanating from +Q pierce the curved cylindrical surface, giving total flux ∮E·dA = Q/ε₀.",
            ncertReference = "NCERT Physics Part-I: Ch. 1, Pg 32",
            marks = 4.0,
            negativeMarks = 1.0,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = "q_neet_chem_01",
            examType = ExamType.NEET,
            subject = Subject.CHEMISTRY,
            section = "Section A",
            chapter = "Coordination Compounds",
            topic = "Crystal Field Splitting & Magnetic Moment",
            questionNumber = 36,
            questionText = "Which among the following octahedral complex ions will exhibit the highest value of spin-only magnetic moment?",
            formulaHighlight = "μ = √(n(n+2)) BM",
            conceptHint = "Coordination Chemistry: Count unpaired d-electrons taking spectrochemical series into account.",
            optionA = "[Co(CN)₆]³⁻",
            optionB = "[Fe(H₂O)₆]²⁺",
            optionC = "[Mn(CN)₆]³⁻",
            optionD = "[FeF₆]³⁻",
            correctAnswer = 3, // D
            explanation = "In [FeF₆]³⁻, Fe is in +3 oxidation state (3d⁵). Fluoride is a weak field ligand, resulting in high spin state with 5 unpaired electrons (t₂g³ eg²). The spin-only magnetic moment μ = √(5(7)) = √35 ≈ 5.92 BM, which is maximum.",
            ncertReference = "NCERT Chemistry Part-I: Ch. 9, Pg 252",
            marks = 4.0,
            negativeMarks = 1.0,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = "q_neet_bio_01",
            examType = ExamType.NEET,
            subject = Subject.BIOLOGY,
            section = "Section A",
            chapter = "Molecular Basis of Inheritance",
            topic = "DNA Replication & Semiconservative Proof",
            questionNumber = 71,
            questionText = "In the Meselson and Stahl experiment, E. coli cells grown in ¹⁵N medium were transferred to ¹⁴N medium. What percentage of hybrid DNA molecules will be present after 3 generations of replication?",
            formulaHighlight = "Generation 1 = 100% hybrid, Gen 2 = 50%, Gen 3 = 25%",
            conceptHint = "Genetics: Semiconservative replication produces 2 hybrid strands in each subsequent cycle.",
            optionA = "12.5%",
            optionB = "25%",
            optionC = "50%",
            optionD = "75%",
            correctAnswer = 1, // B
            explanation = "After 3 generations, 2³ = 8 DNA molecules (16 strands) are formed. Only 2 molecules contain the original ¹⁵N strands paired with ¹⁴N (hybrids). Hence, hybrid fraction = 2/8 = 25%.",
            ncertReference = "NCERT Biology: Ch. 6 (Class 12), Pg 105",
            marks = 4.0,
            negativeMarks = 1.0,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = "q_neet_bio_02",
            examType = ExamType.NEET,
            subject = Subject.BIOLOGY,
            section = "Section A",
            chapter = "Plant Physiology & Photosynthesis",
            topic = "C4 Pathway & Kranz Anatomy",
            questionNumber = 72,
            questionText = "Primary carboxylation in C₄ plants takes place in the:",
            formulaHighlight = "PEP + CO₂ → OAA via PEP carboxylase",
            conceptHint = "Plant Physiology: Spatial separation between mesophyll and bundle sheath cells.",
            optionA = "Mesophyll cells catalyzed by PEP carboxylase",
            optionB = "Bundle sheath cells catalyzed by RuBisCO",
            optionC = "Mesophyll cells catalyzed by RuBisCO",
            optionD = "Bundle sheath cells catalyzed by PEP carboxylase",
            correctAnswer = 0, // A
            explanation = "Primary fixation of atmospheric CO₂ in C₄ plants occurs in mesophyll cells where Phosphoenolpyruvate (PEP) combines with HCO₃⁻ catalyzed by PEP carboxylase to form oxaloacetic acid (OAA).",
            ncertReference = "NCERT Biology: Ch. 13 (Class 11), Pg 218",
            marks = 4.0,
            negativeMarks = 1.0,
            difficulty = Difficulty.EASY
        )
    )

    val sampleChapters: List<ChapterProgress> = listOf(
        ChapterProgress(
            chapterName = "Kinematics & Motion in 1D/2D",
            subject = Subject.PHYSICS,
            totalQuestions = 120,
            attemptedQuestions = 75,
            accuracy = 78,
            completionPercentage = 62
        ),
        ChapterProgress(
            chapterName = "Electrostatics & Gauss Law",
            subject = Subject.PHYSICS,
            totalQuestions = 95,
            attemptedQuestions = 50,
            accuracy = 55,
            completionPercentage = 52
        ),
        ChapterProgress(
            chapterName = "Rotational Motion & Inertia",
            subject = Subject.PHYSICS,
            totalQuestions = 110,
            attemptedQuestions = 60,
            accuracy = 48,
            completionPercentage = 54
        ),
        ChapterProgress(
            chapterName = "Coordination Compounds",
            subject = Subject.CHEMISTRY,
            totalQuestions = 85,
            attemptedQuestions = 70,
            accuracy = 94,
            completionPercentage = 82
        ),
        ChapterProgress(
            chapterName = "Organic Reactions & Mechanisms",
            subject = Subject.CHEMISTRY,
            totalQuestions = 150,
            attemptedQuestions = 90,
            accuracy = 52,
            completionPercentage = 60
        ),
        ChapterProgress(
            chapterName = "Molecular Basis of Inheritance",
            subject = Subject.BIOLOGY,
            totalQuestions = 140,
            attemptedQuestions = 135,
            accuracy = 96,
            completionPercentage = 96
        ),
        ChapterProgress(
            chapterName = "Plant Physiology & Photosynthesis",
            subject = Subject.BIOLOGY,
            totalQuestions = 130,
            attemptedQuestions = 120,
            accuracy = 92,
            completionPercentage = 92
        )
    )
}
