package furhatos.app.masterfinder.flow.main

import furhatos.nlu.Intent
import furhatos.util.Language

class AskAboutMasters: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Tell me about master's programs", "I want to know about the Master programs", "What Master programs are available?", "I want help choosing a Masters", "Master", "Masters")
    }
}

class YesAnswer: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Yes", "Yeah", "Yep", "Sure", "Absolutely", "Of course", "Yup", "Definitely", "Indeed", "Affirmative")
    }
}

class NoAnswer: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("No", "Nope", "Nah", "Not really", "I don’t think so", "Negative", "Absolutely not", "No way", "Not at all", "Never")
    }
}

class AskAboutBachelors: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I don't know what Masters I want", "I have no idea what Masters I want to do")
    }
}

class AskAboutRequirements: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I want to know the requirements of a masters", "I want to know about a pre master", "I would like information about the requirements of a Master", "Requirements", "Admission", "admission requirements")
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
                "What can I do after completing this program?",
                "career",
                "career opportunities"
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

class Dutch: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Dutch", "I am Dutch", "I am from the Netherlands", "I am a Dutch citizen")
    }
}

class European: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I am from another European country", "I am European", "I am from Europe", "Austria", "Belgium", "Bulgaria", "Croatia", "Cyprus", "Czech Republic",
                "Denmark", "Estonia", "Finland", "France", "Germany", "Greece", "Hungary",
                "Iceland", "Ireland", "Italy", "Latvia", "Liechtenstein", "Lithuania",
                "Luxembourg", "Malta", "Norway", "Poland", "Portugal", "Romania",
                "Slovakia", "Slovenia", "Spain", "Sweden",

                // Non-EEA European countries
                "Albania", "Andorra", "Belarus", "Bosnia and Herzegovina", "Georgia",
                "Kosovo", "Moldova", "Monaco", "Montenegro", "North Macedonia",
                "San Marino", "Serbia", "Switzerland", "Ukraine", "United Kingdom", "Vatican City", "Austrian", "Belgian", "Bulgarian", "Croatian", "Cypriot", "Czech",
                "Danish", "Estonian", "Finnish", "French", "German", "Greek", "Hungarian",
                "Icelandic", "Irish", "Italian", "Latvian", "Liechtensteiner",
                "Lithuanian", "Luxembourgish", "Maltese", "Norwegian", "Polish",
                "Portuguese", "Romanian", "Slovak", "Slovenian", "Spanish", "Swedish", "Albanian", "Andorran", "Belarusian", "Bosnian", "Herzegovinian",
                "Georgian", "Kosovar", "Moldovan", "Monegasque", "Montenegrin",
                "Macedonian", "Sammarinese", "Serbian", "Swiss", "Ukrainian", "British",
                "Vatican citizen")
    }
}

class OutsideEurope: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I am not from another European country", "I am not European", "I am not from Europe", "I come from outside of Europe")
    }
}

class GeneralPreMaster: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I want to know general information", "Give me general information", "general information")
    }
}

class DurationPreMaster: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Duration")
    }
}
class BackgroundPreMaster: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("background")
    }
}

class PurposePreMaster: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Purpose")
    }
}

class AskAboutPreMaster: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("pre-master")
    }
}

class Name: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I am",
                "I'm",
                "My name is",
                "Call me",
                "You can call me",
                "They call me",
                "I go by",
                "It's",
                "This is",
                "My friends call me"
        )
    }
}


