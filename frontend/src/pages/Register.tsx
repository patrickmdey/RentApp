import {Register as RegisterComponent} from "../components/Register";
import LogInComponent from "../components/LogIn";
import {Container} from "react-bootstrap";

export default function Register(){
    return (
        <Container style={{width:"50%"}} className=" min-height">
            <RegisterComponent />
        </Container>

    )
}