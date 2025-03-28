package furhatos.app.masterfinder.flow.main

import furhatos.app.masterfinder.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.*
import furhatos.nlu.NullIntent.getExamples
import furhatos.nlu.common.*
import furhatos.util.Language

// Define custom intents
class AdmissionRequest : Intent()
class CareerRequest : Intent()
class StructureRequest : Intent()

class MasterCourses: Intent() {
    override fun getExamples(lang: Language): List<String> {

        val masterStudies = listOf("Applied Mathematics", "Applied Physics", "Biomedical Engineering",
                "Business Administration", "Business Information Technology",
                "Chemical Science and Engineering", "Civil Engineering and Management",
                "Communication Science", "Computer Science", "Construction Management and Engineering",
                "Educational Science and Technology", "Electrical Engineering", "Embedded Systems",
                "Environmental and Energy Management", "European Studies",
                "Geo-Information Science and Earth Observation",
                "Geographical Information Management and Applications", "Health Sciences",
                "Industrial Design Engineering", "Industrial Engineering and Management",
                "Interaction Technology", "Internet Science and Technology", "Mechanical Engineering",
                "Nanotechnology", "Philosophy of Science, Technology and Society", "Psychology",
                "Public Administration", "Robotics", "Spatial Engineering",
                "Sustainable Energy Technology", "Technical Medicine")

        return masterStudies
    }

    fun processUserInput(userInput: String) {
        val masterCourses = getExamples(Language.ENGLISH_GB)

        for (study in masterCourses) {
            if (userInput.contains(study, ignoreCase = true)) {
                // Save the study program to the UserData singleton
                UserData.userOptionalMaster= study
                break
            }
        }

        // Log the study program (just for demo)
        println("User is studying: ${UserData.userOptionalMaster}")
    }
}



val MasterSelection: State = state(Parent) {

    onEntry {
        furhat.ask("Which master's program did you have in mind?")
    }

    onResponse<MasterCourses> {

        val userInput = it.text
        val mastersIntent = MasterCourses()

        mastersIntent.processUserInput(userInput)

        // Find the corresponding program from the list
        val master = UserData.userOptionalMaster

        if (master != null) {
            furhat.say("So you are interested in $master, that is nice to hear")
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
        ),
        MasterProgram(
                name = "Biomedical Engineering",
                duration = "2",
                language = "English",
                faculty = "Biomedical Engineering",
                admission = "Bachelor’s degree in Biomedical Engineering, Mechanical Engineering, or a related field.",
                careerProspects = "Healthcare technology, Medical Devices, Research.",
                structure = "Focus on medical technology, bioinstrumentation, and biomaterials."
        ),
        MasterProgram(
                name = "Business Administration",
                duration = "1",
                language = "English",
                faculty = "Business Administration",
                admission = "Bachelor's degree in Business Administration or related fields.",
                careerProspects = "Management, Consulting, Entrepreneurship.",
                structure = "Management theory, business strategy, and leadership."
        ),
        MasterProgram(
                name = "Business Information Technology",
                duration = "2",
                language = "English",
                faculty = "Business Administration, Computer Science",
                admission = "Bachelor's degree in Information Technology, Business, or related fields.",
                careerProspects = "IT Management, Business Analysis, Software Development.",
                structure = "Business processes, IT architecture, and data management."
        ),
        MasterProgram(
                name = "Chemical Science and Engineering",
                duration = "2",
                language = "English",
                faculty = "Engineering Technology",
                admission = "Bachelor's degree in Chemical Engineering or related fields.",
                careerProspects = "Chemical Engineering, Process Industries, R&D.",
                structure = "Focus on process technology, chemical engineering, and materials science."
        ),
        MasterProgram(
                name = "Civil Engineering and Management",
                duration = "2",
                language = "English",
                faculty = "Engineering Technology",
                admission = "Bachelor’s degree in Civil Engineering or related fields.",
                careerProspects = "Construction, Urban Planning, Project Management.",
                structure = "Urban design, project management, and sustainable infrastructure."
        ),
        MasterProgram(
                name = "Communication Science",
                duration = "2",
                language = "English",
                faculty = "Communication Science",
                admission = "Bachelor's degree in Communication, Media, or related fields.",
                careerProspects = "Public Relations, Media, Communication Strategy.",
                structure = "Focus on communication theory, media studies, and digital communication."
        ),
        MasterProgram(
                name = "Computer Science",
                duration = "2",
                language = "English",
                faculty = "Electrical Engineering, Mathematics and Computer Science",
                admission = "Bachelor's degree in Computer Science, Software Engineering, or related fields.",
                careerProspects = "Software Development, AI, Data Science.",
                structure = "Programming, algorithms, artificial intelligence, and software engineering."
        ),
        MasterProgram(
                name = "Electrical Engineering",
                duration = "2",
                language = "English",
                faculty = "Electrical Engineering, Mathematics and Computer Science",
                admission = "Bachelor's degree in Electrical Engineering or related fields.",
                careerProspects = "Telecommunications, Power Systems, Embedded Systems.",
                structure = "Electronics, circuits, systems design, and signal processing."
        ),
        MasterProgram(
                name = "Embedded Systems",
                duration = "2",
                language = "English",
                faculty = "Electrical Engineering, Mathematics and Computer Science",
                admission = "Bachelor's degree in Electrical Engineering, Computer Science, or related fields.",
                careerProspects = "Embedded Systems Development, IoT, Automation.",
                structure = "Focus on hardware, software, and system integration."
        ),
        MasterProgram(
                name = "Environmental and Energy Management",
                duration = "2",
                language = "English",
                faculty = "Engineering Technology",
                admission = "Bachelor's degree in Environmental Science, Engineering, or related fields.",
                careerProspects = "Sustainability, Energy Management, Policy Development.",
                structure = "Energy systems, sustainability strategies, and environmental impact."
        ),
        MasterProgram(
                name = "Mechanical Engineering",
                duration = "2",
                language = "English",
                faculty = "Engineering Technology",
                admission = "Bachelor’s degree in Mechanical Engineering or related fields.",
                careerProspects = "Engineering, Design, Manufacturing.",
                structure = "Design and analysis, thermodynamics, materials science."
        )
        // Add more programs as needed
)

