package furhatos.app.masterfinder

import furhatos.app.masterfinder.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class MasterfinderSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
