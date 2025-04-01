package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.util.Language

val MasterRequirements: State = state(Parent) {
    init {
        val euDeadline = "July 1st"
        val nonEuDeadline = "May 1st"
        val requiredGpa = "7.0 Dutch scale"
        val englishTests = "IELTS 6.5 or TOEFL 90"
    }

    onEntry {
        furhat.say("Let me help you with the University of Twente Master's application!")
        furhat.ask("To start, are you from an EU/EEA country like the Netherlands or Germany?")
    }

    /* ===== COUNTRY HANDLING ===== */
    onResponse<Yes> {
        furhat.say("Great! EU applicants have simpler processes.")
        goto(CheckEuRequirements)
    }

    onResponse<No> {
        furhat.say("I'll guide you through the international student process.")
        goto(CheckInternationalRequirements)
    }

    // Handle when user mentions a country name
    onResponse {
        val country = it.text.lowercase()
        when {
            country.contains("netherlands") -> {
                furhat.say("Dutch students qualify for the EU deadline of $euDeadline.")
                goto(CheckDutchStudentStatus)
            }
            country.contains("india") || country.contains("china") -> {
                furhat.say("Many students from ${it.text} attend UT! Your deadline is $nonEuDeadline.")
                goto(CheckInternationalRequirements)
            }
            else -> {
                furhat.say("Thanks for sharing! Let's check your requirements.")
                goto(GeneralRequirementsCheck)
            }
        }
    }
}

/* ===== EU APPLICANT FLOW ===== */
val CheckEuRequirements: State = state {
    onEntry {
        furhat.say("""
            |For EU applicants, you'll need:
            |1. A recognized bachelor's degree
            |2. GPA equivalent to $requiredGpa
            |3. $englishTests English proficiency
            |The deadline is $euDeadline.
            """.trimMargin())
        
        furhat.ask("Do you meet these three requirements?")
    }

    onResponse<Yes> {
        furhat.say("Excellent! Let's discuss your program options.")
        goto(ProgramSelection)
    }

    onResponse<No> {
        furhat.say("No worries! Let's explore alternatives.")
        goto(PreMasterOptions)
    }
}

/* ===== INTERNATIONAL APPLICANT FLOW ===== */ 
val CheckInternationalRequirements: State = state {
    onEntry {
        furhat.say("""
            |For international students:
            |1. All standard academic requirements
            |2. Earlier deadline: $nonEuDeadline
            |3. Visa documentation needed
            |4. €100 application fee
            """.trimMargin())
        
        furhat.ask("Would you like me to explain any part in detail?")
    }

    onResponse {
        when (it.text.lowercase()) {
            contains("visa") -> {
                furhat.say("""
                    |You'll need:
                    |1. Valid passport
                    |2. Financial proof (€11,000/year)
                    |3. Health insurance
                    |The university assists with visa applications.
                    """.trimMargin())
            }
            contains("fee") -> {
                furhat.say("The €100 fee is non-refundable and paid via credit card during application.")
            }
            else -> {
                furhat.say("I recommend visiting utwente.nl/international for complete details.")
            }
        }
        goto(GeneralRequirementsCheck)
    }
}

/* ===== CORE STATES ===== */
val GeneralRequirementsCheck: State = state {
    onEntry {
        furhat.ask("""
            |Should we check if you qualify?
            |I'll need to know about:
            |1. Your bachelor's degree
            |2. Your grades
            |3. English test status
            """.trimMargin())
    }

    onResponse<Yes> {
        goto(QualificationInterview)
    }

    onResponse<No> {
        furhat.say("No problem! You can find all requirements at utwente.nl/master.")
        goto(Goodbye)
    }
}

val QualificationInterview: State = state {
    onEntry {
        furhat.ask("First, what was your bachelor's field of study?")
    }

    onResponse {
        users.current.studyField = it.text
        furhat.ask("And was your GPA equivalent to at least $requiredGpa?")
        
        onResponse<Yes> {
            furhat.ask("Do you have $englishTests English test results?")
            
            onResponse<Yes> {
                furhat.say("Congratulations! You likely qualify for direct admission.")
                goto(ProgramSelection)
            }
            
            onResponse<No> {
                furhat.say("You'll need English test results before enrollment.")
                goto(EnglishTestOptions)
            }
        }
        
        onResponse<No> {
            furhat.say("Your GPA might require a pre-master's program.")
            goto(PreMasterOptions)
        }
    }
}

/* ===== SUPPORTING STATES ===== */
val PreMasterOptions: State = state {
    onEntry {
        furhat.say("""
            |The pre-master's:
            |• Takes 6-12 months
            |• Bridges knowledge gaps
            |• Leads to master's admission
            |I can connect you with an advisor if interested.
            """.trimMargin())
        goto(Goodbye)
    }
}

val ProgramSelection: State = state {
    onEntry {
        furhat.say("""
            |Popular programs include:
            |1. Computer Science
            |2. Engineering
            |3. Technical Medicine
            |Should I explain any program?
            """.trimMargin())
    }
}

val EnglishTestOptions: State = state {
    onEntry {
        furhat.say("""
            |You can take:
            |1. IELTS Academic
            |2. TOEFL iBT
            |3. Cambridge C1 Advanced
            |The university accepts tests up to 2 years old.
            """.trimMargin())
        goto(Goodbye)
    }
}

val Goodbye: State = state {
    onEntry {
        furhat.say("""
            |Good luck with your application!
            |Visit utwente.nl/master for details.
            |Come back anytime for help.
            """.trimMargin())
        terminate()
    }
}
