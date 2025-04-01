/*package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*
import furhatos.util.Language

    val euDeadline = "July 1st"
    val nonEuDeadline = "May 1st"
    val requiredGpa = "7.0 Dutch scale"
    val englishTests = "IELTS 6.5 or TOEFL 90"



val MasterRequirements: State = state(Parent) {

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

val Requierements: State = state(Parent) {

    onEntry {
        furhat.ask("To apply for the master's program, you need a relevant bachelor's degree, a sufficient GPA, and English proficiency. Do you believe you meet these requirements?")
    }

    onResponse<Yes> {
        furhat.ask("Great! May I ask about your nationality? Are you a Dutch citizen, a citizen of another EEA country, or a non-EEA citizen?")
    }

    onResponse<No> {
        furhat.ask("Would you like more information about the requirements?")
        
        onResponse<Yes> {
            furhat.say("You need a relevant bachelor's degree in a related field, a minimum GPA requirement depending on the program, and proof of English proficiency, usually IELTS 6.5 or TOEFL 90. Some programs may have additional requirements.")
            furhat.ask("Do you think you can meet these requirements?")
        }

        onResponse<No> {
            furhat.say("If you do not meet all requirements, you may be eligible for a pre-master's program.")
            goto(General)
        }
    }

    onResponse<Dutch> {
        furhat.say("As a Dutch citizen, for a September intake, you can apply from 1 October with a deadline before 1 August. For a February intake, applications open on 1 March with a deadline before 1 January.")
        goto(General)
    }

    onResponse<EEA> {
        furhat.say("As a citizen of another EEA country, for a September intake, you can apply from 1 October with a deadline before 1 July. For a February intake, applications open on 1 March with a deadline before 1 December.")
        goto(General)
    }

    onResponse<NonEEA> {
        furhat.say("As a non-EEA citizen, for a September intake, you can apply from 1 October with a deadline before 1 May. For a February intake, applications open on 1 March with a deadline before 1 October.")
        goto(General)
    }
}
*/