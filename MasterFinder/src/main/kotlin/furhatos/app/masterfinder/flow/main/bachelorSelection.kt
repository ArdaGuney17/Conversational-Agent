package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.Intent
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.Language

/*class TellAboutBachelor: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Tell me about master's programs", "I want to know about the Master programs", "What Master programs are available?", "I want help choosing a Masters")
    }
}*/

class NoBachelor: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I do not have a bachelor degree", "I am not a student")
    }
}

class Bachelors: Intent() {
    private var userStudyProgram: String? = null

    override fun getExamples(lang: Language): List<String> {
        // Static list of Bachelor programs at UT
        val bachelorStudies = listOf( //needs to be updated
            "Applied Mathematics",
            "Biomedical Engineering",
            "Civil Engineering",
            "Electrical Engineering",
            "Industrial Engineering and Management",
            "Mechanical Engineering",
            "Philosophy of Science, Technology, and Society",
            "Psychology",
            "Sustainable Energy Technology",
            "Business Information Technology",
            "International Business Administration",
            "Technical Computer Science",
            "Creative Technology",
            "Advanced Technology",
            "Health Sciences",
            "Public Administration",
            "Design, Production and Management"
        )
        return bachelorStudies
    }

    // This method processes the user's input and stores the study program.
    fun processUserInput(userInput: String) {
        val bachelorStudies = getExamples(Language.ENGLISH_GB)

        for (study in bachelorStudies) {
            if (userInput.contains(study, ignoreCase = true)) {
                // Save the study program to the UserData singleton
                UserData.userStudyProgram = study
                break
            }
        }

        // Log the study program (just for demo)
        println("User is studying: ${UserData.userStudyProgram}")
    }
}


val BachelorSelection: State = state(Parent) {
    onEntry {
        furhat.ask("What is your bachelor degree?")
    }

    onResponse<NoBachelor> {
        furhat.ask("Are you enrolled as a bachelor student?")
        goto(EnrolledAsBachelor) // Transition to the next state
    }

    onResponse<Bachelors> {
        val userInput = it.text // Get the user's response as a String
        val bachelorsIntent = Bachelors()
        bachelorsIntent.processUserInput(userInput) // Call the method to process and save the study program

        goto(MasterDirection)
    }
}

val EnrolledAsBachelor: State = state {
    onResponse<Yes> {
        furhat.ask("What are you studying?")
        goto(TellsAboutBachelor)
    }
    onResponse<No> {
        furhat.say("I can only help you to find a master study. Information about bachelor studies are available on the UT website.")
        goto(General)
    }
}

val TellsAboutBachelor: State = state {
    onResponse<Bachelors> {
        val userInput = it.text // Get the user's response as a String
        val bachelorsIntent = Bachelors()
        bachelorsIntent.processUserInput(userInput) // Call the method to process and save the study program

        goto(MasterDirection)
    }
}

val MasterDirection: State = state {
    onEntry {
        furhat.ask("Do you want to go in the same direction in your masters?")
    }
    onResponse<Yes> {
        furhat.say("Great!")
        goto(MasterSelection)
    }
    onResponse<No> {
        furhat.say("Okay.")
        furhat.ask("What else would you like to learn in your masters?")
        goto(MasterSelection)
    }
}