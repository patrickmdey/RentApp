import { FormGroup, FormLabel, InputGroup, FormControl } from "react-bootstrap";
import { Path, UseFormRegister } from "react-hook-form";

export default function FormInput<T>(props: {
  register: UseFormRegister<T>;
  label: string;
  name: Path<T>;
  type: string;
  placeholder?: string;
  prependIcon?: JSX.Element | string | null;
  appendIcon?: JSX.Element | null;
  validation?: any | null; //TODO: como le paso un {} que tiene props variables adentro ?
}) {
  const {
    register,
    label,
    name,
    type,
    placeholder,
    prependIcon,
    appendIcon,
    validation,
  } = props;
  return (
    <FormGroup>
      <FormLabel>{label}</FormLabel>
      <InputGroup>
        {prependIcon != null && (
          <InputGroup.Text>{prependIcon}</InputGroup.Text>
        )}
        <FormControl
          type={type}
          placeholder={placeholder}
          {...register(name, validation)}
        />
        {appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}
      </InputGroup>
    </FormGroup>
  );
}
