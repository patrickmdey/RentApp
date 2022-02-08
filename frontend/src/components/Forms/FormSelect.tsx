import {
  FormGroup,
  FormLabel,
  InputGroup,
  FormControl,
  Form,
} from "react-bootstrap";
import { UseFormRegister, Path, FieldError } from "react-hook-form";
import ValidationInterface from "./ValidationInterface";

export default function FormSelect<T>(props: {
  register: UseFormRegister<T>;
  label: string;
  name: Path<T>;
  options: [string, string][];
  placeholder?: string;
  prependIcon?: JSX.Element | null;
  appendIcon?: JSX.Element | null;
  validation?: Partial<ValidationInterface<T>>;
  error?: FieldError | null;
  errorMessage?: string;
}) {
  const {
    register,
    label,
    name,
    options,
    prependIcon,
    appendIcon,
    validation,
    error,
    errorMessage,
  } = props;
  return (
    <FormGroup>
      <FormLabel>{label}</FormLabel>
      <InputGroup>
        {prependIcon != null && (
          <InputGroup.Text>{prependIcon}</InputGroup.Text>
        )}
        <Form.Select {...register(name, validation)} isInvalid={error != null}>
          {options.map(([value, message]) => (
            <option key={value} value={value}>
              {message}
            </option>
          ))}
        </Form.Select>
        {appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}
        <FormControl.Feedback type="invalid">
          {errorMessage}
        </FormControl.Feedback>
      </InputGroup>
    </FormGroup>
  );
}
