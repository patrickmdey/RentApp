import {Path, PathValue, Validate, ValidationRule} from "react-hook-form";

export default interface ValidationInterface<T> {
    required: string | ValidationRule<boolean>;
    min: ValidationRule<string | number>;
    max: ValidationRule<string | number>;
    minLength: ValidationRule<number>;
    maxLength: ValidationRule<number>;
    pattern: ValidationRule<RegExp>;
    validate:
        | Validate<PathValue<T, Path<T>>>
        | Record<string, Validate<PathValue<T, Path<T>>>>;
    deps: string[];
}