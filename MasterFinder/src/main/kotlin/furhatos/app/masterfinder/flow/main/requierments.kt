import furhatos.app.masterfinder.flow.Parent
import furhatos.app.masterfinder.flow.main.Ethnicity
import furhatos.app.masterfinder.flow.main.General
import furhatos.flow.kotlin.*
import furhatos.nlu.common.*

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

    onResponse<Ethnicity> {
        furhat.say("As a Dutch citizen, for a September intake, you can apply from 1 October with a deadline before 1 August. For a February intake, applications open on 1 March with a deadline before 1 January.")
        goto(General)
    }

    onResponse<Ethnicity> {
        furhat.say("As a citizen of another EEA country, for a September intake, you can apply from 1 October with a deadline before 1 July. For a February intake, applications open on 1 March with a deadline before 1 December.")
        goto(General)
    }

    onResponse<Ethnicity> {
        furhat.say("As a non-EEA citizen, for a September intake, you can apply from 1 October with a deadline before 1 May. For a February intake, applications open on 1 March with a deadline before 1 October.")
        goto(General)
    }
}
