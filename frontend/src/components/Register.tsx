import {Button, Card, Form, FormControl, InputGroup, Row} from "react-bootstrap";
import {Controller, Path, useForm} from "react-hook-form";
import {strings} from "../i18n/i18n";
import {BsEyeSlash} from "react-icons/bs";

interface RegisterForm {
    firstName: string,
    lastName: string,
    email: string,
    location: string,
    password: string,
    confirmPassword: string,
    isRenter:string,
    data:any, // TODO: Figure out the image thingy

}

export  function Register(){

    const {control, handleSubmit} = useForm<RegisterForm>({
        defaultValues: {
            firstName: "",
            lastName: "",
            email: "",
            location: "",
            password: "",
            confirmPassword: "",
            isRenter:""
        }
    })
    const locationItems : [string,string][]= [["a","a"],["b","b"]]
    function onSubmit(data: RegisterForm){
        console.log(data);
        console.log(typeof data.data)
    }

    function Select(label: string, name: Path<RegisterForm>, options: [string, string][], prependIcon: any = null, appendIcon: any = null) {
        return (

            <Controller defaultValue="" name={name} control={control} render={({field}) =>
                <Form.Group className="mt-3 col-6">
                    <Form.Label className="font-weight-bold "> <b>{label}</b> </Form.Label>
                    <InputGroup>
                        {prependIcon != null && <InputGroup.Text>{prependIcon}</InputGroup.Text>}

                        <Form.Select {...field}>
                            {options.map(t => <option key={t[0]} value={t[0]} label={t[1]}/>)}
                        </Form.Select>
                        {appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}

                    </InputGroup>

                </Form.Group>
            }/>

        )
    }


    function TextBox(label: string, name: Path<RegisterForm>, placeholder: string, type: string = 'text', prependIcon: any = null, appendIcon: any = null) {
        return (
            <Controller name={name} control={control}
                        render={({field}) =>
                            <Form.Group className="mt-3 col-6">
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
        <Card className="shadow card-style create-card mx-3" >
            <Card.Body className="form-container">
            <Form onSubmit={handleSubmit(onSubmit)}>
                <Row>
                    <h3 className="fw-bold my-1"> Register</h3>
                </Row>
                <hr/>
                <Row>
                    {TextBox(strings.collection.register.firstName,"firstName",strings.collection.register.firstNamePlaceholder)}
                    {TextBox(strings.collection.register.lastName,"lastName",strings.collection.register.lastNamePlaceholder)}
                </Row>
                <Row>
                    {TextBox(strings.collection.register.email,"email",strings.collection.register.emailPlaceholder)}
                    {Select(strings.collection.register.location,"location",locationItems)}
                </Row>
                <Row>
                    {TextBox(strings.collection.register.password,"password",strings.collection.register.passwordPlaceholder,"password",null,<BsEyeSlash/>)}
                    {TextBox(strings.collection.register.confirmPassword,"confirmPassword",strings.collection.register.confirmPasswordPlaceholder,"password",null,<BsEyeSlash/>)}
                </Row>
                    <Controller name="isRenter" control={control}
                                render={({field}) =>
                                    <Form.Check type="checkbox"
                                                className="mt-3  my-2"

                                                label={strings.collection.register.isRenter} {...field}/>
                                }/>

                <Row>
                    <Controller name="data" control={control}
                                render={({field}) =>
                                    <Form.Group className="mt-3 mb-3 ">
                                        <Form.Label className="font-weight-bold"> <b>{strings.collection.register.image}</b> </Form.Label>
                                            <FormControl  onChange={(e) => console.log(e.target)}  type='file' />
                                    </Form.Group>
                                }/>
                </Row>

                <Row>
                    <Button type="submit" className=" rounded btn-block bg-color-action btn-dark">
                        {strings.collection.register.confirmButton}
                    </Button>
                </Row>
            </Form>
            </Card.Body>
        </Card>
    )
}