import { AaiValidationRuleMetaData } from '../validation/AaiValidationRuleMetaData';
import { AaiValidationRule } from '../validation/AaiValidationRule';

export class AaiValidationCompat {

    public static instantiate(ruleInfo: AaiValidationRuleMetaData): AaiValidationRule {
        return new ruleInfo.ruleClass(ruleInfo);
    }
}