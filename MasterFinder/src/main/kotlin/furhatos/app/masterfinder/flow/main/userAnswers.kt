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