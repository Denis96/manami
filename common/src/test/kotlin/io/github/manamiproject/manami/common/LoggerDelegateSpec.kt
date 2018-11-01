package io.github.manamiproject.manami.common

import org.assertj.core.api.Assertions.assertThat
import org.slf4j.Logger
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.reflect.*


object LoggerDelegateSpec : Spek({

    Feature("Creating a Logger by delegate") {

        Scenario("Manually creating a logger") {
            lateinit var logger: Logger

            When("") {
                logger = LoggerDelegate().getValue(LoggerDelegateSpec::class.java, TestProp())
            }

            Then("it must create logback instance") {
                assertThat(logger::class.java.toString()).isEqualToIgnoringCase("class ch.qos.logback.classic.Logger")
            }
        }
    }
})


private class TestProp : KProperty<String> {
    override val isSuspend: Boolean
        get() = TODO("not implemented")
    override val annotations: List<Annotation>
        get() = mutableListOf()
    override val getter: KProperty.Getter<String>
        get() = TODO("not implemented")
    override val isAbstract: Boolean
        get() = false
    override val isConst: Boolean
        get() = true
    override val isFinal: Boolean
        get() = true
    override val isLateinit: Boolean
        get() = false
    override val isOpen: Boolean
        get() = false
    override val name: String
        get() = ""
    override val parameters: List<KParameter>
        get() = mutableListOf()
    override val returnType: KType
        get() = TODO("not implemented")
    override val typeParameters: List<KTypeParameter>
        get() = mutableListOf()
    override val visibility: KVisibility?
        get() = null

    override fun call(vararg args: Any?) = ""

    override fun callBy(args: Map<KParameter, Any?>) = ""
}