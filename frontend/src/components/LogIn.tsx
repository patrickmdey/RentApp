import {Button, Card, Container, Form, FormControl, InputGroup, Row} from "react-bootstrap";
import {strings} from "../i18n/i18n";
import {Controller, Path, useForm} from "react-hook-form";
import {BsEye} from "react-icons/bs";
import {useState} from "react";
import {LinkContainer} from "react-router-bootstrap";
import {useLoginMutation} from "../features/api/authentication/authenticationSlice";

interface LogInForm {
    email: string,
    password: string,
    rememberMe: string
}

export default function LogInComponent() {
    const [state, setState] = useState({showPassword: false})
    const [login, result] = useLoginMutation();
    function updatePasswordType() {
        state.showPassword = !state.showPassword;
    }

    const {control, handleSubmit} = useForm<LogInForm>({
        defaultValues: {email: "", password: ""}
    })

    function onSubmit(data: LogInForm){

    }

    function TextBox(label: string, name: Path<LogInForm>, placeholder: string, type: string, prependIcon: any = null, appendIcon: any = null) {
        return (
            <Controller name={name} control={control}
                        render={({field}) =>
                            <Form.Group className="mt-3 ">
                                <Form.Label className="font-weight-bold"> <b>{label}</b> </Form.Label>
                                <InputGroup>
                                    {prependIcon != null && <InputGroup.Text>{prependIcon}</InputGroup.Text>}
                                    <FormControl placeholder={placeholder} type={type} {...field} />
                                    {appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}
                                </InputGroup>
                            </Form.Group>
                        }/>
        )
    }

    return (
        <Card className="shadow card-style create-card mx-3">
            <Form onSubmit={handleSubmit(onSubmit)}>
                <Container>
                    <h3 className="fw-bold my-1">{strings.collection.login.title}</h3>
                    <Row>
                        {TextBox(strings.collection.login.email, "email", strings.collection.login.emailPlaceholder, "text")}
                    </Row>
                    <Row>
                        {TextBox(strings.collection.login.password, "password", strings.collection.login.passwordPlaceholder, state.showPassword ? "text" : "password", null,
                            <span onClick={updatePasswordType}>
                                <BsEye/>
                            </span>)}
                    </Row>
                        <Controller name="rememberMe" control={control}
                                    render={({field}) =>
                                        <Form.Check type="checkbox"
                                                    className="mt-3 --bs-gutter-x"

                                                    label={strings.collection.login.rememberMe} {...field}/>
                                    }/>
                        <Row>
                        <Button className="btn-block bg-color-action btn-dark mt-3 mb-2">
                            {strings.collection.login.loginButton}
                        </Button>
                        </Row>
                        <Row>
                        <LinkContainer to="/register">
                            <button  className="color-action btn">
                                {strings.collection.login.signupButton}
                            </button>
                        </LinkContainer>
                    </Row>
                </Container>
            </Form>
        </Card>
    )
}