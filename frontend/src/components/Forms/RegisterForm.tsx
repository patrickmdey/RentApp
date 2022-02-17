import {Button, Card, Form, Row, Col} from "react-bootstrap";
import {useForm} from "react-hook-form";
import {strings} from "../../i18n/i18n";
import {BsEyeSlash} from "react-icons/bs";
import {useCreateUser} from "../../api/users/usersSlice";
import FormInput from "../FormInputs/FormInput";
import FormSelect from "../FormInputs/FormSelect";
import FormCheckbox from "../FormInputs/FormCheckbox";
import {useListLocations} from "../../api/locations/locationsSlice";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {setCredentials} from "../../api/auth/authSlice";
import {useAppDispatch} from "../../hooks";

interface RegisterForm {
    firstName: string;
    lastName: string;
    email: string;
    location: string;
    password: string;
    confirmPassword: string;
    isOwner: boolean;
    image: FileList;
}

const SELECT_LOCATION: [string, string] = [
    "",
    strings.collection.register.selectLocation,
];

export function RegisterForm() {
    const {
        register,
        handleSubmit,
        getValues,
        formState: {errors},
    } = useForm<RegisterForm>({
        defaultValues: {
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            confirmPassword: "",
            isOwner: false,
            image: undefined,
        },
    });
    const navigate = useNavigate();
    const dispatch = useAppDispatch();

    const {data: locations} = useListLocations();
    const [createUser, result] = useCreateUser();
    useEffect(() => {
        console.log(result.data);
        if (result.isSuccess) {
            dispatch(setCredentials({token: result.data, rememberMe: true}));
            navigate("/marketplace");
        }
    }, [result]);

    function onSubmit(data: RegisterForm) {
        createUser({...data, image: data.image[0]});
    }

    return (
        <Card className="shadow card-style create-card mx-3">
            <Card.Body className="form-container">
                <Form onSubmit={handleSubmit(onSubmit)}>
                    <Row>
                        <h3 className="fw-bold my-1">
                            {strings.collection.register.title}
                        </h3>
                    </Row>
                    <hr/>
                    <Row sm={1} md={2} className="g-3">
                        <Col>
                            <FormInput
                                register={register}
                                label={strings.collection.register.firstName}
                                name="firstName"
                                type="text"
                                placeholder={strings.collection.register.firstNamePlaceholder}
                                validation={{required: true, minLength: 3, maxLength: 20}}
                                error={errors.firstName}
                                errorMessage={strings.collection.register.errors.firstName}
                            />
                        </Col>
                        <Col>
                            <FormInput
                                register={register}
                                label={strings.collection.register.lastName}
                                name="lastName"
                                type="text"
                                placeholder={strings.collection.register.lastNamePlaceholder}
                                validation={{required: true, minLength: 3, maxLength: 20}}
                                error={errors.lastName}
                                errorMessage={strings.collection.register.errors.lastName}
                            />
                        </Col>
                        <Col md={12}>
                            <FormInput
                                register={register}
                                label={strings.collection.register.email}
                                name="email"
                                type="text"
                                placeholder={strings.collection.register.emailPlaceholder}
                                error={errors.email}
                                errorMessage={strings.collection.login.errors.email}
                                validation={{required: true, minLength: 3, maxLength: 320}}
                            />
                        </Col>
                        <Col>
                            <FormInput
                                register={register}
                                label={strings.collection.register.password}
                                name="password"
                                type="password"
                                placeholder={strings.collection.register.passwordPlaceholder}
                                appendIcon={<BsEyeSlash/>}
                                error={errors.password}
                                errorMessage={strings.collection.login.errors.password}
                                validation={{required: true, minLength: 8, maxLength: 20}}
                            />
                        </Col>
                        <Col>
                            <FormInput
                                register={register}
                                label={strings.collection.register.password}
                                name="confirmPassword"
                                type="password"
                                placeholder={strings.collection.register.passwordPlaceholder}
                                appendIcon={<BsEyeSlash/>}
                                validation={{
                                    required: true,
                                    minLength: 8,
                                    maxLength: 20,
                                    validate: () => getValues('password') === getValues('confirmPassword')
                                }}
                                error={errors.confirmPassword}
                                errorMessage={strings.collection.login.errors.password}
                            />
                        </Col>
                        <Col>
                            <FormSelect
                                register={register}
                                name="location"
                                label={strings.collection.register.location}
                                options={
                                    locations
                                        ? [
                                            SELECT_LOCATION,
                                            ...locations.map(
                                                ({id, name}) => [id, name] as [string, string]
                                            ),
                                        ]
                                        : []
                                }
                                validation={{required: true}}
                                error={errors.location}
                                errorMessage={strings.collection.register.errors.location}
                            />
                        </Col>
                        <Col>
                            <FormInput
                                register={register}
                                label={strings.collection.register.image}
                                name="image"
                                type="file"
                                placeholder={strings.collection.register.image}
                                validation={{required: true}}
                                error={errors.image}
                                errorMessage={strings.collection.register.errors.image}
                            />
                        </Col>
                        <Col md={12}>
                            <FormCheckbox
                                register={register}
                                name="isOwner"
                                label={strings.collection.register.isRenter}
                            />
                        </Col>
                        <Col md={12}>
                            <Row>
                                <Button
                                    type="submit"
                                    className="rounded bg-color-action btn-dark"
                                >
                                    {strings.collection.register.confirmButton}
                                </Button>
                            </Row>
                        </Col>
                    </Row>
                </Form>
            </Card.Body>
        </Card>
    );
}
