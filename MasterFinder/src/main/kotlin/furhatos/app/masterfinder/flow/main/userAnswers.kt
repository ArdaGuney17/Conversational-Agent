package furhatos.app.masterfinder.flow.main

import furhatos.nlu.Intent
import furhatos.util.Language

class AskAboutMasters: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Tell me about master's programs", "I want to know about the Master programs", "What Master programs are available?", "I want help choosing a Masters")
    }
}

class AskAboutBachelors: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I don't know what Masters I want", "I have no idea what Masters I want to do")
    }
}

class AskAboutRequirements: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I want to know the requirements of a masters", "I want to know about a pre master", "I would like information about the requirements of a Master")
    }
}

class AdmissionRequest : Intent(){
    override fun getExamples(lang: Language): List<String> {
        return listOf("The admission", "The admission requirements", "I would like to know more about the admission requirements")
    }
}
class CareerRequest : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "The career opportunities",
                "What are the career prospects?",
                "What job opportunities are there after graduation?",
                "What kind of careers can I pursue?",
                "Tell me about the career options",
                "What can I do after completing this program?"
        )
    }
}

class FacultyRequest : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Which faculty offers this program?",
                "What faculty is this master's program part of?",
                "Tell me about the faculty for this program",
                "Who teaches this program?",
                "Which department is responsible for this master's?"
        )
    }
}

class DurationRequest : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "How long does the program take?",
                "What is the duration of this master's?",
                "How many years does it take to complete?",
                "How long will it take to finish the program?",
                "What is the standard length of the master's program?"
        )
    }
}


class StructureRequest : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "The structure",
                "What does the program structure look like?",
                "Can you tell me about the course structure?",
                "What subjects are included in the program?",
                "How is the program organized?",
                "Give me an overview of the program structure"
        )
    }
}

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