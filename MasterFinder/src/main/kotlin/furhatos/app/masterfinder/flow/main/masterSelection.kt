package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.*
import furhatos.nlu.common.*

// Define custom intents
class MasterName(val name: String? = null) : Intent()
class AdmissionRequest : Intent()
class CareerRequest : Intent()
class StructureRequest : Intent()

val MasterSelection: State = state(Parent) {

    onEntry {
        furhat.say("I can help you find information about a master's program at the University of Twente.")
        furhat.ask("Which master's program are you interested in?")
    }

    onResponse<MasterName> { response ->
        val master = masterPrograms.find { it.name.equals(response.intent.name, ignoreCase = true) }

        if (master != null) {
            furhat.say("Great! The ${master.name} program is a ${master.duration} year program. It is taught in ${master.language} and is part of the ${master.faculty}.")
            goto(MasterDetails(master))
        } else {
            furhat.say("I’m sorry, I couldn’t find that master’s program. Could you try again?")
            reentry()
        }
    }

    onResponse<No> {
        furhat.say("Alright, let me know if you need any other assistance.")
        goto(Parent)
    }
}

fun MasterDetails(master: MasterProgram): State = state {

    onEntry {
        furhat.ask("Would you like to know about the admission requirements, career opportunities, or the study structure for ${master.name}?")
    }

    onResponse<AdmissionRequest> {
        furhat.say("For ${master.name}, the admission requirements include: ${master.admission}.")
        goto(ContinueOrEnd)
    }

    onResponse<CareerRequest> {
        furhat.say("Graduates of ${master.name} often pursue careers in ${master.careerProspects}.")
        goto(ContinueOrEnd)
    }

    onResponse<StructureRequest> {
        furhat.say("${master.name} is structured as follows: ${master.structure}.")
        goto(ContinueOrEnd)
    }

    onResponse<No> {
        furhat.say("Alright, let me know if you have any other questions!")
        goto(Parent)
    }
}

val ContinueOrEnd: State = state {
    onEntry {
        furhat.ask("Would you like to ask about another master's program?")
    }

    onResponse<Yes> {
        goto(MasterSelection)
    }

    onResponse<No> {
        furhat.say("Alright, have a great day!")
        goto(Parent)
    }
}

/** Data Model for Master's Programs */
data class MasterProgram(
    val name: String,
    val duration: String,
    val language: String,
    val faculty: String,
    val admission: String,
    val careerProspects: String,
    val structure: String
)

/** List of Available Masters */
val masterPrograms = listOf(
    MasterProgram(
        name = "Applied Mathematics",
        duration = "2",
        language = "English",
        faculty = "Electrical Engineering, Mathematics and Computer Science",
        admission = "A relevant bachelor's degree, sufficient English proficiency.",
        careerProspects = "Data Science, Research, Engineering, Finance.",
        structure = "Includes mathematical modeling, computational science, and data analytics."
    ),
    MasterProgram(
        name = "Applied Physics",
        duration = "2",
        language = "English",
        faculty = "Technical Physics",
        admission = "Physics or engineering background, sufficient mathematics knowledge.",
        careerProspects = "R&D, Engineering, Academia, High-tech industry.",
        structure = "Specializations in nanotechnology, quantum physics, and fluid dynamics."
    ),
    MasterProgram(
        name = "Shaping Responsible Futures",
        duration = "1",
        language = "English",
        faculty = "Transdisciplinary Master-Insert",
        admission = "Any discipline, motivation for transdisciplinary research.",
        careerProspects = "Policy Making, Social Innovation, Systemic Change Consulting.",
        structure = "Includes real-world challenges, collaboration with stakeholders, and self-directed learning."
    )
)
