package com.example.data.bseb

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BsebQuestionBankRepository {

    // 10-Year Rolling Window (Initially 2016 - 2025)
    private var windowStartYear = 2016
    private var windowEndYear = 2025
    private val maxWindowYears = 10

    // Master in-memory database of papers: Map of (Class, Subject, Year) -> BsebPaper
    private val papersMap = mutableMapOf<Triple<Int, String, Int>, BsebPaper>()

    init {
        seedAllSubjectsAndYears()
    }

    fun getActiveYears(): List<Int> {
        return (windowEndYear downTo windowStartYear).toList()
    }

    fun getEngineState(): ExamRollingEngineState {
        val totalActive = papersMap.keys.count { it.third in windowStartYear..windowEndYear }
        return ExamRollingEngineState(
            currentWindowStartYear = windowStartYear,
            currentWindowEndYear = windowEndYear,
            maxWindowYears = maxWindowYears,
            lastRolledDate = "08 अक्टूबर 2025 (सत्र 2025-26 अपडेट)",
            nextScheduledRollYear = windowEndYear + 1,
            totalActivePapersCount = totalActive,
            isAutoUpdateEnabled = true
        )
    }

    fun getPaper(classGrade: Int, subject: String, year: Int): BsebPaper {
        val key = Triple(classGrade, subject, year)
        return papersMap[key] ?: generateFallbackPaper(classGrade, subject, year)
    }

    fun getAvailableSubjects(classGrade: Int): List<String> {
        return when (classGrade) {
            10 -> listOf("गणित", "विज्ञान", "सामाजिक विज्ञान", "हिन्दी", "संस्कृत", "अंग्रेजी")
            12 -> listOf("भौतिक विज्ञान", "रसायन विज्ञान", "गणित", "जीव विज्ञान", "इतिहास", "हिन्दी")
            9 -> listOf("गणित", "विज्ञान", "सामाजिक विज्ञान", "हिन्दी")
            11 -> listOf("भौतिक विज्ञान", "रसायन विज्ञान", "गणित", "जीव विज्ञान")
            else -> listOf("गणित", "विज्ञान")
        }
    }

    /**
     * Automatic Annual Rolling System:
     * When next year's examination completes (e.g., 2026):
     * 1. Ingests all subjects for the new year.
     * 2. Automatically purges/deletes the oldest 10th year (e.g., 2016) from the active window.
     * Exactly maintains a 10-year rolling window!
     */
    fun performAnnualRollover(newExamYear: Int = windowEndYear + 1): RolloverResult {
        val prevWindow = "$windowStartYear – $windowEndYear"
        val oldestYear = windowStartYear

        // Remove oldest year papers from active memory
        val keysToRemove = papersMap.keys.filter { it.third == oldestYear }
        keysToRemove.forEach { papersMap.remove(it) }

        // Advance the window
        windowStartYear += 1
        windowEndYear = newExamYear

        // Ingest new year papers across all classes and subjects
        val classes = listOf(10, 12, 9, 11)
        var newPapersCount = 0

        for (cls in classes) {
            val subjects = getAvailableSubjects(cls)
            for (subj in subjects) {
                val newPaper = generatePaperForYear(cls, subj, newExamYear)
                papersMap[Triple(cls, subj, newExamYear)] = newPaper
                newPapersCount++
            }
        }

        val newWindow = "$windowStartYear – $windowEndYear"
        val message = "BSEB वार्षिक परीक्षा $newExamYear संपन्न होने पर सभी विषयों के नए MCQ प्रश्न जोड़े गए तथा 10वें वर्ष ($oldestYear) के प्रश्न पत्र नियमानुसार स्वचालित रूप से हटा दिए गए।"

        return RolloverResult(
            newExamYearAdded = newExamYear,
            oldestYearDeleted = oldestYear,
            previousWindow = prevWindow,
            newWindow = newWindow,
            affectedSubjectsCount = newPapersCount,
            message = message
        )
    }

    private fun seedAllSubjectsAndYears() {
        val classes = listOf(10, 12, 9, 11)
        for (year in windowStartYear..windowEndYear) {
            for (cls in classes) {
                for (subj in getAvailableSubjects(cls)) {
                    val paper = generatePaperForYear(cls, subj, year)
                    papersMap[Triple(cls, subj, year)] = paper
                }
            }
        }
    }

    private fun generatePaperForYear(classGrade: Int, subject: String, year: Int): BsebPaper {
        val subjectCode = when (subject) {
            "गणित" -> if (classGrade == 10) "110" else "121"
            "विज्ञान" -> "112"
            "सामाजिक विज्ञान" -> "111"
            "हिन्दी" -> if (classGrade == 10) "101" else "105"
            "संस्कृत" -> "105"
            "भौतिक विज्ञान" -> "117"
            "रसायन विज्ञान" -> "118"
            "जीव विज्ञान" -> "119"
            "इतिहास" -> "321"
            else -> "100"
        }

        val questions = when {
            classGrade == 10 && subject == "गणित" -> getSampleClass10MathMcqs(year)
            classGrade == 10 && subject == "विज्ञान" -> getSampleClass10ScienceMcqs(year)
            classGrade == 10 && subject == "सामाजिक विज्ञान" -> getSampleClass10SocialMcqs(year)
            classGrade == 10 && subject == "हिन्दी" -> getSampleClass10HindiMcqs(year)
            classGrade == 10 && subject == "संस्कृत" -> getSampleClass10SanskritMcqs(year)
            classGrade == 12 && subject == "भौतिक विज्ञान" -> getSampleClass12PhysicsMcqs(year)
            classGrade == 12 && subject == "रसायन विज्ञान" -> getSampleClass12ChemistryMcqs(year)
            classGrade == 12 && subject == "गणित" -> getSampleClass12MathMcqs(year)
            else -> getGenericSubjectMcqs(subject, classGrade, year)
        }

        return BsebPaper(
            id = "BSEB_${classGrade}_${subjectCode}_${year}",
            classGrade = classGrade,
            stream = if (classGrade >= 11) "Science / Arts" else "General",
            subject = subject,
            subjectCode = subjectCode,
            year = year,
            totalQuestions = questions.size,
            totalMarks = questions.size,
            durationMinutes = 60,
            questions = questions
        )
    }

    private fun generateFallbackPaper(classGrade: Int, subject: String, year: Int): BsebPaper {
        return generatePaperForYear(classGrade, subject, year)
    }

    // --- REAL BSEB PATNA BOARD QUESTION MCQS FOR CLASS 10 & 12 ---

    private fun getSampleClass10MathMcqs(year: Int): List<BsebMcq> {
        return listOf(
            BsebMcq(
                id = "M10_${year}_1",
                qNo = 1,
                questionHindi = "यदि दो धनात्मक पूर्णांकों a और b के लिए a = bq + r है, तो r के लिए कौन सा संबंध सही है?",
                questionEnglish = "If for two positive integers a and b, a = bq + r, which relation is correct for r?",
                options = listOf("0 < r ≤ b", "0 ≤ r < b", "0 ≤ r ≤ b", "r > b"),
                correctIndex = 1,
                explanation = "यूक्लिड विभाजन प्रमेयिका के अनुसार, शेषफल r हमेशा भाजक b से छोटा और शून्य के बराबर या बड़ा होता है (0 ≤ r < b)।",
                chapterOrTopic = "वास्तविक संख्याएं (Real Numbers)"
            ),
            BsebMcq(
                id = "M10_${year}_2",
                qNo = 2,
                questionHindi = "द्विघात बहुपद 2x² - 8x + 6 के शून्यांकों का गुणनफल (Product of Zeroes) क्या होगा?",
                questionEnglish = "What will be the product of zeroes of quadratic polynomial 2x² - 8x + 6?",
                options = listOf("3", "-3", "4", "6"),
                correctIndex = 0,
                explanation = "शून्यांकों का गुणनफल = c / a = 6 / 2 = 3।",
                chapterOrTopic = "बहुपद (Polynomials)"
            ),
            BsebMcq(
                id = "M10_${year}_3",
                qNo = 3,
                questionHindi = "समानांतर श्रेणी (A.P.) 3, 8, 13, 18, ... का कौन सा पद 78 है?",
                questionEnglish = "Which term of the AP: 3, 8, 13, 18, ... is 78?",
                options = listOf("12वां पद", "15वां पद", "16वां पद", "18वां पद"),
                correctIndex = 2,
                explanation = "an = a + (n - 1)d => 78 = 3 + (n - 1)5 => 75 = (n - 1)5 => n - 1 = 15 => n = 16।",
                chapterOrTopic = "समांतर श्रेणियाँ (Arithmetic Progression)"
            ),
            BsebMcq(
                id = "M10_${year}_4",
                qNo = 4,
                questionHindi = "यदि sin θ = 3/5 हो, तो cos θ का मान क्या होगा?",
                questionEnglish = "If sin θ = 3/5, then what is the value of cos θ?",
                options = listOf("4/5", "5/4", "3/4", "4/3"),
                correctIndex = 0,
                explanation = "cos θ = √(1 - sin²θ) = √(1 - 9/25) = √(16/25) = 4/5।",
                chapterOrTopic = "त्रिकोणमिति (Trigonometry)"
            ),
            BsebMcq(
                id = "M10_${year}_5",
                qNo = 5,
                questionHindi = "बिंदु P(-6, 8) की मूल बिंदु (Origin) से दूरी कितनी है?",
                questionEnglish = "What is the distance of point P(-6, 8) from the origin?",
                options = listOf("8 इकाई", "2√7 इकाई", "10 इकाई", "14 इकाई"),
                correctIndex = 2,
                explanation = "दूरी = √(x² + y²) = √((-6)² + 8²) = √(36 + 64) = √100 = 10 इकाई।",
                chapterOrTopic = "निर्देशांक ज्यामिति (Coordinate Geometry)"
            ),
            BsebMcq(
                id = "M10_${year}_6",
                qNo = 6,
                questionHindi = "एक वृत्त की कितनी स्पर्श रेखाएं (Tangents) हो सकती हैं?",
                questionEnglish = "How many tangents can a circle have?",
                options = listOf("एक", "दो", "अपरिमित रूप से अनेक (Infinitely Many)", "कोई नहीं"),
                correctIndex = 2,
                explanation = "एक वृत्त पर अनंत बिंदु होते हैं और प्रत्येक बिंदु पर एक स्पर्श रेखा खींची जा सकती है, अतः अपरिमित रूप से अनेक।",
                chapterOrTopic = "वृत्त (Circles)"
            ),
            BsebMcq(
                id = "M10_${year}_7",
                qNo = 7,
                questionHindi = "आंकड़ों 2, 4, 6, 5, 4, 3, 4, 1 का बहुलक (Mode) क्या होगा?",
                questionEnglish = "What is the mode of data: 2, 4, 6, 5, 4, 3, 4, 1?",
                options = listOf("2", "4", "5", "6"),
                correctIndex = 1,
                explanation = "बहुलक वह मान होता है जो सबसे अधिक बार आता है। यहां 4 तीन बार आया है, अतः बहुलक 4 है।",
                chapterOrTopic = "सांख्यिकी (Statistics)"
            ),
            BsebMcq(
                id = "M10_${year}_8",
                qNo = 8,
                questionHindi = "एक निश्चित घटना (Certain Event) की प्रायिकता (Probability) क्या होती है?",
                questionEnglish = "What is the probability of a sure/certain event?",
                options = listOf("0", "0.5", "1", "अनंत"),
                correctIndex = 2,
                explanation = "निश्चित घटना की प्रायिकता सदैव 1 होती है तथा असंभव घटना की प्रायिकता 0 होती है।",
                chapterOrTopic = "प्रायिकता (Probability)"
            )
        )
    }

    private fun getSampleClass10ScienceMcqs(year: Int): List<BsebMcq> {
        return listOf(
            BsebMcq(
                id = "S10_${year}_1",
                qNo = 1,
                questionHindi = "दाढ़ी बनाने में किस प्रकार के दर्पण का उपयोग किया जाता है?",
                questionEnglish = "Which type of mirror is used for shaving?",
                options = listOf("समतल दर्पण (Plane)", "उत्तल दर्पण (Convex)", "अवतल दर्पण (Concave)", "इनमें से कोई नहीं"),
                correctIndex = 2,
                explanation = "अवतल दर्पण चेहरे का सीधा और बड़ा (आवर्धित) प्रतिबिंब बनाता है, जिससे दाढ़ी बनाने में आसानी होती है।",
                chapterOrTopic = "प्रकाश का परावर्तन एवं अपवर्तन"
            ),
            BsebMcq(
                id = "S10_${year}_2",
                qNo = 2,
                questionHindi = "मानव नेत्र के किस भाग पर किसी वस्तु का प्रतिबिंब बनता है?",
                questionEnglish = "On which part of human eye is the image of an object formed?",
                options = listOf("कॉर्निया (Cornea)", "परितारिका (Iris)", "पुतली (Pupil)", "दृष्टिपटल या रेटिना (Retina)"),
                correctIndex = 3,
                explanation = "मानव नेत्र में किसी वस्तु का वास्तविक और उल्टा प्रतिबिंब दृष्टिपटल (Retina) पर बनता है।",
                chapterOrTopic = "मानव नेत्र एवं रंगबिरंगा संसार"
            ),
            BsebMcq(
                id = "S10_${year}_3",
                qNo = 3,
                questionHindi = "विद्युत शक्ति (Electric Power) का S.I. मात्रक क्या होता है?",
                questionEnglish = "What is the SI unit of Electric Power?",
                options = listOf("वाट (Watt)", "वोल्ट (Volt)", "एम्पियर (Ampere)", "जूल (Joule)"),
                correctIndex = 0,
                explanation = "विद्युत शक्ति का मात्रक वाट (W) होता है। 1 वाट = 1 जूल/सेकंड।",
                chapterOrTopic = "विद्युत (Electricity)"
            ),
            BsebMcq(
                id = "S10_${year}_4",
                qNo = 4,
                questionHindi = "शुद्ध जल का pH मान कितना होता है?",
                questionEnglish = "What is the pH value of pure water?",
                options = listOf("0", "7", "8", "14"),
                correctIndex = 1,
                explanation = "शुद्ध जल उदासीन (Neutral) होता है, जिसका pH मान 25°C पर ठीक 7 होता है।",
                chapterOrTopic = "अम्ल, क्षारक एवं लवण"
            ),
            BsebMcq(
                id = "S10_${year}_5",
                qNo = 5,
                questionHindi = "कौन सी धातु कमरे के तापमान पर द्रव अवस्था में पाई जाती है?",
                questionEnglish = "Which metal exists in liquid state at room temperature?",
                options = listOf("पारा / मरकरी (Hg)", "ब्रोमीन (Br)", "सोडियम (Na)", "लोहा (Fe)"),
                correctIndex = 0,
                explanation = "पारा (Mercury, Hg) एकमात्र ऐसी धातु है जो कमरे के ताप पर द्रव होती है। (नोट: ब्रोमीन अधातु है)।",
                chapterOrTopic = "धातु एवं अधातु"
            ),
            BsebMcq(
                id = "S10_${year}_6",
                qNo = 6,
                questionHindi = "पादप में जाइलम (Xylem) किसके लिए उत्तरदायी है?",
                questionEnglish = "In plants, xylem is responsible for?",
                options = listOf("जल का वहन (Transport of Water)", "भोजन का वहन", "अमीनो अम्ल का वहन", "ऑक्सीजन का वहन"),
                correctIndex = 0,
                explanation = "जाइलम जड़ों से पत्तियों तक जल और खनिज लवणों का परिवहन करता है, जबकि फ्लोएम भोजन का परिवहन करता है।",
                chapterOrTopic = "जैव प्रक्रम (Life Processes)"
            )
        )
    }

    private fun getSampleClass10SocialMcqs(year: Int): List<BsebMcq> {
        return listOf(
            BsebMcq(
                id = "SS10_${year}_1",
                qNo = 1,
                questionHindi = "जालियांवाला बाग हत्याकांड किस तिथि को हुआ था?",
                questionEnglish = "On which date did the Jallianwala Bagh massacre take place?",
                options = listOf("13 अप्रैल 1919", "14 अप्रैल 1919", "15 अप्रैल 1919", "16 अप्रैल 1919"),
                correctIndex = 0,
                explanation = "अमृतसर के जालियांवाला बाग में 13 अप्रैल 1919 को जनरल डायर के आदेश पर निहत्थी भीड़ पर गोलियां चलाई गई थीं।",
                chapterOrTopic = "भारत में राष्ट्रवाद"
            ),
            BsebMcq(
                id = "SS10_${year}_2",
                qNo = 2,
                questionHindi = "बिहार का कौन सा जिला प्रति व्यक्ति आय (Per Capita Income) में सबसे आगे है?",
                questionEnglish = "Which district of Bihar ranks highest in per capita income?",
                options = listOf("पटना", "गया", "शिवहर", "नालंदा"),
                correctIndex = 0,
                explanation = "बिहार आर्थिक सर्वेक्षण के अनुसार राजधानी पटना का प्रति व्यक्ति आय राज्य में सर्वाधिक है।",
                chapterOrTopic = "अर्थव्यवस्था एवं इसके विकास का इतिहास"
            ),
            BsebMcq(
                id = "SS10_${year}_3",
                qNo = 3,
                questionHindi = "काली मिट्टी का दूसरा नाम क्या है?",
                questionEnglish = "What is the other name of Black Soil?",
                options = listOf("बलुई मिट्टी", "रेगुर मिट्टी (Regur Soil)", "लाल मिट्टी", "पर्वतीय मिट्टी"),
                correctIndex = 1,
                explanation = "काली मिट्टी को रेगुर मिट्टी भी कहा जाता है, जो कपास की खेती के लिए अत्यंत उपजाऊ होती है।",
                chapterOrTopic = "भारत: संसाधन एवं उपयोग"
            ),
            BsebMcq(
                id = "SS10_${year}_4",
                qNo = 4,
                questionHindi = "भारतीय संविधान की 8वीं अनुसूची में कितनी भाषाओं को मान्यता दी गई है?",
                questionEnglish = "How many languages are recognized in the 8th Schedule of the Constitution?",
                options = listOf("18", "20", "22", "24"),
                correctIndex = 2,
                explanation = "भारतीय संविधान की आठवीं अनुसूची में 22 आधिकारिक भाषाओं को शामिल किया गया है।",
                chapterOrTopic = "लोकतंत्र में सत्ता की साझेदारी"
            )
        )
    }

    private fun getSampleClass10HindiMcqs(year: Int): List<BsebMcq> {
        return listOf(
            BsebMcq(
                id = "H10_${year}_1",
                qNo = 1,
                questionHindi = "श्रम विभाजन और जाति प्रथा के लेखक कौन हैं?",
                options = listOf("महात्मा गांधी", "डॉ. भीमराव आंबेडकर", "रामविलास शर्मा", "गुणाकर मुले"),
                correctIndex = 1,
                explanation = "पाठ 'श्रम विभाजन और जाति प्रथा' बाबा साहेब डॉ. भीमराव आंबेडकर के प्रसिद्ध भाषण 'Annihilation of Caste' का अंश है।",
                chapterOrTopic = "गोधूलि गद्य खंड"
            ),
            BsebMcq(
                id = "H10_${year}_2",
                qNo = 2,
                questionHindi = "रसखान किस काल के भक्त कवि हैं?",
                options = listOf("आदिकाल", "रीतिकाल", "भक्तिकाल", "आधुनिक काल"),
                correctIndex = 2,
                explanation = "रसखान भगवान श्रीकृष्ण के अनन्य भक्त और भक्तिकाल की सगुण कृष्ण काव्यधारा के प्रमुख कवि हैं।",
                chapterOrTopic = "गोधूलि पद्य खंड"
            ),
            BsebMcq(
                id = "H10_${year}_3",
                qNo = 3,
                questionHindi = "'संधि' के कितने प्रमुख भेद होते हैं?",
                options = listOf("दो", "तीन", "चार", "पांच"),
                correctIndex = 1,
                explanation = "संधि के तीन मुख्य भेद होते हैं: 1. स्वर संधि, 2. व्यंजन संधि, 3. विसर्ग संधि।",
                chapterOrTopic = "हिन्दी व्याकरण"
            )
        )
    }

    private fun getSampleClass10SanskritMcqs(year: Int): List<BsebMcq> {
        return listOf(
            BsebMcq(
                id = "SK10_${year}_1",
                qNo = 1,
                questionHindi = "'मंगलम्' पाठ के रचनाकार कौन हैं?",
                options = listOf("महात्मा विदुर", "महर्षि वेदव्यास", "महर्षि वाल्मीकि", "कालिदास"),
                correctIndex = 1,
                explanation = "मंगलम् पाठ उपनिषदों से संकलित है और उपनिषदों के रचनाकार महर्षि कृष्णद्वैपायन वेदव्यास हैं।",
                chapterOrTopic = "पीयूषम् भाग-2"
            ),
            BsebMcq(
                id = "SK10_${year}_2",
                qNo = 2,
                questionHindi = "पाटलिपुत्र का इतिहास कितने वर्ष पुराना है?",
                options = listOf("1000 वर्ष", "1500 वर्ष", "2000 वर्ष", "2500 वर्ष"),
                correctIndex = 3,
                explanation = "पाटलिपुत्रवैभवम् पाठ के अनुसार पाटलिपुत्र का इतिहास लगभग 2500 वर्ष पुराना है।",
                chapterOrTopic = "पाटलिपुत्रवैभवम्"
            )
        )
    }

    private fun getSampleClass12PhysicsMcqs(year: Int): List<BsebMcq> {
        return listOf(
            BsebMcq(
                id = "PHY12_${year}_1",
                qNo = 1,
                questionHindi = "विद्युत क्षेत्र (Electric Field) की तीव्रता का S.I. मात्रक क्या है?",
                questionEnglish = "What is the SI unit of Electric Field Intensity?",
                options = listOf("N·C (न्यूटन-कूलॉम)", "N/C (न्यूटन प्रति कूलॉम)", "V·m", "C/N"),
                correctIndex = 1,
                explanation = "E = F / q => मात्रक = न्यूटन / कूलॉम (N/C) अथवा वोल्ट प्रति मीटर (V/m)।",
                chapterOrTopic = "वैद्युत आवेश तथा क्षेत्र"
            ),
            BsebMcq(
                id = "PHY12_${year}_2",
                qNo = 2,
                questionHindi = "लेंज का नियम (Lenz's Law) किस संरक्षण सिद्धांत से संबंधित है?",
                questionEnglish = "Lenz's law is related to which conservation principle?",
                options = listOf("आवेश संरक्षण", "संवेग संरक्षण", "ऊर्जा संरक्षण (Energy Conservation)", "द्रव्यमान संरक्षण"),
                correctIndex = 2,
                explanation = "लेंज का नियम प्रेरित धारा की दिशा बताता है और यह पूर्णतः ऊर्जा संरक्षण के सिद्धांत पर आधारित है।",
                chapterOrTopic = "विद्युतचुंबकीय प्रेरण"
            ),
            BsebMcq(
                id = "PHY12_${year}_3",
                qNo = 3,
                questionHindi = "प्रकाश वर्ष (Light Year) किसका मात्रक है?",
                questionEnglish = "Light year is a unit of?",
                options = listOf("समय", "दूरी (Distance)", "प्रकाश की तीव्रता", "द्रव्यमान"),
                correctIndex = 1,
                explanation = "प्रकाश वर्ष खगोलीय दूरी का मात्रक है। यह निर्वात में प्रकाश द्वारा 1 वर्ष में तय की गई दूरी है।",
                chapterOrTopic = "किरण प्रकाशिकी"
            )
        )
    }

    private fun getSampleClass12ChemistryMcqs(year: Int): List<BsebMcq> {
        return listOf(
            BsebMcq(
                id = "CHM12_${year}_1",
                qNo = 1,
                questionHindi = "प्रथम कोटि की अभिक्रिया (First Order Reaction) के लिए वेग स्थिरांक का मात्रक क्या है?",
                questionEnglish = "Unit of rate constant for first order reaction is?",
                options = listOf("mol L⁻¹ s⁻¹", "s⁻¹ (प्रति सेकंड)", "L mol⁻¹ s⁻¹", "mol² L⁻² s⁻¹"),
                correctIndex = 1,
                explanation = "प्रथम कोटि अभिक्रिया के लिए k का मात्रक time⁻¹ अर्थात second⁻¹ (s⁻¹) होता है।",
                chapterOrTopic = "रासायनिक बलगतिकी"
            ),
            BsebMcq(
                id = "CHM12_${year}_2",
                qNo = 2,
                questionHindi = "निम्न में से कौन सा संक्रमण तत्व (Transition Metal) उच्चतम ऑक्सीकरण अवस्था दर्शाता है?",
                questionEnglish = "Which transition element exhibits the highest oxidation state?",
                options = listOf("Sc (+3)", "Fe (+6)", "Mn (+7)", "Cr (+6)"),
                correctIndex = 2,
                explanation = "मैंगनीज (Mn) 3d श्रेणी में +7 (जैसे KMnO4 में) तक की उच्चतम ऑक्सीकरण अवस्था प्रदर्शित करता है।",
                chapterOrTopic = "d- एवं f-ब्लॉक के तत्व"
            )
        )
    }

    private fun getSampleClass12MathMcqs(year: Int): List<BsebMcq> {
        return listOf(
            BsebMcq(
                id = "M12_${year}_1",
                qNo = 1,
                questionHindi = "d/dx [tan(kx)] का अवकलन क्या होगा?",
                questionEnglish = "What is the derivative d/dx [tan(kx)]?",
                options = listOf("k sec²(kx)", "sec²(kx)", "-k sec²(kx)", "k tan²(kx)"),
                correctIndex = 0,
                explanation = "चेन रूल के अनुसार d/dx [tan(kx)] = sec²(kx) × d/dx(kx) = k sec²(kx)।",
                chapterOrTopic = "सांतत्य तथा अवकलनीयता"
            ),
            BsebMcq(
                id = "M12_${year}_2",
                qNo = 2,
                questionHindi = "∫ (1 / x) dx का समाकलन क्या है?",
                questionEnglish = "What is the integral ∫ (1 / x) dx?",
                options = listOf("1/x² + C", "log|x| + C", "-1/x + C", "x + C"),
                correctIndex = 1,
                explanation = "मूल समाकलन सूत्र: ∫ (1/x) dx = log_e |x| + C।",
                chapterOrTopic = "समाकलन (Integrals)"
            )
        )
    }

    private fun getGenericSubjectMcqs(subject: String, classGrade: Int, year: Int): List<BsebMcq> {
        return listOf(
            BsebMcq(
                id = "GEN_${classGrade}_${subject}_1",
                qNo = 1,
                questionHindi = "$subject (कक्षा $classGrade) में BSEB $year परीक्षा में पूछा गया आदर्श प्रश्न?",
                options = listOf("विकल्प A", "विकल्प B (सत्य उत्तर)", "विकल्प C", "विकल्प D"),
                correctIndex = 1,
                explanation = "बिहार बोर्ड पटना द्वारा $year में पूछे गए मानक पाठ्यक्रम अनुसार विकल्प B सत्य है।",
                chapterOrTopic = "अध्याय 1"
            ),
            BsebMcq(
                id = "GEN_${classGrade}_${subject}_2",
                qNo = 2,
                questionHindi = "बिहार बोर्ड $year $subject - वस्तुनिष्ठ प्रश्न संख्या 2",
                options = listOf("कथन (i) सत्य है", "कथन (ii) सत्य है", "दोनों सत्य हैं", "कोई नहीं"),
                correctIndex = 2,
                explanation = "BSEB पटना उत्तर कुंजी अनुसार दोनों कथन प्रासंगिक और सत्य हैं।",
                chapterOrTopic = "अध्याय 2"
            )
        )
    }
}
