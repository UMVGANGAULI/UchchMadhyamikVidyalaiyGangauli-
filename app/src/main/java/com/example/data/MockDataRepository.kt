package com.example.data

object MockDataRepository {

    val demoStudents = listOf(
        Student(
            id = "STU_10014",
            rollNo = 14,
            name = "अमित कुमार (Amit Kumar)",
            studentClass = 10,
            section = "A",
            stream = "General",
            mobile = "9876543210",
            fatherName = "श्री राधेश्याम सिंह",
            motherName = "श्रीमती संजू देवी",
            dob = "15/07/2009",
            bloodGroup = "O+",
            attendancePercentage = 93.5f,
            scholarshipStatus = "स्वीकृत (DBT ₹10,000 खाते में अंतरित)",
            scholarshipScheme = "मुख्यमंत्री बालक प्रोत्साहन योजना 2025-26",
            udiseStudentId = "10301901805014"
        ),
        Student(
            id = "STU_10001",
            rollNo = 1,
            name = "प्रिया कुमारी (Priya Kumari)",
            studentClass = 10,
            section = "A",
            stream = "General",
            mobile = "9876543201",
            fatherName = "श्री मनोज कुमार",
            motherName = "श्रीमती रीता देवी",
            dob = "10/03/2009",
            bloodGroup = "B+",
            attendancePercentage = 96.0f,
            scholarshipStatus = "स्वीकृत (साइकिल योजना एवं पोशाक राशि)",
            scholarshipScheme = "मुख्यमंत्री बालिका प्रोत्साहन योजना",
            udiseStudentId = "10301901805001"
        ),
        Student(
            id = "STU_12005",
            rollNo = 5,
            name = "रोहित कुमार ओझा (Rohit Ojha)",
            studentClass = 12,
            section = "A",
            stream = "Science",
            mobile = "9876543205",
            fatherName = "श्री उपेंद्र ओझा",
            motherName = "श्रीमती मीना ओझा",
            dob = "22/11/2007",
            bloodGroup = "AB+",
            attendancePercentage = 89.2f,
            scholarshipStatus = "सत्यापित (विद्यालय स्तर से अग्रसारित)",
            scholarshipScheme = "बिहार पोस्ट-मैट्रिक छात्रवृत्ति",
            udiseStudentId = "10301901805005"
        ),
        Student(
            id = "STU_12012",
            rollNo = 12,
            name = "खुशी कुमारी (Khushi Kumari)",
            studentClass = 12,
            section = "B",
            stream = "Arts",
            mobile = "9876543212",
            fatherName = "श्री सत्येंद्र चौबे",
            motherName = "श्रीमती किरण देवी",
            dob = "05/09/2007",
            bloodGroup = "A+",
            attendancePercentage = 94.8f,
            scholarshipStatus = "स्वीकृत (DBT स्वीकृत)",
            scholarshipScheme = "मुख्यमंत्री कन्या उत्थान योजना",
            udiseStudentId = "10301901805012"
        ),
        Student(
            id = "STU_11003",
            rollNo = 3,
            name = "सौरभ कुमार (Saurabh Kumar)",
            studentClass = 11,
            section = "A",
            stream = "Science",
            mobile = "9876543215",
            fatherName = "श्री विनय राय",
            motherName = "श्रीमती सुमित्रा देवी",
            dob = "18/01/2008",
            bloodGroup = "O-",
            attendancePercentage = 88.0f,
            scholarshipStatus = "प्रक्रियाधीन (PFMS सत्यापन)",
            scholarshipScheme = "बिहार राज्य मेधा छात्रवृत्ति",
            udiseStudentId = "10301901805015"
        ),
        Student(
            id = "STU_09008",
            rollNo = 8,
            name = "अंकिता कुमारी (Ankita Kumari)",
            studentClass = 9,
            section = "A",
            stream = "General",
            mobile = "9876543220",
            fatherName = "श्री अमरेश तिवारी",
            motherName = "श्रीमती पुष्पा देवी",
            dob = "14/08/2010",
            bloodGroup = "B+",
            attendancePercentage = 97.1f,
            scholarshipStatus = "स्वीकृत (मुफ्त पाठ्यपुस्तक एवं पोशाक)",
            scholarshipScheme = "सर्व शिक्षा अभियान योजना",
            udiseStudentId = "10301901805020"
        ),
        Student(
            id = "STU_09015",
            rollNo = 15,
            name = "मनीष कुमार यादव (Manish Yadav)",
            studentClass = 9,
            section = "B",
            stream = "General",
            mobile = "9876543225",
            fatherName = "श्री वीरेन्द्र यादव",
            motherName = "श्रीमती शांति देवी",
            dob = "02/05/2010",
            bloodGroup = "O+",
            attendancePercentage = 91.0f,
            scholarshipStatus = "स्वीकृत",
            scholarshipScheme = "मुख्यमंत्री बालक साइकिल योजना",
            udiseStudentId = "10301901805025"
        )
    )

    val demoTeachers = listOf(
        Teacher(
            id = "TCH_001",
            name = "मुकेश कुमार (Mukesh Kumar)",
            designation = "PGT Mathematics (गणित प्रवक्ता)",
            subject = "गणित (Mathematics)",
            mobile = "9876500010",
            assignedClasses = "कक्षा 10वीं, 11वीं, 12वीं",
            qualification = "M.Sc. (Math), B.Ed., STET",
            email = "mukesh.math@umvgangauli.edu.in"
        ),
        Teacher(
            id = "TCH_002",
            name = "श्रीमती अनिता शर्मा (Anita Sharma)",
            designation = "PGT Physics (भौतिकी प्रवक्ता)",
            subject = "भौतिक विज्ञान (Physics)",
            mobile = "9876500011",
            assignedClasses = "कक्षा 11वीं, 12वीं (Science)",
            qualification = "M.Sc. (Physics), B.Ed.",
            email = "anita.phy@umvgangauli.edu.in"
        ),
        Teacher(
            id = "TCH_003",
            name = "श्री राजेश कुमार पाठक (Rajesh Pathak)",
            designation = "TGT Science (विज्ञान शिक्षक)",
            subject = "विज्ञान (Science & Chemistry)",
            mobile = "9876500012",
            assignedClasses = "कक्षा 9वीं, 10वीं",
            qualification = "M.Sc. (Chem), B.Ed.",
            email = "rajesh.sci@umvgangauli.edu.in"
        ),
        Teacher(
            id = "TCH_004",
            name = "डॉ. शंभू नाथ सिंह (Dr. Shambhu Singh)",
            designation = "PGT Hindi (हिन्दी प्रवक्ता)",
            subject = "हिन्दी साहित्य (Hindi)",
            mobile = "9876500013",
            assignedClasses = "कक्षा 9वीं से 12वीं",
            qualification = "M.A. (Hindi), Ph.D., B.Ed.",
            email = "shambhu.hindi@umvgangauli.edu.in"
        ),
        Teacher(
            id = "TCH_005",
            name = "श्री शंभू शरण प्रसाद (Librarian)",
            designation = "पुस्तकालयाध्यक्ष (School Librarian)",
            subject = "पुस्तकालय एवं सूचना विज्ञान",
            mobile = "9876500002",
            assignedClasses = "समस्त कक्षाएं (9-12)",
            qualification = "M.Lib.I.Sc., UGC-NET",
            email = "librarian@umvgangauli.edu.in"
        )
    )

    val demoBooks = listOf(
        Book(
            id = "BK_001",
            accessionNo = "UMV/LIB/2025/0001",
            isbn = "978-81-7450-634-1",
            title = "Mathematics Class 10 NCERT",
            hindiTitle = "गणित (कक्षा 10वीं - एनसीईआरटी)",
            author = "NCERT Editorial Board",
            publisher = "NCERT New Delhi / Bihar State Textbook",
            subject = "गणित",
            targetClass = "10",
            category = "पाठ्यपुस्तक",
            rackNo = "Rack R-01 (Shelf 2)",
            isIssued = true,
            issuedToStudentName = "अमित कुमार",
            issuedToRoll = 14,
            issuedToClass = 10,
            issueDate = "15/09/2025",
            dueDate = "29/09/2025",
            eGranthalayaSyncId = "EG4-UMV-1001"
        ),
        Book(
            id = "BK_002",
            accessionNo = "UMV/LIB/2025/0002",
            isbn = "978-81-7450-645-7",
            title = "Science Class 10 NCERT",
            hindiTitle = "विज्ञान (कक्षा 10वीं - एनसीईआरटी)",
            author = "NCERT Board",
            publisher = "Bihar State Textbook Publishing Corp",
            subject = "विज्ञान",
            targetClass = "10",
            category = "पाठ्यपुस्तक",
            rackNo = "Rack R-01 (Shelf 3)",
            isIssued = false,
            eGranthalayaSyncId = "EG4-UMV-1002"
        ),
        Book(
            id = "BK_003",
            accessionNo = "UMV/LIB/2025/0003",
            isbn = "978-81-2072-451-2",
            title = "Godan (गोदान)",
            hindiTitle = "गोदान - मुंशी प्रेमचंद (उपन्यास)",
            author = "मुंशी प्रेमचंद",
            publisher = "राजकमल प्रकाशन",
            subject = "हिंदी साहित्य",
            targetClass = "All",
            category = "कहानी एवं साहित्य",
            rackNo = "Rack R-04 (Literature)",
            isIssued = false,
            eGranthalayaSyncId = "EG4-UMV-1003"
        ),
        Book(
            id = "BK_004",
            accessionNo = "UMV/LIB/2025/0004",
            isbn = "978-81-7450-523-8",
            title = "Physics Part 1 Class 12",
            hindiTitle = "भौतिकी भाग-1 (कक्षा 12वीं)",
            author = "NCERT",
            publisher = "NCERT",
            subject = "भौतिक विज्ञान",
            targetClass = "12",
            category = "पाठ्यपुस्तक",
            rackNo = "Rack R-02 (Science)",
            isIssued = true,
            issuedToStudentName = "रोहित कुमार ओझा",
            issuedToRoll = 5,
            issuedToClass = 12,
            issueDate = "02/10/2025",
            dueDate = "16/10/2025",
            eGranthalayaSyncId = "EG4-UMV-1004"
        ),
        Book(
            id = "BK_005",
            accessionNo = "UMV/LIB/2025/0005",
            isbn = "978-93-5144-890-4",
            title = "Higher Secondary Mathematics Vol 2",
            hindiTitle = "उच्च माध्यमिक गणित भाग-2 (आर.डी. शर्मा)",
            author = "डॉ. आर. डी. शर्मा",
            publisher = "धनपत राय पब्लिकेशन्स",
            subject = "गणित",
            targetClass = "12",
            category = "संदर्भ",
            rackNo = "Rack R-02 (Ref Math)",
            isIssued = false,
            eGranthalayaSyncId = "EG4-UMV-1005"
        ),
        Book(
            id = "BK_006",
            accessionNo = "UMV/LIB/2025/0006",
            isbn = "978-93-8632-111-9",
            title = "Bihar Samanya Gyan (General Knowledge)",
            hindiTitle = "बिहार सामान्य ज्ञान 2025-26 (डॉ. मनीष रंजन)",
            author = "डॉ. मनीष रंजन IAS",
            publisher = "प्रभात प्रकाशन",
            subject = "सामान्य ज्ञान",
            targetClass = "All",
            category = "प्रतियोगी",
            rackNo = "Rack R-05 (Competitive)",
            isIssued = false,
            eGranthalayaSyncId = "EG4-UMV-1006"
        ),
        Book(
            id = "BK_007",
            accessionNo = "UMV/LIB/2025/0007",
            isbn = "978-81-7450-701-0",
            title = "India and the Contemporary World I",
            hindiTitle = "भारत और समकालीन विश्व-1 (इतिहास कक्षा 9वीं)",
            author = "NCERT",
            publisher = "NCERT / Bihar Board",
            subject = "सामाजिक विज्ञान",
            targetClass = "9",
            category = "पाठ्यपुस्तक",
            rackNo = "Rack R-03 (Social)",
            isIssued = false,
            eGranthalayaSyncId = "EG4-UMV-1007"
        ),
        Book(
            id = "BK_008",
            accessionNo = "UMV/LIB/2025/0008",
            isbn = "978-81-7450-811-6",
            title = "Flamingo English Reader Class 12",
            hindiTitle = "फ्लेमिंगो अंग्रेजी रीडर (कक्षा 12वीं)",
            author = "NCERT Editorial",
            publisher = "NCERT",
            subject = "अंग्रेजी",
            targetClass = "12",
            category = "पाठ्यपुस्तक",
            rackNo = "Rack R-03 (English)",
            isIssued = false,
            eGranthalayaSyncId = "EG4-UMV-1008"
        ),
        Book(
            id = "BK_009",
            accessionNo = "UMV/LIB/2025/0009",
            isbn = "978-81-7450-934-2",
            title = "Chemistry Part 1 Class 11",
            hindiTitle = "रसायन विज्ञान भाग-1 (कक्षा 11वीं)",
            author = "NCERT",
            publisher = "NCERT",
            subject = "रसायन विज्ञान",
            targetClass = "11",
            category = "पाठ्यपुस्तक",
            rackNo = "Rack R-02 (Chemistry)",
            isIssued = true,
            issuedToStudentName = "सौरभ कुमार",
            issuedToRoll = 3,
            issuedToClass = 11,
            issueDate = "10/09/2025",
            dueDate = "24/09/2025",
            eGranthalayaSyncId = "EG4-UMV-1009"
        ),
        Book(
            id = "BK_010",
            accessionNo = "UMV/LIB/2025/0010",
            isbn = "978-93-5094-118-2",
            title = "Concepts of Physics Vol 1 - HC Verma",
            hindiTitle = "कॉन्सेप्ट्स ऑफ फिजिक्स - एच.सी. वर्मा (भाग 1)",
            author = "डॉ. एच. सी. वर्मा",
            publisher = "भारती भवन (पटना)",
            subject = "भौतिक विज्ञान",
            targetClass = "11",
            category = "संदर्भ",
            rackNo = "Rack R-02 (Physics Ref)",
            isIssued = false,
            eGranthalayaSyncId = "EG4-UMV-1010"
        )
    )

    val demoIssueRecords = listOf(
        BookIssueRecord(
            id = "ISS_001",
            bookId = "BK_001",
            bookTitle = "गणित (कक्षा 10वीं - एनसीईआरटी)",
            accessionNo = "UMV/LIB/2025/0001",
            studentName = "अमित कुमार",
            rollNo = 14,
            studentClass = 10,
            issueDate = "15/09/2025",
            dueDate = "29/09/2025",
            fineAmount = 9, // Late by 9 days (7 days * 1 + 2 days * 2 = 7+4 = 11? Or Bihar fine norms)
            condition = "उत्कृष्ट (Good)",
            isReturned = false
        ),
        BookIssueRecord(
            id = "ISS_002",
            bookId = "BK_004",
            bookTitle = "भौतिकी भाग-1 (कक्षा 12वीं)",
            accessionNo = "UMV/LIB/2025/0004",
            studentName = "रोहित कुमार ओझा",
            rollNo = 5,
            studentClass = 12,
            issueDate = "02/10/2025",
            dueDate = "16/10/2025",
            fineAmount = 0,
            condition = "उत्कृष्ट (Good)",
            isReturned = false
        ),
        BookIssueRecord(
            id = "ISS_003",
            bookId = "BK_009",
            bookTitle = "रसायन विज्ञान भाग-1 (कक्षा 11वीं)",
            accessionNo = "UMV/LIB/2025/0009",
            studentName = "सौरभ कुमार",
            rollNo = 3,
            studentClass = 11,
            issueDate = "10/09/2025",
            dueDate = "24/09/2025",
            fineAmount = 14,
            condition = "उत्कृष्ट (Good)",
            isReturned = false
        ),
        BookIssueRecord(
            id = "ISS_004",
            bookId = "BK_003",
            bookTitle = "गोदान - मुंशी प्रेमचंद (उपन्यास)",
            accessionNo = "UMV/LIB/2025/0003",
            studentName = "प्रिया कुमारी",
            rollNo = 1,
            studentClass = 10,
            issueDate = "01/08/2025",
            dueDate = "15/08/2025",
            returnDate = "14/08/2025",
            fineAmount = 0,
            condition = "उत्कृष्ट (Good)",
            isReturned = true
        )
    )

    val demoMarks = listOf(
        MarkItem("गणित (Mathematics)", 100, 92, "A1"),
        MarkItem("विज्ञान (Science)", 100, 88, "A2"),
        MarkItem("सामाजिक विज्ञान (Social Science)", 100, 85, "A2"),
        MarkItem("हिन्दी (Hindi)", 100, 90, "A1"),
        MarkItem("संस्कृत (Sanskrit)", 100, 94, "A1"),
        MarkItem("अंग्रेजी (English)", 100, 81, "B1")
    )

    val demoTimetable = listOf(
        TimeTableEntry(1, "09:00 - 09:45 AM", "गणित (Mathematics)", "मुकेश कुमार", "कक्ष सं. 10"),
        TimeTableEntry(2, "09:45 - 10:30 AM", "विज्ञान (Physics)", "श्रीमती अनिता शर्मा", "प्रयोगशाला 01"),
        TimeTableEntry(3, "10:30 - 11:15 AM", "हिन्दी (Hindi)", "डॉ. शंभू नाथ सिंह", "कक्ष सं. 10"),
        TimeTableEntry(4, "11:15 - 12:00 PM", "अंग्रेजी (English)", "श्री अशोक कुमार", "कक्ष सं. 10"),
        TimeTableEntry(5, "12:00 - 12:45 PM", "मध्याह्न भोजन (MDM Break)", "भोजन कक्ष", "परिसर"),
        TimeTableEntry(6, "12:45 - 01:30 PM", "सामाजिक विज्ञान", "श्री विजय कुमार", "कक्ष सं. 10"),
        TimeTableEntry(7, "01:30 - 02:15 PM", "कंप्यूटर / ICT क्लास", "श्री अमित रंजन", "ICT लैब"),
        TimeTableEntry(8, "02:15 - 03:00 PM", "पुस्तकालय वाचन (Library)", "श्री शंभू शरण प्रसाद", "केंद्रीय पुस्तकालय")
    )

    val demoNotices = listOf(
        Notice(
            id = "NOT_01",
            title = "बिहार बोर्ड मैट्रिक परीक्षा 2026: परीक्षा फॉर्म भरने एवं शुल्क भुगतान की अंतिम तिथि 25 अक्टूबर 2025 तक बढ़ाई गई।",
            date = "08 अक्टूबर 2025",
            category = "BSEB",
            isUrgent = true
        ),
        Notice(
            id = "NOT_02",
            title = "कक्षा 9वीं एवं 10वीं के छात्र-छात्राओं के लिए साइकिल एवं पोशाक राशि डीबीटी (DBT) के माध्यम से बैंक खाते में भेजी गई।",
            date = "06 अक्टूबर 2025",
            category = "छात्रवृत्ति",
            isUrgent = false
        ),
        Notice(
            id = "NOT_03",
            title = "केंद्रीय पुस्तकालय (e-Granthalaya) में कक्षा 11वीं एवं 12वीं के लिए नई NCERT और संदर्भ पुस्तकें उपलब्ध।",
            date = "04 अक्टूबर 2025",
            category = "पुस्तकालय",
            isUrgent = false
        ),
        Notice(
            id = "NOT_04",
            title = "e-ShikshaKosh पोर्टल पर सभी शिक्षकों एवं विद्यार्थियों की दैनिक उपस्थिति 9:00 AM तक अनिवार्य रूप से दर्ज की जाए।",
            date = "01 अक्टूबर 2025",
            category = "विद्यालय",
            isUrgent = true
        )
    )

    val schoolFacilities = listOf(
        "स्मार्ट क्लासरूम (Smart Interactive Panels)" to "प्रत्येक कक्षा में 75-इंच डिजिटल इंटरैक्टिव बोर्ड और हाई-स्पीड इंटरनेट कनेक्टिविटी।",
        "अत्याधुनिक विज्ञान प्रयोगशाला (Science Labs)" to "भौतिकी, रसायन और जीव विज्ञान के अलग-अलग आधुनिक प्रायोगिक उपकरण व सुरक्षा साधन।",
        "ICT कंप्यूटर लैब (Computer Lab)" to "30 आधुनिक कंप्यूटर सिस्टम, कोडिंग और डिजिटल साक्षरता प्रशिक्षण हेतु समर्पित कक्ष।",
        "केंद्रीय ई-ग्रंथालय (e-Granthalaya Library)" to "4,250 से अधिक पुस्तकें, डिजिटल ई-बुक्स, वाचनालय और बारकोड आधारित पुस्तक निर्गमन प्रणाली।",
        "खेलकूद मैदान एवं खेल सामग्री" to "वॉलीबॉल, फुटबॉल, क्रिकेट, बैडमिंटन और कबड्डी कोर्ट सहित विशाल खेल परिसर।",
        "स्वच्छ पेयजल (RO Water) एवं मध्याह्न भोजन" to "स्वच्छ आधुनिक भोजनालय और 500 लीटर प्रति घंटा क्षमता वाला स्वचालित RO वाटर प्लांट।"
    )
}
