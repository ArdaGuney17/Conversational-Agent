package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Requirements: State = state(Parent) {  // Fixed typo in variable name (Requierements -> Requirements)
    onEntry {
        furhat.say("""
            The general requirements for Master's programs at University of Twente are:
            1. A relevant Bachelor's degree or equivalent
            2. Proof of English proficiency (IELTS 6.5 or TOEFL 90)
            3. Additional program-specific requirements may apply
        """.trimIndent())
        
        furhat.ask("Are you from an EU/EEA country or Switzerland?")
    }

    onResponse<Yes> {
        furhat.say("""
            For EU/EEA/Swiss applicants:
            - Application deadline: June 1st for September start
            - No visa required
            - Tuition fee: €2,530 per year (2024)
        """.trimIndent())
        goto(ApplicationProcess)
    }

    onResponse<No> {
        furhat.say("""
            For non-EU/EEA applicants:
            - Application deadline: May 1st for September start
            - Visa required (apply early)
            - Tuition fee: €18,000-€36,000 per year depending on program
        """.trimIndent())
        goto(ApplicationProcess)
    }
}

val ApplicationProcess: State = state(Parent) {
    onEntry {
        furhat.say("""
            The application process involves:
            1. Creating an account on Studielink
            2. Submitting required documents
            3. Paying application fee (if applicable)
            4. Waiting for admission decision
        """.trimIndent())
        furhat.ask("Would you like me to explain any part in more detail?")
    }

    onResponse<Yes> {
        // Add more detailed explanations here if needed
        furhat.say("Let me know which part you'd like more information about.")
        goto(General)
    }

    onResponse<No> {
        goto(General)
    }
}
