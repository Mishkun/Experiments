package com.livermor.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider
import com.livermor.detekt.rules.NoInternalImportRule

/**
 * @author dumchev on 25/10/2018.
 */
class CustomRulesProvider : RuleSetProvider {

    override val ruleSetId: String = "detekt-custom-rules"

    override fun instance(config: Config) = RuleSet(
            ruleSetId,
            listOf(
                    NoInternalImportRule(config)
            )
    )
}